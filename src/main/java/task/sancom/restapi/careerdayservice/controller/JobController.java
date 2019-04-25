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

import task.sancom.restapi.careerdayservice.exception.ResourceNotFoundException1;
import task.sancom.restapi.careerdayservice.repository.JobRepository;


import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@RestController
public class JobController {


    private JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {

        this.jobRepository = jobRepository;
    }

    //Save job application
    @PostMapping("/jobs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Job> saveJob(@RequestBody @Valid Job job){

        return ResponseEntity.ok(jobRepository.save(job));

    }

    //Find all jobs
    @GetMapping("/jobs")
    public Page<Job> findAllJobs(Pageable pageable){
        return jobRepository.findAll(pageable);
    }


    //Find job given job ID
    @GetMapping("/jobs/{jobID}")
    public Job findJob(@PathVariable UUID jobID) throws ResourceNotFoundException1 {
        return jobRepository.findById(jobID).orElseThrow(()->new ResourceNotFoundException1(Job.class,"Job ID",jobID.toString()));
    }

    //Delete Job  Entry
    @DeleteMapping("/jobs/{jobID}")
    public ResponseEntity<?> deleteApplicant(@PathVariable UUID jobID) throws ResourceNotFoundException1{

        return jobRepository.findById(jobID).map(job -> {
                    jobRepository.delete(job);
                    return ResponseEntity.ok().build();
                }
        ).orElseThrow(() -> new ResourceNotFoundException1(Job.class,"Job ID",jobID.toString()));

    }


    //View all Participants for a given job
    @GetMapping("/jobs/{jobID}/participants")
    public Set<JobApplicant> getJobParticipants(@PathVariable UUID jobID) throws ResourceNotFoundException1{
      return jobRepository.findById(jobID).map(job -> {
          return job.getJobApplicants();
      }).orElseThrow(()->new ResourceNotFoundException1(Job.class,"Job ID",jobID.toString()));
    }


    //Search for jobs(Simple search functionality)
    @GetMapping("/jobs/search/jobs-available")
    public Page<Job> findJobInterviews(@RequestParam ("name") String jobName, @RequestParam("interview-date")ZonedDateTime interviewDate,
                                       @RequestParam("job-type") String type,@RequestParam("education-level")String educationLevel,
                                       @RequestParam("years-of-experience") int yearsOfExperience,Pageable pageable) throws ResourceNotFoundException1{
        if(interviewDate !=null){
            if(type != null){
                if(educationLevel != null){
                    if(yearsOfExperience > 0){
                        return  jobRepository.findByInterviewDateAndTypeIgnoreCaseAndQualification_EducationLevelIgnoreCaseAndQualification_YearsOfExperience(
                                interviewDate,type,educationLevel,yearsOfExperience,pageable   );
                    }
                }else {
                   return  jobRepository.findByInterviewDateAndTypeIgnoreCase(interviewDate,type,pageable);
                }
            }else if(educationLevel != null){
                return jobRepository.findByInterviewDateAndQualification_EducationLevelIgnoreCase(interviewDate,educationLevel,pageable);
            }else if(yearsOfExperience > 0){
                return jobRepository.findByInterviewDateAndQualification_YearsOfExperience(interviewDate,yearsOfExperience,pageable);
            }else {
                return jobRepository.findByInterviewDate(interviewDate,pageable);
            }
        }else if (jobName != null){
            return jobRepository.findByJobName(jobName,pageable);
        }else if(yearsOfExperience > 0){
            return jobRepository.findByQualification_YearsOfExperience(yearsOfExperience,pageable);
        }else if(educationLevel != null){
           return  jobRepository.findByQualification_EducationLevel(educationLevel,pageable);
        }else {

            throw  new ResourceNotFoundException1(Job.class,"No Resource was found","");

        }
        return null;
    }


}
