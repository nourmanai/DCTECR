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
import tn.esprit.spring.entities.Campaign;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.serviceInterface.ICampaignService;

@RestController
@Api(tags = "Campaign")
@RequestMapping("/Campaign")
public class CampaignRestController {
	
	@Autowired
	ICampaignService campaignService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all campaigns 
	@ApiOperation(value = "Récupérer la liste des campaigns")
	@GetMapping("/retrieve-all-campaigns")
	@ResponseBody		
	public  List<Campaign> retrieveAllCampaigns() {		
		
		return campaignService.retrieveAllCampaigns();
		  
    }
 
	
	// get list Campaign by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des campaigns par fieldName ")
		@GetMapping("/retrieve-Campaign-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Campaign> getCampaignbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return campaignService.getCampaignbyField( fieldName,  value );
			  
	    }

		
	//   Add Campaign
		
		@ApiOperation(value = "Add Campaign")  
		@PostMapping("/add-Campaign")
	    public String addCampaign(@RequestBody Map<String, Object> fields) {
			campaignService.addCampaign(fields);
	        
		    System.out.println("Campaign added successfully!");
	        return "Campaign added successfully!";
	    }
		
		
		//  Update Campaign
		
		@ApiOperation(value = "Update Campaign")  
		@PostMapping("/update-Campaign")
		public ResponseEntity<String> updateCampaign(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            campaignService.updateCampaign(objectId, fields);
		        return ResponseEntity.ok("Campaign updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Campaign: " + e.getMessage());
		    }
		}

	   //  Delete Campaign 
		
		@ApiOperation(value = "Delete Campaign")  
		@DeleteMapping("/delete-Campaign/{id}")
	    public ResponseEntity<String> deleteCampaign(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            campaignService.DeleteCampaign(objectId);
	            return new ResponseEntity<>("Campaign deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Campaign: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		// add field to Campaign 
		@ApiOperation(value = "Add Field to Campaign")  
		@PostMapping("/add-Field-Campaign")
		    public ResponseEntity<String> addFieldCampaign(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            campaignService.AddFieldToCampaign(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
