package com.neu.webtools.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.webtools.exception.StudentClassException;
import com.neu.webtools.pojo.JobApplication;
import com.neu.webtools.pojo.JobDetails;

public class StudentDAO extends DAO {

	// Method for returning all jobs
	public List<JobDetails> listAllJobs() throws StudentClassException {
		try {
			begin();
			Query query = getSession().createQuery("from JobDetails");
			System.out.println("INSIDE listAllJobs DAO method");
			List<JobDetails> allJobList = query.list();
			commit();
			close();
			return allJobList;
		} catch (HibernateException e) {
			rollback();
			throw new StudentClassException("Could not find employer", e);
		}
	}

	// Method for uploading files for job application
	public void saveFiles(JobApplication uploadFile) throws StudentClassException {
			try {
				System.out.println("INSIDE saveFiles METHOD");
				begin();
				getSession().save(uploadFile);
				close();
			}catch(HibernateException e) {
				rollback();
				e.printStackTrace();
				throw new StudentClassException("Error occured in uploading files at DAO level",e);
			}
					}
}
