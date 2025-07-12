package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Property;

public interface IPropertyService {
	List<Property> retrieveAllProperties();

	List<Property> getPropertybyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addProperty(Map<String, Object> fields);

	void updateProperty(ObjectId id, Map<String, Object> fields);

	void DeleteProperty(ObjectId id);

	void AddFieldToProperty(ObjectId id, String fieldName, String fieldValue);
}
