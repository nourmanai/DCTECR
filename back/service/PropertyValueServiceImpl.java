package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.PropertyValue;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.IPropertyValueService;

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
public class PropertyValueServiceImpl implements IPropertyValueService {
	
    private static final Logger log = LoggerFactory.getLogger(PropertyValueServiceImpl.class);
  
    
	MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("Dim_products_attributes_value");

	@Autowired
	IGlobalService globalService;	
	
	@Override
	public List<PropertyValue> retrieveAllPropertyValues() {
		
		List< PropertyValue> propertyValues =  new ArrayList<>();
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
	        PropertyValue obj = new PropertyValue(id, Fields);
//			 log.info("PropertyValue log: {}", obj);
//			 log.info("PropertyValues list: {}","PropertyValue{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 propertyValues.add(obj);	        	       
	    }
		return propertyValues;	
	}
	

	// get PropertyValues by Field
	
	@Override
	public List<PropertyValue> getPropertyValuebyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<PropertyValue> PropertyValues = retrieveAllPropertyValues();
		List<PropertyValue> matching = globalService.getObjectsByFieldValue(PropertyValues, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " PropertyValues have been found.");
		    return matching;
		} else {
		    System.out.println("No PropertyValues found.");
		    return Collections.emptyList();
		}
		
	}
	
	// Add PropertyValue 
	@Override
	public void addPropertyValue( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update PropertyValue 
	@Override	 
	public void updatePropertyValue( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete PropertyValue 
	@Override	 
	public void DeletePropertyValue( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		}	
	
	// Add Field to  PropertyValue 
	@Override	 
	public void AddFieldToPropertyValue( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	
}
