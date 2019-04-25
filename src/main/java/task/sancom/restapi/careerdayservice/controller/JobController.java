package task.sancom.restapi.careerdayservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.sancom.restapi.careerdayservice.component.TimeFormatterComponent;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;

import task.sancom.restapi.careerdayservice.exception.FieldViolationException;
import task.sancom.restapi.careerdayservice.exception.ResourceNotFoundException1;
import task.sancom.restapi.careerdayservice.repository.JobRepository;
import task.sancom.restapi.careerdayservice.service.JobService;


import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@RestController
public class JobController {

    @Autowired
   private JobService jobService;

    //Save job application
    @PostMapping("/jobs")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveJob(@RequestBody @Valid Job job) throws FieldViolationException {

        jobService.saveJob(job);

    }

    //Find all jobs
    @GetMapping("/jobs")
    @ResponseStatus(HttpStatus.FOUND)
    public Page<Job> findAllJobs(Pageable pageable) throws ResourceNotFoundException1{
        return jobService.findAll(pageable);
    }


    //Find job given job ID
    @GetMapping("/jobs/{jobID}")
    @ResponseStatus(HttpStatus.FOUND)
    public Job findJob(@PathVariable UUID jobID) throws ResourceNotFoundException1 {
        return jobService.findById(jobID).get();
    }

    //Delete Job  Entry
    @DeleteMapping("/jobs/{jobID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteApplicant(@PathVariable UUID jobID) throws ResourceNotFoundException1{

        jobService.delete(jobID);

    }


    //View all Participants for a given job
    @GetMapping("/jobs/{jobID}/participants")
    @ResponseStatus(HttpStatus.FOUND)
    public Set<JobApplicant> getJobParticipants(@PathVariable UUID jobID) throws ResourceNotFoundException1{
      return jobService.findById(jobID).map(job -> {
          return job.getJobApplicants();
      }).orElseThrow(()->new ResourceNotFoundException1(Job.class,"Job ID",jobID.toString(),""));
    }


    //Search for jobs(Simple search functionality)
    @GetMapping("/jobs/search/jobs-available")
    @ResponseStatus(HttpStatus.FOUND)
    public Page<Job> findJobInterviews(@RequestParam ("name") String jobName, @RequestParam("interview-date") Date interviewDate,
                                       @RequestParam("job-type") String type,@RequestParam("education-level")String educationLevel,
                                       @RequestParam("years-of-experience") int yearsOfExperience,Pageable pageable) throws ResourceNotFoundException1{
        return jobService.searchJob(interviewDate,type,educationLevel,yearsOfExperience,jobName,pageable);

    }


}
