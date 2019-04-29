package task.sancom.restapi.careerdayservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import task.sancom.restapi.careerdayservice.controller.JobController;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;
import task.sancom.restapi.careerdayservice.entity.Qualification;
import task.sancom.restapi.careerdayservice.entity.enumerated.EducationLevel;
import task.sancom.restapi.careerdayservice.entity.enumerated.Gender;
import task.sancom.restapi.careerdayservice.exception.FieldViolationException;
import task.sancom.restapi.careerdayservice.exception.ResourceNotFoundException1;


import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(JobController.class)
public class JobControllerTest {

    @MockBean
    private JobController jobController;

    @Autowired
    private MockMvc mockMvc;


    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected JobApplicant testApplicant() {
        JobApplicant jobApplicant =
                new JobApplicant();
        jobApplicant.setApplicantId(new UUID(22, 22));
        jobApplicant.setEmail("Anne@email.com");
        jobApplicant.setFirstName("Anne");
        jobApplicant.setGender(Gender.MALE);
        jobApplicant.setLastname("");

        jobApplicant.setQualification(new Qualification(EducationLevel.GRADUATE, 1));
        return jobApplicant;
    }

    protected Job testJob(){
        return new Job(new UUID(8,23),"developer","develop any system", "API Engineer",new Date(),new Date(),new Date(System.currentTimeMillis()+20*60*1000),new Qualification(EducationLevel.GRADUATE,1));
    }

    //Test save method
    @Test
    public void saveJobTest() throws Exception{
        Job job = testJob();

           String json = mapToJson(job);

            mockMvc.perform(
                    MockMvcRequestBuilders.post("/job")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn();

    }


          @Test
    public void findAllJobsTest() throws Exception{
        Job job = testJob();

        List<Job> jobList = new ArrayList<Job>();
        jobList.add(job);

        Page<Job> jobs = new PageImpl<>(jobList);



        given(jobController.findAllJobs(Pageable.unpaged())).willReturn((jobs));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get(("/jobs"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());

    }


    @Test
    public void findJobGivenId() throws Exception{
        Job job = testJob();

        given(jobController.findJob(job.getJobId())).willReturn((job));
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/jobs/{jobID}",job.getJobId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())

                .andExpect(MockMvcResultMatchers.jsonPath("$.jobId").isNotEmpty()
                );

    }

    @Test
    public void deleteJobTest() throws Exception{
        Job job = testJob();
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/jobs/{jobID}",job.getJobId().toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    public void getJobParticipantsTest()throws Exception{
        JobApplicant applicant = testApplicant();
        Job job = testJob();

        Set<JobApplicant> applicants = new HashSet<>();
        applicants.add(applicant);
        job.setJobApplicants(applicants);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/jobs/{jobID}/participants",job.getJobId().toString()))

                .andExpect(status().isFound())
                .andDo(print())
                .andReturn();



    }


    @Test
    public void searchJobInterviews() throws Exception{
        Job job = testJob();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/jobs/search?name="+job.getJobName()+"&years-of-experience=0"))

                .andExpect(status().isFound())
                .andDo(print())
                .andReturn();

    }

}
