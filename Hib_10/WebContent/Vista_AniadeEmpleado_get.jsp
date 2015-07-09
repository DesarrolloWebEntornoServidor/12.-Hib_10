<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="paqueteAplicacion.generadoHibernate.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modificación de empleado</title>
</head>
<body>
<%
	List <Emp> listaEmples = null;
	List <Dept> listaDepts = null;
	Map<String, String> errores = null;
	try {
		listaEmples = (List<Emp>) request.getAttribute("listaEmpleados");
		listaDepts = (List<Dept>) request.getAttribute("listaDepartamentos");
		errores = (Map<String, String>) request.getAttribute("errores");
	} catch (Exception e) {
		out.println("Error en la recepción de atributos");
	}
%>
<h3>Datos del empleado</h3>
<form action="AniadeEmpleado" method="post">

Empno <input type="text" name="empno" value="" /> <span style="color: red;">${errores.empno}</span> <br />
Ename <input type="text" name="ename" value="" /> <span style="color: red;">${errores.ename}</span> <br />
Job  <input type="text" name="job" value="" /> <span style="color: red;">${errores.job}</span> <br />
Hiredate <input type="text" name="hiredate" value="" />  <span style="color: red;">${errores.hiredate}</span> <br />
Sal <input type="text" name="sal" value="" />  <span style="color: red;">${errores.sal}</span> <br />
Comm <input type="text" name="comm" value="" />  <span style="color: red;">${errores.comm}</span> <br />
  
Mgr <select name="mgr">
  <option value="0" selected="selected">Sin jefe</option>
<%
	if ( (listaEmples!=null) && (!listaEmples.isEmpty()) ) {
    	for (Iterator iterator = listaEmples.iterator(); iterator.hasNext();) {
			Emp jefe = (Emp) iterator.next();
			out.println("<option value='" + jefe.getEmpno() + "'>" + jefe.getEname() + "</option>");
    	}
  	}
%>
</select>  <span style="color: red;">${errores.mgr}</span> <br />
 
Dname <select name="deptno">
<%
	if ( (listaDepts!=null) && (!listaDepts.isEmpty()) ) {
    	for (Iterator iterator = listaDepts.iterator(); iterator.hasNext();) {
			Dept dep = (Dept) iterator.next();
			out.println("<option value='" + dep.getDeptno() + "'>" + dep.getDname() + "</option>");
    	}
  	}
%>
</select>  <span style="color: red;">${errores.deptno}</span> <br />

<input type="submit" value="Añade empleado" />
</form>


</body>
</html>