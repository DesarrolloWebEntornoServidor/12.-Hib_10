package paqueteAplicacion.paqueteServlets;
import paqueteAplicacion.paqueteHelpers.*;
import paqueteAplicacion.generadoHibernate.*;
import paqueteAplicacion.LogicaNegocio.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ListaEmpleados")
public class ListaEmpleados extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Emp_Helper eH = new Emp_Helper();
		List<Emp> listaEmpleados = eH.devuelveTodosEmpleados();
		Dept_Helper dH = new Dept_Helper();
		List<Dept> listaDepartamentos = dH.devuelveTodosDepartamentos();
		
    	request.setAttribute("listaEmpleados", listaEmpleados);
    	request.setAttribute("listaDepartamentos", listaDepartamentos);
    	
    	String vista = "/Vista_ListadoEmpleados.jsp";
    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(vista);
   		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// se reciben los parámetros
		String patronEname = request.getParameter("ename");
		String minSal = request.getParameter("minSal");
		String maxSal = request.getParameter("maxSal");
		String minHiredate = request.getParameter("minHiredate");
		String maxHiredate = request.getParameter("maxHiredate");
		String[] codigosDepartamento = request.getParameterValues("deptnos");
		List<String> avisos = Logica.verificaConsultaEmpleados(patronEname, minSal, maxSal, minHiredate, maxHiredate, codigosDepartamento);
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		int numeroAvisos = avisos.size(); 
		if (numeroAvisos > 0) {  // ha habido avisos de errores de negocio => no se puede lanzar la consulta
			out.println("Se han producido " + numeroAvisos + " avisos, NO SE PUEDE LANZAR LA CONSULTA");
			out.println("<br />");
			for (String aviso: avisos) {
				out.println(aviso);
			}
		} else {  // no ha habido avisos de errores de negocio => se puede lanzar la consulta
			out.println("No se han producido avisos, SE PUEDE LANZAR LA CONSULTA");
			// aquí vendría la consulta
		}
		
	}

}
