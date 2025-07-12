package tn.esprit.spring.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonObjectId;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.MongoCommandException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.serviceInterface.IGlobalService;

@Service 
@Slf4j
public class GlobalServiceImpl implements IGlobalService{
	 private static final Logger log = LoggerFactory.getLogger(GlobalServiceImpl.class);


	// Returning one object 
	  @Override
	  public <T> Optional<T> getObjectByFieldValue(List<T> objects, String fieldName, Object value) {
	    for (T object : objects) {
	        try {
	            Method method = object.getClass().getMethod("getFields");
	            Map<String, Object> fields = (Map<String, Object>) method.invoke(object);
	            if (fields.get(fieldName).equals(value)) {
	                return Optional.of(object);
	            }
	        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
	            e.printStackTrace();
	        }
	    }
	    return Optional.empty();
	}


	
	// Retrieve a list of objects By any Field value 
	
//	@Override
//	public  <T> List<T> getObjectsByFieldValue(List<T> objects, String fieldName, Object value) 
//	        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//	    List<T> result = new ArrayList<>();
//	    for (T object : objects) {
//	        Method method = object.getClass().getMethod("getFields");
//	        Map<String, Object> fields = (Map<String, Object>) method.invoke(object);
//	        if (fields.get(fieldName).equals(value)) {
//	            result.add(object);
//	        }
//	    }
//	    return result;
//	}
	  
	  @Override
	  public <T> List<T> getObjectsByFieldValue(List<T> objects, String fieldName, Object value) 
	          throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
	      System.out.println(" fieldName  " + fieldName + " "+ "value" + value);

