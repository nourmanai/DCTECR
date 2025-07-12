package tn.esprit.spring.serviceInterface;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.BsonValue;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
public interface IGlobalService   {

	
	<T> Optional<T> getObjectByFieldValue(List<T> objects, String fieldName, Object value); // return only one object 
	
	<T> List<T> getObjectsByFieldValue(List<T> objects, String fieldName, Object value)  throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addDocument(MongoCollection<Document> collection, Map<String, Object> fields);
	void updateDocument(MongoCollection<Document> collection, ObjectId id, Map<String, Object> fields);
	
	void deleteDocument(MongoCollection<Document> collection, ObjectId id);

	void addFieldToDocument(MongoCollection<Document> collection, ObjectId id, String fieldName, Object fieldValue);
	
	Document getDocumentById(MongoCollection<Document> collection, ObjectId id);

	Document getDocumentIfValid(MongoCollection<Document> collection, ObjectId objectId);

	Document getLastDocument(MongoCollection<Document> collection);

	Map<String, Object> getFieldOfDocument(Document document);

	void createCompoundIndex(MongoCollection<Document> collection , String field);

	void createDynamicIndex(MongoCollection<Document> collection);

	ObjectId convertToObjectId(String hexString);

	BsonValue getFieldType(String field, MongoCollection<Document> collection, int numFields);
}
