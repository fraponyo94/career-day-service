package task.sancom.restapi.careerdayservice.component;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;
import task.sancom.restapi.careerdayservice.repository.JobApplicantRepository;
import task.sancom.restapi.careerdayservice.repository.JobRepository;

import java.sql.Time;
import java.time.ZonedDateTime;

import java.util.Date;
import java.util.UUID;

@Component
public class JobInterviewComponent {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobApplicantRepository applicantRepository;

    //Check if a maximum of 20 participants is reached for a given job interview
    public boolean maximumParticipantsReached(Job job) {

        if (job.getJobApplicants() != null) {
                if (job.getJobApplicants().size() == 20) {

                    return true;
                }
            }
       return  false;
    }

    //Check if already enrolled job interviews lie within same interview date and time
    public boolean isJobInterviewConflict(UUID applicandId, Date interviewDate, Date interviewStartTime, Date intervieEndTime){
       boolean isJobInterviewConflict = true ;

        //Find Applicant given applicant Id
        JobApplicant applicant = applicantRepository.findById(applicandId).get();

        if(applicant.getJobInterviews() != null) {
            for (Job job : applicant.getJobInterviews()) {
                if (job.getInterviewDate().equals(interviewDate) && (job.getInterviewEndTime().before(interviewStartTime) ||
                        job.getInterviewStartTime().after(intervieEndTime))) {
                    isJobInterviewConflict = false;
                } else {
                    break;
                }
            }
        }

        return isJobInterviewConflict;


    }


    //Check for maximum of 3 interviews per day for each applicant
    public boolean maximumInterviewsPerDayReached(UUID applicandId,Date interviewDate){
        //Find Applicant given applicant Id
        JobApplicant applicant = applicantRepository.findById(applicandId).get();
        int count =0;

         if(applicant.getJobInterviews() != null){
            for (Job job: applicant.getJobInterviews()){
                if(job.getInterviewDate() == interviewDate){
                    count++;
                }
            }
         }

        //check limit
        if(count >= 3){
            return true;
        }else {
            return false;
        }


    }
}
