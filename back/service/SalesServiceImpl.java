package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Sales;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.ISalesService;

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
public class SalesServiceImpl implements ISalesService {
	
    private static final Logger log = LoggerFactory.getLogger(SalesServiceImpl.class);

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Sales");

	@Autowired
	IGlobalService globalService;	
	
	@Override
	public List<Sales> retrieveAllSales() {
		
		List< Sales> sales = new ArrayList<>();
	    for (Document document : collection.find()) {
	        ObjectId id = document.getObjectId("_id"); 
	        Map<String, Object> Fields = new HashMap<>();
	        for (Entry<String, Object> entry : document.entrySet()) {
	            if (!entry.getKey().equals("_id")) {
	                Object value = entry.getValue();
	                
	                if (value instanceof ObjectId) {
	                	Fields.put(entry.getKey(), ((ObjectId) value).toHexString());
	                } else if (value instanceof List) {
	                    List<?> valueList = (List<?>) value;
	                    List<Object> convertedList = new ArrayList<>();
	                    
	                    for (Object item : valueList) {
	                        if (item instanceof ObjectId) {
	                            convertedList.add(((ObjectId) item).toHexString());
	                        } else {
	                            convertedList.add(item);
	                        }
	                    }
	                    Fields.put(entry.getKey(), convertedList);
	                } else {
	                	Fields.put(entry.getKey(), value);
	                }
	            }
	        }
	        Sales obj = new Sales(id, Fields);
//			 log.info("Sale log: {}", obj);
//			 log.info("Sales list: {}","Sale{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 sales.add(obj);	        	       
	    }
		return sales;	
	}

	// get Sales by Field
	
	@Override
	public List<Sales> getSalesbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Sales> sales = retrieveAllSales();
		List<Sales> matching = globalService.getObjectsByFieldValue(sales, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " sales have been found.");
		    return matching;
		} else {
		    System.out.println("No sales found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add Sales 
	@Override
	public void addSales( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Sales 
	@Override	 
	public void updateSales( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Sales 
	@Override	 
	public void DeleteSales( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  Sales 
	@Override	 
	public void AddFieldToSales( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	
}
