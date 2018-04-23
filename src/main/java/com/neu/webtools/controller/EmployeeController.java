package com.neu.webtools.controller;

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
	public ModelAndView getJobPostForm(HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		String user = req.getParameter("user");
		
		System.out.println(user);
		req.getSession().setAttribute("user", user);
		//String userText = req.getParameter("userText");
		mav.setViewName("post-job");
		return mav;
	}
	
	@RequestMapping(value = "employeer/postjobsuccess.htm", method = RequestMethod.POST)
	public String postAJob(HttpServletRequest request, EmployerDAO employerDao, ModelMap map) {
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
		
		JobDetails jobDetails = new JobDetails();
		AppUsers users = new AppUsers();
		users.setUsername(user);
		//users = (AppUsers) session.getAttribute("users");
		jobDetails.setUser(users);
		
		jobDetails.setJobTitle(title);
		jobDetails.setCompanyName(company);
		jobDetails.setJobType(jobType);
		jobDetails.setCountry(country);
		jobDetails.setState(state);
		jobDetails.setIndustry(majCategory);
		jobDetails.setMajor(major);
		jobDetails.setJobUrl(jobDescUrl);
		jobDetails.setDescription(jobDesc);
		
		jobDetails = employerDao.postJob(jobDetails);
		map.addAttribute("successMessage", "Your job has been posted successfully!");
		
		//ModelAndView mav = new ModelAndView();
		return "employeer-home";
	}
}

