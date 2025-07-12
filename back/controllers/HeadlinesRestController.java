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
import tn.esprit.spring.entities.Headlines;
import tn.esprit.spring.serviceInterface.IHeadlinesService;

@RestController
@Api(tags = "Headlines")
@RequestMapping("/Headlines")
public class HeadlinesRestController {
	
	@Autowired
	IHeadlinesService headlinesService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Headlines 
	@ApiOperation(value = "Récupérer la liste des headlines")
	@GetMapping("/retrieve-all-Headlines")
	@ResponseBody		
	public  List<Headlines> retrieveAllHeadlines() {		
		
		return headlinesService.retrieveAllHeadlines();
		  
    }
	
	// get list Headlines by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des headlines par fieldName ")
		@GetMapping("/retrieve-Headline-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Headlines> getHeadlinesbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return headlinesService.getHeadlinesbyField(fieldName, value);
			  
	    }

		
	//   Add Headline
		
		@ApiOperation(value = "Add Headline")  
		@PostMapping("/add-Headline")
	    public String addHeadline(@RequestBody Map<String, Object> fields) {
			headlinesService.addHeadline(fields);
	        
		    System.out.println("Headline added successfully!");
	        return "Headline added successfully!";
	    }
		
		
		//  Update Headline
		
		@ApiOperation(value = "Update Headline")  
		@PostMapping("/update-Headline")
		public ResponseEntity<String> updateHeadline(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            headlinesService.updateHeadline(objectId, fields);
		        return ResponseEntity.ok("Headline updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Headline: " + e.getMessage());
		    }
		}

	   //  Delete Headline 
		
		@ApiOperation(value = "Delete Headline")  
		@DeleteMapping("/delete-Headline/{id}")
	    public ResponseEntity<String> deleteHeadline(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            headlinesService.DeleteHeadline(objectId);
	            return new ResponseEntity<>("Headline deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Headline: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		// add field to Headline 
		@ApiOperation(value = "Add Field to Headline")  
		@PostMapping("/add-Field-Headline")
		    public ResponseEntity<String> addFieldHeadline(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            headlinesService.AddFieldToHeadlines(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
