package com.b505.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="longlattype")
public class LongLatType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4477134060588643292L;
	private int typeid;
	private String longlattype;
	
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="typeid")
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	@Column(name="longlattype")
	public String getLonglattype() {
		return longlattype;
	}
	public void setLonglattype(String longlattype) {
		this.longlattype = longlattype;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((longlattype == null) ? 0 : longlattype.hashCode());
		result = prime * result + typeid;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LongLatType other = (LongLatType) obj;
		if (longlattype == null) {
			if (other.longlattype != null)
				return false;
		} else if (!longlattype.equals(other.longlattype))
			return false;
		if (typeid != other.typeid)
			return false;
		return true;
	}

}
