package task.sancom.restapi.careerdayservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.sancom.restapi.careerdayservice.entity.Job;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository  extends JpaRepository<Job, UUID> {
    Job findByJobIdAAndInterviewDate(UUID jobId, ZonedDateTime date);
    Page<Job> findByJobName(String JobName,Pageable pageable);
    Page<Job> findByInterviewDate(ZonedDateTime interviewDate,Pageable pageable);
    Page<Job> findByInterviewDateAndTypeIgnoreCase(ZonedDateTime interviewDate,String jobType,Pageable pageable);
    Page<Job> findByInterviewDateAndQualification_EducationLevelIgnoreCase(ZonedDateTime interviewDate,String eductationLevel,Pageable pageable);
    Page<Job> findByQualification_YearsOfExperience(int yearsOfExperience,Pageable pageable);
    Page<Job> findByInterviewDateAndQualification_YearsOfExperience(ZonedDateTime interviewDate,int yearsOfExperience,Pageable pageable);
    Page<Job> findByInterviewDateAndTypeIgnoreCaseAndQualification_EducationLevelIgnoreCaseAndQualification_YearsOfExperience(
            ZonedDateTime interviewDate,String jobType,String educationLevel,int yearsOfExperience ,Pageable pageable  );
    Page<Job> findByQualification_EducationLevel(String educationLevel,Pageable pageable);

}