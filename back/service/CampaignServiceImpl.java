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
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Campaign;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.serviceInterface.ICampaignService;
import tn.esprit.spring.serviceInterface.IGlobalService;


@Service
@Slf4j
public class CampaignServiceImpl implements ICampaignService {
	
    private static final Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);
	
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Campaigns");

	@Autowired
	IGlobalService globalService;	
	
	//retrieve All Campaigns

	@Override
	public List<Campaign> retrieveAllCampaigns() {
		List<Campaign> campaigns =  new ArrayList<>();
	    for (Document document : collection.find()) {
	        ObjectId id = document.getObjectId("_id"); 
	        Map<String, Object> campaignFields = new HashMap<>();
	       
	        for (Entry<String, Object> entry : document.entrySet()) {
	            if (!entry.getKey().equals("_id")) {
	                Object value = entry.getValue();
	                if (value instanceof ObjectId) {
	                	campaignFields.put(entry.getKey(), ((ObjectId) value).toHexString());
	                } else {
	                	campaignFields.put(entry.getKey(), value);
	                }
	            }
	        }
	        Campaign campaign = new Campaign(id, campaignFields);
//			 log.info("Campaign log: {}", campaign);
//			 log.info("Campaigns list: {}","Campaign{" +
//		                "id=" + campaign.getId() +
//		                ", fields=" + campaign.getFields() +
//		                '}');
			 campaigns.add(campaign);
	        
	        

	    }
	return campaigns;
	
	}
	
	// get Campaigns by Field
	
			@Override
			public List<Campaign> getCampaignbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
				List<Campaign> campaigns = retrieveAllCampaigns();
				List<Campaign> matching = globalService.getObjectsByFieldValue(campaigns, fieldName, value);

				if (!matching.isEmpty()) {
				    System.out.println(matching.size() + " campaigns have been found.");
				    return matching;
				} else {
				    System.out.println("No campaigns found.");
				    return Collections.emptyList();
				}
				
			}
			
			// Add Campaign 
			@Override
			public void addCampaign( Map<String, Object> fields)
			{
				globalService.addDocument(collection, fields);

			}
			
			// Update Campaign 
			@Override	 
			public void updateCampaign( ObjectId id, Map<String, Object> fields) {
							
				globalService.updateDocument(collection, id, fields);
				
			}
			
			
			// Delete Campaign 
			@Override	 
			public void DeleteCampaign( ObjectId id) {
							
				globalService.deleteDocument(collection, id);
				
			}	
			// Add Field to  Campaign 
			@Override	 
			public void AddFieldToCampaign( ObjectId id, String fieldName, String fieldValue) {
							
				globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
				
			}	

}
