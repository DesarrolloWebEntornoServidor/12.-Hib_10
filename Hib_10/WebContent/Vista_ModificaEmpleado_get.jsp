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
	Emp emple = (Emp) request.getAttribute("elEmpleado"); 
	List <Emp> listaEmples = (List<Emp>) request.getAttribute("listaEmpleados");
	List <Dept> listaDepts = (List<Dept>) request.getAttribute("listaDepartamentos");
	String comm = "";
	if (emple.getComm()!=null) {
		comm = emple.getComm().toString();
	}	
%>
<h3>Datos del empleado</h3>
<form action="ModificaEmpleado" method="post">
Empno <input type="text" name="empno" value="<%= emple.getEmpno() %>" readonly="readonly"  /> <br />
Ename <input type="text" name="ename" value="<%= emple.getEname() %>" /> <br />
Job  <input type="text" name="job" value="<%= emple.getJob() %>" /> <br />
Hiredate <input type="text" name="hirdate" value="<%= emple.getHiredate() %>" /> <br />
Sal <input type="text" name="sal" value="<%= emple.getSal() %>" /> <br />
Comm <input type="text" name="comm" value="<%= comm %>" /> <br />
  
Mgr <select name="mgr">
<%
	if (listaEmples.size()>0) {
    	for (Iterator iterator = listaEmples.iterator(); iterator.hasNext();) {
			Emp jefe = (Emp) iterator.next();
			if (emple.getEmp().getEmpno() == jefe.getEmpno()) {
	    		out.println("<option value='" + jefe.getEmpno() + "' selected='selected'>" + jefe.getEname() + "</option>");
			} else {
				out.println("<option value='" + jefe.getEmpno() + "'>" + jefe.getEname() + "</option>");
			}

    	}
  	}
%>
</select> <br />

<%-- 
Deptno <input type="text" name="deptno" value="<%= emple.getDept().getDeptno() %>" /> <br />
Dname <input type="text" name="dname" value="<%= emple.getDept().getDname() %>" /> <br />
--%>
 
Dname <select name="dname">
<%
	if (listaDepts.size()>0) {
    	for (Iterator iterator = listaDepts.iterator(); iterator.hasNext();) {
			Dept dep = (Dept) iterator.next();
			if (emple.getDept().getDeptno() == dep.getDeptno()) {
	    		out.println("<option value='" + dep.getDeptno() + "' selected='selected'>" + dep.getDname() + "</option>");
			} else {
				out.println("<option value='" + dep.getDeptno() + "'>" + dep.getDname() + "</option>");
			}

    	}
  	}
%>
</select> <br />

<input type="submit" value="Modifica empleado" />
</form>


</body>
</html>