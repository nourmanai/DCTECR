package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Order;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.IOrderService;

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
public class OrderServiceImpl implements IOrderService {
	
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Orders");

	@Autowired
	IGlobalService globalService;	
	
	@Override
	public List<Order> retrieveAllOrders() {
		
		List<Order> orders =  new ArrayList<>();
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
	        Order obj = new Order(id, Fields);
//			 log.info("Order log: {}", obj);
//			 log.info("Orders list: {}","Order{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 orders.add(obj);	       	        
	    }
		return orders;	
	}
	

	// get Order by Field
	
	@Override
	public List<Order> getOrderbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Order> Orders = retrieveAllOrders();
		List<Order> matching = globalService.getObjectsByFieldValue(Orders, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " Orders have been found.");
		    return matching;
		} else {
		    System.out.println("No Orders found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add Order 
	@Override
	public void addOrder( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Order 
	@Override	 
	public void updateOrder( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Order 
	@Override	 
	public void DeleteOrder( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  Order 
	@Override	 
	public void AddFieldToOrder( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	
}
