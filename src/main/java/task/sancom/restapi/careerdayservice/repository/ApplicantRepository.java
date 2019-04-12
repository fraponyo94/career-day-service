package task.sancom.restapi.careerdayservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.sancom.restapi.careerdayservice.entity.Applicant;

import java.util.UUID;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
}
