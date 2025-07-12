package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Sales;

public interface ISalesService {
	List<Sales> retrieveAllSales();

	List<Sales> getSalesbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addSales(Map<String, Object> fields);

	void updateSales(ObjectId id, Map<String, Object> fields);

	void DeleteSales(ObjectId id);

	void AddFieldToSales(ObjectId id, String fieldName, String fieldValue);
}
