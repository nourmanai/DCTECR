package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Category;

public interface ICategoryService {
	
	List<Category> retrieveAllCategories();

	List<Category> getCategorybyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addCategory(Map<String, Object> fields);

	void updateCategory(ObjectId id, Map<String, Object> fields);

	void DeleteCategory(ObjectId id);

	void AddFieldToCategory(ObjectId id, String fieldName, String fieldValue);

}
