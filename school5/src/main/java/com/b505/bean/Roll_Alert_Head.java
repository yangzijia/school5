package com.b505.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="roll_alert_head")
@DynamicUpdate(true)
public class Roll_Alert_Head implements Serializable
{
    private static final long serialVersionUID = 1903941282069892725L;
    /**
     * 
     * 
     */
    private int head_id;
    private String heading;
    @Id
    @GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="head_id")
	public int getHead_id()
	{
		return head_id;
	}
	public void setHead_id(int head_id)
	{
		this.head_id = head_id;
	}
	@Column(name="heading")
	public String getHeading()
	{
		return heading;
	}
	
	public void setHeading(String heading)
	{
		this.heading = heading;
	}
	@Override
	public String toString()
	{
		return "Roll_Alert_Head [head_id=" + head_id + ", heading=" + heading
				+ "]";
	}
	
}
