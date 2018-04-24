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
			
			/*job.setCompanyName(job.getCompanyName());
			job.setCountry(job.getCountry());
			job.setDescription(job.getDescription());
			job.setIndustry(job.getIndustry());
			job.setJobTitle(job.getJobTitle());
			job.setJobType(job.getJobType());
			job.setJobUrl(job.getJobUrl());
			job.setMajor(job.getMajor());
			job.setState(job.getState());
			job.setPostedOn(job.getPostedOn());
			AppUsers users = new AppUsers();
			users = job.getUser();
			job.setUser(users);*/
			
			getSession().persist(job);
			commit();
			return job;
		}catch(HibernateException e) {
			rollback();
			e.printStackTrace();
			return null;
		}
	}
}

