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
import tn.esprit.spring.entities.Ad;
import tn.esprit.spring.serviceInterface.IAdService;

@RestController
@Api(tags = "Ad")
@RequestMapping("/Ad")
public class AdRestController {

	@Autowired
	IAdService adService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Ads 
	
	@ApiOperation(value = "Récupérer la liste des Ads")
	@GetMapping("/retrieve-all-Ads")
	@ResponseBody		
	public  List<Ad> retrieveAllAds() {		
		
		return adService.retrieveAllAds();
		  
    }
	
	// get list Ads by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des Ads par fieldName ")
		@GetMapping("/retrieve-Ad-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Ad> getAdbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return adService.getAdbyField( fieldName,  value );
			  
	    }
		
		
	//   Add Ad
		
		@ApiOperation(value = "Add Ad")  
		@PostMapping("/add-Ad")
	    public String addAd(@RequestBody Map<String, Object> fields) {
			adService.addAd(fields);
	        
		    System.out.println("Ad added successfully!");
	        return "Ad added successfully!";
	    }
		
		
		//  Update Ad
		
		@ApiOperation(value = "Update Ad")  
		@PostMapping("/update-Ad")
		public ResponseEntity<String> updateAd(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            adService.updateAd(objectId, fields);
		        return ResponseEntity.ok("Ad updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Ad: " + e.getMessage());
		    }
		}

	   //  Delete Ad 
		
		@ApiOperation(value = "Delete Ad")  
		@DeleteMapping("/delete-Ad/{id}")
	    public ResponseEntity<String> deleteAd(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            adService.DeleteAd(objectId);
	            return new ResponseEntity<>("Ad deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Ad: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

		
		// add field to Ad 
		@ApiOperation(value = "Add Field to Ad")  
		@PostMapping("/add-Field-Ad")
		    public ResponseEntity<String> addFieldAd(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            adService.AddFieldToAd(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
