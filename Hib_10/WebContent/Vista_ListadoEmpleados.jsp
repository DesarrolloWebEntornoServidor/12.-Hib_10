<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="paqueteAplicacion.generadoHibernate.*, java.util.List, java.util.Iterator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	List <Emp> listaEmples = (List<Emp>) request.getAttribute("listaEmpleados");
	List <Dept> listaDepts = (List<Dept>) request.getAttribute("listaDepartamentos");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Visualización de empleado</title>
</head>
<body>
<h3>Listado de empleados</h3>
<a href="AniadeEmpleado">Añade empleado</a>
<fieldset>
<legend>Parámetros de búsqueda</legend>
<form name="fConsulta" action="ListaEmpleados" method="post">
<div id="avisos" style="color:red;">
<%
	List<String> avisos = (List<String>) request.getAttribute("avisos"); 
	if ( (avisos != null) && (!avisos.isEmpty()) ) {
		out.println(avisos);		
	}
%>
</div>
Ename <input type="text" name="patronEname" />
Dname <select name="deptnos" multiple="multiple" size="2">
<%
	if (listaDepts.size() > 0) {
    	for (Iterator iterator = listaDepts.iterator(); iterator.hasNext();) {
			Dept unDepartamento = (Dept) iterator.next();
    		out.println("<option value='" + unDepartamento.getDeptno() + "'>" + unDepartamento.getDname() + "</option>");
    	}
	}
%>
</select>  <br />

Sal. min <input type="text" name="minSal" value="0.0" /> 
Sal. max <input type="text" name="maxSal" /> <br />
Hiredate min. <input type="text" name="minHiredate" />
Hiredate max. <input type="text" name="maxHiredate" /> <br />
<input type="submit" name="Lanzar consulta" />

</form>
</fieldset>
<br />
<fieldset>
<legend>Resultado de la consulta</legend>
<%

  	if (listaEmples.size()>0) {
  		out.println("<table border='1'>");
  		out.println("<tr'>");
  		out.println("  <th>Ename</th>");
  		out.println("  <th>Job</th>");
  		out.println("  <th>Mgr</th>");
  		out.println("  <th>Hiredate</th>");
  		out.println("  <th>Sal</th>");
  		out.println("  <th>Dept</th>");
  		out.println("  <th>MODIFICAR</th>");
  		out.println("  <th>ELIMINAR</th>");
  		out.println("</tr'>");
    	for (Iterator iterator = listaEmples.iterator(); iterator.hasNext();) {
			Emp unEmpleado = (Emp) iterator.next();
    		out.println("<tr>");
      		out.println("  <td>" + unEmpleado.getEname() + "</td>");
      		out.println("  <td>" + unEmpleado.getJob() + "</td>");

			// se comprueba que el empleado tenga jefe
      		if (unEmpleado.getEmp() != null) {
      			out.println("  <td>" + unEmpleado.getEmp().getEname() + "</td>");
      		} else {
      			out.println("  <td>&nbsp;</td>");
      		}
      		out.println("  <td>" + unEmpleado.getHiredate() + "</td>");
			out.println("  <td>" + unEmpleado.getSal() + "</td>");
			
			// se comprueba que el empleado tenga departamento
			if (unEmpleado.getDept() != null) {
      			out.println("  <td>" + unEmpleado.getDept().getDname() + "</td>");
      		} else {
      			out.println("  <td>&nbsp;</td>");
      		}
			
			out.print("  <td><a href='ModificaEmpleado?empno=" + unEmpleado.getEmpno() + "'>");
			out.println("<img src='imgs/edit-icon.png' width='20' height='20' /></a></td>");
			out.print("  <td><a href='EliminaEmpleado?empno=" + unEmpleado.getEmpno() + "'>");
			out.println("<img src='imgs/delete-icon.png' width='20' height='20' /></a></td>");
    		out.println("</tr'>");
    	}
  		out.print("</table>");
  	}
%>
</fieldset>

</body>
</html>