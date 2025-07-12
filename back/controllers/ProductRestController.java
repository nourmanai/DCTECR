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
import tn.esprit.spring.entities.Product;
import tn.esprit.spring.serviceInterface.IProductService;

@RestController
@Api(tags = "Product")
@RequestMapping("/Product")
public class ProductRestController {
	@Autowired
	IProductService productService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Products 
	@ApiOperation(value = "Récupérer la liste des Products")
	@GetMapping("/retrieve-all-Products")
	@ResponseBody		
	public  List<Product> retrieveAllProducts() {		
		
		return productService.retrieveAllProducts();
		  
    }
	
	// get list Products by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des Products par fieldName ")
		@GetMapping("/retrieve-Product-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Product> getProductbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return productService.getProductbyField( fieldName,  value );
			  
	    }
	//   Add Product
		
		@ApiOperation(value = "Add Product")  
		@PostMapping("/add-Product")
	    public String addProduct(@RequestBody Map<String, Object> fields) {
			productService.addProduct(fields);
	        
		    System.out.println("Product added successfully!");
	        return "Product added successfully!";
	    }
		
		
		//  Update Product
		
		@ApiOperation(value = "Update Product")  
		@PostMapping("/update-Product")
		public ResponseEntity<String> updateProduct(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            productService.updateProduct(objectId, fields);
		        return ResponseEntity.ok("Product updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Product: " + e.getMessage());
		    }
		}

	   //  Delete Product 
		
		@ApiOperation(value = "Delete Product")  
		@DeleteMapping("/delete-Product/{id}")
	    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            productService.DeleteProduct(objectId);
	            return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		// add field to Product 
		@ApiOperation(value = "Add Field to Product")  
		@PostMapping("/add-Field-Product")
		    public ResponseEntity<String> addFieldProduct(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            productService.AddFieldToProduct(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
