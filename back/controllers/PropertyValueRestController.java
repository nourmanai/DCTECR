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
import tn.esprit.spring.entities.PropertyValue;
import tn.esprit.spring.serviceInterface.IPropertyValueService;

@RestController
@Api(tags = " Property Value")
@RequestMapping("/PropertyValue")
public class PropertyValueRestController {
	@Autowired
	IPropertyValueService propertyValueService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all propertyValues 
	@ApiOperation(value = "Récupérer la liste des propertyValues")
	@GetMapping("/retrieve-all-PropertyValues")
	@ResponseBody		
	public  List<PropertyValue> retrieveAllPropertyValues() {		
		
		return propertyValueService.retrieveAllPropertyValues();
		  
    }
	
	// get list PropertyValues by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des PropertyValues par fieldName ")
		@GetMapping("/retrieve-PropertyValue-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<PropertyValue> getPropertyValuebyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return propertyValueService.getPropertyValuebyField( fieldName,  value );
			  
	    }
		
		//   Add PropertyValue
		
		@ApiOperation(value = "Add Property Value")  
		@PostMapping("/add-PropertyValue")
	    public String addPropertyValue(@RequestBody Map<String, Object> fields) {
			propertyValueService.addPropertyValue(fields);
	        
		    System.out.println("Property Value added successfully!");
	        return "Property Value added successfully!";
	    }
		
		
		//  Update PropertyValue
		
		@ApiOperation(value = "Update Property Value")  
		@PostMapping("/update-PropertyValue")
		public ResponseEntity<String> updateDocument(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            propertyValueService.updatePropertyValue(objectId, fields);
		        return ResponseEntity.ok("Property Value updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Property Value: " + e.getMessage());
		    }
		}

	   //  Delete PropertyValue 
		
		@ApiOperation(value = "Delete Property Value")  
		@DeleteMapping("/delete-PropertyValue/{id}")
	    public ResponseEntity<String> deleteClient(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            propertyValueService.DeletePropertyValue(objectId);
	            return new ResponseEntity<>(" Property Value deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Property Value: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		// add field to PropertyValue 
		@ApiOperation(value = "Add Field to PropertyValue")  
		@PostMapping("/add-Field-PropertyValue")
		    public ResponseEntity<String> addFieldPropertyValue(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            propertyValueService.AddFieldToPropertyValue(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
