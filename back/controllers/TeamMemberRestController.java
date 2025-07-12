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
import tn.esprit.spring.entities.TeamMember;
import tn.esprit.spring.serviceInterface.ITeamMemberService;

@RestController
@Api(tags = "Team Member")
@RequestMapping("/TeamMember")
public class TeamMemberRestController {

	
	@Autowired
	ITeamMemberService teamMemberService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all TeamMembers 
	@ApiOperation(value = "Récupérer la liste des TeamMembers")
	@GetMapping("/retrieve-all-TeamMembers")
	@ResponseBody		
	public  List<TeamMember> retrieveAllTeamMembers() {		
		
		return teamMemberService.retrieveAllTeamMembers();
		  
    }
	
	// get list TeamMembers by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des TeamMembers par fieldName ")
		@GetMapping("/retrieve-TeamMember-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<TeamMember> getTeamMemberbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return teamMemberService.getTeamMemberbyField( fieldName,  value );
			  
	    }
		
	//   Add TeamMember
		
		@ApiOperation(value = "Add Team Member")  
		@PostMapping("/add-TeamMember")
	    public String addTeamMember(@RequestBody Map<String, Object> fields) {
			teamMemberService.addTeamMember(fields);
	        
		    System.out.println("Team Member added successfully!");
	        return "Team Member added successfully!";
	    }
		
		
		//  Update Client
		
		@ApiOperation(value = "Update Team Member")  
		@PostMapping("/update-TeamMember")
		public ResponseEntity<String> updateTeamMember(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            teamMemberService.updateTeamMember(objectId, fields);
		        return ResponseEntity.ok("Team Member updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Team Member: " + e.getMessage());
		    }
		}

	   //  Delete TeamMember 
		
		@ApiOperation(value = "Delete Team Member")  
		@DeleteMapping("/delete-TeamMember/{id}")
	    public ResponseEntity<String> deleteTeamMember(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            teamMemberService.DeleteTeamMember(objectId);
	            return new ResponseEntity<>("Team Member deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Team Member: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		// add field to TeamMember 
		@ApiOperation(value = "Add Field to TeamMember")  
		@PostMapping("/add-Field-TeamMember")
		    public ResponseEntity<String> addFieldTeamMember(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            teamMemberService.AddFieldToTeamMember(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
