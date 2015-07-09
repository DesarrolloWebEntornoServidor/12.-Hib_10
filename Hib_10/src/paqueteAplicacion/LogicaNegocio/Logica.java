package paqueteAplicacion.LogicaNegocio;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Logica {

	public static List<String> verificaConsultaEmpleados(String patronEname, String minSal, String maxSal, 
											String minHiredate, String maxHiredate, String[] listaCodigosDeparts) {
		List<String> avisos = new ArrayList();
		
		// verifica el salario m�nimo
		if (minSal != "") {  // se ha recibido alg�n par�metro
			try {
				Float minSal_OK = Float.parseFloat(minSal);
			} catch (NumberFormatException e) {
				avisos.add("El salario m�nimo no tiene un formato correcto. Ej v�lido: 1825.73");
			}
		}

		// verifica la fecha m�nima de contrataci�n
		if (minHiredate != "") {  // se ha recibido alg�n par�metro
			try {
				DateTimeFormatter formateador =  DateTimeFormat.forPattern("yyyy-MM-dd").withOffsetParsed();
				DateTime dateTime = formateador.parseDateTime(minHiredate);
				GregorianCalendar cal = dateTime.toGregorianCalendar();
			} catch (IllegalArgumentException e) {
				avisos.add("La fecha m�nima de contrataci�n no tiene un formato correcto. Ej v�lido: 2000-07-22");
			}
/*
				DateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd"); 
				Date minHiredate_OK = null;
			try {
				minHiredate_OK = sdf.parse(minHiredate);
				if (minHiredate_OK == null) {
					avisos += "La fecha m�nima de contrataci�n no tiene un formato correcto. Ej v�lido: 2000-07-22";
				}	
			} catch (ParseException e) {
				avisos += "La fecha m�nima de contrataci�n no tiene un formato correcto. Ej v�lido: 2000-07-22";
			}

*/
		}	
		
		return avisos;
	}
	
	public static List<String> verificaAltaEmpleado(String empno, String ename, String job, String mgr, 
													String hiredate, String sal, String comm, String deptno) {
		List<String> avisos = new ArrayList();
		
		// verifica el salario m�nimo
		if (sal != "") {  // se ha recibido alg�n par�metro
			try {
				Float sal_OK = Float.parseFloat(sal);
			} catch (NumberFormatException e) {
				avisos.add("El salario no tiene un formato correcto. Ej v�lido: 1825.73");
			}
		}

		// verifica la fecha m�nima de contrataci�n
		if (hiredate != "") {  // se ha recibido alg�n par�metro
			try {
				DateTimeFormatter formateador =  DateTimeFormat.forPattern("yyyy-MM-dd").withOffsetParsed();
				DateTime dateTime = formateador.parseDateTime(hiredate);
				GregorianCalendar cal = dateTime.toGregorianCalendar();
			} catch (IllegalArgumentException e) {
				avisos.add("La fecha de contrataci�n [<b>" + hiredate  + "</b>] no tiene un formato correcto. Ej v�lido: 2000-07-22");
			} catch (Exception e) {
				avisos.add("La fecha de contrataci�n [<b>" + hiredate  + "</b>] no tiene un formato correcto. Ej v�lido: 2000-07-22");
				avisos.add(e.getMessage());
				
			}
		}	
		
		return avisos;
	}

}
