package com.dream.team.Basketball;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.dream.team.basketball.entity.DreamTeam;
import com.dream.team.basketball.service.DreamTeamService;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ServicesTest {
	
	private DreamTeamService dts;
	
	@Before
    public void setup(){
		dts = Mockito.mock(DreamTeamService.class);
	}
	
	@Test(expected = NullPointerException.class)
    public void findOneDreamTeam(){
		DreamTeam dt = dts.findOne(0);
		dt.getName();
	}
	
	@Test
    public void findAllDreamTeam(){
		assertTrue(dts.findAll() instanceof java.util.List<?>);
	}
	
	@Test
    public void saveEmptyObject(){
		assertEquals(null, dts.save(new DreamTeam()));
	}
	
	

}
