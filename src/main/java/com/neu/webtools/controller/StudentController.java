package com.neu.webtools.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.neu.webtools.dao.EmployerDAO;
import com.neu.webtools.dao.StudentDAO;
import com.neu.webtools.exception.JobsPostedException;
import com.neu.webtools.exception.StudentClassException;
import com.neu.webtools.pojo.AppUsers;
import com.neu.webtools.pojo.JobApplication;
import com.neu.webtools.pojo.JobDetails;

@Controller
@RequestMapping(value = "/")
public class StudentController {

	@RequestMapping(value = "student/viewalljobs.htm", method = RequestMethod.GET)
	public ModelAndView viewAllJobs(HttpServletRequest request, StudentDAO studentDao) throws Exception {
		AppUsers appUsers = (AppUsers) request.getSession().getAttribute("name");
		JobDetails jobdetails = (JobDetails) request.getSession().getAttribute(null);
		try {
			request.getSession().setAttribute("name", appUsers);
			List<JobDetails> jobDetails = studentDao.listAllJobs();
			request.getSession().setAttribute("jobID", jobDetails);
			return new ModelAndView("view-jobs", "allJobs", jobDetails);
		} catch (StudentClassException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("errors", "errorMessage", "Error occured while retrieving jobs");
		}

	}

	@RequestMapping(value = "student/showUploadPage.htm", method = RequestMethod.GET)
	public ModelAndView showApplicationPage(HttpServletRequest request, EmployerDAO employerDao,StudentDAO studentDao)
			throws JobsPostedException, StudentClassException {
		
		//ModelAndView mav = new ModelAndView("apply-job");
		HttpSession session = (HttpSession) request.getSession();
		// Fetching Job ID of the job for applying and then setting it into session
		String jobID = request.getParameter("jobID");
		long id = Long.parseLong(jobID);
		JobDetails jobdetails = employerDao.updateJobPost(id);
		session.setAttribute("jobID", jobdetails);
		AppUsers appUsers = (AppUsers) request.getSession().getAttribute("name");
		request.getSession().setAttribute("name", appUsers);
		// System.out.println("JOBDETAILS-------->: "+jobdetails+ "
		// APPUSERS--------------->: "+appUsers);
		System.out.println("INSIDE showApplicationPage method");
		
		//token = studentDao.userExists(appUsers, jobdetails);
		//System.out.println("ISEIZE" + list.size());
		return new ModelAndView("apply-job");
	
	}

	@RequestMapping(value = "student/apply.htm", method = RequestMethod.POST)
	public ModelAndView handleFileUpload(HttpServletRequest request, StudentDAO studentDao,
			@RequestParam CommonsMultipartFile[] fileUpload) throws Exception {
		//boolean token = true;
		List<JobDetails> jd = new ArrayList<JobDetails>();
		// Fetching JobDetails and AppUsers object from the session
		JobDetails jobdetails = (JobDetails) request.getSession().getAttribute("jobID");
		AppUsers appUsers = (AppUsers) request.getSession().getAttribute("name");
		System.out.println("JOBDETAILS: " + jobdetails + " APPUSERS: " + appUsers);
		//System.out.println("ISEIZE" + list.size());

		try {
			/*token = studentDao.userExists(appUsers, jobdetails);
			if(token == true) {
				return new ModelAndView("errors", "errorMessage","You have already applied for this job");
			}else {*/
			request.getSession().setAttribute("name", appUsers);
			request.getSession().setAttribute("JobID", jobdetails);
			if (fileUpload != null && fileUpload.length > 0) {
				for (CommonsMultipartFile aFile : fileUpload) {
					System.out.println("Saving file: " + aFile.getOriginalFilename());
					JobApplication uploadFile = new JobApplication();
					uploadFile.setFileName(aFile.getOriginalFilename());
					uploadFile.setData(aFile.getBytes());
					uploadFile.setUser(appUsers);
					uploadFile.setJobdetails(jobdetails);
					studentDao.saveFiles(uploadFile);
				}
			}
			jd.add(jobdetails);
			request.getSession().setAttribute("jobList", jd);
			return new ModelAndView("view-jobs");
		} catch (StudentClassException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("errors", "errorMessage",
					"Error occured while uploading file at the Controller level");

		}
	}

	@RequestMapping(value = "student/viewMyJobs.htm", method = RequestMethod.GET)
	public ModelAndView listAllApplications(HttpServletRequest request, StudentDAO studentDao) throws Exception {
		try {
			AppUsers appUsers = (AppUsers) request.getSession().getAttribute("name");
			List<JobDetails> jd = (List<JobDetails>)request.getSession().getAttribute("jobList");
			//JobDetails jobdetails = (JobDetails) request.getSession().getAttribute("jobID");
			//System.out.println("JOBID------------>" + jobdetails);
			System.out.println("USERID: " + appUsers.getUserid());
			// List<JobDetails> applications = studentDao.listAppliedJobs(jobdetails);
			System.out.println("SIZE:-> " + jd.size());
			return new ModelAndView("view-applied-jobs", "apply", jd);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ModelAndView("errors", "errorMessage",
					"Error occured while listing all jobs at the Controller level");

		}
	}
	
	@RequestMapping(value = "/student/deleteMyApplication.htm", method = RequestMethod.GET)
	public ModelAndView deleteJobPost(HttpServletRequest request, StudentDAO studentDao) throws StudentClassException {
		System.out.println("INSIDE deleteJobPost CONTROLLER METHOD");
		HttpSession session = (HttpSession) request.getSession();
		String jobId = request.getParameter("jobID");
		//JobDetails jobdetails = (JobDetails) request.getSession().getAttribute("jobID");
		try {
			if (jobId.equals(null)) {
				System.out.println("No Id found to delete the job post");
			} else {
				session.setAttribute("jobId", jobId);

				System.out.println("JOB ID " + jobId);
				long id = Long.parseLong(jobId);
				studentDao.delete(id);
			}
			return new ModelAndView("delete-success-student");
		} catch (StudentClassException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("errors", "errorMessage", "Error occured while deleting the job application");
		}

	}

}
