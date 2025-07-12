package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Account;

public interface IAccountService {
	List<Account> retrieveAllAccounts();

	List<Account> getAccountbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addAccount(Map<String, Object> fields);

	void updateAccount(ObjectId id, Map<String, Object> fields);

	void DeleteAccount(ObjectId id);

	void AddFieldToAccount(ObjectId id, String fieldName, String fieldValue);



}
