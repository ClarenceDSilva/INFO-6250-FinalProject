package com.neu.webtools.dao;

import org.hibernate.HibernateException;

import com.neu.webtools.pojo.AppUsers;
import com.neu.webtools.pojo.JobDetails;

//Method for saving the job details to the database
public class EmployerDAO extends DAO {
	public JobDetails postJob(JobDetails job) {
		try {
			begin();
			System.out.println("INSIDE postJob METHOD");
			JobDetails jobObj = new JobDetails();
			jobObj.setCompanyName(job.getCompanyName());
			jobObj.setCountry(job.getCountry());
			jobObj.setDescription(job.getDescription());
			jobObj.setIndustry(job.getIndustry());
			jobObj.setJobTitle(job.getJobTitle());
			jobObj.setJobType(job.getJobType());
			jobObj.setJobUrl(job.getJobUrl());
			jobObj.setMajor(job.getMajor());
			jobObj.setState(job.getState());
			AppUsers users = new AppUsers();
			users = jobObj.getUser();
			jobObj.setUser(users);
			
			getSession().save(jobObj);
			commit();
			return job;
		}catch(HibernateException e) {
			rollback();
			e.printStackTrace();
			return null;
		}
	}
}

