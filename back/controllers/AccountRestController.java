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
import tn.esprit.spring.entities.Account;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.serviceInterface.IAccountService;

@RestController
@Api(tags = "Account")
@RequestMapping("/Account")
public class AccountRestController {

	@Autowired
	IAccountService accountService;	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	// get all Clients 
	@ApiOperation(value = "Récupérer la liste des accounts")
	@GetMapping("/retrieve-all-accounts")
	@ResponseBody		
	public  List<Account> retrieveAllAccount() {		
		
		return accountService.retrieveAllAccounts();
		  
    }
	
	// get list Accounts by Field name of your choice 
		@ApiOperation(value = "Récupérer la liste des Accounts par fieldName ")
		@GetMapping("/retrieve-Account-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<Account> getClientbyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return accountService.getAccountbyField(fieldName, value);
			  
	    }
		
	//   Add Account
		
		@ApiOperation(value = "Add Account")  
		@PostMapping("/add-Account")
	    public String addAccount(@RequestBody Map<String, Object> fields) {
			accountService.addAccount(fields);
	        
		    System.out.println("Account added successfully!");
	        return "Account added successfully!";
	    }
		
		
		//  Update Account
		
		@ApiOperation(value = "Update Account")  
		@PostMapping("/update-Account")
		public ResponseEntity<String> updateAccount(@RequestParam String id, @RequestBody Map<String, Object> fields) {
		    try {
		    	
	            ObjectId objectId = new ObjectId(id);
	            accountService.updateAccount(objectId, fields);
		        return ResponseEntity.ok("Account updated successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Account: " + e.getMessage());
		    }
		}

	   //  Delete Account 
		
		@ApiOperation(value = "Delete Account")  
		@DeleteMapping("/delete-Account/{id}")
	    public ResponseEntity<String> deleteAccount(@PathVariable("id") String id) {
	        try {
	            ObjectId objectId = new ObjectId(id);
	            accountService.DeleteAccount(objectId);
	            return new ResponseEntity<>("Account deleted successfully!", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error deleting Account: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

		// add field to Account 
		@ApiOperation(value = "Add Field to Account")  
		@PostMapping("/add-Field-Account")
		    public ResponseEntity<String> addFieldAccount(@RequestParam String id,
		                                           @RequestParam String fieldName,
		                                           @RequestParam String fieldValue) {

		        try {
		            ObjectId objectId = new ObjectId(id);

		            accountService.AddFieldToAccount(objectId, fieldName, fieldValue);;

		            return ResponseEntity.ok("Field added successfully!");

		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add field");
		        }
		    }
}
