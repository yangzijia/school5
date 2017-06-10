package com.b505.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="apppushinfo")
public class AppPushInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String push_title;
	private String push_text;
	private String push_sender;
	private String push_geter;
	private String push_time;
	
	@Id
	@GeneratedValue()
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPush_sender() {
		return push_sender;
	}

	public void setPush_sender(String push_sender) {
		this.push_sender = push_sender;
	}
	public String getPush_title() {
		return push_title;
	}

	public void setPush_title(String push_title) {
		this.push_title = push_title;
	}

	public String getPush_text() {
		return push_text;
	}

	public void setPush_text(String push_text) {
		this.push_text = push_text;
	}

	public String getPush_time() {
		return push_time;
	}

	public void setPush_time(String push_time) {
		this.push_time = push_time;
	}

	public String getPush_geter() {
		return push_geter;
	}

	public void setPush_geter(String push_geter) {
		this.push_geter = push_geter;
	}
	
	
}
