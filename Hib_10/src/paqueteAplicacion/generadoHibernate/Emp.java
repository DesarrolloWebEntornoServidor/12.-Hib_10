package paqueteAplicacion.generadoHibernate;
// Generated 12-mar-2014 10:52:53 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Emp generated by hbm2java
 */
@Entity
@Table(name = "emp", catalog = "scott")
public class Emp implements java.io.Serializable {

	private short empno;
	private Emp emp;
	private Dept dept;
	private String ename;
	private String job;
	private Date hiredate;
	private BigDecimal sal;
	private BigDecimal comm;
	private Set<Emp> emps = new HashSet<Emp>(0);

	public Emp() {
	}

	public Emp(short empno) {
		this.empno = empno;
	}

	public Emp(short empno, Emp emp, Dept dept, String ename, String job,
			Date hiredate, BigDecimal sal, BigDecimal comm, Set<Emp> emps) {
		this.empno = empno;
		this.emp = emp;
		this.dept = dept;
		this.ename = ename;
		this.job = job;
		this.hiredate = hiredate;
		this.sal = sal;
		this.comm = comm;
		this.emps = emps;
	}

	@Id
	@Column(name = "empno", unique = true, nullable = false, precision = 4, scale = 0)
	public short getEmpno() {
		return this.empno;
	}

	public void setEmpno(short empno) {
		this.empno = empno;
	}

// Modificado LAZY por EAGER
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mgr")
	public Emp getEmp() {
		return this.emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

// Modificado LAZY por EAGER	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deptno")
	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "ename", length = 10)
	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column(name = "job", length = 9)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "hiredate", length = 10)
	public Date getHiredate() {
		return this.hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	@Column(name = "sal", precision = 7)
	public BigDecimal getSal() {
		return this.sal;
	}

	public void setSal(BigDecimal sal) {
		this.sal = sal;
	}

	@Column(name = "comm", precision = 7)
	public BigDecimal getComm() {
		return this.comm;
	}

	public void setComm(BigDecimal comm) {
		this.comm = comm;
	}

// Modificado LAZY por EAGER
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "emp")
	public Set<Emp> getEmps() {
		return this.emps;
	}

	public void setEmps(Set<Emp> emps) {
		this.emps = emps;
	}

}
