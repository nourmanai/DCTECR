package tn.esprit.spring.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.IntStream;

import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.FactTable;
import tn.esprit.spring.entities.Shipment;
import tn.esprit.spring.serviceInterface.IFactTableService;
import tn.esprit.spring.serviceInterface.IGlobalService;

@RestController
@Api(tags = "Fact Table")
@RequestMapping("/FactTable")
public class FactTableRestController {
	
	@Autowired
	IFactTableService factTableService;	
	
	@Autowired
	IGlobalService globalService;	
	
    //http://localhost:8087/SpringMVC/swagger-ui/index.html
	

	
	
	
	// get all FactTable 
	
//	@ApiOperation(value = "Récupérer la  FactTable")
//	@GetMapping("/retrieve-all-FactTable")
//	@ResponseBody		
//	public  List<FactTable> retrieveAllFactTable() {		
//		
//		return factTableService.retrieveAllFactTable();
//		  
//    }
	
	
	
	
	// retrieve all fact table (num page and size )
	@GetMapping("/retrieve-all-FactTable")
	@ResponseBody
	public List<FactTable> retrieveAllFactTable(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "369574") int size) {
		
	    return factTableService.retrieveFactTablePaginated(page, size);
	    
	}

	
	// get list FactTable by Field name of your choice 
	
		@ApiOperation(value = "Récupérer la liste FactTable par fieldName ")
		@GetMapping("/retrieve-FactTable-by-field/{fieldName}/{value}")
		@ResponseBody		
		public  List<FactTable> getFactTablebyField(@PathVariable String fieldName, @PathVariable String value ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			return factTableService.getFactTablebyField(fieldName, value);
			  
	    }

		
	// get last fact from fact table 
		
		@ApiOperation(value = "Récupérer  dernière Fact  ")
		@GetMapping("/retrieve-Lastfact")
		@ResponseBody		
		public FactTable getLastFact( ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {	
			
			Document document =  factTableService.getLastFact();
			Gson gson = new Gson();
			FactTable fact = gson.fromJson(document.toJson(), FactTable.class);
			return fact;
			  
	    }
	
		
		// get fact by id 
		
		@ApiOperation(value = "Récupérer  Fact par Id ")
		@GetMapping("/retrieve-fact-byId/{id}")
		@ResponseBody		
		public FactTable getFactTablebyId( @PathVariable ObjectId id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {		
			
			FactTable fact =  factTableService.getFactById(id) ;
			return fact;
			  
	    }
		
		
		// calculate and add KPIs by fact id 
		
		@ApiOperation(value = "calculate And Add KPIs ")
		@GetMapping("/factTable-calculate-Add-KPIs/{id}")
		@ResponseBody	
		public void calculateAndAddKPIs(@PathVariable String id) {
					
			factTableService.calculateAndAddKPIs( id);			 
		 }
		
//		 update fact table 
//		@ApiOperation(value = "Update FactTable KPIs")
//		@PostMapping("/update-factTable")
//		public void updateFactTableKPIs( ) {
//			
//			
//			for (int i=0 ; i<=16 038 ; i++)
//			
//			{  List<FactTable> factTableList = factTableService.retrieveFactTablePaginated(i,20);
//	  
//		    	for (FactTable fact : factTableList )		 
//		    	{
//		    		
//		    		if (fact.getFields().size()==11) {
//		                factTableService.calculateAndAddKPIs(fact.getId().toString());	
//		    		}
//		    	}	
//		    	System.out.println("page : //////////////////////////////  :  "+ i);
//			}
//			//@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size
//		}
		
		@ApiOperation(value = "Update FactTable KPIs")
		@PostMapping("/update-factTable")
		public void updateFactTableKPIs() {
			
		    int startPage = 0;
		    int endPage = 16037;
		    int pageSize = 20;

		    IntStream.rangeClosed(startPage, endPage)
		            .parallel() // Enable parallel processing
		            .forEach(page -> {
		                List<FactTable> factTableList = factTableService.retrieveFactTablePaginated(page, pageSize);

		                factTableList.stream()
		                        .filter(fact -> fact.getFields().size() == 11)
		                        .forEach(fact -> {
		                            factTableService.calculateAndAddKPIs(fact.getId().toString());
		                        });

		                System.out.println("page : //////////////////////////////  :  " + page);
		            });
		}

		
		
		
		
		
		@ApiOperation(value = "Update Last Fact KPIs")
		@PostMapping("/update-last-fact")
		public void updateLastFactKPIs( ) {
			
			factTableService.UpdateLastFact();
		}
		
		@ApiOperation(value = "Update  FactTable and add KPIs when order is added ")
		@PostMapping("/update-fact-after-add")
		public void updateFactKPIs( ) {
			
			factTableService.AddFact();
		}
		
		@ApiOperation(value = "Testing Filter function ")
		 @GetMapping("/test")
		    public ResponseEntity<?> testAggregation() {
			
			
			 //  Filter(DateType, aggregationType, field, key, aggregationStage, matchValue, LookupCollection, LookupField, projectField1, projectField2)
//	        AggregateIterable<Document> group = Filter(null,"sum", field, key, "group", null, null, null, null, null);

			
//			
//			String key = "productSKU_key";
//
//			AggregateIterable<Document> results = factTableService.Filter(
//				    null, null, "totalPurchases", key, "match", "5",
//				    null, null, null, null, true);
//
//				System.out.println("results :: " + results);
//
//				List<String> jsonResults = new ArrayList<>();
//				int i =0;
//				for (Document document : results) {
//					java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.ALL);
//
//					try {
//	
//						 if (document != null && document.containsKey("totalPurchases")) {
//							 i++;
//						        String json = document.toJson();
//						        if (json != null) {
//						          //  System.out.println("doc json   ::  " + json);
//						            jsonResults.add(json);
//						           // System.out.println("done " + i);
//							           System.out.println("last ::  " + document.get("_id"));
//
//									//System.out.println("results size"+ jsonResults.size());
//
//						        } else {
//						            System.out.println("Document toJson() returned null or field does not exist ");
//						        }
//						    } else {
//						        System.out.println("Document is null");
//						    }
//							
//					
//				        // Your existing logic here...
//				    } catch (Exception e) {
//				        e.printStackTrace();
//				        System.out.println("Error processing document: " + document);
//				    }
//					
//				}
//				 System.out.println("jsonResults  ::  " + jsonResults);
//
//				return new ResponseEntity<>(jsonResults, HttpStatus.OK);
			
	
			String key = "productSKU_key";
			AggregateIterable<Document> results = (AggregateIterable<Document>) factTableService.Filter(
			        null, null, "totalPurchases", key, "match", "9",
			        null, null, null, null, true);
			
			


			System.out.println("results :: " + results);

			List<String> jsonResults = new ArrayList<>();
			int i = 0;
			for (Document document : results) {
			    java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.ALL);

			    try {
			        if (document != null && document.containsKey("totalPurchases")) {
			            i++;
			            String json = document.toJson();
			            if (json != null) {
			                // System.out.println("doc json   ::  " + json);
			                jsonResults.add(json);
			                // System.out.println("done " + i);
			               // System.out.println("last ::  " + document.get("_id"));
			                // System.out.println("results size"+ jsonResults.size());
			            } else {
			                System.out.println("Document toJson() returned null or field does not exist ");
			            }
			        } else {
			            System.out.println("Document is null or does not contain 'totalPurchases' field");
			        }

			    } catch (MongoException e) {
			        System.out.println("Error processing MongoDB document: " + document);
			        e.printStackTrace();
			    } catch (Exception e) {
			        System.out.println("Error processing document: " + document);
			        e.printStackTrace();
			    }
			}

			System.out.println("jsonResults  ::  " + jsonResults);

			return new ResponseEntity<>(jsonResults, HttpStatus.OK);

				

		}
		

		
		
		// test get field type but won't work cause i changed it to global it needs to define the collection 		
		
//		 @ApiOperation(value = "test_get_fieldType ")
//		 @GetMapping("/test_get_fieldType/{field}")
//		
//	     public	BsonType getFieldType(@PathVariable String field) {
//			
//			BsonValue firstDistinctValue = globalService.getFieldType(field,collectionFactTable );
//
//			return firstDistinctValue.getBsonType();
//		}
//		 
		 
		 
		 
		 
		 
		 
		 
		 
		
	
		
}
