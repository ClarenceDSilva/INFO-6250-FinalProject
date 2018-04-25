package com.neu.webtools.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.webtools.dao.EmployerDAO;
import com.neu.webtools.exception.JobsPostedException;
import com.neu.webtools.pojo.AppUsers;
import com.neu.webtools.pojo.JobDetails;



@Controller
@RequestMapping(value = "/")
public class EmployeeController {
	
	@RequestMapping(value = "employeer/postjob.htm", method = RequestMethod.GET)
	public ModelAndView getJobPostForm(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		String name = request.getParameter("name");
		
		System.out.println(name);
		request.getSession().setAttribute("user", name);
		mav.setViewName("post-job");
		return mav;
	}
	
	@RequestMapping(value = "employeer/postjobsuccess.htm", method = RequestMethod.POST)
	public ModelAndView postAJob(HttpServletRequest request, EmployerDAO employerDao, ModelMap map, AppUsers users) {
		String jobId = request.getParameter("job_id");
		String title = request.getParameter("jobtitle");
		String company = request.getParameter("job_company_name");
		String jobType = request.getParameter("job_type");
		String country = request.getParameter("country");
		String state = request.getParameter("state");
		String majCategory = request.getParameter("majCategory");
		String major = request.getParameter("major");
		String jobDescUrl = request.getParameter("job_url");
		String jobDesc = request.getParameter("job_description");
		Date postedOn = new Date();
		
		//Session to retrieve the user's object
		AppUsers appUsers = (AppUsers)request.getSession().getAttribute("name");
		
		JobDetails jobDetails = new JobDetails();
		jobDetails.setJobID(jobId);
		jobDetails.setJobTitle(title);
		jobDetails.setCompanyName(company);
		jobDetails.setJobType(jobType);
		jobDetails.setCountry(country);
		jobDetails.setState(state);
		jobDetails.setIndustry(majCategory);
		jobDetails.setMajor(major);
		jobDetails.setJobUrl(jobDescUrl);
		jobDetails.setDescription(jobDesc);
		jobDetails.setPostedOn(postedOn);
		jobDetails.setUser(appUsers);
				
		try {
			jobDetails = employerDao.postJob(jobDetails);
			if(jobDetails != null) {
				map.addAttribute("successMessage", "Your job has been posted successfully!");
				return new ModelAndView("employeer-home","jobPost", jobDetails);
			}else {
				map.addAttribute("errorMessage", "Error occured in saving your job posting. Please try again!");
				return new ModelAndView("post-job");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error in saving job details");
		}
		return null;
		
	}
	
	@RequestMapping(value = "/employer/myjobposts.htm", method = RequestMethod.GET)
	public ModelAndView listMyPostedJobs(HttpServletRequest request, EmployerDAO employerDao) {
		try {
			System.out.println("INSIDE listMyPostedJobs CONTROLLER");
			AppUsers user = (AppUsers)request.getSession().getAttribute("name");
			System.out.println("FIRST NAME: " + user);
			List<JobDetails> jobPost = employerDao.listJobPosts(user);
			return new ModelAndView("employer-posted-jobs", "jobPost", jobPost);
		}catch(JobsPostedException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("errors", "errorMessage", "Error occured while displaying your posteds jobs");
		}
	}
}

