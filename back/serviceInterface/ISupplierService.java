package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Supplier;

public interface ISupplierService {
	List<Supplier> retrieveAllSuppliers();

	List<Supplier> getSupplierbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addSupplier(Map<String, Object> fields);

	void updateSupplier(ObjectId id, Map<String, Object> fields);

	void DeleteSupplier(ObjectId id);

	void AddFieldToSupplier(ObjectId id, String fieldName, String fieldValue);
}
