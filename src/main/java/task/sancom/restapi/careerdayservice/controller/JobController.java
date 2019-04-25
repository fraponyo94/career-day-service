package task.sancom.restapi.careerdayservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class JobController {

    @Autowired
   private JobService jobService;

    @Autowired
    private TimeFormatterComponent timeFormatterComponent;

    //Save job application
    @PostMapping("/job")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveJob(@RequestBody @Valid Job job) throws FieldViolationException,ParseException {

        jobService.saveJob(job);

    }

     @PutMapping("/job")
     @ResponseStatus(HttpStatus.OK)
     public void update(@RequestBody @Valid Job updated) throws ResourceNotFoundException1,FieldViolationException{
        if (jobService.findById(updated.getJobId()).isPresent()){
            jobService.saveJob(updated);
        }
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
    @GetMapping("/jobs/search")
    @ResponseStatus(HttpStatus.FOUND)
    public Page<Job> searchJobInterviews(@RequestParam (value = "name",required = false) String jobName, @RequestParam(value = "interview-date",required = false) Date interviewDate,
                                       @RequestParam(value = "job-type",required = false) String type,@RequestParam(value = "education-level",required = false)String educationLevel,
                                       @RequestParam(value = "years-of-experience",defaultValue = "0") int yearsOfExperience,Pageable pageable) throws ResourceNotFoundException1{


            return jobService.searchJob(interviewDate,type,educationLevel,yearsOfExperience,jobName,pageable);
       // }




    }


}
