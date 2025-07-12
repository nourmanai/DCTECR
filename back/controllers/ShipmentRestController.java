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
import tn.esprit.spring.entities.Shipment;
import tn.esprit.spring.serviceInterface.IShipmentService;

@RestController
@Api(tags = "Shipment")
@RequestMapping("/Shipment")
public class ShipmentRestController {

	
	@Autowired
	IShipmentService shipmentService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Shipments 
	@ApiOperation(value = "Récupérer la liste des Shipments")
	@GetMapping("/retrieve-all-Shipments")
	@ResponseBody		
	public  List<Shipment> retrieveAllShipments() {		
		
		return shipmentService.retrieveAllShipments();
		  
    }
	
	// get list Shipments by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste des Shipments par fieldName ")
		@GetMapping("/retrieve-Shipment-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Shipment> getShipmentbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return shipmentService.getShipmentbyField( fieldName,  value );
			  
	    }
		
	//   Add Shipment
		
		@ApiOperation(value = "Add Shipment")  
		@PostMapping("/add-Shipment")
	    public String addShipment(@RequestBody Map<String, Object> fields) {
			shipmentService.addShipment(fields);
	        
		    System.out.println("Shipment added successfully!");
	        return "Shipment added successfully!";
	    }
		
		
		//  Update Shipment
		
		@ApiOperation(value = "Update Shipment")  
		@PostMapping("/update-Shipment")
		public ResponseEntity<String> updateShipment(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            shipmentService.updateShipment(objectId, fields);
		        return ResponseEntity.ok("Shipment updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Shipment: " + e.getMessage());
		    }
		}

	   //  Delete Shipment 
		
		@ApiOperation(value = "Delete Shipment")  
		@DeleteMapping("/delete-Shipment/{id}")
	    public ResponseEntity<String> deleteShipment(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            shipmentService.DeleteShipment(objectId);
	            return new ResponseEntity<>("Shipment deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Shipment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		// add field to Shipment 
		@ApiOperation(value = "Add Field to Shipment")  
		@PostMapping("/add-Field-Shipment")
		    public ResponseEntity<String> addFieldShipment(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            shipmentService.AddFieldToShipment(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
