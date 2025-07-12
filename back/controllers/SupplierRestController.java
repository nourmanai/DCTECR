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
import tn.esprit.spring.entities.Supplier;
import tn.esprit.spring.serviceInterface.ISupplierService;

@RestController
@Api(tags = "Supplier")
@RequestMapping("/Supplier")
public class SupplierRestController {

	
	@Autowired
	ISupplierService supplierService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Suppliers 
	@ApiOperation(value = "Récupérer la liste des Suppliers")
	@GetMapping("/retrieve-all-Suppliers")
	@ResponseBody		
	public  List<Supplier> retrieveAllSuppliers() {		
		
		return supplierService.retrieveAllSuppliers();
		  
    }
	
	// get list Suppliers by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des Suppliers par fieldName ")
		@GetMapping("/retrieve-Supplier-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Supplier> getSupplierbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return supplierService.getSupplierbyField( fieldName,  value );
			  
	    }
		
	//   Add Supplier
		
		@ApiOperation(value = "Add Supplier")  
		@PostMapping("/add-Supplier")
	    public String addSupplier(@RequestBody Map<String, Object> fields) {
			supplierService.addSupplier(fields);
	        
		    System.out.println("Supplier added successfully!");
	        return "Supplier added successfully!";
	    }
		
		
		//  Update Supplier
		
		@ApiOperation(value = "Update Supplier")  
		@PostMapping("/update-Supplier")
		public ResponseEntity<String> updateSupplier(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            supplierService.updateSupplier(objectId, fields);
		        return ResponseEntity.ok("Supplier updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Supplier: " + e.getMessage());
		    }
		}

	   //  Delete Supplier 
		
		@ApiOperation(value = "Delete Supplier")  
		@DeleteMapping("/delete-Supplier/{id}")
	    public ResponseEntity<String> deleteSupplier(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            supplierService.DeleteSupplier(objectId);
	            return new ResponseEntity<>("Supplier deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Supplier: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		// add field to client 
		@ApiOperation(value = "Add Field to Supplier")  
		@PostMapping("/add-Field-Supplier")
		    public ResponseEntity<String> addFieldSupplier(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            supplierService.AddFieldToSupplier(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
