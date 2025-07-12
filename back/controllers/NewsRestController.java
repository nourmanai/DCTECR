package tn.esprit.spring.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import tn.esprit.spring.entities.News;
import tn.esprit.spring.serviceInterface.INewsService;

@RestController
@Api(tags = "News")
@RequestMapping("/News")
@CrossOrigin(origins = "*", maxAge = 3600)

public class NewsRestController {
	
	@Autowired
	INewsService newsService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all News 
	@ApiOperation(value = "Récupérer la liste des News")
	@GetMapping("/retrieve-all-News")
	@ResponseBody	
	public List<News> retrieveAllNews() {		
	
		return newsService.retrieveAllNews();
		  
    }
	
//	public ResponseEntity<List<News>> retrieveAllNews() {		
//		List<News>data= newsService.retrieveAllNews();
//		  HttpHeaders headers = new HttpHeaders();
//		    headers.add("Access-Control-Allow-Origin", "*");
//		    headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
//		    headers.add("Access-Control-Max-Age", "3600");
//		return new ResponseEntity<>(data, headers, HttpStatus.OK);
//		  
//    }
//	
	// get list News by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des News par fieldName ")
		@GetMapping("/retrieve-New-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<News> getNewsbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return newsService.getNewsbyField( fieldName,  value );
			  
	    }
		
	//   Add News
		
		@ApiOperation(value = "Add News")  
		@PostMapping("/add-News")
	    public String addNews(@RequestBody Map<String, Object> fields) {
			newsService.addNews(fields);
	        
		    System.out.println("News added successfully!");
	        return "News added successfully!";
	    }
		
		
		//  Update News
		
		@ApiOperation(value = "Update News")  
		@PostMapping("/update-News")
		public ResponseEntity<String> updateNews(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            newsService.updateNews(objectId, fields);
		        return ResponseEntity.ok("News updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the News: " + e.getMessage());
		    }
		}

	   //  Delete News 
		
		@ApiOperation(value = "Delete News")  
		@DeleteMapping("/delete-News/{id}")
	    public ResponseEntity<String> deleteNews(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            newsService.DeleteNews(objectId);
	            return new ResponseEntity<>("News deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting News: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		// add field to News 
		@ApiOperation(value = "Add Field to News")  
		@PostMapping("/add-Field-News")
		    public ResponseEntity<String> addFieldNews(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            newsService.AddFieldToNews(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }

}
