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
import tn.esprit.spring.entities.Order;
import tn.esprit.spring.serviceInterface.IOrderService;

@RestController
@Api(tags = "Order")
@RequestMapping("/Order")
public class OrderRestController {
	@Autowired
	IOrderService orderService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Orders 
	@ApiOperation(value = "Récupérer la liste des Orders")
	@GetMapping("/retrieve-all-Orders")
	@ResponseBody		
	public  List<Order> retrieveAllOrders() {		
		
		return orderService.retrieveAllOrders();
		  
    }
	
	// get list Orders by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des Orders par fieldName ")
		@GetMapping("/retrieve-Order-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Order> getOrderbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return orderService.getOrderbyField( fieldName,  value );
			  
	    }
		
	//   Add Order
		
		@ApiOperation(value = "Add Order")  
		@PostMapping("/add-Order")
	    public String addOrder(@RequestBody Map<String, Object> fields) {
			
			orderService.addOrder(fields);
	        
		    System.out.println("Order added successfully!");
	        return "Order added successfully!";
	    }
		
		
		//  Update Order
		
		@ApiOperation(value = "Update Order")  
		@PostMapping("/update-Order")
		public ResponseEntity<String> updateOrder(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            orderService.updateOrder(objectId, fields);
		        return ResponseEntity.ok("Order updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Order: " + e.getMessage());
		    }
		}

	   //  Delete Order 
		
		@ApiOperation(value = "Delete Order")  
		@DeleteMapping("/delete-Order/{id}")
	    public ResponseEntity<String> deleteOrder(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            orderService.DeleteOrder(objectId);
	            return new ResponseEntity<>("Order deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		// add field to Order 
		@ApiOperation(value = "Add Field to Order")  
		@PostMapping("/add-Field-Order")
		    public ResponseEntity<String> addFieldOrder(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            orderService.AddFieldToOrder(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
