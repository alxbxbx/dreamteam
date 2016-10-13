package com.dream.team.basketball.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dream.team.basketball.entity.DreamTeam;
import com.dream.team.basketball.entity.User;
import com.dream.team.basketball.service.DreamTeamService;
import com.dream.team.basketball.service.UserService;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@RestController
@RequestMapping("api/export")
public class ExportController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	DreamTeamService dts;
	
	@Autowired
    Environment env;
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public void getUsersExcel(HttpServletResponse response){
		List<User> users = userService.findAll();
		File exlFile = null;
		InputStream is = null;
		
		try{
			String path = env.getProperty("exportPath")  + "myfile.xls";
			exlFile = new File(path);
	        WritableWorkbook writableWorkbook = Workbook
	                .createWorkbook(exlFile);

	        WritableSheet writableSheet = writableWorkbook.createSheet(
	                "Sheet1", 0);
	        
	        
	        writableSheet.addCell(new Label(0, 0, "ID"));
	        writableSheet.addCell(new Label(1, 0, "Username"));
	        writableSheet.addCell(new Label(2, 0, "Name"));
	        writableSheet.addCell(new Label(3, 0, "Last name"));
	        writableSheet.addCell(new Label(4, 0, "Role"));
	        writableSheet.addCell(new Label(5, 0, "Email"));
	        
	        int counter = 2;
	        
	        for(User user : users){
	        	writableSheet.addCell(new Label(0, counter, user.getId().toString()));
		        writableSheet.addCell(new Label(1, counter, user.getUsername()));
		        writableSheet.addCell(new Label(2, counter, user.getName()));
		        writableSheet.addCell(new Label(3, counter, user.getLastName()));
		        writableSheet.addCell(new Label(4, counter, user.getRole()));
		        writableSheet.addCell(new Label(5, counter, user.getEmail()));
		        counter ++;
	        }
	        
	        writableWorkbook.write();
	        writableWorkbook.close();
	        
	        
	        //Setting type of response
	        String mimeType = "application/octet-stream";
            response.setContentType(mimeType);
            response.setContentLength((int) exlFile.length());
	        
            //Setting header content
	        String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    exlFile.getName());
            response.setHeader(headerKey, headerValue);
	        
	        is = new FileInputStream(exlFile);
	        IOUtils.copy(is, response.getOutputStream());
	        is.close();
	        response.flushBuffer();
	        
			
		}catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }catch(IOException e){
        	e.printStackTrace();
        }
		
	}
	
	@RequestMapping(value="/dreamteam/{id}", method=RequestMethod.GET)
	public void getDreamTeamExcel(HttpServletResponse response, @PathVariable Integer id) throws JSONException{
		DreamTeam dt = dts.findOne(id);
		File exlFile = null;
		InputStream is = null;
		try{
			String path = env.getProperty("exportPath")  + "myfile.xls";
			exlFile = new File(path);
	        WritableWorkbook writableWorkbook = Workbook
	                .createWorkbook(exlFile);

	        WritableSheet writableSheet = writableWorkbook.createSheet(
	                "Sheet1", 0);
	        
	        //Formating sheets and layout
	        Label tn = new Label(0, 0, "Team name");
	        Label oi = new Label(1, 0, "Overal index");
	        Label teamName = new Label(0, 1, dt.getName());
	        Label overalIndex = new Label(1, 1, dt.getSuccessIndex().toString());
	        
	        Label pn = new Label(0, 3, "Player name");
	        Label pos = new Label(1, 3, "Positions");
	        Label year = new Label(2, 3, "Year");
	        Label pts = new Label(3, 3, "Points");
	        Label reb = new Label(4, 3, "Rebounds");
	        Label asts = new Label(5, 3, "Assists");
	        Label stl = new Label(6, 3, "Steals");
	        Label blk = new Label(7, 3, "Blocks");
	        Label to = new Label(8, 3, "Turnovers");
	        Label misses = new Label(9, 3, "Misses");
	        
	        
	        writableSheet.addCell(tn);
	        writableSheet.addCell(oi);
	        writableSheet.addCell(teamName);
	        writableSheet.addCell(overalIndex);
	        
	        
	        writableSheet.addCell(pn);
	        writableSheet.addCell(pos);
	        writableSheet.addCell(year);
	        writableSheet.addCell(pts);
	        writableSheet.addCell(reb);
	        writableSheet.addCell(asts);
	        writableSheet.addCell(stl);
	        writableSheet.addCell(blk);
	        writableSheet.addCell(to);
	        writableSheet.addCell(misses);
	        
	        //Getting statistics from another service
	        URL players = new URL("http://localhost:8888/api/stats");
	        URLConnection yc = players.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	        String inputLine;
	        inputLine = in.readLine();
	        in.close();
	       
	        JSONObject jsonObject = new JSONObject(inputLine);
	        JSONArray jsonPlayers = (JSONArray) jsonObject.get("players");
	        
	        
	        //Iterating through results and writting in excel
	        int counter = 4;
	        for(String strId : dt.getPlayers()){
	        	for(int i=0; i<jsonPlayers.length(); i++){
		        	if(strId.equals(jsonPlayers.getJSONObject(i).getString("id"))){
		        		JSONObject onePlayer = jsonPlayers.getJSONObject(i);
		        		String name = onePlayer.getString("firstname") + onePlayer.getString("lastname");
		        		JSONArray jsonPos = onePlayer.getJSONArray("pos");
		        		String positions = jsonPos.getString(0);
		        		if(jsonPos.length() > 1)
		        			positions = positions + " " +  jsonPos.getString(1);
		        		
		        		String strPts = String.valueOf(onePlayer.getInt("pts"));
		        		String strReb = String.valueOf(onePlayer.getInt("reb"));
		        		String strAsts = String.valueOf(onePlayer.getInt("asts"));
		        		String strStl = String.valueOf(onePlayer.getInt("stl"));
		        		String strBlk = String.valueOf(onePlayer.getInt("blk"));
		        		String strTo = String.valueOf(onePlayer.getInt("to"));
		        		int fg = onePlayer.getInt("fga") - onePlayer.getInt("fgm");
		        		int ft = onePlayer.getInt("fta") - onePlayer.getInt("ftm");
		        		Integer intMisses = fg + ft;
		        		
		        		writableSheet.addCell(new Label(0, counter, name));
		        		writableSheet.addCell(new Label(1, counter, positions));
		        		writableSheet.addCell(new Label(2, counter, onePlayer.getString("year")));
		        		writableSheet.addCell(new Label(3, counter, strPts));
		        		writableSheet.addCell(new Label(4, counter, strReb));
		        		writableSheet.addCell(new Label(5, counter, strAsts));
		        		writableSheet.addCell(new Label(6, counter, strStl));
		        		writableSheet.addCell(new Label(7, counter, strBlk));
		        		writableSheet.addCell(new Label(8, counter, strTo));
		        		writableSheet.addCell(new Label(9, counter, intMisses.toString()));
		        		
		        		counter ++;
		        		break;
		        		
		        		
		        	}
		        }
	        }
	        
	        
	        

	        //Write and close the workbook
	        writableWorkbook.write();
	        writableWorkbook.close();
	        
	        String mimeType = "application/octet-stream";
            response.setContentType(mimeType);
            response.setContentLength((int) exlFile.length());
	        
	        String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    exlFile.getName());
            response.setHeader(headerKey, headerValue);
	        
	        is = new FileInputStream(exlFile);
	        IOUtils.copy(is, response.getOutputStream());
	        is.close();
	        response.flushBuffer();
	        
		}catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }catch(IOException e){
        	e.printStackTrace();
        }
		
	}

	
	
}
