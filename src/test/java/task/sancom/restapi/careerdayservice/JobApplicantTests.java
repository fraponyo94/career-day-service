package task.sancom.restapi.careerdayservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import task.sancom.restapi.careerdayservice.controller.JobApplicantController;
import task.sancom.restapi.careerdayservice.entity.JobApplicant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



@RunWith(SpringRunner.class)
@WebMvcTest(JobApplicantController.class)
public class JobApplicantTests {
   @Autowired
   private MockMvc mockMvc;



    @Test
    public void findApplicantByIdTest() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/applicants/{applicantID}","")
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.applicants").exists())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.applicants[*].applicantID").isNotEmpty()
                        );

    }

    @Test
    public void findAllApplicantsTest() throws Exception{
        mockMvc.perform( MockMvcRequestBuilders
                .get("/applicants")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.applicants").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].applicantID").isNotEmpty());
    }


}
