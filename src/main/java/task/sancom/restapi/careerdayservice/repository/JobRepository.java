package task.sancom.restapi.careerdayservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.sancom.restapi.careerdayservice.entity.Job;

import java.util.UUID;

@Repository
public interface JobRepository  extends JpaRepository<Job, UUID> {
    Job findDistinctByJobName(String jobName);
}
