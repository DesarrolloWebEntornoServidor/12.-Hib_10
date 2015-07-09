package paqueteAplicacion.paqueteServlets;
import paqueteAplicacion.paqueteHelpers.*;
import paqueteAplicacion.generadoHibernate.*;

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

@WebServlet("/ModificaEmpleado")
public class ModificaEmpleado extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		short empno = Short.parseShort(request.getParameter("empno"));
		Emp_Helper eH = new Emp_Helper();
		Dept_Helper dH = new Dept_Helper();
		try {
			Emp emple = eH.devuelveEmpleadoPorId(empno);
			List<Emp> listaEmpleados = eH.devuelveTodosEmpleados();
			List<Dept> listaDepartamentos = dH.devuelveTodosDepartamentos();
			
	    	request.setAttribute("elEmpleado", emple);
	    	request.setAttribute("listaEmpleados", listaEmpleados);
	    	request.setAttribute("listaDepartamentos", listaDepartamentos);
	    	
	    	String vista = "/Vista_ModificaEmpleado_get.jsp";
	    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(vista);
	   		dispatcher.forward(request, response);
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			e.printStackTrace(out);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
