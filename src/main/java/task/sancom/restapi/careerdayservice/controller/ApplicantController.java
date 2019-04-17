package task.sancom.restapi.careerdayservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;
import task.sancom.restapi.careerdayservice.exception.ResourceNotFoundException;
import task.sancom.restapi.careerdayservice.repository.ApplicantRepository;

import java.util.UUID;

@RestController
public class ApplicantController {

    @Autowired
    private ApplicantRepository applicantRepository;


    //Add job jobApplicant details
    @PostMapping( "/applicant")
    public ResponseEntity<?> save(@RequestBody JobApplicant jobApplicant){
        return ResponseEntity.ok(applicantRepository.save(jobApplicant));
    }


    //Update job applicant details
    @PutMapping("/applicant/{applicantID}")
    public  ResponseEntity<JobApplicant> update(@PathVariable UUID applicantID, @RequestBody JobApplicant updatedJobApplicant){
        return applicantRepository.findById(applicantID).map(jobApplicant -> {
            jobApplicant.setFirstName(updatedJobApplicant.getFirstName());
            jobApplicant.setLastname(updatedJobApplicant.getLastname());
            jobApplicant.setEmail(updatedJobApplicant.getEmail());
            jobApplicant.setPhoneNumber(updatedJobApplicant.getPhoneNumber());
            jobApplicant.setGender(updatedJobApplicant.getGender());
            jobApplicant.getQualification().setEducationLevel(updatedJobApplicant.getQualification().getEducationLevel());
            jobApplicant.setDateCreated(updatedJobApplicant.getDateCreated());
            jobApplicant.setNationality(updatedJobApplicant.getNationality());
            jobApplicant.setStudyProgramme(updatedJobApplicant.getStudyProgramme());
            jobApplicant.getQualification().setYearsOfExperience(updatedJobApplicant.getQualification().getYearsOfExperience());
           applicantRepository.save(jobApplicant);
           return ResponseEntity.ok(jobApplicant);

        }).orElseThrow(()-> new ResourceNotFoundException("Job JobApplicant with JobApplicant Id = "+applicantID+" cannot be found"));
    }


    //Find all applicants
    @GetMapping("/applicants")
    public Page<JobApplicant> findAll(Pageable pageable){
        return applicantRepository.findAll(pageable);
    }

    //Find JobApplicant given applicant Id
    @GetMapping("/applicants/{applicantID}")
    public JobApplicant findApplicant(@PathVariable UUID applicantID){
        return applicantRepository.findById(applicantID).orElseThrow(()->new ResourceNotFoundException("Job JobApplicant with JobApplicant Id = "+applicantID+" cannot be found"));
    }


    //Delete Job applicant Entry
    @DeleteMapping("/applicants/{applicantID}")
    public ResponseEntity<?> deleteApplicant(@PathVariable UUID applicantID){

        return applicantRepository.findById(applicantID).map(jobApplicant -> {
                    applicantRepository.delete(jobApplicant);
                    return ResponseEntity.ok().build();
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Job JobApplicant with JobApplicant Id = "+applicantID+ " cannot be found"));

    }



}
