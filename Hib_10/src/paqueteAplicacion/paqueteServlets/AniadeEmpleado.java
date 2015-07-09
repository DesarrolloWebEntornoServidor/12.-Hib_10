package paqueteAplicacion.paqueteServlets;
import paqueteAplicacion.paqueteHelpers.*;
import paqueteAplicacion.LogicaNegocio.Logica;
import paqueteAplicacion.generadoHibernate.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AniadeEmpleado")
public class AniadeEmpleado extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Emp_Helper eH = new Emp_Helper();
		Dept_Helper dH = new Dept_Helper();
		try {
			List<Emp> listaEmpleados = eH.devuelveTodosEmpleados();
			List<Dept> listaDepartamentos = dH.devuelveTodosDepartamentos();
			
	    	request.setAttribute("listaEmpleados", listaEmpleados);
	    	request.setAttribute("listaDepartamentos", listaDepartamentos);
	    	
	    	String vista = "/Vista_AniadeEmpleado_get.jsp";
	    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(vista);
	   		dispatcher.forward(request, response);
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			e.printStackTrace(out);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// se reciben los parámetros
		String empno = request.getParameter("empno");
		String ename = request.getParameter("ename");
		String job = request.getParameter("job");
		String mgr = request.getParameter("mgr");
		String hiredate = request.getParameter("hiredate");
		String sal = request.getParameter("sal");
		String comm = request.getParameter("comm");
		String deptno = request.getParameter("deptno");
		List<String> avisos = Logica.verificaAltaEmpleado(empno, ename, job, mgr, hiredate, sal, comm, deptno);

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		if (!avisos.isEmpty()) {  // ha habido avisos de errores de negocio => no se puede lanzar la consulta
			out.println("Se han producido estos avisos, NO SE PUEDE LANZAR LA CONSULTA");
			out.println("<br />");
			out.println(avisos);
		} else {  // no ha habido avisos de errores de negocio => se puede lanzar la consulta
			// out.println("No se han producido estos avisos, SE PUEDE LANZAR LA CONSULTA");
			Emp_Helper eH = new Emp_Helper();
			Dept_Helper dH = new Dept_Helper();
			Emp jefe = null;
			if (Short.parseShort(mgr) > 0) {  // se ha recibido un código numérico de jefe
				jefe = eH.devuelveEmpleadoPorId(Short.parseShort(mgr));
			}
			Dept elDept = dH.devuelveDepartamentoPorId(Byte.parseByte(deptno));
			
			// Creación de un DecimalFormat que se ajuste al patrón deseado para el BigDecimal
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			// symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);

			// parsing del String
			
			BigDecimal sal_OK;
			try {
				sal_OK = (BigDecimal) decimalFormat.parse(sal);
			} catch (ParseException e) {
				sal_OK = new BigDecimal(0);
			}
			BigDecimal comm_OK;
			try {
				comm_OK = (BigDecimal) decimalFormat.parse(comm);
			} catch (ParseException e) {
				comm_OK = new BigDecimal(0);
			}
			
			// construcción del conjunto de subordinados
			Set<Emp> emps = new HashSet<Emp>();
// se añade el emplesdo con la fecha de contratación vacía
			Emp elEmpleado = new Emp(Short.parseShort(empno), jefe, elDept, ename, job, new Date(), sal_OK, comm_OK, emps);
	
			Short idEmpleadoGrabado = eH.aniadeEmpleado(elEmpleado);
			if (idEmpleadoGrabado != null) {
				out.println("Empleado con ID " + idEmpleadoGrabado + " correctamente añadido" + "<br />");
			} else {
				out.println("ERROR: No se ha podido dar de alta el empleado" + "<br />");				
			}
			out.println("<a href='ListaEmpleados'>Listado de empleados</a>");
			
		}

	}

}
