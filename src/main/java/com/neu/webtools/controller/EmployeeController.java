package com.neu.webtools.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.webtools.dao.EmployerDAO;
import com.neu.webtools.pojo.AppUsers;
import com.neu.webtools.pojo.JobDetails;



@Controller
@RequestMapping(value = "/")
public class EmployeeController {
	
	@RequestMapping(value = "employeer/postjob.htm", method = RequestMethod.GET)
	public ModelAndView getJobPostForm(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		String user = request.getParameter("user");
		
		System.out.println(user);
		request.getSession().setAttribute("user", user);
		//String userText = req.getParameter("userText");
		mav.setViewName("post-job");
		return mav;
	}
	
	@RequestMapping(value = "employeer/postjobsuccess.htm", method = RequestMethod.POST)
	public String postAJob(HttpServletRequest request, EmployerDAO employerDao, ModelMap map, AppUsers users) {
		HttpSession session = null;
		String title = request.getParameter("jobtitle");
		String company = request.getParameter("job_company_name");
		String jobType = request.getParameter("job_type");
		String country = request.getParameter("country");
		String state = request.getParameter("state");
		String majCategory = request.getParameter("majCategory");
		String major = request.getParameter("major");
		String jobDescUrl = request.getParameter("job_desc_url");
		String jobDesc = request.getParameter("job_desc");
		String user = request.getParameter("user");
		Calendar postedOn = Calendar.getInstance();

		users.setFname(user);
		//users = (AppUsers) session.getAttribute("users");
		
		JobDetails jobDetails = new JobDetails();
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
		//AppUsers users = new AppUsers();
		//users = (AppUsers) request.getSession().getAttribute("user");
		jobDetails.setUser(users);
				
		try {
			jobDetails = employerDao.postJob(jobDetails);
			if(jobDetails != null) {
				map.addAttribute("successMessage", "Your job has been posted successfully!");
				return "employeer-home";
			}else {
				map.addAttribute("errorMessage", "Error occured in saving your job posting. Please try again!");
				return "employeer-home";
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error in saving job details");
		}
		return null;
		
	}
}

