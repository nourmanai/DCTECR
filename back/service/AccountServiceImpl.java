package tn.esprit.spring.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import tn.esprit.spring.serviceInterface.IAccountService;
import tn.esprit.spring.serviceInterface.IGlobalService;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Account;
import tn.esprit.spring.entities.Client;


@Service
@Slf4j
public class AccountServiceImpl  implements IAccountService{
	
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);


	MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Accounts");

	@Autowired
	IGlobalService globalService;	
	
	
	// retrieve all Accounts 
	
	@Override
	public List<Account> retrieveAllAccounts() {
		   List<Account> accounts = new ArrayList<>();
		    for (Document document : collection.find()) {
		        ObjectId id = document.getObjectId("_id"); 
		        Map<String, Object> accountFields = new HashMap<>();
		       
		        for (Entry<String, Object> entry : document.entrySet()) {
		            if (!entry.getKey().equals("_id")) {
		                Object value = entry.getValue();
		                if (value instanceof ObjectId) {
		                	accountFields.put(entry.getKey(), ((ObjectId) value).toHexString());
		                } else {
		                	accountFields.put(entry.getKey(), value);
		                }
		            }
		        }
		        Account account = new Account(id, accountFields);
//				 log.info("account log: {}", account);
//				 log.info("account list: {}","Account{" +
//			                "id=" + account.getId() +
//			                ", fields=" + account.getFields() +
//			                '}');
				 accounts.add(account);
		    }
	            return accounts;
	}

	
	// get account by Field 
	
	@Override
	public List<Account> getAccountbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Account> accounts = retrieveAllAccounts();
		List<Account> matching = globalService.getObjectsByFieldValue(accounts, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " accounts have been found.");
		    return matching;
		} else {
		    System.out.println("No accounts found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add Account 
	@Override
	public void addAccount( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Account 	
	@Override	 
	public void updateAccount( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Account 
	@Override	 
	public void DeleteAccount( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  Account 
	@Override	 
	public void AddFieldToAccount( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	


}
