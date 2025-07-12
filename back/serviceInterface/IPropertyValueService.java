package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.PropertyValue;

public interface IPropertyValueService {
	List<PropertyValue> retrieveAllPropertyValues();

	List<PropertyValue> getPropertyValuebyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addPropertyValue(Map<String, Object> fields);

	void updatePropertyValue(ObjectId id, Map<String, Object> fields);

	void DeletePropertyValue(ObjectId id);

	void AddFieldToPropertyValue(ObjectId id, String fieldName, String fieldValue);
}
