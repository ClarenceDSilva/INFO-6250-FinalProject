package com.neu.webtools.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JOB_APPLICATIONS")
public class JobApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", unique=true, nullable = false)
	private long id;
	
	private String fileName;
	
	private byte[] data;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "userid")
	private AppUsers user;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "jobid")
	private JobDetails jobdetails;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "FILE_NAME")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name = "FILE_DATA")
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public AppUsers getUser() {
		return user;
	}

	public void setUser(AppUsers user) {
		this.user = user;
	}

	public JobDetails getJobdetails() {
		return jobdetails;
	}

	public void setJobdetails(JobDetails jobdetails) {
		this.jobdetails = jobdetails;
	}
	
	
}
