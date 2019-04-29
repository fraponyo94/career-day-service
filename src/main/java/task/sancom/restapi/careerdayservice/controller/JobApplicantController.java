package task.sancom.restapi.careerdayservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.sancom.restapi.careerdayservice.component.JobInterviewComponent;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;
import task.sancom.restapi.careerdayservice.exception.*;
import task.sancom.restapi.careerdayservice.repository.JobApplicantRepository;
import task.sancom.restapi.careerdayservice.repository.JobRepository;

import javax.validation.Valid;
import java.util.*;

@RestController
public class JobApplicantController {

    private JobApplicantRepository applicantRepository;
    private JobInterviewComponent jobInterviewComponent;
    private JobRepository jobRepository;

    @Autowired
    public JobApplicantController(JobApplicantRepository applicantRepository, JobInterviewComponent jobInterviewComponent, JobRepository jobRepository) {
        this.applicantRepository = applicantRepository;
        this.jobInterviewComponent = jobInterviewComponent;
        this.jobRepository = jobRepository;
    }

    //Add job jobApplicant details
    @PostMapping( "/applicant")
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplicant save(@RequestBody @Valid JobApplicant jobApplicant) throws ResourceNotFoundException1{
        if(jobApplicant != null) {
           return applicantRepository.save(jobApplicant);
        }else {
            throw new ResourceNotFoundException1(JobApplicant.class,"Resource is empty","Please provide some data to save");
        }
    }


    //Update job applicant details
    @PutMapping("/applicants/{applicantId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  JobApplicant update( @RequestBody @Valid JobApplicant updatedJobApplicant,@PathVariable UUID applicantId) throws ResourceNotFoundException1 {


        Optional<JobApplicant> jobApplicant1 = applicantRepository.findById(applicantId);
        if (jobApplicant1.isPresent()) {
            JobApplicant jobApplicant = jobApplicant1.get();
            jobApplicant.setFirstName(updatedJobApplicant.getFirstName());
            jobApplicant.setLastname(updatedJobApplicant.getLastname());
            jobApplicant.getQualification().setEducationLevel(updatedJobApplicant.getQualification().getEducationLevel());
            jobApplicant.getQualification().setYearsOfExperience(updatedJobApplicant.getQualification().getYearsOfExperience());
            jobApplicant.setGender(updatedJobApplicant.getGender());
            jobApplicant.setEmail(updatedJobApplicant.getEmail());
            jobApplicant.setStudyProgramme(updatedJobApplicant.getStudyProgramme());
            jobApplicant.setPhoneNumber(updatedJobApplicant.getPhoneNumber());

            return applicantRepository.save(jobApplicant);

        } else {
            throw new ResourceNotFoundException1(JobApplicant.class, "Job-Applicant ID", updatedJobApplicant.getApplicantId().toString());
        }
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
    @PutMapping("applicants/{jobApplicantId}/select")
    @ResponseStatus(HttpStatus.OK)
    public  void selectJobInterviews(@RequestParam("id") UUID jobID,@PathVariable UUID jobApplicantId)
    throws MaximumJobsAppliedException,JobHasMaximumParticipantsException,JobConflictException,ResourceNotFoundException1{

        //Job intervie selected
        Optional<Job> jobInterviewSelected1 = jobRepository.findById(jobID);

        //Find the job applicant object
        Optional<JobApplicant> applicant1 = applicantRepository.findById(jobApplicantId);

        if(applicant1.isPresent() && jobInterviewSelected1.isPresent()) {
            Job jobInterviewSelected = jobInterviewSelected1.get();
            JobApplicant applicant = applicant1.get();
            //check if Job has Maximum participants
            if (!jobInterviewComponent.maximumParticipantsReached(jobInterviewSelected)) {

                if (!jobInterviewComponent.maximumInterviewsPerDayReached(jobApplicantId, jobInterviewSelected.getInterviewDate())) {

                    if (jobInterviewComponent.isJobInterviewConflict(jobApplicantId, jobInterviewSelected.getInterviewDate()
                            , jobInterviewSelected.getInterviewStartTime(), jobInterviewSelected.getInterviewEndTime())) {

                            Set<Job> jobInterviews = new HashSet<>();
                            jobInterviews.add(jobInterviewSelected);
                            applicant.setJobInterviews(jobInterviews);

                            applicantRepository.save(applicant);


                    } else {
                        throw new JobConflictException(Job.class,"Job interviews Conflict","");
                    }
                }else {
                    throw new MaximumJobsAppliedException(Job.class,"Youu have reached your maximum job Application","");
                }
            } else{
                throw new JobHasMaximumParticipantsException(Job.class,"Maximum number of participants reached.Please try again later","");


                }
        }else {
            throw new ResourceNotFoundException1(JobApplicant.class,"Job Applicant with id = ",jobApplicantId.toString());
        }

    }


    //DeSelect job interviews to participate opt out
    @PutMapping("applicants/{jobApplicantId}/deselect")
    @ResponseStatus(HttpStatus.OK)
   public void deselectJobInterviews(@PathVariable UUID jobApplicantId,@RequestParam("id") UUID jobID ) throws ResourceNotFoundException1{

        //Job intervie selected
        Job jobInterviewdeselected = jobRepository.findById(jobID).get();
       //Find the job applicant object
       JobApplicant applicant = applicantRepository.findById(jobApplicantId).get();

       if(applicant != null){

           if(applicant.getJobInterviews().contains(jobInterviewdeselected)){
               applicant.getJobInterviews().remove(jobInterviewdeselected);
               applicantRepository.save(applicant);
           }else{
               throw new ResourceNotFoundException1(Job.class,"Job ID",jobID.toString());
           }
       }else {
           throw new ResourceNotFoundException1(JobApplicant.class,"Job-Applicant ID",jobApplicantId.toString());
       }
   }


    //Job interviews an applicant has enrolled
    @GetMapping("applicants/job-interviews")
    public Set<Job> viewJobInterviewsPerApplicant(@RequestParam("applicant-id") UUID applicantId) throws ResourceNotFoundException1 {

        return applicantRepository.findById(applicantId).map(applicant -> {
            return applicant.getJobInterviews();
        }).orElseThrow(() ->new ResourceNotFoundException1(JobApplicant.class,"Job-Applicant ID",applicantId.toString()));
    }










}
