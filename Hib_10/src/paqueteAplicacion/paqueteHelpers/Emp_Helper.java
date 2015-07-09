package paqueteAplicacion.paqueteHelpers;

import paqueteAplicacion.generadoHibernate.Dept;
import paqueteAplicacion.generadoHibernate.Emp;
import paqueteAplicacion.paqueteUtil.HibernateUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class Emp_Helper {
    Session sesion = null;

    public Emp_Helper() {
    	this.sesion = HibernateUtil.getSessionFactory().openSession();
        // this.sesion = HibernateUtil.getSessionFactory().getCurrentSession();
   }
    
    public Emp devuelveEmpleadoPorId(short empno) {
        List<Emp> listaEmpleados = new <Emp>ArrayList();
		Transaction tx = null;
		try {
			tx = sesion.beginTransaction();
			String consulta = "FROM Emp e WHERE e.empno = " + empno;
            // Emp elEmple = sesion.load(Emp.class, empno);
			Query q = sesion.createQuery(consulta);
            listaEmpleados = (List<Emp>) q.list();
            // Los objetos dentro de Emp (Dept, Emp_Jefe, Emps_Subords) se inicializan en modo lazy,
            // ie, se inicializan bajo demanda en una sesión no cerrada,
            // ie, hay que inicializarlos todos ellos manualmente en un bucle tras la recuoeración desde la BD
            for (Emp emple : listaEmpleados) {
                Hibernate.initialize(emple.getEmp());
                Hibernate.initialize(emple.getEmps());
                Hibernate.initialize(emple.getDept());
            }
            tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
		      tx.rollback();
		    }		
		    throw e;
		} finally {
			sesion.flush();
			sesion.close(); 
		}
		return (Emp)listaEmpleados.get(0);
    }
	
	
	
	// Crear un empleado
	public Short aniadeEmpleado(Emp elEmpleado) {
		this.sesion = HibernateUtil.getSessionFactory().openSession();
		Short employeeID = null;
		Transaction tx = null;
		try {
			tx = sesion.beginTransaction();
		    // Emp employee = new Employee(fname, lname, salary);
		    employeeID = (Short) sesion.save(elEmpleado); 
		    tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
		      tx.rollback();
		    }		
		    e.printStackTrace(); 
		} finally {
			// sesion.flush();
			// sesion.close(); 
		}
		return employeeID;
	}
		  
	// Obtener todos los empleados
	public List<Emp> devuelveTodosEmpleados() {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    // se instancia una lista de empleados vacía
	    List<Emp> listaEmpleados = new ArrayList<Emp>();
	    try {
	      tx = sesion.beginTransaction();
	      listaEmpleados = sesion.createQuery("FROM Emp").list();
          // Los objetos dentro de Emp (Dept, Emp_Jefe, Emps_Subords) se inicializan en modo lazy,
          // ie, se inicializan bajo demanda en una sesión no cerrada,
          // ie, hay que inicializarlos todos ellos manualmente en un bucle tras la recuoeración desde la BD
          for (Emp emple : listaEmpleados) {
              Hibernate.initialize(emple.getEmp());
              Hibernate.initialize(emple.getEmps());
              Hibernate.initialize(emple.getDept());
          }
	      tx.commit();
	    } catch (HibernateException e) {
	      if (tx != null) {
		    tx.rollback();
		  }	
	      e.printStackTrace(); 
	    } finally {
	    	sesion.flush();
	    	sesion.close(); 
	    }
	    return listaEmpleados;
	  }
	
	
	// Obtener los empleados que cumplan unos criterios
	public List<Emp> devuelveEmpleadosParametrizado(String patronEname, BigInteger minSal, BigInteger maxSal, 
													Date minHiredate, Date maxHiredate, List listaDeparts) {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    // se instancia una lista de empleados vacía
	    List<Emp> listaEmpleados = new ArrayList<Emp>();
	    try {
	      tx = sesion.beginTransaction();
	      String consultaHQL = "FROM Emp";
	      int numeroCondiciones = 0;
	      List<String> listaCondiciones = new ArrayList();

	      // Construcción de las condiciones
	      if (patronEname != "") {
	    	  listaCondiciones.add("e.ename LIKE '%:patronEname%'");
	    	  // query.setParameter("patronEname", patronEname);
	      }
	      // Construcción de las condiciones
	      if (minSal != null) {    
	    	  listaCondiciones.add("e.minSal >= :minSal");
	    	  // query.setParameter("minSal", minSal);
	      }
	      if (maxSal != null)  {   
	    	  listaCondiciones.add("e.maxSal <= :maxSal");
	    	  // query.setParameter("maxSal", maxSal);
	      }	

		if (minHiredate != null)  {   
	    	  listaCondiciones.add("e.minHiredate >= :minHiredate");
	    	  // query.setParameter("maxSal", maxSal);
	      }	

		if (maxHiredate != null)  {   
	    	  listaCondiciones.add("e.maxHiredate <= :maxHiredate");
	    	  // query.setParameter("maxSal", maxSal);
	      }	
		
		int tamanioListaDeparts = listaDeparts.size(); 
		if (tamanioListaDeparts > 0) {
			  String estaCondicion = "e.depts.deptno IN (";
			  for (int i = 0; i < tamanioListaDeparts; i++) {
				  Dept unDept = (Dept) listaDeparts.get(i);
				  estaCondicion += "" + unDept.getDeptno() + ", ";
			  }
			  estaCondicion.substring(1, estaCondicion.length()-1);  // se le quita la última coma
			  estaCondicion += ")";
			  listaCondiciones.add(estaCondicion);
		}
	    
		if (listaCondiciones.size() > 0) {
			consultaHQL += " WHERE ";
	    	for (Iterator iterator = listaCondiciones.iterator(); iterator.hasNext();) {
				String unaCondicion = (String) iterator.next();
				consultaHQL += unaCondicion + " AND ";
	    	}
	    	consultaHQL.substring(1, consultaHQL.length()-5);  // se le quita el último AND
		}
	      
			Query consulta = sesion.createQuery(consultaHQL);
			// consulta.setParameter("salary", 1000);
			// consulta.setParameter("employee_id", 10);
			listaEmpleados = consulta.list();

			//      listaEmpleados = sesion.createQuery(consultaHQL).list();
          
			// Los objetos dentro de Emp (Dept, Emp_Jefe, Emps_Subords) se inicializan en modo lazy,
          // ie, se inicializan bajo demanda en una sesión no cerrada,
          // ie, hay que inicializarlos todos ellos manualmente en un bucle tras la recuoeración desde la BD
          for (Emp emple : listaEmpleados) {
              Hibernate.initialize(emple.getEmp());
              Hibernate.initialize(emple.getEmps());
              Hibernate.initialize(emple.getDept());
          }
	      tx.commit();
	    } catch (HibernateException e) {
	      if (tx != null) {
		    tx.rollback();
		  }	
	      e.printStackTrace(); 
	    } finally {
	    	sesion.flush();
	    	sesion.close(); 
	    }
	    return listaEmpleados;
	  }
	
		  
	// Actualización del salario de un empleado
	public void modificaSalarioEmpleado(Integer EmployeeID, BigDecimal salario) {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try {
	      tx = sesion.beginTransaction();
	      Emp elEmpleado = (Emp) sesion.get(Emp.class, EmployeeID); 
	      elEmpleado.setSal(salario);
	      sesion.update(elEmpleado); 
	      tx.commit();
	    } catch (HibernateException e) {
	      if (tx != null) {
		    tx.rollback();
		  }	
	      e.printStackTrace(); 
	    } finally {
	    	sesion.flush();
	    	sesion.close(); 
	    }
	  }
		  
	  // Eliminar un empleado
	  public void eliminaEmpleado(short empno) {
		  // Session sesion = HibernateUtil.getSessionFactory().openSession();
		  Transaction tx = null;
		  try {
		      tx = sesion.beginTransaction();
		      Emp emple = (Emp) sesion.get(Emp.class, empno); 
		      sesion.delete(emple);		      
		      tx.commit();
		  } catch (HibernateException e) {
		      if (tx != null) {
		    	  tx.rollback();
			  }	
		      e.printStackTrace(); 
		  } finally {
			  sesion.flush();
		      sesion.close(); 
		  }
	  }
}	


