package com.dream.team.Basketball;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dream.team.basketball.web.controller.DreamTeamController;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class EndpointIT{
	
//	@Autowired
//    private WebApplicationContext webApplicationContext;
	@Autowired
	DreamTeamController dtc;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(dtc)//.webAppContextSetup(webApplicationContext)
                .build();   
    }
	
	@Test
    public void accessToDreamTeam() throws Exception {
		MockHttpServletRequestBuilder req = options("/api/dreamteam");
		req.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3ByaW5nLWJvb3QtYXBwbGljYXRpb24iXSwidXNlcl9uYW1lIjoiYWx4Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MjEwNjkxNzExNSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiIwOWE2ZjE5ZS1jOTU3LTQyNGYtYTZlNi0xZDg3OGU5ZTBmZGUiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWFwcCJ9.L2VhY3djSKFEK9RiQBmlk_7VqfWyiGRtDhqOf3avofw");
        mockMvc.perform(req).andDo(MockMvcResultHandlers.log())
        .andExpect(status().isOk());
        
    }
	
	@Test
    public void accessToPlayers() throws Exception {
		MockHttpServletRequestBuilder req = options("/api/dreamteam/17");
		req.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3ByaW5nLWJvb3QtYXBwbGljYXRpb24iXSwidXNlcl9uYW1lIjoiYWx4Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MjEwNjkxNzExNSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiIwOWE2ZjE5ZS1jOTU3LTQyNGYtYTZlNi0xZDg3OGU5ZTBmZGUiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWFwcCJ9.L2VhY3djSKFEK9RiQBmlk_7VqfWyiGRtDhqOf3avofw");
        mockMvc.perform(req).andDo(MockMvcResultHandlers.log())
        .andExpect(status().isOk());
    }
	
	
}
