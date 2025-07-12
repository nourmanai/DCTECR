package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Order;

public interface IOrderService {
	List<Order> retrieveAllOrders();

	List<Order> getOrderbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addOrder(Map<String, Object> fields);

	void updateOrder(ObjectId id, Map<String, Object> fields);

	void DeleteOrder(ObjectId id);

	void AddFieldToOrder(ObjectId id, String fieldName, String fieldValue);
}
