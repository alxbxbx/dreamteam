package com.dream.team.Basketball;

import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class DreamTeamTest {
	
	
	@Test
    public void accessToDreamTeam() {
        RestAssured.given().when().get("http://localhost:8080/api/dreamteam").then().statusCode(403);
    }
	
	@Test
    public void accessToPlayers() {
        RestAssured.given().when().get("http://localhost:8080/api/players").then().statusCode(403);
    }
	
	@Test
    public void accessToStats() {
        RestAssured.given().when().get("http://localhost:8080/api/stats").then().statusCode(403);
    }
	
	@Test
	public void testAuthentication() {
        RestAssured.given()
        .when().post("http://localhost:8080/oauth/token").then()
        .statusCode(401);
    }

}
