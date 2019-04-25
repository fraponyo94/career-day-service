package task.sancom.restapi.careerdayservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.sancom.restapi.careerdayservice.entity.Job;


import java.util.Date;

import java.util.UUID;

@Repository
public interface JobRepository  extends JpaRepository<Job, UUID> {
    Page<Job> findByJobName(String JobName,Pageable pageable);
    Page<Job> findByInterviewDate(Date interviewDate,Pageable pageable);

    Page<Job> findByInterviewDateAndJobTypeIgnoreCase(Date interviewDate,String jobType,Pageable pageable);
    Page<Job> findByInterviewDateAndQualification_EducationLevelIgnoreCase(Date interviewDate,String eductationLevel,Pageable pageable);
    Page<Job> findByQualification_YearsOfExperience(int yearsOfExperience,Pageable pageable);
    Page<Job> findByInterviewDateAndQualification_YearsOfExperience(Date interviewDate,int yearsOfExperience,Pageable pageable);
    Page<Job> findByQualification_EducationLevel(String educationLevel,Pageable pageable);


}