		  if(fieldName.matches( "_id"))
			{

			  List<T> result = new ArrayList<>();
		      
		      for (T object : objects) {
		    	  Method method = object.getClass().getMethod("getId");
		          ObjectId id = (ObjectId) method.invoke(object);
//			      System.out.println(" id  " + id);
//			      System.out.println(" id  " + id.toString() + " value " + value.toString() + " bool " + id.toString().equals(value.toString()));


		              // Convert ObjectId to string for comparison
		              if (id.toString().equals(value.toString())) {
		                  result.add(object);
		              
		          }
		      }
		      System.out.println("results id  " + result);
		      return result;
			}
			else {


	      System.out.println("value of type : " + value.getClass().getName());
	      List<T> result = new ArrayList<>();
	      
	      for (T object : objects) {
	         
	          Method method = object.getClass().getMethod("getFields");
	          Map<String, Object> fields = (Map<String, Object>) method.invoke(object);

	          // Check if the field is present in the document
	          if (fields.containsKey(fieldName)) {
	              Object fieldValue = fields.get(fieldName);

	              System.out.println("fieldValue         " + fieldValue.getClass().getName() + "     " + fieldValue + 
	                  "       value         " + value.getClass().getName() + "     " + value);

	              // Convert ObjectId to string for comparison
	              if (fieldValue.toString().equals(value.toString())) {
	                  result.add(object);
	              }
	          }
	      }
	      
	      System.out.println("results   " + result);
	      return result;
			}
	  }



	// Create a Document of any Object to Add to a mongodb  collection
	
	@Override
	 public  void addDocument(MongoCollection<Document> collection, Map<String, Object> fields) {
	        Document document = new Document(fields);
	        collection.insertOne(document);
	    }

	 // update a Document of any any mongodb collection  
	
	 @Override
	 public void updateDocument(MongoCollection<Document> collection, ObjectId id, Map<String, Object> fields) {
		    Bson filter = Filters.eq("_id", id);
		    Bson update = new Document("$set", new Document(fields));
		    UpdateResult result = collection.updateOne(filter, update);
		    if (result.getModifiedCount() > 0) {
		        System.out.println("Document updated successfully!");
		    } else {
		        System.out.println("No document found with the provided id.");
		    }
		}

	 
	 // Delete a Document of any  mongodb collection  

	 @Override	 
	 public void deleteDocument(MongoCollection<Document> collection, ObjectId id) {
		    Bson filter = Filters.eq("_id", id);
		    DeleteResult result = collection.deleteOne(filter);
		    if (result.getDeletedCount() > 0) {
		        System.out.println("Document deleted successfully!");
		    } else {
		        System.out.println("No document found with the provided id.");
		    }
		}
	 
	 
	 // add field with it's value to any collection 
	 
	 
	 @Override
	 public void addFieldToDocument(MongoCollection<Document> collection, ObjectId id, String fieldName, Object fieldValue) {
		    Bson filter = Filters.eq("_id", id);
		    Bson update = new Document("$set", new Document(fieldName, fieldValue));
		    UpdateResult result = collection.updateOne(filter, update);
		    if (result.getModifiedCount() > 0) {
		        System.out.println("Field added successfully!");
		    } else {
		        System.out.println("No document found with the provided id.");
		    }
		}
	 
	 // get document by Id 
	 
	 @Override
	 public Document getDocumentById(MongoCollection<Document> collection, ObjectId id) {
		 
		 if ( id != null )
		 {  log.info("Searching for document with ObjectId: " + id.toHexString());

	     Bson filter = Filters.eq("_id", id);
	   
	    	 Document result = collection.find(filter).first();
	         log.info("Result for ObjectId " + id + ": " + result);

	         return  result;

	    }
		 else return null ; 
	 }
	 // get document by Id if valid (not NaN )
	 
	@Override
	 public Document getDocumentIfValid(MongoCollection<Document> collection, ObjectId objectId) {
		    if (objectId == null || !ObjectId.isValid(objectId.toHexString())) {
		        return null;
		    }
		    return getDocumentById(collection, objectId);
		}

	 
	@Override
	public  Document getLastDocument( MongoCollection<Document> collection ) {

            FindIterable<Document> iterable = collection.find().sort(new Document("_id", -1)).limit(1);

            try (MongoCursor<Document> cursor = iterable.iterator()) {
            	
                if (cursor.hasNext()) {
                    return cursor.next();
                }
            }
        

        return null;
    }

	
	@Override
	public Map<String, Object> getFieldOfDocument (Document document){
			
		if (document != null )
		{Map<String, Object> fieldsdoc = new HashMap<>();
		for (Entry<String, Object> entry : document.entrySet()) {
			if (!entry.getKey().equals("_id")) { 
        	fieldsdoc.put(entry.getKey(), entry.getValue());
			}
		}
		
		return fieldsdoc;}
		else return null ; 
	}
	
	@Override
	public  void createCompoundIndex(MongoCollection<Document> collection , String field) {
		try {
			 Document index = new Document(field, 1);
		        collection.createIndex(index);
		        System.out.println("Compound Index created for "+ field +" in collection: " + collection.getNamespace().getCollectionName());
		        } catch (MongoCommandException e) {
		    System.out.println("Error creating index: " + e.getMessage());
		}

       
    }
	
	
	@Override
	public void createDynamicIndex(MongoCollection<Document> collection)
	{
		Document index = new Document("$**", 1);
		IndexOptions indexOptions = new IndexOptions().background(true);
		collection.createIndex(index, indexOptions);

	}
	
	@Override
	 public  ObjectId convertToObjectId( String hexString) {
		 
	        try {
	            if (!hexString.equalsIgnoreCase("NaN")) {
	                return new ObjectId(hexString);
	            } else {
	                System.out.println("Hexadecimal string is NaN, setting ObjectId to null.");
	                return null;
	            }
	        } catch (IllegalArgumentException e) {
	            // Handle the case where the hexadecimal representation is invalid.
	            System.out.println("Error: Invalid hexadecimal representation of an ObjectId");
	            return null;
	        }
	    }
	
	
	@Override
	public BsonValue getFieldType(String field,MongoCollection<Document> collection, int numFields) {
	    MongoCursor<Document> cursor = collection.find().iterator();

	    Document firstDocumentWithAllFields = null;
	    int countFieldsInFirstDocument = 0;

	    while (countFieldsInFirstDocument<numFields && cursor.hasNext()) {
	        Document document = cursor.next();

	      
	        countFieldsInFirstDocument = document.size();
	        if (firstDocumentWithAllFields == null && countFieldsInFirstDocument == numFields ) {
	            firstDocumentWithAllFields = document;
	            countFieldsInFirstDocument = document.size();
	        }
	    }
	    
        System.out.println("firstDocumentWithAllFields" + firstDocumentWithAllFields);


	    BsonValue firstDistinctValue = null;

	  
	        Object fieldValue = firstDocumentWithAllFields.get(field);
	        
	        System.out.println("fieldValue " + fieldValue);
           firstDistinctValue = convertToBsonValue(fieldValue);

	    return firstDistinctValue;
	}

	
	private BsonValue convertToBsonValue(Object value) {
	    if (value instanceof BsonValue) {
	        return (BsonValue) value;
	    } else if (value instanceof Integer) {
	        return new BsonInt32((Integer) value);
	    } else if (value instanceof Long) {
	        return new BsonInt64((Long) value);
	    } else if (value instanceof Double) {
	        return new BsonDouble((Double) value);
	    } else if (value instanceof String) {
	        return new BsonString((String) value);
	    }  else if (value instanceof ObjectId) {
	        return new BsonObjectId((ObjectId) value);
	    }else {
	        throw new IllegalArgumentException("Unsupported type for conversion to BsonValue: " + value.getClass());
	    }
	}
	
	
	// example on running a bash script 
	
	/*
	 * import java.io.IOException;

public class RunBashScript {

    public static void main(String[] args) {
        try {
            // Specify the path to your Bash script
            String scriptPath = "/path/to/your/run_python_script.sh";

            // Specify the condition to pass as an argument
            String condition = "condition1";

            // Build the command
            ProcessBuilder processBuilder = new ProcessBuilder(scriptPath, condition);

            // Start the process
            Process process = processBuilder.start();

            // Wait for the process to complete
            int exitCode = process.waitFor();

            // Print the exit code
            System.out.println("Script exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

*/
	 
	 
}
