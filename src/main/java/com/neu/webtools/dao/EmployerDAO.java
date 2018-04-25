package com.neu.webtools.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.webtools.exception.JobsPostedException;
import com.neu.webtools.pojo.AppUsers;
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
}

