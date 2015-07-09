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

@WebServlet("/EliminaEmpleado")
public class EliminaEmpleado extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		short empno = Short.parseShort(request.getParameter("empno"));
		Emp_Helper eH = new Emp_Helper();

		try {
			eH.eliminaEmpleado(empno);
			out.println("El empleado de ID " + empno + " se eliminó correctamente");
			out.println("<br />");
			out.println("<a href='ListaEmpleados'>Volver al listado de empleados</a>");
		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
