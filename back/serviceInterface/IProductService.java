package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Product;

public interface IProductService {
	List<Product> retrieveAllProducts();

	List<Product> getProductbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addProduct(Map<String, Object> fields);

	void updateProduct(ObjectId id, Map<String, Object> fields);

	void DeleteProduct(ObjectId id);

	void AddFieldToProduct(ObjectId id, String fieldName, String fieldValue);
}
