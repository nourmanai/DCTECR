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
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Team;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.ITeamService;

@Service
@Slf4j
public class TeamServiceImpl implements ITeamService  {
	
    private static final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_Teams");

	@Autowired
	IGlobalService globalService;	
	@Override
	public List<Team> retrieveAllTeams() {
		List< Team>  teams =  new ArrayList<>();
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
	        
	        Team obj = new Team(id, Fields);
//		 log.info("Team log: {}", obj);
//		 log.info("Teams list: {}","Team{" +
//	                "id=" + obj.getId() +
//	                ", fields=" + obj.getFields() +
//	                '}');
		 teams.add(obj);
		 }
		return  teams;
	}

	
//	@Override
//	public List<Team> retrieveAllTeams() {
//		List< Team>  teams =  new ArrayList<>();
//	    for (Document document : collection.find()) {
//	        ObjectId id = document.getObjectId("_id"); 
//	        Map<String, Object> Fields = new HashMap<>();
//	        
//	        for (Entry<String, Object> entry : document.entrySet()) {
//	            if (!entry.getKey().equals("_id")) {
//	                Object value = entry.getValue();
//	                if (value instanceof ObjectId) {
//	                	Fields.put(entry.getKey(), ((ObjectId) value).toHexString());
//	                } else {
//	                	Fields.put(entry.getKey(), value);
//	                }
//	            }
//	        }
//	        Team obj = new Team(id, Fields);
////			 log.info("Team log: {}", obj);
////			 log.info("Teams list: {}","Team{" +
////		                "id=" + obj.getId() +
////		                ", fields=" + obj.getFields() +
////		                '}');
//			 teams.add(obj);
//	        
//	        
//
//	    }
//		return  teams;
//	}

	// get Teams by Field
	
	@Override
	public List<Team> getTeambyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Team> Teams = retrieveAllTeams();
		
		List<Team> matching = globalService.getObjectsByFieldValue(Teams, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " Teams have been found.");
		    return matching;
		} else {
		    System.out.println("No Teams found.");
		    return Collections.emptyList();
		}
		
	}
	
	
	// Add Team 
	@Override
	public void addTeam( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update Team 
	@Override	 
	public void updateTeam( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete Team 
	@Override	 
	public void DeleteTeam( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  Team 
	@Override	 
	public void AddFieldToTeam( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	
}
