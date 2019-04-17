package task.sancom.restapi.careerdayservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.exception.ResourceNotFoundException;
import task.sancom.restapi.careerdayservice.repository.JobRepository;

import java.util.UUID;

@RestController
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    //Save job application
    @PostMapping("/jobs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> saveJob(@RequestBody Job job){
        return ResponseEntity.ok(jobRepository.save(job));

    }

    //Find all jobs
    @GetMapping("/jobs")
    public Page<Job> findAllJobs(Pageable pageable){
        return jobRepository.findAll(pageable);
    }

    //Find job given job ID
    @GetMapping("/jobs/{jobID}")
    public Job findJob(@PathVariable UUID jobID){
        return jobRepository.findById(jobID).orElseThrow(()->new ResourceNotFoundException("Job with Id = "+jobID+" cannot be found"));
    }

    //Delete Job  Entry
    @DeleteMapping("/jobs/{jobID}")
    public ResponseEntity<?> deleteApplicant(@PathVariable UUID jobID){

        return jobRepository.findById(jobID).map(job -> {
                    jobRepository.delete(job);
                    return ResponseEntity.ok().build();
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Job with Id = \"+jobID+\" cannot be found"));

    }

    //View Participants for a given job



}
