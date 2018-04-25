package com.neu.webtools.pojo;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "JOB_DETAILS")
public class JobDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", unique=true, nullable = false)
	private long id;
	
	@Column(name = "jobID")
	private String jobID;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "userid")
	private AppUsers user;
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	@Column(name = "JOB_TYPE")
	private String jobType;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "INDUSTRY")
	private String industry;
	
	@Column(name = "MAJOR")
	private String major;
	
	@Column(name = "JOB_POST_URL")
	private String jobUrl;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date postedOn;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getJobID() {
		return jobID;
	}
	public void setJobID(String jobID) {
		this.jobID = jobID;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getJobUrl() {
		return jobUrl;
	}
	public void setJobUrl(String jobUrl) {
		this.jobUrl = jobUrl;
	}
	public AppUsers getUser() {
		return user;
	}
	public void setUser(AppUsers user) {
		this.user = user;
	}
	public Date getPostedOn() {
		return postedOn;
	}
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}
	
}
