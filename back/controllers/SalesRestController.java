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
import tn.esprit.spring.entities.Sales;
import tn.esprit.spring.serviceInterface.ISalesService;

@RestController
@Api(tags = "Sales")
@RequestMapping("/Sales")
public class SalesRestController {

	
	@Autowired
	ISalesService salesService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Sales 
	@ApiOperation(value = "Récupérer la liste des Sales")
	@GetMapping("/retrieve-all-Sales")
	@ResponseBody		
	public  List<Sales> retrieveAllSales() {		
		
		return salesService.retrieveAllSales();
		  
    }
	
	// get list Sales by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des Sales par fieldName ")
		@GetMapping("/retrieve-Sale-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Sales> getSalesbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return salesService.getSalesbyField(fieldName, value);
			  
	    }
		
	//   Add Sales
		
		@ApiOperation(value = "Add Sales")  
		@PostMapping("/add-Sales")
	    public String addSales(@RequestBody Map<String, Object> fields) {
			salesService.addSales(fields);
	        
		    System.out.println("Sales added successfully!");
	        return "Sales added successfully!";
	    }
		
		
		//  Update Sales
		
		@ApiOperation(value = "Update Sales")  
		@PostMapping("/update-Sales")
		public ResponseEntity<String> updateSales(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            salesService.updateSales(objectId, fields);
		        return ResponseEntity.ok("Sales updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Sales: " + e.getMessage());
		    }
		}

	   //  Delete Sales 
		
		@ApiOperation(value = "Delete Sales")  
		@DeleteMapping("/delete-Sales/{id}")
	    public ResponseEntity<String> deleteSales(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            salesService.DeleteSales(objectId);
	            return new ResponseEntity<>("Sales deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Sales: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		// add field to Sales 
		@ApiOperation(value = "Add Field to Sales")  
		@PostMapping("/add-Field-Sales")
		    public ResponseEntity<String> addFieldSales(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            salesService.AddFieldToSales(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
