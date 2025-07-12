package tn.esprit.spring.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
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

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.FactTable;
import tn.esprit.spring.serviceInterface.IClientService;
import tn.esprit.spring.serviceInterface.IGlobalService;
import com.google.gson.reflect.TypeToken;


@Service
@Slf4j
public class ClientServiceImpl implements IClientService {
	
	 private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
		MongoCollection<Document> collection = database.getCollection("DIM_Clients");

		@Autowired
		IGlobalService globalService;	
		
		
		//retrieve All Clients
		
		@Override
		public List<Client> retrieveAllClients() {
		   List<Client> clients = new ArrayList<>();
		    for (Document document : collection.find()) {
		        ObjectId id = document.getObjectId("_id"); 
		        Map<String, Object> Fields = new HashMap<>();
		        
		        
		        for (Entry<String, Object> entry : document.entrySet()) {
		            if (!entry.getKey().equals("_id")) {
		                Object value = entry.getValue();
		                if (value instanceof ObjectId) {
		                	Fields.put(entry.getKey(), ((ObjectId) value).toHexString());
		                } else {
		                	Fields.put(entry.getKey(), value);
		                }
		            }
		        }
		        
		        Client obj = new Client(id, Fields);
//				 log.info("client log: {}", obj);
//				 log.info("clients list: {}","Client{" +
//			                "id=" + obj.getId() +
//			                ", fields=" + obj.getFields() +
//			                '}');
		        clients.add(obj);
		        
		        

		    }
		    return clients;
			
		}

		
		// get Client by Field
		
		@Override
		public List<Client> getClientbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
			List<Client> clients = retrieveAllClients();
			List<Client> matching = globalService.getObjectsByFieldValue(clients, fieldName, value);

			if (!matching.isEmpty()) {
			    System.out.println(matching.size() + " clients have been found.");
			    return matching;
			} else {
			    System.out.println("No clients found.");
			    return Collections.emptyList();
			}
			
		}

		
		@Override
		public Client getClientById (ObjectId id ) {
			
			Document document = globalService.getDocumentById(collection, id);
			Gson gson = new Gson();			
	        Map<String, Object> Fields = new HashMap<>();
	        for (Entry<String, Object> entry : document.entrySet()) {
	            if (!entry.getKey().equals("_id")) { 
	               Fields.put(entry.getKey(), entry.getValue());
	            }
	        }
	        Client client = new Client(id, Fields);

			return client;						
		}
	
		

	/*	
	 *  Returning one object 
	 *
	 * 
	 * @Override
		public Optional<Client> getClientbyField(String fieldName, String value ) {
			List<Client> clients = retrieveAllClients();
			

			Optional<Client> client = globalService.getObjectByFieldValue(clients, fieldName + "", value);

			if (client.isPresent()) {
			    System.out.println(client.get().getFields().get(fieldName) + " has been found.");
			    return client ;			} else {
			    System.out.println("Client not found.");
			    return null;
			}
			

		}*/
		
		
		
		/*{
			List<Object> myList = Arrays.asList("value1", 123, true);
			Document document = createDocument(myList);
			collection.insertOne(document);

		}*/

		
		// Add Client 
		@Override
		public void addClient( Map<String, Object> fields)
		{
			globalService.addDocument(collection, fields);

		}
		
		// Update Client 
		@Override	 
		public void updateClient( ObjectId id, Map<String, Object> fields) {
						
			globalService.updateDocument(collection, id, fields);
			
		}
		
		
		// Delete Client 
		@Override	 
		public void DeleteClient( ObjectId id) {
						
			globalService.deleteDocument(collection, id);
			
		}	
		
		// Add Field to  Client 
				@Override	 
				public void AddFieldToClient( ObjectId id, String fieldName, String fieldValue) {
								
					globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
					
				}	
				
				
		
}


		


