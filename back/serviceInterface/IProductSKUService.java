package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.ProductSKU;

public interface IProductSKUService {
	List<ProductSKU> retrieveAllProductSKUs();

	List<ProductSKU> getProductSKUbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addProductSKU(Map<String, Object> fields);

	void updateProductSKU(ObjectId id, Map<String, Object> fields);

	void DeleteProductSKU(ObjectId id);

	void AddFieldToProductSKU(ObjectId id, String fieldName, String fieldValue);
}
