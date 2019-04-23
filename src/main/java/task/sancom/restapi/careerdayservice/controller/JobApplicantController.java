package task.sancom.restapi.careerdayservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.sancom.restapi.careerdayservice.component.JobInterviewComponent;
import task.sancom.restapi.careerdayservice.component.TimeFormatterComponent;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;
import task.sancom.restapi.careerdayservice.exception.*;
import task.sancom.restapi.careerdayservice.repository.JobApplicantRepository;
import task.sancom.restapi.careerdayservice.repository.JobRepository;

import javax.validation.Valid;
import java.util.HashSet;

import java.util.Set;
import java.util.UUID;

@RestController
public class JobApplicantController {

    private JobApplicantRepository applicantRepository;
    private JobInterviewComponent jobInterviewComponent;
    private JobRepository jobRepository;

    public JobApplicantController(JobApplicantRepository applicantRepository, JobInterviewComponent jobInterviewComponent, JobRepository jobRepository) {
        this.applicantRepository = applicantRepository;
        this.jobInterviewComponent = jobInterviewComponent;
        this.jobRepository = jobRepository;
    }

    //Add job jobApplicant details
    @PostMapping( "/applicants")
    public ResponseEntity<?> save(@RequestBody @Valid JobApplicant jobApplicant){

        return ResponseEntity.ok(applicantRepository.save(jobApplicant));
    }


    //Update job applicant details
    @PutMapping("/applicants/{applicantID}")
    public  ResponseEntity<JobApplicant> update(@PathVariable UUID applicantID, @RequestBody @Valid JobApplicant updatedJobApplicant) throws ResourceNotFoundException1{


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

            }).orElseThrow(()-> new ResourceNotFoundException1(JobApplicant.class,"Job-Applicant ID",applicantID.toString()));



    }


    //Find all applicants
    @GetMapping("/applicants")
    public Page<JobApplicant> findAll(Pageable pageable){
        return applicantRepository.findAll(pageable);
    }


    //Find JobApplicant given applicant Id
    @GetMapping("/applicants/{applicantID}")
    public JobApplicant findApplicant(@PathVariable UUID applicantID) throws ResourceNotFoundException1{

        return applicantRepository.findById(applicantID).orElseThrow(()->new ResourceNotFoundException1(JobApplicant.class,"Job-Applicant ID",applicantID.toString()));
    }


    //Delete Job applicant Entry
    @DeleteMapping("/applicants/{applicantID}")
    public ResponseEntity<?> deleteApplicant(@PathVariable UUID applicantID) throws ResourceNotFoundException1{

        return applicantRepository.findById(applicantID).map(jobApplicant -> {
                    applicantRepository.delete(jobApplicant);
                    return ResponseEntity.ok().build();
                }
        ).orElseThrow(() -> new ResourceNotFoundException1(JobApplicant.class,"Job-Applicant ID",applicantID.toString()));

    }

    //Select job interviews to participate in
    @PostMapping("applicants/{jobApplicantId}/select")
    public  ResponseEntity<?> selectJobInterviews(@RequestParam("id") UUID jobID,@PathVariable UUID jobApplicantId)
    throws MaximumJobsAppliedException,JobHasMaximumParticipantsException,JobConflictException,ResourceNotFoundException1{

        //Job intervie selected
        Job jobInterviewSelected = jobRepository.findById(jobID).get();

        //Find the job applicant object
        JobApplicant applicant = applicantRepository.findById(jobApplicantId).get();

        if(applicant != null && jobInterviewSelected != null) {
            //check if Job has Maximum participants
            if (!jobInterviewComponent.maximumParticipantsReached(jobApplicantId, jobInterviewSelected.getInterviewDate())) {
                if (!jobInterviewComponent.maximumInterviewsPerDayReached(jobApplicantId, jobInterviewSelected.getInterviewDate())) {

                    if (!jobInterviewComponent.isJobInterviewConflict(jobApplicantId, jobInterviewSelected.getInterviewDate()
                            , jobInterviewSelected.getInterviewStartTime(), jobInterviewSelected.getInterviewEndTime())) {

                            Set<Job> jobInterviews = new HashSet<>();
                            jobInterviews.add(jobInterviewSelected);
                            applicant.setJobInterviews(jobInterviews);

                            return   ResponseEntity.ok(applicantRepository.save(applicant));


                    } else {
                        throw new JobConflictException(Job.class,"Job interviews Conflict. You have already enrolled for another job interview which lies within the same interview date and time selected");
                    }
                }else {
                    throw new MaximumJobsAppliedException(Job.class,"Youu have reached your maximum job Application");
                }
            } else{
                throw new JobHasMaximumParticipantsException(Job.class,"Maximum number of participants reached.Please try again later");


                }
        }else {
            throw new ResourceNotFoundException1(JobApplicant.class,"Job Applicant with id = ",jobApplicantId.toString());
        }

    }


    //DeSelect job interviews to participate opt out
    @PostMapping("applicants/{jobApplicantId}/deselect")
   public ResponseEntity<?> deselectJobInterviews(@PathVariable UUID jobApplicantId,@RequestParam("id") UUID jobID ) throws ResourceNotFoundException1{

        //Job intervie selected
        Job jobInterviewdeselected = jobRepository.findById(jobID).get();
       //Find the job applicant object
       JobApplicant applicant = applicantRepository.findById(jobApplicantId).get();

       if(applicant != null){

           if(applicant.getJobInterviews().contains(jobInterviewdeselected)){
               applicant.getJobInterviews().remove(jobInterviewdeselected);
               return ResponseEntity.ok(applicantRepository.save(applicant));
           }else{
               throw new ResourceNotFoundException1(Job.class,"Job ID",jobID.toString());
           }
       }else {
           throw new ResourceNotFoundException1(JobApplicant.class,"Job-Applicant ID",jobApplicantId.toString());
       }
   }


   //List job interviews an applicant has enrolled for
    @GetMapping("applicants/{applicantId}/job-interviews")
    public Set<Job> viewJobInterviewsPerApplicant(@PathVariable UUID applicantId) throws ResourceNotFoundException1 {

        return applicantRepository.findById(applicantId).map(applicant -> {
            return applicant.getJobInterviews();
        }).orElseThrow(() ->new ResourceNotFoundException1(JobApplicant.class,"Job-Applicant ID",applicantId.toString()));
    }










}
