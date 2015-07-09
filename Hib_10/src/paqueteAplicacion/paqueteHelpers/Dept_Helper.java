package paqueteAplicacion.paqueteHelpers;

import paqueteAplicacion.generadoHibernate.Dept;
import paqueteAplicacion.paqueteUtil.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class Dept_Helper {
    Session sesion = null;

    public Dept_Helper() {
    	this.sesion = HibernateUtil.getSessionFactory().openSession();
        // this.sesion = HibernateUtil.getSessionFactory().getCurrentSession();
   }
    
    public Dept devuelveDepartamentoPorId(byte deptno) {
        // List<Dept> listaEmpleados = new <Emp>ArrayList();
		Transaction tx = null;
		Dept elDept = null;
		try {
			tx = sesion.beginTransaction();
            elDept = (Dept) sesion.get(Dept.class, deptno);
            if (elDept != null) {
            	Hibernate.initialize(elDept.getEmps());
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
		return elDept;
    }
	
	
	
	// Crear un empleado
/*	public Integer aniadeEmpleado(Emp elEmpleado) {
		// Session sesion = HibernateUtil.getSessionFactory().openSession();
		Integer employeeID = null;
		Transaction tx = null;
		try {
			tx = sesion.beginTransaction();
		    // Emp employee = new Employee(fname, lname, salary);
		    employeeID = (Integer) sesion.save(elEmpleado); 
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
		return employeeID;
	}
*/
    
	// Obtener todos los departamentos
	public List<Dept> devuelveTodosDepartamentos() {
		// Session sesion = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    // se instancia una lista de empleados vacía
	    List<Dept> listaDepartamentos = new ArrayList<Dept>();
	    try {
	      tx = sesion.beginTransaction();
	      listaDepartamentos = sesion.createQuery("FROM Dept").list();
          // Los objetos dentro de Emp (Dept, Emp_Jefe, Emps_Subords) se inicializan en modo lazy,
          // ie, se inicializan bajo demanda en una sesión no cerrada,
          // ie, hay que inicializarlos todos ellos manualmente en un bucle tras la recuoeración desde la BD
          for (Dept depart : listaDepartamentos) {
              Hibernate.initialize(depart.getEmps());
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
	    return listaDepartamentos;
	  }
		  
	// Actualización del salario de un empleado
/*
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
*/
		  
	  // Eliminar un empleado
/*
	public void eliminaEmpleado(Integer EmployeeID) {
		  Session sesion = HibernateUtil.getSessionFactory().openSession();
		  Transaction tx = null;
		  try {
		      tx = sesion.beginTransaction();
		      Emp employee = (Emp) sesion.get(Emp.class, EmployeeID); 
		      sesion.delete(employee); 
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
	
*/	
}