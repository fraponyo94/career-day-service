package task.sancom.restapi.careerdayservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import task.sancom.restapi.careerdayservice.controller.JobApplicantController;
import task.sancom.restapi.careerdayservice.entity.Job;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;
import task.sancom.restapi.careerdayservice.entity.Qualification;
import task.sancom.restapi.careerdayservice.entity.enumerated.EducationLevel;
import task.sancom.restapi.careerdayservice.entity.enumerated.Gender;
import task.sancom.restapi.careerdayservice.repository.JobApplicantRepository;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.*;

@RunWith(SpringRunner.class)
@WebMvcTest(JobApplicantController.class)
public class JobApplicantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  JobApplicantController jobApplicantController;

    @MockBean
    private JobApplicantRepository jobApplicantRepository;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected JobApplicant testApplicant(){
        JobApplicant jobApplicant =
                new JobApplicant();
        jobApplicant.setApplicantId(new UUID(22,22));
        jobApplicant.setEmail("Anne@email.com");
        jobApplicant.setFirstName("Anne");
        jobApplicant.setGender(Gender.MALE);
        jobApplicant.setLastname("");
        jobApplicant.setNationality("Kennyan");
        jobApplicant.setPhoneNumber(1000000001);
        jobApplicant.setQualification(new Qualification(EducationLevel.GRADUATE,1));
        return jobApplicant;

    }

    public Job testJob(){
        return new Job(new UUID(22,22),"developer","develop any system", ZonedDateTime.now(),ZonedDateTime.now().minusHours(2),ZonedDateTime.now(),new Qualification(EducationLevel.GRADUATE,1));
    }


    //Find all applicants Tests
    @Test
    public void findAllApplicants() throws Exception{
        JobApplicant applicant = testApplicant();

        List<JobApplicant> applicants = new ArrayList<>();
        applicants.add(applicant);

        Page<JobApplicant> jobApplicants = new PageImpl<>(applicants);



        given(jobApplicantController.findAll(Pageable.unpaged())).willReturn((jobApplicants));

       mockMvc.perform(
               MockMvcRequestBuilders
                       .get(("/applicants"))
                       .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                       .andExpect(status().isOk());


    }


    @Test
    public void findApplicantGivenId() throws Exception {
        JobApplicant applicant = testApplicant();

        given(jobApplicantController.findApplicant(applicant.getApplicantId())).willReturn(applicant);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/applicants/{applicantID}",applicant.getApplicantId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.applicantId").isNotEmpty()
                    );

    }

    @Test
    public  void deleteApplicant() throws Exception{
        JobApplicant jobApplicant = testApplicant();
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/applicants/{applicantID}",jobApplicant.getApplicantId().toString())
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk());

    }


    @Test
    public void saveTest() throws Exception{

        JobApplicant jobApplicant = testApplicant();

       given(jobApplicantController.save(jobApplicant)).willReturn(jobApplicant);

      String json = mapToJson(jobApplicant);


        mockMvc.perform(
                MockMvcRequestBuilders.post("/applicants")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                        .andExpect(status().isCreated())
                        .andDo(print())
                        .andReturn();


    }

    @Test
    public  void updateApplicantTest() throws Exception{
        JobApplicant jobApplicant = testApplicant();
        JobApplicant saved = jobApplicantRepository.save(jobApplicant);
        assertThat(saved.getFirstName()).isNotNull();
        System.out.println(saved);


//        //update saved user
//        saved.setFirstName("updated");
//
//        given(jobApplicantController.update(jobApplicant.getApplicantId(),saved)).willReturn(jobApplicant);
//
//        String updateBody = mapToJson(saved);
//
//     mockMvc.perform(
//        MockMvcRequestBuilders
//                .put("/applicants/{applicantID}",jobApplicant.getApplicantId())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(updateBody))
//                .andExpect(status().isAccepted())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("updated"))
//                .andDo(print())
//                .andReturn();





    }

    @Test
    public void selectJobInterviewsTest() throws Exception{
        JobApplicant applicant = testApplicant();
        Job job = testJob();

        Set<Job> jobInterviews = new HashSet<>();
        jobInterviews.add(job);
        applicant.setJobInterviews(jobInterviews);

        //when(jobApplicantController.selectJobInterviews(job.getJobId(),applicant.getApplicantId())).then(status().isOk());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("applicants/{jobApplicantId}/select",applicant.getApplicantId().toString())
                        .param("id",job.getJobId().toString()))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn();




    }





}
