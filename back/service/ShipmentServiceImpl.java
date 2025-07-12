package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Shipment;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.IShipmentService;

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
public class ShipmentServiceImpl implements IShipmentService {
	
    private static final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Shippments");

	@Autowired
	IGlobalService globalService;	
	
	@Override
	public List<Shipment> retrieveAllShipments() {
		
		List< Shipment>  shipments =   new ArrayList<>();
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
	        Shipment obj = new Shipment(id, Fields);
//			 log.info("Shipment log: {}", obj);
//			 log.info("Shipments list: {}","Shipment{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 shipments.add(obj);	        	        
	    }
		return  shipments;
	
	}
	

	// get Shipments by Field
	
	@Override
	public List<Shipment> getShipmentbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Shipment> shipments = retrieveAllShipments();
		List<Shipment> matching = globalService.getObjectsByFieldValue(shipments, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " shipments have been found.");
		    return matching;
		} else {
		    System.out.println("No shipments found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add Shipment 
	@Override
	public void addShipment( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Shipment 
	@Override	 
	public void updateShipment( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Shipment 
	@Override	 
	public void DeleteShipment( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	// Add Field to  Shipment 
	@Override	 
	public void AddFieldToShipment( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	

}
