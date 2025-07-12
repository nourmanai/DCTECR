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
import tn.esprit.spring.entities.TeamMember;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.ITeamMemberService;

@Service
@Slf4j
public class TeamMemberServiceImpl implements ITeamMemberService  {
	
    private static final Logger log = LoggerFactory.getLogger(TeamMemberServiceImpl.class);

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collection = database.getCollection("DIM_TeamMembers");

	@Autowired
	IGlobalService globalService;	
	
	@Override
	public List<TeamMember> retrieveAllTeamMembers() {
		List< TeamMember>  teamMembers =   new ArrayList<>();
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
	        TeamMember obj = new TeamMember(id, Fields);
//			 log.info("TeamMember log: {}", obj);
//			 log.info("TeamMembers list: {}","TeamMember{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 teamMembers.add(obj);
	        
	        

	    }
		return  teamMembers;
	}


	// get TeamMembers by Field
	
	@Override
	public List<TeamMember> getTeamMemberbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<TeamMember> teamMembers = retrieveAllTeamMembers();
		List<TeamMember> matching = globalService.getObjectsByFieldValue(teamMembers, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " teamMembers have been found.");
		    return matching;
		} else {
		    System.out.println("No teamMembers found.");
		    return Collections.emptyList();
		}
		
	}

	
	// Add TeamMember 
	@Override
	public void addTeamMember( Map<String, Object> fields)
	{
		globalService.addDocument(collection, fields);

	}
	
	// Update TeamMember 
	@Override	 
	public void updateTeamMember( ObjectId id, Map<String, Object> fields) {
					
		globalService.updateDocument(collection, id, fields);
		
	}
	
	
	// Delete TeamMember 
	@Override	 
	public void DeleteTeamMember( ObjectId id) {
					
		globalService.deleteDocument(collection, id);
		
	}	
	
	// Add Field to  TeamMember 
	@Override	 
	public void AddFieldToTeamMember( ObjectId id, String fieldName, String fieldValue) {
					
		globalService.addFieldToDocument(collection, id, fieldName, fieldValue);
		
	}	
}
