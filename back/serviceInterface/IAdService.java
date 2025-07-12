package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Ad;

public interface IAdService {
	List<Ad> retrieveAllAds();

	List<Ad> getAdbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addAd(Map<String, Object> fields);

	void updateAd(ObjectId id, Map<String, Object> fields);

	void DeleteAd(ObjectId id);

	void AddFieldToAd(ObjectId id, String fieldName, String fieldValue);

}
