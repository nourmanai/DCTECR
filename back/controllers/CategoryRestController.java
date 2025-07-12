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
import tn.esprit.spring.entities.Category;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.serviceInterface.ICategoryService;

@RestController
@Api(tags = "Category")
@RequestMapping("/Category")
public class CategoryRestController {
	@Autowired
	ICategoryService categoryService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Categories 
	@ApiOperation(value = "Récupérer la liste des Categories")
	@GetMapping("/retrieve-all-Categories")
	@ResponseBody		
	public  List<Category> retrieveAllCategories() {		
		
		return categoryService.retrieveAllCategories();
		  
    }
	
	// get list Categories by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des Categories par fieldName ")
		@GetMapping("/retrieve-Category-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Category> getCategorybyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return categoryService.getCategorybyField(fieldName, value);
			  
	    }
		
	//   Add Category
		
		@ApiOperation(value = "Add Category")  
		@PostMapping("/add-Category")
	    public String addCategory(@RequestBody Map<String, Object> fields) {
			categoryService.addCategory(fields);
	        
		    System.out.println("Category added successfully!");
	        return "Category added successfully!";
	    }
		
		
		//  Update category
		
		@ApiOperation(value = "Update Category")  
		@PostMapping("/update-Category")
		public ResponseEntity<String> updateCategory(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            categoryService.updateCategory(objectId, fields);
		        return ResponseEntity.ok("Category updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Category: " + e.getMessage());
		    }
		}

	   //  Delete category
		
		@ApiOperation(value = "Delete Category")  
		@DeleteMapping("/Category-Category/{id}")
	    public ResponseEntity<String> deleteCategory(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            categoryService.DeleteCategory(objectId);
	            return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		// add field to Category 
		@ApiOperation(value = "Add Field to Category")  
		@PostMapping("/add-Field-Category")
		    public ResponseEntity<String> addFieldCategory(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            categoryService.AddFieldToCategory(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
