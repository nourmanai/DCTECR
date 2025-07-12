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
import tn.esprit.spring.entities.Property;
import tn.esprit.spring.serviceInterface.IPropertyService;

@RestController
@Api(tags = "Property")
@RequestMapping("/Property")
public class PropertyRestController {
	@Autowired
	IPropertyService propertyService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Properties 
	@ApiOperation(value = "Récupérer la liste des Property")
	@GetMapping("/retrieve-all-Properties")
	@ResponseBody		
	public  List<Property> retrieveAllProperties() {		
		
		return propertyService.retrieveAllProperties();
		  
    }
	
	// get list Properties by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des Properties par fieldName ")
		@GetMapping("/retrieve-Property-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Property> getPropertybyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return propertyService.getPropertybyField( fieldName,  value );
			  
	    }
		
		//   Add Property
		
		@ApiOperation(value = "Add Property")  
		@PostMapping("/add-Property")
	    public String addProperty(@RequestBody Map<String, Object> fields) {
			propertyService.addProperty(fields);
	        
		    System.out.println("Property added successfully!");
	        return "Property added successfully!";
	    }
		
		
		//  Update Property
		
		@ApiOperation(value = "Update Property")  
		@PostMapping("/update-Property")
		public ResponseEntity<String> updateDocument(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            propertyService.updateProperty(objectId, fields);
		        return ResponseEntity.ok("Property updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Property: " + e.getMessage());
		    }
		}

	   //  Delete Property 
		
		@ApiOperation(value = "Delete Property")  
		@DeleteMapping("/delete-Property/{id}")
	    public ResponseEntity<String> deleteClient(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            propertyService.DeleteProperty(objectId);
	            return new ResponseEntity<>("Property deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Property: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		// add field to Property 
		@ApiOperation(value = "Add Field to Property")  
		@PostMapping("/add-Field-Property")
		    public ResponseEntity<String> addFieldProperty(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            propertyService.AddFieldToProperty(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
		
}
