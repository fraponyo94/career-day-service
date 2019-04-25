package task.sancom.restapi.careerdayservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.entity.enumerated.EducationLevel;
import task.sancom.restapi.careerdayservice.exception.FieldViolationException;
import task.sancom.restapi.careerdayservice.exception.ResourceNotFoundException1;
import task.sancom.restapi.careerdayservice.repository.JobRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobService {
    @Autowired
    private JobRepository repository;

    //Save Job
    public void saveJob(Job job) throws FieldViolationException{
        if(job.getInterviewStartTime().after(job.getInterviewEndTime())){
            throw new FieldViolationException(Job.class,"Interview Start Time cannot be after Interview End Time","");
        }else {
            repository.save(job);
        }
    }

    //ffnd All
       public Page<Job> findAll(Pageable pageable) throws ResourceNotFoundException1{
            Page<Job> jobs = repository.findAll(pageable);
            if(!jobs.isEmpty()){
                return jobs;
            }else {
                new ResourceNotFoundException1(Job.class, "No Jobs were Found","");
            }
       }

       //Find by Id
        public Optional<Job> findById(UUID jobId) throws ResourceNotFoundException1{
               Optional<Job> job = repository.findById(jobId);
               if (job.isPresent()){
                   return job;
               }else {
                   throw new ResourceNotFoundException1(Job.class, "No Job with id", job.get().getJobId() + "NoT FOUND");
               }

        }

        //Delete
        public  void delete(UUID jobId) throws ResourceNotFoundException1 {
            Job job = repository.findById(jobId).get();
            if (job != null) {
                repository.delete(job);
            } else {
                throw new ResourceNotFoundException1(Job.class, "No Job with id", job.getJobId() + "exist");
            }

        }

        public Page<Job> searchJob(Date interviewDate,String type, String educationLevel,int yearsOfExperience,
                                    String jobName,Pageable pageable) throws ResourceNotFoundException1{

                        if(interviewDate !=null){
                            if(type != null){
                                    return  repository.findByInterviewDateAndTypeIgnoreCase(interviewDate,type,pageable);
                                }
                            else if(educationLevel != null){
                                return repository.findByInterviewDateAndQualification_EducationLevelIgnoreCase(interviewDate,educationLevel,pageable);
                            }else if(yearsOfExperience > 0){
                                return repository.findByInterviewDateAndQualification_YearsOfExperience(interviewDate,yearsOfExperience,pageable);
                            }else {
                                return repository.findByInterviewDate(interviewDate,pageable);
                            }

                        }else if ( jobName!= null){
                            return repository.findByJobName(jobName,pageable);
                        }else if(yearsOfExperience > 0){
                            return repository.findByQualification_YearsOfExperience(yearsOfExperience,pageable);
                        }else if(educationLevel != null){
                            return  repository.findByQualification_EducationLevel(educationLevel,pageable);
                        }else {

                            throw  new ResourceNotFoundException1(Job.class,"No Resource was found","");

                        }

                    }
    }

