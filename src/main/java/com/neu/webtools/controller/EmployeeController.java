package com.neu.webtools.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.webtools.dao.EmployerDAO;
import com.neu.webtools.exception.JobsPostedException;
import com.neu.webtools.pojo.AppUsers;
import com.neu.webtools.pojo.JobApplication;
import com.neu.webtools.pojo.JobDetails;

@Controller
@RequestMapping(value = "/")
public class EmployeeController {

	@RequestMapping(value = "employeer/postjob.htm", method = RequestMethod.GET)
	public ModelAndView getJobPostForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		AppUsers appUsers = (AppUsers) request.getSession().getAttribute("name");
		//String name = request.getParameter("name");
		//System.out.println(name);
		request.getSession().setAttribute("name", appUsers);
		mav.setViewName("post-job");
		return mav;
	}

	@RequestMapping(value = "employer/postjobsuccess.htm", method = RequestMethod.POST)
	public ModelAndView postAJob(HttpServletRequest request, EmployerDAO employerDao, ModelMap map) {
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

		// Session to retrieve the user's object and to associate the job details to it
		AppUsers appUsers = (AppUsers) request.getSession().getAttribute("name");

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
			if (jobDetails != null) {
				map.addAttribute("successMessage", "Your job has been posted successfully!");
				return new ModelAndView("employeer-home", "jobPost", jobDetails);
			} else {
				map.addAttribute("errorMessage", "Error occured in saving your job posting. Please try again!");
				return new ModelAndView("post-job");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in saving job details");
		}
		return null;

	}

	@RequestMapping(value = "/employer/myjobposts.htm", method = RequestMethod.GET)
	public ModelAndView listMyPostedJobs(HttpServletRequest request, EmployerDAO employerDao) {
		try {
			System.out.println("INSIDE listMyPostedJobs CONTROLLER");
			AppUsers user = (AppUsers) request.getSession().getAttribute("name");
			System.out.println("FIRST NAME: " + user);
			List<JobDetails> jobPost = employerDao.listJobPosts(user);
			return new ModelAndView("employer-posted-jobs", "jobPost", jobPost);
		} catch (JobsPostedException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("errors", "errorMessage", "Error occured while displaying your posted jobs");
		}
	}

	@RequestMapping(value = "/employer/deleteMyJobposts.htm", method = RequestMethod.GET)
	public ModelAndView deleteJobPost(HttpServletRequest request, EmployerDAO employerDao) {
		System.out.println("INSIDE deleteJobPost CONTROLLER METHOD");
		HttpSession session = (HttpSession) request.getSession();
		String jobId = request.getParameter("jobID");
		try {
			if (jobId.equals(null)) {
				System.out.println("No Id found to delete the job post");
			} else {
				session.setAttribute("jobId", jobId);

				System.out.println("JOB ID " + jobId);
				long id = Long.parseLong(jobId);
				employerDao.delete(id);
			}
			return new ModelAndView("delete-success");
		} catch (JobsPostedException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("errors", "errorMessage", "Error occured while deleting the job post");
		}

	}
	
	@RequestMapping(value = "/employer/updateJobDetails.htm", method = RequestMethod.GET)
	public ModelAndView updateJobPost(HttpServletRequest request, EmployerDAO employerDao) {
		//Retrieving the job id and name to be updated and setting it into the session
		HttpSession session =  request.getSession();
		String id = request.getParameter("jobID");
		AppUsers appUsers = (AppUsers) request.getSession().getAttribute("name");
		session.setAttribute("id", id);
		session.setAttribute("name", appUsers);
		long jobid = Long.parseLong(id);
		try {
			JobDetails jobDetails = employerDao.updateJobPost(jobid);
			request.setAttribute("jobDetails", jobDetails);
			System.out.println("Updaing job post of: "+ jobDetails.getCompanyName());
			return new ModelAndView("update-job-details", "jobDetails", jobDetails);
		}catch(JobsPostedException e) {
			return new ModelAndView("errors", "errorMessage", "Error occured while updating the job post");
		}
		
	}
	@RequestMapping(value = "/employer/updateJobDetails.htm", method = RequestMethod.POST)
	public ModelAndView onUpdateClick(HttpServletRequest request, EmployerDAO employerDao, ModelMap map, @ModelAttribute("jobdetails") JobDetails jobdetails) {
		// Using ModelAttribute annotation to inject details from the update-job-details from into the controller
		
		HttpSession session =  request.getSession();
		String id = (String)session.getAttribute("id");
		//String name = request.getParameter("name");
		AppUsers appUsers = (AppUsers) request.getSession().getAttribute("name");
		
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
		
		jobdetails.setJobID(jobId);
		jobdetails.setJobTitle(title);
		jobdetails.setCompanyName(company);
		jobdetails.setJobType(jobType);
		jobdetails.setCountry(country);
		jobdetails.setState(state);
		jobdetails.setIndustry(majCategory);
		jobdetails.setMajor(major);
		jobdetails.setJobUrl(jobDescUrl);
		jobdetails.setDescription(jobDesc);
		jobdetails.setPostedOn(postedOn);
		
		
		try {
			long newJobId = Long.parseLong(id);
			JobDetails details = employerDao.onClickUpdateJobPost(jobdetails, newJobId);
			request.getSession().setAttribute("updatedJobDetails", details);
			session.setAttribute("name", appUsers);
			map.addAttribute("successMessage", "Your job has been updated successfully!");
			return new ModelAndView("employeer-home", "jobDetails", details);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in updating job details");
		}
		return null;
		
	}
	
	@RequestMapping(value = "/employer/viewCandidatesApplied", method = RequestMethod.GET)
	public ModelAndView listCandidatesApplied(HttpServletRequest request,EmployerDAO employerDao) {
			AppUsers appUsers = (AppUsers) request.getSession().getAttribute("name");
			HttpSession session = request.getSession();
			String id = request.getParameter("jobID");
			session.setAttribute("id", id);
			session.setAttribute("name", appUsers);
			long jobid = Long.parseLong(id);
			System.out.println("JOB_ID--------->"+jobid);
			try {
				List<JobApplication> listApplication = employerDao.listOfCandidatesApplied(jobid);
				for(JobApplication ja : listApplication) {
					int userid = ja.getUser().getUserid();
					System.out.println("USER_ID--------->"+userid);
					List<AppUsers> appUserInfo = employerDao.getUserInfo(userid);
					ModelAndView mav = new ModelAndView("candidates-applied");
					mav.addObject("listapp",listApplication);
					mav.addObject("listuser",appUserInfo);
					return mav;
				}
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error in viewing candidate details");
			}
			return null;
	}
}
