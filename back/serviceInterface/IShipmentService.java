package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Shipment;

public interface IShipmentService {
	List<Shipment> retrieveAllShipments();

	List<Shipment> getShipmentbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addShipment(Map<String, Object> fields);

	void updateShipment(ObjectId id, Map<String, Object> fields);

	void DeleteShipment(ObjectId id);

	void AddFieldToShipment(ObjectId id, String fieldName, String fieldValue);
}
