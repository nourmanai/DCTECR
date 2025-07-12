package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Campaign;

public interface ICampaignService {
	
	
	List<Campaign> retrieveAllCampaigns();

	List<Campaign> getCampaignbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addCampaign(Map<String, Object> fields);

	void updateCampaign(ObjectId id, Map<String, Object> fields);

	void DeleteCampaign(ObjectId id);

	void AddFieldToCampaign(ObjectId id, String fieldName, String fieldValue);

}
