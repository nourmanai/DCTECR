package tn.esprit.spring.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Team;
import tn.esprit.spring.serviceInterface.ITeamService;

@RestController
@Api(tags = "Team")
@RequestMapping("/Team")
public class TeamRestController {

	
	@Autowired
	ITeamService teamService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Teams 
	@ApiOperation(value = "Récupérer la liste des Teams")
	@GetMapping("/retrieve-all-Teams")
	@ResponseBody		
	public  List<Team> retrieveAllTeams() {		
		
		return teamService.retrieveAllTeams();
		  
    }
	
	// get list Teams by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des Teams par fieldName ")
		@GetMapping("/retrieve-Team-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Team> getTeambyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return teamService.getTeambyField( fieldName,  value );
			  
	    }
		
	//   Add Team
		
		@ApiOperation(value = "Add Team")  
		@PostMapping("/add-Team")
	    public String addTeam(@RequestBody Map<String, Object> fields) {
			teamService.addTeam(fields);
	        
		    System.out.println("Team added successfully!");
	        return "Team added successfully!";
	    }
		
		
		//  Update Team
		
		@ApiOperation(value = "Update Team")  
		@PostMapping("/update-Team")
		public ResponseEntity<String> updateTeam(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            teamService.updateTeam(objectId, fields);
		        return ResponseEntity.ok("Team updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Team: " + e.getMessage());
		    }
		}

	   //  Delete Team 
		
		@ApiOperation(value = "Delete Team")  
		@DeleteMapping("/delete-Team/{id}")
	    public ResponseEntity<String> deleteTeam(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            teamService.DeleteTeam(objectId);
	            return new ResponseEntity<>("Team deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Team: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		// add field to Team 
		@ApiOperation(value = "Add Field to Team")  
		@PostMapping("/add-Field-Team")
		    public ResponseEntity<String> addFieldTeam(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            teamService.AddFieldToTeam(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
