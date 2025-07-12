package tn.esprit.spring.controllers;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import tn.esprit.spring.service.ClientServiceImpl;
import tn.esprit.spring.serviceInterface.IClientService;



@RestController
@Api(tags = "Client")
@RequestMapping("/Clients")
public class ClientRestController {
	 private static final Logger log = LoggerFactory.getLogger(ClientRestController.class);

	@Autowired
	IClientService clientService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Clients 
	@ApiOperation(value = "Récupérer la liste des clients")
	@GetMapping("/retrieve-all-Clients")
	@ResponseBody		
	public  List<Client> retrieveAllClients() {		
		
		return clientService.retrieveAllClients();
		  
    }
 
	/*
	 *  Returning one object 
	 * 
	 * @ApiOperation(value = "Récupérer la liste des clients par nom ")
	@GetMapping("/retrieve-client-by-name/{fieldName}/{value}")
	@ResponseBody		
	public  Optional<Client> getClientbyField(@PathVariable String fieldName, @PathVariable String value ) {		
		
		return clientService.getClientbyField( fieldName,  value );
		  
    }*/
	
	
	// get list Client by Field name of your choice 
	
	@ApiOperation(value = "Récupérer la liste des clients par fieldName ")
	@GetMapping("/retrieve-Client-by-field/{fieldName}/{value}")
	@ResponseBody		
	public  List<Client> getClientbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
		
		return clientService.getClientbyField( fieldName,  value );
		  
    }
	
	//   Add Client
	
	@ApiOperation(value = "Add Client")  
	@PostMapping("/add-Client")
    public String addClient(@RequestBody Map<String, Object> fields) {
        clientService.addClient(fields);
        
	    System.out.println("Client added successfully!");
        return "Client added successfully!";
    }
	
	
	//  Update Client
	
	@ApiOperation(value = "Update Client")  
	@PostMapping("/update-Client")
	public ResponseEntity<String> updateClient(@RequestParam String id, @RequestBody Map<String, Object> fields) {
	    try {
	    	
            ObjectId objectId = new ObjectId(id);
	    	clientService.updateClient(objectId, fields);
	        return ResponseEntity.ok("Client updated successfully!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Client: " + e.getMessage());
	    }
	}

   //  Delete Client 
	
	@ApiOperation(value = "Delete Client")  
	@DeleteMapping("/delete-Client/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            clientService.DeleteClient(objectId);
            return new ResponseEntity<>("Client deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting Client: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	// add field to client 
	@ApiOperation(value = "Add Field to Client")  
	@PostMapping("/add-Field-Client")
	    public ResponseEntity<String> addFieldClient(@RequestParam String id,
	                                           @RequestParam String fieldName,
	                                           @RequestParam String fieldValue) {

	        try {
	            ObjectId objectId = new ObjectId(id);

	            clientService.AddFieldToClient(objectId, fieldName, fieldValue);;

	            return ResponseEntity.ok("Field added successfully!");

	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
	        }
	    }
	
	
	@ApiOperation(value = "Récupérer client par id ")
	@GetMapping("/retrieve-client-by-id/{id}")
	@ResponseBody		
	public  Client getClientbyId(@PathVariable String id ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		

        ObjectId objectId = new ObjectId(id);
     

		return clientService.getClientById(objectId);
		  
    }
	
}
