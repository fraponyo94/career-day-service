package task.sancom.restapi.careerdayservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.sancom.restapi.careerdayservice.component.JobInterviewComponent;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;
import task.sancom.restapi.careerdayservice.exception.JobConflictException;
import task.sancom.restapi.careerdayservice.exception.JobHasMaximumParticipantsException;
import task.sancom.restapi.careerdayservice.exception.MaximumJobsAppliedException;
import task.sancom.restapi.careerdayservice.exception.ResourceNotFoundException;
import task.sancom.restapi.careerdayservice.repository.JobApplicantRepository;

import java.util.HashSet;

import java.util.Set;
import java.util.UUID;

@RestController
public class JobApplicantController {

    @Autowired
    private JobApplicantRepository applicantRepository;

    @Autowired
    private JobInterviewComponent jobInterviewComponent;



    //Add job jobApplicant details
    @PostMapping( "/applicants")
    public ResponseEntity<?> save(@RequestBody JobApplicant jobApplicant){
        return ResponseEntity.ok(applicantRepository.save(jobApplicant));
    }


    //Update job applicant details
    @PutMapping("/applicants/{applicantID}")
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

    //Select job interviews to participate in
    @PostMapping("applicants/{jobApplicantId}/select/job-interviews")
    public  ResponseEntity<?> selectJobInterviews(Job jobInterviewSelected,@PathVariable UUID jobApplicantId){
        //Find the job applicant object
        JobApplicant applicant = applicantRepository.findById(jobApplicantId).get();

        if(applicant != null) {
            //check if Job has Maximum participants
            if (jobInterviewComponent.maximumInterviewsPerDayReached(jobApplicantId, jobInterviewSelected.getInterviewDate())) {
                throw new JobHasMaximumParticipantsException();
            } else if (jobInterviewComponent.isJobInterviewConflict(jobApplicantId, jobInterviewSelected.getInterviewDate()
                    , jobInterviewSelected.getInterviewStartTime(), jobInterviewSelected.getInterviewEndTime())) {
                throw new JobConflictException();

            } else if (jobInterviewComponent.maximumInterviewsPerDayReached(jobApplicantId, jobInterviewSelected.getInterviewDate())) {
                throw new MaximumJobsAppliedException();
            } else {
                Set<Job> jobInterviews = new HashSet<>();
                jobInterviews.add(jobInterviewSelected);
                applicant.setJobInterviews(jobInterviews);

              return   ResponseEntity.ok(applicantRepository.save(applicant));

            }
        }else {
            throw new ResourceNotFoundException("Job Applicant with id = "+jobApplicantId+" cannot be found");
        }

    }


    //DeSelect job interviews to participate opt out
    @PostMapping("applicants/{jobApplicantId}/deselect/job-interviews")
   public ResponseEntity<?> deselectJobInterviews(@PathVariable UUID jobApplicantId,Job jobInterviewdeselected ){
       //Find the job applicant object
       JobApplicant applicant = applicantRepository.findById(jobApplicantId).get();

       if(applicant != null){
           if(applicant.getJobInterviews().contains(jobInterviewdeselected)){
               applicant.getJobInterviews().remove(jobInterviewdeselected);
               return ResponseEntity.ok(applicantRepository.save(applicant));
           }else{
               throw new ResourceNotFoundException("Deselected Job interview does not exist.Ensure you select first before deselecting");
           }
       }else {
           throw new ResourceNotFoundException("Job Applicant with id = "+jobApplicantId+" cannot be found");
       }
   }


   //List job interviews an applicant has enrolled for
    @GetMapping("applicants/{applicantId}/job-interviews")
    public Set<Job> viewJobInterviewsPerApplicant(@PathVariable UUID applicantId) {

        return applicantRepository.findById(applicantId).map(applicant -> {
            return applicant.getJobInterviews();
        }).orElseThrow(() -> new ResourceNotFoundException("Job with Id = " + applicantId + " cannot be found"));
    }










}
