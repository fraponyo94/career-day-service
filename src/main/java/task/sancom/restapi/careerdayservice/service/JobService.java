package task.sancom.restapi.careerdayservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;
import task.sancom.restapi.careerdayservice.repository.JobRepository;

import java.util.Set;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    //Find


}
