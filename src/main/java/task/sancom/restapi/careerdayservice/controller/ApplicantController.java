package task.sancom.restapi.careerdayservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.sancom.restapi.careerdayservice.entity.Applicant;
import task.sancom.restapi.careerdayservice.exception.ResourceNotFoundException;
import task.sancom.restapi.careerdayservice.repository.ApplicantRepository;

import java.util.UUID;

@RestController
public class ApplicantController {

    @Autowired
    private ApplicantRepository applicantRepository;


    //Add job applicant details
    @RequestMapping(value = "/applicant",method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Applicant save(@RequestBody Applicant applicant){
        return applicantRepository.save(applicant);
    }


    //Update job applicant details
    @RequestMapping(value = "/applicant/{applicantID}",method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public  Applicant update(@PathVariable UUID applicantID,@RequestBody Applicant updatedApplicant){
        return applicantRepository.findById(applicantID).map(applicant -> {
            applicant.setFirstName(updatedApplicant.getFirstName());
            applicant.setLastname(updatedApplicant.getLastname());
            applicant.setEmail(updatedApplicant.getEmail());
            applicant.setPhoneNumber(updatedApplicant.getPhoneNumber());
            applicant.setGender(updatedApplicant.getGender());
            applicant.setEducationLevel(updatedApplicant.getEducationLevel());
            applicant.setDateCreated(updatedApplicant.getDateCreated());
            applicant.setNationality(updatedApplicant.getNationality());
            applicant.setStudyProgramme(updatedApplicant.getStudyProgramme());
            applicant.setYearsOfExperience(updatedApplicant.getYearsOfExperience());
           return applicantRepository.save(applicant);

        }).orElseThrow(()-> new ResourceNotFoundException("Job Applicant with Applicant Id = "+applicantID+" cannot be found"));
    }


    //Find all applicants
    @RequestMapping(value = "/applicants")
    public Page<Applicant> findAll(Pageable pageable){
        return applicantRepository.findAll(pageable);
    }

    //Find Applicant given applicant Id
    @RequestMapping(value="/applicants/{applicantID}")
    public  Applicant findApplicant(@PathVariable UUID applicantID){
        return applicantRepository.findById(applicantID).orElseThrow(()->new ResourceNotFoundException("Job Applicant with Applicant Id = "+applicantID+" cannot be found"));
    }


    //Delete Job applicant Entry
    @RequestMapping(value = "/applicants/{applicantID}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteApplicant(@PathVariable UUID applicantID){

        return applicantRepository.findById(applicantID).map(applicant -> {
                    applicantRepository.delete(applicant);
                    return ResponseEntity.ok().build();
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Job Applicant with Applicant Id = "+applicantID+ " cannot be found"));

    }



}
