package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Supplier;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.ISupplierService;

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
@Service
@Slf4j
public class SupplierServiceImpl implements ISupplierService {
	
    private static final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

	MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Suppliers");

	@Autowired
	IGlobalService globalService;	
	
	@Override
	public List<Supplier> retrieveAllSuppliers() {
		
		List< Supplier>  suppliers =   new ArrayList<>();
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
	        Supplier obj = new Supplier(id, Fields);
//			 log.info("supplier log: {}", obj);
//			 log.info("suppliers list: {}","supplier{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 suppliers.add(obj);
	        
	        

	    }
		return  suppliers;
	
	}

	// get Suppliers by Field
	
	@Override
	public List<Supplier> getSupplierbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Supplier> suppliers = retrieveAllSuppliers();
		List<Supplier> matching = globalService.getObjectsByFieldValue(suppliers, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " suppliers have been found.");
		    return matching;
		} else {
		    System.out.println("No suppliers found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add Supplier 
	@Override
	public void addSupplier( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Supplier 
	@Override	 
	public void updateSupplier( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Supplier 
	@Override	 
	public void DeleteSupplier( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  Supplier 
	@Override	 
	public void AddFieldToSupplier( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	
}
