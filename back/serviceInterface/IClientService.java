package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import tn.esprit.spring.entities.Client;
public interface IClientService {
	
	
	List<Client> retrieveAllClients();
	
	//Optional<Client> getClientbyField(String fieldName, String value); return one object 

	List<Client> getClientbyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addClient( Map<String, Object> fields);

	void updateClient(ObjectId id, Map<String, Object> fields);

	void DeleteClient(ObjectId id);

	void AddFieldToClient(ObjectId id, String fieldName, String fieldValue);

	Client getClientById(ObjectId id);

}
