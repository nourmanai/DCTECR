package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Headlines;

public interface IHeadlinesService {
	List<Headlines> retrieveAllHeadlines();

	List<Headlines> getHeadlinesbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addHeadline(Map<String, Object> fields);

	void updateHeadline(ObjectId id, Map<String, Object> fields);

	void DeleteHeadline(ObjectId id);

	void AddFieldToHeadlines(ObjectId id, String fieldName, String fieldValue);

}
