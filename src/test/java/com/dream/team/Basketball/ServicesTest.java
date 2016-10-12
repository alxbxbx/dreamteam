package com.dream.team.Basketball;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.dream.team.basketball.entity.DreamTeam;
import com.dream.team.basketball.service.DreamTeamService;
import com.dream.team.basketball.web.controller.DreamTeamController;


public class ServicesTest {
	
	DreamTeamController dtc = new DreamTeamController();
	
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
