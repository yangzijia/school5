package com.b505.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="course")
//serialization 允许你将实现了Serializable接口的对象转换为字节序列
//这些字节序列可以被完全存储以备以后重新生成原来的对象。  
public class Course implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int courseid;
	private int gid;
	private String mon_onetwo;
	private String mon_threefour;
	private String mon_fivesix;
	private String mon_seveneight;
	private String mon_nineten;
	private String tue_onetwo;
	private String tue_threefour;
	private String tue_fivesix;
	private String tue_seveneight;
	private String tue_nineten;
	private String wed_onetwo;
	private String wed_threefour;
	private String wed_fivesix;
	private String wed_seveneight;
	private String wed_nineten;
	private String thu_onetwo;
	private String thu_threefour;
	private String thu_fivesix;
	private String thu_seveneight;
	private String thu_nineten;
	private String fri_onetwo;
	private String fri_threefour;
	private String fri_fivesix;
	private String fri_seveneight;
	private String fri_nineten;
	private String sat_onetwo;
	private String sat_threefour;
	private String sat_fivesix;
	private String sat_seveneight;
	private String sat_nineten;
	private String sun_onetwo;
	private String sun_threefour;
	private String sun_fivesix;
	private String sun_seveneight;
	private String sun_nineten;
	
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="courseid")
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	@Column(name="gid")
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	@Column(name="mon_onetwo")
	public String getMon_onetwo() {
		return mon_onetwo;
	}
	public void setMon_onetwo(String mon_onetwo) {
		this.mon_onetwo = mon_onetwo;
	}
	@Column(name="mon_threefour")
	public String getMon_threefour() {
		return mon_threefour;
	}
	public void setMon_threefour(String mon_threefour) {
		this.mon_threefour = mon_threefour;
	}
	@Column(name="mon_fivesix")
	public String getMon_fivesix() {
		return mon_fivesix;
	}
	public void setMon_fivesix(String mon_fivesix) {
		this.mon_fivesix = mon_fivesix;
	}
	@Column(name="mon_seveneight")
	public String getMon_seveneight() {
		return mon_seveneight;
	}
	public void setMon_seveneight(String mon_seveneight) {
		this.mon_seveneight = mon_seveneight;
	}
	@Column(name="tue_onetwo")
	public String getTue_onetwo() {
		return tue_onetwo;
	}
	public void setTue_onetwo(String tue_onetwo) {
		this.tue_onetwo = tue_onetwo;
	}
	@Column(name="tue_threefour")
	public String getTue_threefour() {
		return tue_threefour;
	}
	public void setTue_threefour(String tue_threefour) {
		this.tue_threefour = tue_threefour;
	}
	@Column(name="tue_fivesix")
	public String getTue_fivesix() {
		return tue_fivesix;
	}
	public void setTue_fivesix(String tue_fivesix) {
		this.tue_fivesix = tue_fivesix;
	}
	@Column(name="tue_seveneight")
	public String getTue_seveneight() {
		return tue_seveneight;
	}
	public void setTue_seveneight(String tue_seveneight) {
		this.tue_seveneight = tue_seveneight;
	}
	@Column(name="wed_onetwo")
	public String getWed_onetwo() {
		return wed_onetwo;
	}
	public void setWed_onetwo(String wed_onetwo) {
		this.wed_onetwo = wed_onetwo;
	}
	@Column(name="wed_threefour")
	public String getWed_threefour() {
		return wed_threefour;
	}
	public void setWed_threefour(String wed_threefour) {
		this.wed_threefour = wed_threefour;
	}
	@Column(name="wed_fivesix")
	public String getWed_fivesix() {
		return wed_fivesix;
	}
	public void setWed_fivesix(String wed_fivesix) {
		this.wed_fivesix = wed_fivesix;
	}
	@Column(name="wed_seveneight")
	public String getWed_seveneight() {
		return wed_seveneight;
	}
	public void setWed_seveneight(String wed_seveneight) {
		this.wed_seveneight = wed_seveneight;
	}
	@Column(name="thu_onetwo")
	public String getThu_onetwo() {
		return thu_onetwo;
	}
	public void setThu_onetwo(String thu_onetwo) {
		this.thu_onetwo = thu_onetwo;
	}
	@Column(name="thu_threefour")
	public String getThu_threefour() {
		return thu_threefour;
	}
	public void setThu_threefour(String thu_threefour) {
		this.thu_threefour = thu_threefour;
	}
	@Column(name="thu_fivesix")
	public String getThu_fivesix() {
		return thu_fivesix;
	}
	public void setThu_fivesix(String thu_fivesix) {
		this.thu_fivesix = thu_fivesix;
	}
	@Column(name="thu_seveneight")
	public String getThu_seveneight() {
		return thu_seveneight;
	}
	public void setThu_seveneight(String thu_seveneight) {
		this.thu_seveneight = thu_seveneight;
	}
	@Column(name="fri_onetwo")
	public String getFri_onetwo() {
		return fri_onetwo;
	}
	public void setFri_onetwo(String fri_onetwo) {
		this.fri_onetwo = fri_onetwo;
	}
	@Column(name="fri_threefour")
	public String getFri_threefour() {
		return fri_threefour;
	}
	public void setFri_threefour(String fri_threefour) {
		this.fri_threefour = fri_threefour;
	}
	@Column(name="fri_fivesix")
	public String getFri_fivesix() {
		return fri_fivesix;
	}
	public void setFri_fivesix(String fri_fivesix) {
		this.fri_fivesix = fri_fivesix;
	}
	@Column(name="fri_seveneight")
	public String getFri_seveneight() {
		return fri_seveneight;
	}
	public void setFri_seveneight(String fri_seveneight) {
		this.fri_seveneight = fri_seveneight;
	}
	@Column(name="sat_onetwo")
	public String getSat_onetwo() {
		return sat_onetwo;
	}
	public void setSat_onetwo(String sat_onetwo) {
		this.sat_onetwo = sat_onetwo;
	}
	@Column(name="sat_threefour")
	public String getSat_threefour() {
		return sat_threefour;
	}
	public void setSat_threefour(String sat_threefour) {
		this.sat_threefour = sat_threefour;
	}
	@Column(name="sat_fivesix")
	public String getSat_fivesix() {
		return sat_fivesix;
	}
	public void setSat_fivesix(String sat_fivesix) {
		this.sat_fivesix = sat_fivesix;
	}
	@Column(name="sat_seveneight")
	public String getSat_seveneight() {
		return sat_seveneight;
	}
	public void setSat_seveneight(String sat_seveneight) {
		this.sat_seveneight = sat_seveneight;
	}
	@Column(name="mon_nineten")
	public String getMon_nineten()
	{
		return mon_nineten;
	}
	public void setMon_nineten(String mon_nineten)
	{
		this.mon_nineten = mon_nineten;
	}
	@Column(name="tue_nineten")
	public String getTue_nineten()
	{
		return tue_nineten;
	}
	public void setTue_nineten(String tue_nineten)
	{
		this.tue_nineten = tue_nineten;
	}
	@Column(name="wed_nineten")
	public String getWed_nineten()
	{
		return wed_nineten;
	}
	public void setWed_nineten(String wed_nineten)
	{
		this.wed_nineten = wed_nineten;
	}
	@Column(name="thu_nineten")
	public String getThu_nineten()
	{
		return thu_nineten;
	}
	public void setThu_nineten(String thu_nineten)
	{
		this.thu_nineten = thu_nineten;
	}
	@Column(name="fri_nineten")
	public String getFri_nineten()
	{
		return fri_nineten;
	}
	public void setFri_nineten(String fri_nineten)
	{
		this.fri_nineten = fri_nineten;
	}
	@Column(name="sat_nineten")
	public String getSat_nineten()
	{
		return sat_nineten;
	}
	public void setSat_nineten(String sat_nineten)
	{
		this.sat_nineten = sat_nineten;
	}
	@Column(name="sun_onetwo")
	public String getSun_onetwo()
	{
		return sun_onetwo;
	}
	public void setSun_onetwo(String sun_onetwo)
	{
		this.sun_onetwo = sun_onetwo;
	}
	@Column(name="sun_threefour")
	public String getSun_threefour()
	{
		return sun_threefour;
	}
	public void setSun_threefour(String sun_threefour)
	{
		this.sun_threefour = sun_threefour;
	}
	@Column(name="sun_fivesix")
	public String getSun_fivesix()
	{
		return sun_fivesix;
	}
	public void setSun_fivesix(String sun_fivesix)
	{
		this.sun_fivesix = sun_fivesix;
	}
	@Column(name="sun_seveneight")
	public String getSun_seveneight()
	{
		return sun_seveneight;
	}
	public void setSun_seveneight(String sun_seveneight)
	{
		this.sun_seveneight = sun_seveneight;
	}
	@Column(name="sun_nineten")
	public String getSun_nineten()
	{
		return sun_nineten;
	}
	public void setSun_nineten(String sun_nineten)
	{
		this.sun_nineten = sun_nineten;
	}
	@Override
	public String toString()
	{
		return "Course [courseid=" + courseid + ", gid=" + gid
				+ ", mon_onetwo=" + mon_onetwo + ", mon_threefour="
				+ mon_threefour + ", mon_fivesix=" + mon_fivesix
				+ ", mon_seveneight=" + mon_seveneight + ", mon_nineten="
				+ mon_nineten + ", tue_onetwo=" + tue_onetwo
				+ ", tue_threefour=" + tue_threefour + ", tue_fivesix="
				+ tue_fivesix + ", tue_seveneight=" + tue_seveneight
				+ ", tue_nineten=" + tue_nineten + ", wed_onetwo=" + wed_onetwo
				+ ", wed_threefour=" + wed_threefour + ", wed_fivesix="
				+ wed_fivesix + ", wed_seveneight=" + wed_seveneight
				+ ", wed_nineten=" + wed_nineten + ", thu_onetwo=" + thu_onetwo
				+ ", thu_threefour=" + thu_threefour + ", thu_fivesix="
				+ thu_fivesix + ", thu_seveneight=" + thu_seveneight
				+ ", thu_nineten=" + thu_nineten + ", fri_onetwo=" + fri_onetwo
				+ ", fri_threefour=" + fri_threefour + ", fri_fivesix="
				+ fri_fivesix + ", fri_seveneight=" + fri_seveneight
				+ ", fri_nineten=" + fri_nineten + ", sat_onetwo=" + sat_onetwo
				+ ", sat_threefour=" + sat_threefour + ", sat_fivesix="
				+ sat_fivesix + ", sat_seveneight=" + sat_seveneight
				+ ", sat_nineten=" + sat_nineten + ", sun_onetwo=" + sun_onetwo
				+ ", sun_threefour=" + sun_threefour + ", sun_fivesix="
				+ sun_fivesix + ", sun_seveneight=" + sun_seveneight
				+ ", sun_nineten=" + sun_nineten + "]";
	}
	
	
}
