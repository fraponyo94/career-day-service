package task.sancom.restapi.careerdayservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;
import task.sancom.restapi.careerdayservice.repository.JobApplicantRepository;
import task.sancom.restapi.careerdayservice.repository.JobRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class JobService {

    @Autowired
    private JobApplicantRepository jobApplicantRepository;



    //Save job Interviews selected
    public void saveSelectedJobInterviews(Set<Job> jobInterview, UUID jobApplicantId){
        //get job applicant
        Optional<JobApplicant> applicant = jobApplicantRepository.findById(jobApplicantId);

        applicant.ifPresent(jobApplicant -> {
            jobApplicant.setJobInterviews(jobInterview);
            jobApplicantRepository.save(jobApplicant);

        });



    }



}
