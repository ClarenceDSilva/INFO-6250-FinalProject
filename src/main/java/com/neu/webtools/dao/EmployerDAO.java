package com.neu.webtools.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.webtools.exception.JobsPostedException;
import com.neu.webtools.pojo.AppUsers;
import com.neu.webtools.pojo.JobApplication;
import com.neu.webtools.pojo.JobDetails;

public class EmployerDAO extends DAO {
	
	//Method for saving the job details to the database
	public JobDetails postJob(JobDetails job) {
		try {
			begin();
			System.out.println("INSIDE postJob METHOD");	
			getSession().save(job);
			commit();
			close();
			return job;
		}catch(HibernateException e) {
			rollback();
			e.printStackTrace();
			return null;
		}
	}
	
	//Method for listing all the jobs posted by the employer
	public List<JobDetails> listJobPosts(AppUsers user) throws JobsPostedException{
		try {
			begin();
			System.out.println("INSIDE listJobPosts METHOD ");
			int userid = user.getUserid();
			System.out.println("USERID: "+ userid);
			Query query = getSession().createQuery("from JobDetails where user = '"+ userid +"' ");
			System.out.println("Query: "+ query);
			List<JobDetails> jobPost = query.list();
			commit();
			close();
			return jobPost;
		}catch(HibernateException e) {
			rollback();
			throw new JobsPostedException("Could not find any posts for you!", e);
		}
	}
	
	//Method for deleting Job Post
	public boolean delete(long jobid) throws JobsPostedException{
		try {
			begin();
			Query query = getSession().createQuery("delete from JobDetails where id = '" + jobid + "' ");
			System.out.println("Inside EMPLOYEER DAO delete method");
			query.executeUpdate();
			commit();
			close();
			return true;
		}catch(HibernateException e) {
			rollback();
			throw new JobsPostedException("Error in deleting the job post", e);
		}
	}
	
	//Method for fetching the id to update the job post
	public JobDetails updateJobPost(long jobid) throws JobsPostedException{
		try {
			begin();
			Query query = getSession().createQuery("from JobDetails where id = '" + jobid + "' ");
			System.out.println("INSIDE EMPLOYER DAO update method");
			JobDetails jobdetails = (JobDetails) query.uniqueResult();
			commit();
			close();
			return jobdetails;
		}catch(HibernateException e) {
			rollback();
			throw new JobsPostedException("Could not update the job at the DAO level", e);
		}
	}
	
	//Method for updating the job post
	public JobDetails onClickUpdateJobPost(JobDetails jobdetails, long newJobid) throws JobsPostedException{
		try {
			begin();
			String jobid = jobdetails.getJobID();
			String title = jobdetails.getJobTitle();
			String company = jobdetails.getCompanyName();
			String jobType = jobdetails.getJobType();
			String country = jobdetails.getCountry();
			String state = jobdetails.getState();
			String industry = jobdetails.getIndustry();
			String major = jobdetails.getMajor();
			String url = jobdetails.getJobUrl();
			String description = jobdetails.getDescription();
			
			Query query = getSession().createQuery("update JobDetails set jobID ='" + jobid + "' , jobTitle ='" + title + "', companyName = '" + company + "', jobType = '" + jobType + "', country = '" + country + "', state = '" + state + "', industry = '" + industry + "', major = '" + major + "', jobUrl = '" + url + "', description = '" + description + "' where id = '" + newJobid + "' ");
			System.out.println("INSIDE EMPLOYER DAO onClickUpdateJobPost METHOD ");
			query.executeUpdate();
			commit();
			close();
			return jobdetails;
		}catch(HibernateException e) {
			rollback();
			throw new JobsPostedException("Error occured while updating the job at the DAO Level", e);
		}
	}
	
	//Method to list all the candidates JobApplication applied to the post based on id
	public List<JobApplication> listOfCandidatesApplied(long jobid) throws JobsPostedException{
		try {
			begin();
			System.out.println("PART1: INSIDE listOfCandidatesApplied DAO METHOD ");
			Query query = getSession().createQuery("from JobApplication where jobdetails= '" + jobid + "' ");
			List<JobApplication> application = query.list();
			commit();
			close();
			return application;
		}catch (HibernateException e) {
            rollback();
            throw new JobsPostedException("Could not find candidate", e);
        }
	}
	
	//Method to get list of candidate information using userid
	public List<AppUsers> getUserInfo(int userid) throws JobsPostedException{
		try {
			begin();
			System.out.println("PART2: INSIDE getUserInfo DAO METHOD ");
			Query query = getSession().createQuery("from AppUsers where userid= '" + userid + "' ");
			List<AppUsers> userinfo = query.list();
			commit();
			close();
			return userinfo;
		}catch (HibernateException e) {
            rollback();
            throw new JobsPostedException("Could not find candidate info", e);
        }
	}
	
}

