package paqueteAplicacion.paqueteServlets;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@WebServlet("/Valida_AniadeEmpleado")
public class Valida_AniadeEmpleado extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  Map<String, String> errores = new HashMap<String, String>();
		  
		  // se reciben los parámetros
		  String empno = request.getParameter("empno");
		  String ename = request.getParameter("ename");
		  String job = request.getParameter("job");
		  String mgr = request.getParameter("mgr");
		  String hiredate = request.getParameter("hiredate");
		  String sal = request.getParameter("sal");
		  String comm = request.getParameter("comm");
		  String deptno = request.getParameter("deptno");
		  
		  
		  // verifica el apellido
		  if (ename.isEmpty()) {  // se ha recibido algún parámetro
			  errores.put("ename", "El apellido no puede estar vacío");
		  }

		  // verifica el salario
		  if (!sal.isEmpty()) {  // se ha recibido algún parámetro
			  try {
				  Float sal_OK = Float.parseFloat(sal);
			  } catch (NumberFormatException e) {
				  errores.put("sal", "El salario no tiene un formato correcto. Ej válido: 1825.73");
			  }
		  }
		  
		  // verifica la fecha mínima de contratación
		  if (!hiredate.isEmpty()) {  // se ha recibido algún parámetro
			  try {
				  DateTimeFormatter formateador =  DateTimeFormat.forPattern("yyyy-MM-dd").withOffsetParsed();
				  DateTime dateTime = formateador.parseDateTime(hiredate);
				  GregorianCalendar cal = dateTime.toGregorianCalendar();
			  } catch (Exception e) {
				  errores.put("hiredate", "La fecha de contratación [<b>" + hiredate  + "</b>] no tiene un formato correcto. Ej válido: 2000-07-22");
			  }
		  }	


		  // Repeat for all parameters.

		  if (errores.isEmpty()) {  // Sin errores de lógica de negocio
		        response.sendRedirect("http://amtrak.com");
		  } else {  // Put errors in request scope and forward back to JSP.
		        request.setAttribute("errores", errores);
		        request.getRequestDispatcher("Vista_AniadeEmpleado_get.jsp").forward(request, response);
		    }
	}

}
