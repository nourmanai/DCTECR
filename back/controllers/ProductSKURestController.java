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
import tn.esprit.spring.entities.ProductSKU;
import tn.esprit.spring.serviceInterface.IProductSKUService;

@RestController
@Api(tags = "Product SKU")
@RequestMapping("/ProductSKU")
public class ProductSKURestController {

	
	@Autowired
	IProductSKUService productSKUService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all ProductSKU 
	@ApiOperation(value = "Récupérer la liste des ProductSKU")
	@GetMapping("/retrieve-all-ProductSKU")
	@ResponseBody		
	public  List<ProductSKU> retrieveAllProductSKUs() {		
		
		return productSKUService.retrieveAllProductSKUs();
		  
    }
	
	// get list ProductSKU by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des ProductSKUs par fieldName ")
		@GetMapping("/retrieve-ProductSKU-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<ProductSKU> getProductSKUbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return productSKUService.getProductSKUbyField( fieldName,  value );
			  
	    }
		
		//   Add ProductSKU
		
		@ApiOperation(value = "Add ProductSKU")  
		@PostMapping("/add-ProductSKU")
	    public String addProductSKU(@RequestBody Map<String, Object> fields) {
			productSKUService.addProductSKU(fields);
	        
		    System.out.println("Client added successfully!");
	        return "Client added successfully!";
	    }
		
		
		//  Update ProductSKU
		
		@ApiOperation(value = "Update ProductSKU")  
		@PostMapping("/update-ProductSKU")
		public ResponseEntity<String> updateProductSKU(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            productSKUService.updateProductSKU(objectId, fields);
		        return ResponseEntity.ok("ProductSKU updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the ProductSKU: " + e.getMessage());
		    }
		}

	   //  Delete ProductSKU 
		
		@ApiOperation(value = "Delete ProductSKU")  
		@DeleteMapping("/delete-ProductSKU/{id}")
	    public ResponseEntity<String> deleteProductSKU(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            productSKUService.DeleteProductSKU(objectId);
	            return new ResponseEntity<>("ProductSKU deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting ProductSKU: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		// add field to ProductSKU 
		@ApiOperation(value = "Add Field to ProductSKU")  
		@PostMapping("/add-Field-ProductSKU")
		    public ResponseEntity<String> addFieldProductSKU(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            productSKUService.AddFieldToProductSKU(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
