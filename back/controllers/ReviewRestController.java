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
import tn.esprit.spring.entities.Review;
import tn.esprit.spring.serviceInterface.IReviewService;

@RestController
@Api(tags = " Review")
@RequestMapping("/Review")
public class ReviewRestController {

	
	@Autowired
	IReviewService reviewService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Reviews 
	@ApiOperation(value = "Récupérer la liste des Reviews")
	@GetMapping("/retrieve-all-Reviews")
	@ResponseBody		
	public  List<Review> retrieveAllReviews() {		
		
		return reviewService.retrieveAllReviews();
		  
    }
	
	// get list Client by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des Reviews par fieldName ")
		@GetMapping("/retrieve-Review-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Review> getReviewbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return reviewService.getReviewbyField( fieldName,  value );
			  
	    }
		
	//   Add Review
		
		@ApiOperation(value = "Add Review")  
		@PostMapping("/add-Review")
	    public String addReview(@RequestBody Map<String, Object> fields) {
			reviewService.addReview(fields);
	        
		    System.out.println("Review added successfully!");
	        return "Review added successfully!";
	    }
		
		
		//  Update Review
		
		@ApiOperation(value = "Update Review")  
		@PostMapping("/update-Review")
		public ResponseEntity<String> updateReview(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            reviewService.updateReview(objectId, fields);
		        return ResponseEntity.ok("Review updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Review: " + e.getMessage());
		    }
		}

	   //  Delete Review 
		
		@ApiOperation(value = "Delete Review")  
		@DeleteMapping("/delete-Review/{id}")
	    public ResponseEntity<String> deleteReview(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            reviewService.DeleteReview(objectId);
	            return new ResponseEntity<>("Review deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Review: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		// add field to Review 
		@ApiOperation(value = "Add Field to Review")  
		@PostMapping("/add-Field-Review")
		    public ResponseEntity<String> addFieldReview(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            reviewService.AddFieldToReview(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
