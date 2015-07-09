package paqueteAplicacion.generadoHibernate;
// Generated 12-mar-2014 10:52:53 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Dept generated by hbm2java
 */
@Entity
@Table(name = "dept", catalog = "scott")
public class Dept implements java.io.Serializable {

	private byte deptno;
	private String dname;
	private String loc;
	private Set<Emp> emps = new HashSet<Emp>(0);

	public Dept() {
	}

	public Dept(byte deptno) {
		this.deptno = deptno;
	}

	public Dept(byte deptno, String dname, String loc, Set<Emp> emps) {
		this.deptno = deptno;
		this.dname = dname;
		this.loc = loc;
		this.emps = emps;
	}

	@Id
	@Column(name = "deptno", unique = true, nullable = false, precision = 2, scale = 0)
	public byte getDeptno() {
		return this.deptno;
	}

	public void setDeptno(byte deptno) {
		this.deptno = deptno;
	}

	@Column(name = "dname", length = 14)
	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	@Column(name = "loc", length = 13)
	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dept")
	public Set<Emp> getEmps() {
		return this.emps;
	}

	public void setEmps(Set<Emp> emps) {
		this.emps = emps;
	}

}