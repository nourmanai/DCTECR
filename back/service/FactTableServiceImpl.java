package tn.esprit.spring.service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonObjectId;
import org.bson.BsonString;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.codecs.BsonValueCodecProvider;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Account;
import tn.esprit.spring.entities.Ad;
import tn.esprit.spring.entities.Campaign;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.FactTable;
import tn.esprit.spring.entities.Order;
import tn.esprit.spring.entities.ProductSKU;
import tn.esprit.spring.entities.Review;
import tn.esprit.spring.entities.Sales;
import tn.esprit.spring.entities.Shipment;
import tn.esprit.spring.entities.Supplier;
import tn.esprit.spring.entities.Team;
import tn.esprit.spring.serviceInterface.IAdService;
import tn.esprit.spring.serviceInterface.IClientService;
import tn.esprit.spring.serviceInterface.IFactTableService;
import tn.esprit.spring.serviceInterface.IGlobalService;
import tn.esprit.spring.serviceInterface.IReviewService;
import tn.esprit.spring.serviceInterface.ISalesService;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


@Service
@Slf4j
public class FactTableServiceImpl<T> implements IFactTableService {
	
    private static final Logger log = LoggerFactory.getLogger(FactTableServiceImpl.class);
    
    CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
    ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    MongoClientSettings settings = MongoClientSettings.builder()
    		.applyConnectionString(connectionString)
            .codecRegistry(codecRegistry)
            .build();

    MongoClient mongoClient = MongoClients.create(settings);
    
   // MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	MongoDatabase database = mongoClient.getDatabase("DCSTEC_DW");
	MongoCollection<Document> collectionFactTable = database.getCollection("FactTable");
	
	

	// Other classes 
	
	MongoCollection<Document> collectionAccounts = database.getCollection("DIM_Accounts");
	MongoCollection<Document> collectionAds = database.getCollection("DIM_Ads");
	MongoCollection<Document> collectionCampaigns = database.getCollection("DIM_Campaigns");
	MongoCollection<Document> collectionClients = database.getCollection("DIM_Clients");
	MongoCollection<Document> collectionOrders = database.getCollection("DIM_Orders");
	MongoCollection<Document> collectionProducts_SKU = database.getCollection("DIM_Products_SKU");
	MongoCollection<Document> collectionShippments = database.getCollection("DIM_Shippments");
	MongoCollection<Document> collectionSuppliers = database.getCollection("DIM_Suppliers");
	MongoCollection<Document> collectionReviews = database.getCollection("DIM_Reviews");
	MongoCollection<Document> collectionDIM_Sales = database.getCollection("DIM_Sales");
	MongoCollection<Document> collectionDIM_Teams = database.getCollection("DIM_Teams");
	
	
	MongoCollection<Document> collectiontest = database.getCollection("test");



	@Autowired
	IGlobalService globalService;
	

	@Autowired
	IClientService clientService;	
	
	
	@Autowired
	IReviewService reviewService;
	
	@Autowired
	ISalesService saleService;
	
	@Autowired
	IAdService adService;
	

	
	
	// retrieve all fact table 
	@Override
	public List<FactTable> retrieveAllFactTable() {
		
		List<FactTable> FactTable =  new ArrayList<>();
	    for (Document document : collectionFactTable.find()) {
	        ObjectId id = document.getObjectId("_id"); 
	        Map<String, Object> Fields = new HashMap<>();
	       
	        for (Entry<String, Object> entry : document.entrySet()) {
	            if (!entry.getKey().equals("_id")) {
	                Object value = entry.getValue();
	                if (value instanceof ObjectId) {
	                	Fields.put(entry.getKey(), ((ObjectId) value).toHexString());
	                } else {
	                	Fields.put(entry.getKey(), value);
	                }
	            }
	        }
	        FactTable obj = new FactTable(id, Fields);
//			 log.info("FactTable log: {}", obj);
//			 log.info("FactTable list: {}","FactTable{" +
//		                "id=" + obj.getId() +
//		                ", fields=" + obj.getFields() +
//		                '}');
			 FactTable.add(obj);        
	    }
		return FactTable;	
	}	
	
	
	
	// get Fact table  by Field
	
	@Override
	public List<FactTable> getFactTablebyField(String fieldName, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		List<FactTable> factTable = retrieveAllFactTable();
		
		List<FactTable> matching = globalService.getObjectsByFieldValue(factTable, fieldName, value);

		if (!matching.isEmpty()) {
		    System.out.println(matching.size() + " factTable have been found.");
		    return matching;
		} else {
		    System.out.println("No factTable found.");
		    return Collections.emptyList();
		}
		
	}
	
	

	
	// get fact by id 
	@Override
	public FactTable getFactById(ObjectId id) {
	    Document document = globalService.getDocumentById(collectionFactTable, id);
	    Gson gson = new Gson();
	    Map<String, Object> fields = new HashMap<>();

	    for (Entry<String, Object> entry : document.entrySet()) {
	        if (!entry.getKey().equals("_id")) {
	            Object value = entry.getValue();
	            System.out.println("Key: " + entry.getKey() + ", Value: " + value);

	            if (value instanceof ObjectId) {
		            System.out.println("heeere: , Value: " + value);

	                try {
	                    String objectId =  value .toString();
			            System.out.println("heeere: , objectId: " + objectId);

	                    fields.put(entry.getKey(), objectId);
	                } catch (IllegalArgumentException e) {
	                    fields.put(entry.getKey(), value);
			            System.out.println("heeere: , catch: ");
	                }
	            } else {
	                fields.put(entry.getKey(), value);
		            System.out.println("heeere: , else: ");

	            }
	        }
	    }

	    FactTable fact = new FactTable(id, fields);
	    return fact;
	}

	
	
	 
	// get last fact added to Fact table 
	
	@Override
    public Document getLastFact() {
		
	 Document lastDocument = globalService.getLastDocument(collectionFactTable);

     if (lastDocument != null) {
         System.out.println("Last Fact: " + lastDocument.toJson());
    	 return lastDocument;

     } else {
         System.out.println("No Fact found in the collection.");
    	 return null;

     }
     
    }
	
	
	
	//get object by field (keys)
	
	@Override
	public Document GetObjectFromFactTable(FactTable item , MongoCollection<Document> collection, String fieldName ) {

		String fieldValue = (String) item.getFields().get(fieldName);
	    	
	        if (fieldValue != null ) {
	          
	        	ObjectId key = new ObjectId(fieldValue) ;
	        	Document document = globalService.getDocumentById(collection, key);
	        	return document;
	    }
	        else return null;
	}
	
	
	
	// Spend
	
	@Override
	public double Calcul_Spend(Order order, Campaign campaign) {
	    
		double spend = 0;         
	    
	    // have to test on supplier attribute = true in shipment in order (globalService.getDocumentById)    
	    	
    	ObjectId shipment_key = new ObjectId(order.getFields().get("shipment_key").toString());

	    Document shipmentDocument = globalService.getDocumentById(collectionShippments, shipment_key);
	    Gson gson = new Gson();
    	Map<String, Object> fieldsdoc3 = new HashMap<>();
        for (Entry<String, Object> entry : shipmentDocument.entrySet()) {
            if (!entry.getKey().equals("_id")) { 
            	fieldsdoc3.put(entry.getKey(), entry.getValue());
            }
        }
        Shipment shipment = new Shipment (shipment_key, fieldsdoc3);
        System.out.println(" shipment supplier " + shipment.getFields().get("isSupplier"));

	    
	    try {
	        if (Boolean.TRUE.equals(shipment.getFields().get("isSupplier"))) {
	        	 double price = 0 ;
	        	if (shipment.getFields().get("price")!=null)
	        	{price = (double) shipment.getFields().get("price");}
	            else price = (double) shipment.getFields().get("shipment_price");
	            double totalSpent = (double)  campaign.getFields().get("total_spent");
	            
	            spend = (double)((price != 0 ? price : 0) + (totalSpent != 0 ? totalSpent : 0));       
	            System.out.println(" is supplier "+ spend);

	        }        
	        else { 

	            spend = (double) campaign.getFields().get("total_spent");
	            spend = spend != 0 ? spend : 0;
	            System.out.println(" is not supplier " + spend);

	        }
	    } catch (ClassCastException e) {
	        e.printStackTrace();
	    }
	     
	    return spend; 
	}

	
	
	// nbr likes 
	
	@Override
	public float Calcul_nbrlikes(Ad ad, Review review) {
		float likes = 0.F;
		double rating = 0;
	    Object ratingObj = review.getFields().get("Rating");
	    if (ratingObj != null) {
	         rating = (double) ratingObj;
	    }		
	     System.out.println("rating ********************************" + rating);
	     int impressions = (int) ad.getFields().get("Impressions");
	     System.out.println("Impressions" + impressions);

	     if (rating != 0 && rating > 8) {
		     
	    	 likes = (impressions != 0 ? impressions : 0) + 1;

		        }	
	     else likes = (impressions != 0 ? impressions : 0);     	
	    	    
	    System.out.println("likes" + likes);


	    return likes;
	}

	 
	
	// nbr Reviews 
	
     @Override
	 public int Calcul_nbrReviews ( Ad ad , Review review, int reviews) {
    	 if (review !=null ) {reviews += 1;} 		
    	 
		return reviews ;
	  }   
     
     
     // nbr views 
     
     @Override
     public int Calcul_nbrViews (Ad ad) {
    	    int nb_clicks = (int) ad.getFields().get("Clicks");
    	 return nb_clicks; 	
     }
     
     
     
     // nbr purchases by order 
     
	@Override
     public int Calcul_nbrPurchasesbyOrder(Order order) {
		//int purchases = (int) order.getFields().get("Quantity_Ordered");
		 Object actual = order.getFields().get("Quantity_Ordered");

		    if (actual instanceof Integer) {
		    	int purchases = (int) actual;
		        return purchases;
		    }
	
		    else return 0; 
    	 
     }

	
    // nbr purchases by client  

	@Override
	public int Calcul_nbrPurchasesbyClient(Client client) {
		int purchases = (int) client.getFields().get("nbr_purchase_Monthly");
		return purchases;
	}
  
	
	// fulfilled orders 
	
	@Override
	public int Calcul_FulfilledOrder(Order order, int fulfilledOrders) {
		
    	ObjectId shipment_key = new ObjectId(order.getFields().get("shipment_key").toString());

	    Document shipmentDocument = globalService.getDocumentById(collectionShippments, shipment_key);
	    Gson gson = new Gson();
    	Map<String, Object> fieldsdoc3 = new HashMap<>();
        for (Entry<String, Object> entry : shipmentDocument.entrySet()) {
            if (!entry.getKey().equals("_id")) { 
            	fieldsdoc3.put(entry.getKey(), entry.getValue());
            }
        }
        Shipment shipment = new Shipment (shipment_key, fieldsdoc3);
		if (shipment.getFields().get("status").equals("Delivered") || shipment.getFields().get("status").equals("Shipped"))
		{
			fulfilledOrders ++;
		}
	
		return fulfilledOrders;		
	}
	
	
	// returned orders 
	
	@Override
	public int Calcul_ReturnedOrder(Order order, int ReturnedOrders) {

		ObjectId shipment_key = new ObjectId(order.getFields().get("shipment_key").toString());

	    Document shipmentDocument = globalService.getDocumentById(collectionShippments, shipment_key);
	    Gson gson = new Gson();
    	Map<String, Object> fieldsdoc3 = new HashMap<>();
        for (Entry<String, Object> entry : shipmentDocument.entrySet()) {
            if (!entry.getKey().equals("_id")) { 
            	fieldsdoc3.put(entry.getKey(), entry.getValue());
            }
        }
        Shipment shipment = new Shipment (shipment_key, fieldsdoc3);
		if (shipment.getFields().get("status").equals("Refunded") || shipment.getFields().get("status").equals("Return to Sender"))
		{
			ReturnedOrders ++;
		}
	
		return ReturnedOrders;		
	}
	
	// satisfied clients 
	
	@Override
	public int Calcul_SatisfiedClients (Client client, int satisfiedClients) {
		
		 if ( (Boolean.TRUE.equals(client.getFields().get("satisfied"))) && (Boolean.FALSE.equals(client.getFields().get("reseller")))  )
		 { 
			 satisfiedClients ++; 
		 }
			return satisfiedClients; 
		   }
	
	
	// unsatisfied clients 

	@Override
	public int Calcul_UnsatisfiedClients (Client client, int unsatisfiedClients) {
		
		 if ( (Boolean.FALSE.equals(client.getFields().get("satisfied"))) && (Boolean.FALSE.equals(client.getFields().get("reseller")))  )
		 { 
			 unsatisfiedClients ++; 
		 }
			return unsatisfiedClients; 
		   }
		 	
	
	
	// satisfied resellers 
	@Override
    public int Calcul_SatisfiedResellers (Client client, int satisfiedResellers) {
				
		if ((Boolean.TRUE.equals(client.getFields().get("satisfied"))) && (Boolean.TRUE.equals(client.getFields().get("reseller"))) ) {
				 
				 satisfiedResellers++;
				 
			 }
			
			return satisfiedResellers; 
		}
	
	
	
	// unsatisfied resellers 	
	@Override
    public int Calcul_UnsatisfiedResellers (Client client, int unsatisfiedResellers) {
				
		if ((Boolean.FALSE.equals(client.getFields().get("satisfied"))) && (Boolean.TRUE.equals(client.getFields().get("reseller"))) ) {
				 
				 unsatisfiedResellers++;
				 
			 }
			
			return unsatisfiedResellers; 
		}

	
	// production Capacity 
	@Override
	public float Calcul_productionCapacity( Order order , ProductSKU productSKU) {
	
		int nbr_unit_inventory = (int) productSKU.getFields().get("nbr_unit_inventory");
		System.out.println("order" + order.getFields());
		int Qt_Ordered = (int) order.getFields().get("Quantity_Ordered");
		
		float percentage = ( Qt_Ordered *100 )/ nbr_unit_inventory ;
	
		return percentage;			
	}
	
	
	// Availability 
	@Override
	public int Calcul_Availability(Shipment shipment, int late_days) {
			//late_days = (int) shipment.getFields().get("late_days");
			 Object actuallateDays = shipment.getFields().get("late_days");

			    if (actuallateDays instanceof Integer) {
			        late_days = (int) actuallateDays;
			        return late_days;
			    }
		
			    else return 0;
		// if (Boolean.TRUE.equals(shipment.getFields().get("isSupplier"))) in fact i have to test if it's supllier or not to know where to aad the value 
		// in ShippmentAvailability or supplier availability fields 
	}
	

	
	// nbr new resellers 
	@Override
	public int Calcul_nbrNewResellers (Client client ,int nbr_newReselers) {
		
		// use this function when adding Client 
		if (Boolean.TRUE.equals(client.getFields().get("reseller")))
				{
			nbr_newReselers ++ ;
				}
		
		return nbr_newReselers;
	}
	
	
	
	// net profit margin 
	@Override
	public float Calcul_Net_ProfitMargin(Sales sales , Campaign campaign ){
		
		// returning percentage 
		
		String  totalSales = (String) sales.getFields().get("Total");
	     String numericPart = totalSales.replaceAll("[^\\d.]", "");
	     float total_sales = Float.parseFloat(numericPart);
		    float total_revenu = ((Number) campaign.getFields().get("total_revenue")).floatValue();

	     float net_profitMargin = (total_revenu-total_sales) *100 / total_revenu  ;			
		return net_profitMargin ;	
	}
	
	 // gross profit margin 
	@Override
	public float Calcul_Gross_ProfitMargin (Sales sales, double Total_Spend, Campaign campaign) {
		
		// Total_Spend  is coming from Calcul_totalSpend()
		
		String  totalSales = (String) sales.getFields().get("Total");
	     String numericPart = totalSales.replaceAll("[^\\d.]", "");
	     float total_sales = Float.parseFloat(numericPart);
	     double total_revenu = ((Number) campaign.getFields().get("total_revenue")).doubleValue();

	     float Gross_ProfitMargin = (float) ((total_sales-Total_Spend)*100/total_revenu) ;
		
		return Gross_ProfitMargin;
	}
	
	// total profit sales 
	@Override
	public float Calcul_TotalProfit_Sales(Sales sales, double Total_Spend) {
		
		String  totalSales = (String) sales.getFields().get("Total");
	     String numericPart = totalSales.replaceAll("[^\\d.]", "");
	     float total_sales = Float.parseFloat(numericPart);

		 Float TotalProfit = total_sales-(float)Total_Spend;
		
		return TotalProfit;
	}
	
	// total profit campaigns 
	@Override
	public float Calcul_TotalProfit_Campaign(Campaign campaign, double Total_Spend) {
		
	     Float total_revenu = ((Number) campaign.getFields().get("total_revenue")).floatValue();
		float TotalProfit = total_revenu-(float)Total_Spend;

		return TotalProfit;
	}
	
	// repeat purchases rate 
	@Override
	public int calcul_RepeatPurchaseRate(Client customer, int repeatCustomers) {
	

		List<Client> Clients = clientService.retrieveAllClients();


	        if ((int)customer.getFields().get("nbr_purchase_Monthly") > 1) {
	            repeatCustomers++;
	        }
	    

	    return (int) repeatCustomers / Clients.size();
	}

	
	
	// purchase frequency 
	@Override
	public float calcul_PurchaseFrequency(Client client,  float totalPurchases ) {

		List<Client> Clients = clientService.retrieveAllClients();

	    totalPurchases += (int) client.getFields().get("nbr_purchase_Monthly");
	    
	    return (float) (totalPurchases / Clients.size());
	}

	
	
	// total revenu 
	@Override
	public float Calcul_TotalRevenu(Sales sales, Campaign campaign) {
		
	    float total_revenu = ((Number) campaign.getFields().get("total_revenue")).floatValue();
			String  totalSales = (String) sales.getFields().get("Total");
	     String numericPart = totalSales.replaceAll("[^\\d.]", "");
	     float total_sales = Float.parseFloat(numericPart);
	     Float total_Revenu = total_sales+total_revenu;
	     
	     return total_Revenu;
	}
	
	
	// total purchases 
	@Override
	public float Calcul_TotalPurchases (Client client , int totalPurchases) {
		
//		List<Client> Clients = clientService.retrieveAllClients();
	     Float nbr_purchase_Monthly = ((Number)  client.getFields().get("nbr_purchase_Monthly")).floatValue();

	    totalPurchases += nbr_purchase_Monthly;
	    
	    return (float) totalPurchases;
	}
	
	
	// Avg puchases 
	@Override
	public float calcul_AveragePurchaseValue(float totalRevenue, float totalPurchases) {
		
		// Calcul_TotalRevenu () / Calcul_TotalPurchases()  passer en parametres 
		
	    return totalRevenue / totalPurchases;
	}

	
	// purchase cycle 
	@Override
	public float calcul_PurchaseCycle(float purchaseFrequency) {
		
		// calcul_PurchaseFrequency()
	    return 1 / purchaseFrequency;
	}

	
	// customer life span 
	@Override
	public float calcul_CustomerLifespan(float purchaseCycle) {
		
		//calcul_PurchaseCycle()
	    return 1 / purchaseCycle;
	}

	
	// CLV 
	@Override
	public float calcul_CLV(Client client, float averagePurchaseValue, float purchaseFrequency, float customerLifespan) {
		
		//calcul_PurchaseCycle() / calcul_PurchaseFrequency() / calcul_CustomerLifespan()
	    return averagePurchaseValue * purchaseFrequency * customerLifespan;
	}

	
	// is Qualified lead 
	@Override
	public boolean isQualifiedLead(Client lead) {
		
	    float minAnnualSalary = 50000;  // Minimum required annual salary
	    float maxDebtRatio = 0.5F;       // Maximum allowed debt-to-income ratio
	    float minNetWorth = 10000;      // Minimum required net worth

	    float annualSalary = ((Number)  lead.getFields().get("annual Salary")).floatValue();

	    float netWorth = ((Number)  lead.getFields().get("net worth")).floatValue();
	    		

	    float debtToIncomeRatio = ((Number)  lead.getFields().get("credit card debt")).floatValue();

	    boolean isQualified = annualSalary >= minAnnualSalary
	                          && debtToIncomeRatio <= maxDebtRatio
	                          && netWorth >= minNetWorth;

	    return isQualified; // true if qualified else false 
	}
	
	
	
	
	// nbr qualified leads 
	@Override
	public int calcul_QualifiedLeads(Client client ,  int qualifiedLeads ) {
	   
	        if (isQualifiedLead(client)) {
	            qualifiedLeads++;
	        
	    }
	    
	    return qualifiedLeads;
	}
	
	
	
	
	// active clients 
	@Override
	public int calcul_ActiveClients(Shipment shipment, int days, int activeClients) {

		
		LocalDate cutoffDate = LocalDate.now().minusDays(days);
	    
	    Object actualShipmentDateObj = shipment.getFields().get("actual_shipmentDate");

	    if (actualShipmentDateObj instanceof Date) {
	        Date actualShipmentDate = (Date) actualShipmentDateObj;

	        if (actualShipmentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(cutoffDate)) {
	            activeClients++;
	        }
	    }

	    return activeClients;
	}

	
	
	// MRR 
	@Override
	public float Calcul_MRR(int activeClients, Client client, float averagePurchaseValue){
		
		// calcul_ActiveClients() / calcul_AveragePurchaseValue() 
	    float purchasesPerMonth = ((Number)  client.getFields().get("nbr_purchase_Monthly")).floatValue();

		float MRR = activeClients * purchasesPerMonth * averagePurchaseValue;

		return MRR; 
	}
	
	// ROI sales 
	@Override
	public float calcul_ROI_Sales(float TotalProfit_Sales, double Total_Spend ) {
		
		//Calcul_TotalProfit_Sales() / Calcul_Spend
		
		float gainFromInvestment = TotalProfit_Sales ;
		float costOfInvestment = (float)Total_Spend ;

	    return (gainFromInvestment - costOfInvestment) / costOfInvestment;
	}

	
	
	// ROI campaigns 
	@Override
	public float calcul_ROI_Campaigns(float TotalProfit_Campaign, double Total_Spend) {
		
		//Calcul_TotalProfit_Campaign() / Calcul_Spend
		
		float gainFromInvestment = TotalProfit_Campaign ;
		float costOfInvestment =(float)Total_Spend ;

	    return (gainFromInvestment - costOfInvestment) / costOfInvestment;
	}

	
	
	
    // Caclcul + add KPIs 
	@Override 
    public void calculateAndAddKPIs(String id) {
		
	
    	// get fact table id to use in function add field below 
    	 ObjectId objectId = new ObjectId(id);
    	 FactTable fact = getFactById(objectId);
         System.out.println("Fact " + fact + " objectId " + objectId);

    	 Map<String, Object> fields = fact.getFields();
         System.out.println("fields " + fields );

        
     	ObjectId orderKey =  globalService.convertToObjectId(fields.get("order_key").toString()); 	
        System.out.println("orderKey " + orderKey );

     	ObjectId productSKU_key =  globalService.convertToObjectId(fields.get("productSKU_key").toString()); 	
        System.out.println("productSKU_key " + productSKU_key );

     	ObjectId shipment_key =  globalService.convertToObjectId(fields.get("shipment_key").toString()); 	
        System.out.println("shipment_key " + shipment_key );

     	ObjectId client_key =  globalService.convertToObjectId(fields.get("client_key").toString()); 	
        System.out.println("client_key " +client_key );

        ObjectId supplier_key =  globalService.convertToObjectId(fields.get("supplier_key").toString()); 
        System.out.println("supplier_key " + supplier_key );

        ObjectId review_key =  globalService.convertToObjectId(fields.get("review_key").toString());
        System.out.println("review_key" +review_key );

        ObjectId sale_key =  globalService.convertToObjectId(fields.get("sale_key").toString());
        System.out.println("sale_key " + sale_key );

        ObjectId Ad_key =  globalService.convertToObjectId(fields.get("Ad_key").toString());
        System.out.println("Ad_key " + Ad_key );

        ObjectId campaign_key =  globalService.convertToObjectId(fields.get("campaign_key").toString());
        System.out.println("campaign_key " + campaign_key );

        ObjectId team_key =  globalService.convertToObjectId(fields.get("team_key").toString());
        System.out.println("team_key " + team_key );

        ObjectId account_key =  globalService.convertToObjectId(fields.get("account_key").toString());
        System.out.println("account_key " + account_key );



  
    	 // retrieve order object 
    	Document orderDocument = globalService.getDocumentIfValid(collectionOrders, orderKey);
    	Map<String, Object> fieldsdoc = globalService.getFieldOfDocument(orderDocument);
        Order order = new Order (orderKey, fieldsdoc);

   	    // retrieve productSKU object 
    	Document productSKUDocument = globalService.getDocumentIfValid(collectionProducts_SKU, productSKU_key);
    	  Map<String, Object> fieldsdoc2 = globalService.getFieldOfDocument(productSKUDocument);
    	  ProductSKU productSKU = new ProductSKU (productSKU_key, fieldsdoc2);


   	    // retrieve shipment object 
    	Document shipmentDocument = globalService.getDocumentIfValid(collectionShippments, shipment_key);
    	Map<String, Object> fieldsdoc3 = globalService.getFieldOfDocument(shipmentDocument);
        Shipment shipment = new Shipment (shipment_key, fieldsdoc3);


   	    // retrieve client object 
    	Document clientDocument = globalService.getDocumentIfValid(collectionClients, client_key);
    	Map<String, Object> fieldsdoc4 = globalService.getFieldOfDocument(clientDocument);
        Client client = new Client (client_key, fieldsdoc4);


   	    // retrieve supplier object 
    	Document supplierDocument = globalService.getDocumentIfValid(collectionSuppliers, supplier_key);
    	Map<String, Object> fieldsdoc5 = globalService.getFieldOfDocument(supplierDocument);
        Supplier supplier = new Supplier (supplier_key, fieldsdoc5);


   	    // retrieve review object 
    	Document reviewDocument = globalService.getDocumentIfValid(collectionReviews, review_key);
    	Map<String, Object> fieldsdoc6 =globalService.getFieldOfDocument(reviewDocument);
        Review review  = new Review (review_key, fieldsdoc6);


   	    // retrieve Sales object 
    	Document saleDocument = globalService.getDocumentIfValid(collectionDIM_Sales, sale_key);
    	Map<String, Object> fieldsdoc7 = globalService.getFieldOfDocument(saleDocument);
         Sales sale  = new Sales (sale_key, fieldsdoc7);


   	    // retrieve campaign object 
    	Document adDocument = globalService.getDocumentIfValid(collectionAds, Ad_key);
    	Map<String, Object> fieldsdoc8 =  globalService.getFieldOfDocument(adDocument);      
    	Ad ad= new  Ad (Ad_key, fieldsdoc8);



   	    // retrieve campaign object 
    	Document campaignDocument = globalService.getDocumentIfValid(collectionCampaigns, campaign_key);
    	Map<String, Object> fieldsdoc9 = globalService.getFieldOfDocument(campaignDocument);
        Campaign campaign = new  Campaign (campaign_key, fieldsdoc9);


   	    // retrieve team object 
    	Document teamDocument = globalService.getDocumentIfValid(collectionDIM_Teams, team_key);
    	Map<String, Object> fieldsdoc10 = globalService.getFieldOfDocument(teamDocument);
        Team team  = new  Team (team_key, fieldsdoc10);


   	    // retrieve account object 
    	Document accountDocument = globalService.getDocumentIfValid(collectionAccounts, account_key);
    	Map<String, Object> fieldsdoc11 = globalService.getFieldOfDocument(accountDocument);
        Account account   = new  Account (account_key, fieldsdoc11);


   	 
        
        // collectiontest !!! 

    	if (shipmentDocument != null) {

    		
    		 int late_days = Calcul_Availability(shipment, 0); 		
    		 globalService.addFieldToDocument(collectiontest, objectId, "availability", late_days);
    		 
    		 int ActiveClients = calcul_ActiveClients(shipment, 15 , 0);   		 
    		 globalService.addFieldToDocument(collectiontest, objectId, "activeClients", ActiveClients);
  	
    	}

    	if (adDocument != null) {
    		 int nbr_views = Calcul_nbrViews(ad);
    		 globalService.addFieldToDocument(collectiontest, objectId, "nbr_views", nbr_views);
    		 
    		 if (reviewDocument != null ) 
    		 {
    			Float  nbr_likes = Calcul_nbrlikes(ad, review);
       		  globalService.addFieldToDocument(collectiontest, objectId, "nbr_likes", nbr_likes);
       		  
       		  int nbr_reviews = Calcul_nbrReviews(ad, review, 0);
       		  globalService.addFieldToDocument(collectiontest, objectId, "nbr_reviews", nbr_reviews);

    		 }   		
    			}
    	
    	if (orderDocument != null ) { 	
    		
    		int nbr_purchasesByOrder = Calcul_nbrPurchasesbyOrder(order);
     		 globalService.addFieldToDocument(collectiontest, objectId, "nbr_purchasesByOrder", nbr_purchasesByOrder);
     		 
     		 int nbr_Fulfilled_Orders = Calcul_FulfilledOrder(order, 0);
     		 globalService.addFieldToDocument(collectiontest, objectId, "nbr_Fulfilled_Orders", nbr_Fulfilled_Orders);
     		 
     		 int nbr_Returned_Orders = Calcul_ReturnedOrder(order, 0);
     		 globalService.addFieldToDocument(collectiontest, objectId, "nbr_Returned_Orders", nbr_Returned_Orders);
     		 
     		 
     		 if (productSKUDocument != null ) {
     			 
     			 Float productionCapacity = Calcul_productionCapacity(order, productSKU);
         		 globalService.addFieldToDocument(collectiontest, objectId, "productionCapacity", productionCapacity);
	 
     		                          }
     		 
     		 if (campaignDocument != null )
     		 {
     			 double Total_Spend = Calcul_Spend(order, campaign);
         		 globalService.addFieldToDocument(collectiontest, objectId, "Total_Spend", Total_Spend);
         		 
         		 if (saleDocument != null) {
         		 
         		    Float Gross_ProfitMargin = Calcul_Gross_ProfitMargin(sale, Total_Spend, campaign);
            		globalService.addFieldToDocument(collectiontest, objectId, "Gross_ProfitMargin", Gross_ProfitMargin);
            		
            		Float TotalProfit_Sales = Calcul_TotalProfit_Sales(sale, Total_Spend);
            		globalService.addFieldToDocument(collectiontest, objectId, "TotalProfit_Sales", TotalProfit_Sales);
            		
            		Float TotalProfit_Campaign= Calcul_TotalProfit_Campaign(campaign, Total_Spend);
            		globalService.addFieldToDocument(collectiontest, objectId, "TotalProfit_Campaign", TotalProfit_Campaign);

         		    Float ROI_sales = calcul_ROI_Sales(TotalProfit_Sales, Total_Spend);
            		globalService.addFieldToDocument(collectiontest, objectId, "ROI_sales", ROI_sales);
            		
            		 Float ROI_campaign = calcul_ROI_Campaigns(TotalProfit_Campaign, Total_Spend);
             		globalService.addFieldToDocument(collectiontest, objectId, "ROI_campaign", ROI_campaign);
   
         		               }
	 
     		 }
    	}
    	
    	
    	if (clientDocument != null) {
    		
    		int nbr_PurchaseByClient = Calcul_nbrPurchasesbyClient(client);
			globalService.addFieldToDocument(collectiontest, objectId, "nbr_PurchaseByClient", nbr_PurchaseByClient);
			
			int nb_SatisfiedClients = Calcul_SatisfiedClients(client, 0); 
			globalService.addFieldToDocument(collectiontest, objectId, "nb_SatisfiedClients", nb_SatisfiedClients);
			
			int nb_SatisfiedReselers = Calcul_SatisfiedResellers(client, 0); 
			globalService.addFieldToDocument(collectiontest, objectId, "nb_SatisfiedReselers", nb_SatisfiedReselers);
			
			int nbr_New_Resellers = Calcul_nbrNewResellers(client, 0);
			globalService.addFieldToDocument(collectiontest, objectId, "nbr_New_Resellers", nbr_New_Resellers);
			
			int RepeatPurchaseRate = calcul_RepeatPurchaseRate(client, 0);
			globalService.addFieldToDocument(collectiontest, objectId, "RepeatPurchaseRate", RepeatPurchaseRate);
			
			
		    Float totalPurchases = Calcul_TotalPurchases(client, 0);
			globalService.addFieldToDocument(collectiontest, objectId, "totalPurchases", totalPurchases);

			Float purchaseFrequency = calcul_PurchaseFrequency(client, totalPurchases);
			Float purchaseCycle = calcul_PurchaseCycle(purchaseFrequency);
			
			Float Client_LifeSpan = calcul_CustomerLifespan(purchaseCycle);
			globalService.addFieldToDocument(collectiontest, objectId, "Client_LifeSpan", Client_LifeSpan);
			
			
			int Unsatisfied_Clients = Calcul_UnsatisfiedClients(client, 0);
			globalService.addFieldToDocument(collectiontest, objectId, "Unsatisfied_Clients", Unsatisfied_Clients);
			
			int Unsatisfied_Resellers = Calcul_UnsatisfiedResellers(client, 0);
			globalService.addFieldToDocument(collectiontest, objectId, "Unsatisfied_Resellers", Unsatisfied_Resellers);
			
			int nbr_QualifiedLeads = calcul_QualifiedLeads(client, 0);
			globalService.addFieldToDocument(collectiontest, objectId, "nbr_QualifiedLeads", nbr_QualifiedLeads);
			
				

    			if ((saleDocument !=null) && (campaignDocument != null)) {
	  
    				Float Net_ProfitMargin = Calcul_Net_ProfitMargin(sale, campaign);
    				globalService.addFieldToDocument(collectiontest, objectId, "Net_ProfitMargin", Net_ProfitMargin);

    				Float Total_Revenu = Calcul_TotalRevenu(sale, campaign);
    				globalService.addFieldToDocument(collectiontest, objectId, "Total_Revenu", Total_Revenu);
    				
    				Float averagePurchaseValue = calcul_AveragePurchaseValue(Total_Revenu, totalPurchases);
    				globalService.addFieldToDocument(collectiontest, objectId, "averagePurchaseValue", averagePurchaseValue);

    				Float CLV = calcul_CLV(client, averagePurchaseValue, purchaseFrequency, Client_LifeSpan);
    				globalService.addFieldToDocument(collectiontest, objectId, "CLV", CLV);
    				
    				
    				if (shipmentDocument != null) {
    					int ActiveClients = calcul_ActiveClients(shipment, 365, 0);   				
    					Float MRR = Calcul_MRR(ActiveClients, client, averagePurchaseValue);
    					globalService.addFieldToDocument(collectiontest, objectId, "MRR", MRR);

    						}
  
    				} 	
    	}
  
    }
	
	
	// Update  Fact by id  
	@Override
	public void Update_FactTable (String id ) {
	       calculateAndAddKPIs(id);			
	} 
	
	
	// add fact to FactTable (keys)
	
	// !!!! see if i can add KPIs in the same function without adding then  retrieving the fact from mongoDb => done 
	@Override
	public void AddFact() {
		
		List<FactTable> facts = new ArrayList<>();
		Map<String, Object> fields = new HashMap<>();
		Map<String, Object> sales_fields = new HashMap<>();

		
				
		Document document_order = globalService.getLastDocument(collectionOrders);		
		Order order = new Order(new ObjectId(document_order.get("_id").toString()),globalService.getFieldOfDocument(document_order));
		
		Document document_sale = globalService.getLastDocument(collectionDIM_Sales);		
		Sales last_sale = new Sales(new ObjectId(document_sale.get("_id").toString()),globalService.getFieldOfDocument(document_sale));
		
        fields.put("order_key", order.getId());		
		fields.put("productSKU_key", new ObjectId(order.getFields().get("productSKU_key").toString()));		
		fields.put("shipment_key", new ObjectId(order.getFields().get("shipment_key").toString()));		
		fields.put("client_key", new ObjectId(order.getFields().get("client_key").toString()));		
		fields.put("supplier_key", new ObjectId(order.getFields().get("supplier_key").toString()));
		
		List<Sales> sales = saleService.retrieveAllSales();
		System.out.println("product order "+ order.getFields().get("productSKU_key"));
		Optional<Sales> matching = globalService.getObjectByFieldValue(sales, "productSKU_key", order.getFields().get("productSKU_key"));
		Sales sale = matching.orElse(null);
		
		System.out.println("sale ///////////  " + sale.getId() );
		double compairmax = 0F;
		double compairmin = 0;
		float numericpart = 0F ;
		double total_sales = 0;
	if (last_sale.getFields().get("Total") instanceof String)
	{
		
		String numericPart = ((String) last_sale.getFields().get("Total")).replaceAll("[^\\d.]", "");	
        total_sales = Float.parseFloat(numericPart);	
     //  total_sales +=  Float.parseFloat(numericPart);			    				     
       String updatedTotal =  ((String) sale.getFields().get("Total")).replace(numericPart, String.valueOf(total_sales));
		 compairmax = Float.parseFloat(numericPart);
		 compairmin = Float.parseFloat(numericPart);

	}
	else {
		 compairmax = (double)last_sale.getFields().get("Total");
		 compairmin = (double)last_sale.getFields().get("Total");
		System.out.println("compairmax , compairmin " + compairmax+compairmin);
		
		
		}
		
		if ( sale != null ) {
			
			// updating the sale found 

			System.out.println("updating the sale found ");

			List<ObjectId> keys = (List<ObjectId>) sale.getFields().get("orders_key"); 
			 total_sales = (double) sale.getFields().get("Total");	
			 ArrayList<Object> totalList = (ArrayList<Object>) order.getFields().get("Total");
		        Object[] totalArray = totalList.toArray();
		        total_sales += (int) totalArray[0];	

//			for(ObjectId key : keys) {
//				
//				 Document Orderdoc = globalService.getDocumentById(collectionOrders, key);
//				 Order item = new Order(new ObjectId(Orderdoc.get("_id").toString()),globalService.getFieldOfDocument(Orderdoc));				
//				ArrayList<Object> totalList = (ArrayList<Object>) item.getFields().get("Total");
//		        Object[] totalArray = totalList.toArray();	        
//		       
//		        total_sales += (int) totalArray[0];	
//				
//		      }
			
//			 float total = 0F;
//				if (last_sale.getFields().get("Total") instanceof Integer)
//				{  total = (int) last_sale.getFields().get("Total");	}
//				else { total = Float.parseFloat(((String) last_sale.getFields().get("Total")).replaceAll("[^\\d.]", "")); }
//				
//				
				if(total_sales>compairmax) {
					compairmax = total_sales;
		         }
				else if(total_sales<compairmin) {
					compairmin = total_sales;
		         }
		      
				
				if (!keys.contains(order.getId())) {
					keys.add(order.getId());
				}
		   sale.getFields().put("Total",total_sales);
		
		   saleService.updateSales(sale.getId() , sale.getFields());
		   fields.put("sale_key",sale.getId());

		} 
		
		else {
			
			// adding new sale 
			System.out.println("adding new sale");

			
			 List<ObjectId> ordersKeyList = new ArrayList<>();
			 ordersKeyList.add(order.getId());
             sales_fields.put("orders_key", ordersKeyList);	
             ArrayList<Object> totalList = (ArrayList<Object>) order.getFields().get("Total");

             // Convert the ArrayList to an array of Objects
             Object[] totalArray = totalList.toArray();
             System.out.println("total array : " + Arrays.toString(totalArray) + " first " + (int) totalArray[0]);
             

          // Convert array to ArrayList
          ArrayList<Object> totalArrayList = new ArrayList<>(Arrays.asList(totalArray));

             sales_fields.put("Total", Arrays.toString(totalArray));
             
             sales_fields.put( "productSKU_key" , order.getFields().get("productSKU_key"));
               total_sales = (int) totalArray[0];
             String sales_status = "";
             if ( total_sales <= compairmin) { sales_status = "Low Sales";
                         sales_fields.put( "status" , sales_status);}
             else if (total_sales >= compairmax) {sales_status = "High Sales" ;
             			sales_fields.put( "status" , sales_status); }
             else {sales_status = "Medium Sales";
             			sales_fields.put( "status" , sales_status);}

             System.out.println("sales fields : " + sales_fields);
             saleService.addSales(sales_fields);
             Document document_sales = globalService.getLastDocument(collectionDIM_Sales);     
    		 Sales saledb  = new Sales(new ObjectId(document_sales.get("_id").toString()),globalService.getFieldOfDocument(document_sales));

           	 fields.put("sale_key",saledb.getId());

		}
 
		List<Review> reviews = reviewService.retrieveAllReviews();
		List<Ad> Ads = adService.retrieveAllAds();

		
		try {
			System.out.println("heeere try ");
			
			ObjectId product_key = new ObjectId((String) order.getFields().get("productSKU_key"));
			
			List<Review> matching_reviews = globalService.getObjectsByFieldValue(reviews, "productSKU_key",product_key);
			
			System.out.println("heeere matching reviews  " + matching_reviews );

			List<Ad> matching_ads = globalService.getObjectsByFieldValue(Ads, "productSKU_key", product_key);
			System.out.println("heeere matching ads  " + matching_ads );

			if (matching_ads.isEmpty() && !matching_reviews.isEmpty()) {
				
					for (Review review : matching_reviews)
						
					{
						int j = 0;
			           	 fields.put("review_key",review.getId());
			         	 fields.put("Ad_key","NaN");
			    	   	 fields.put("campaign_key", "NaN");
			    		 fields.put("team_key","NaN");
			    		 fields.put("account_key", "NaN");
			    		 
			    		
			    		 globalService.addDocument(collectionFactTable, fields);
			    		 j++;
			    		 System.out.println("review num : "+j);
			    		 
			    		 Document document_fact = globalService.getLastDocument(collectionFactTable);		
			    		 FactTable fact = new FactTable(new ObjectId(document_fact.get("_id").toString()),globalService.getFieldOfDocument(document_fact));
			    		 
			    		 System.out.println("new fact : " + fact.getId());
			    		 facts.add(fact);
					
				}	    		 
			}

			else if (!matching_ads.isEmpty() && !matching_reviews.isEmpty()){
				
				for (Ad ad : matching_ads)
				{
					for (Review review : matching_reviews)
						
					{
						int j = 0;
			           	 fields.put("review_key",review.getId());
			         	 fields.put("Ad_key", ad.getId());
			    	   	 fields.put("campaign_key", ad.getFields().get("Campaign_Id"));
			    		 fields.put("team_key",ad.getFields().get("CreativeTeam_id"));
			    		 fields.put("account_key",ad.getFields().get("Account_id"));
			    		 
			    		
			    		 globalService.addDocument(collectionFactTable, fields);
			    		 j++;
			    		 System.out.println("review num : "+j);
			    		 
			    		 Document document_fact = globalService.getLastDocument(collectionFactTable);		
			    		 FactTable fact = new FactTable(new ObjectId(document_fact.get("_id").toString()),globalService.getFieldOfDocument(document_fact));
			    		 
			    		 System.out.println("new fact : " + fact.getId());
			    		 facts.add(fact);
					
				}	    		 
					
					}
			}
			else if (!matching_ads.isEmpty() && matching_reviews.isEmpty()) {
				
				for (Ad ad : matching_ads)
					
				{
					int j = 0;
		           	 fields.put("review_key","NaN");
		         	 fields.put("Ad_key", ad.getId());
		    	   	 fields.put("campaign_key", ad.getFields().get("Campaign_Id"));
		    		 fields.put("team_key",ad.getFields().get("CreativeTeam_id"));
		    		 fields.put("account_key",ad.getFields().get("Account_id"));
		    		 
		    		
		    		 globalService.addDocument(collectionFactTable, fields);
		    		 j++;
		    		 System.out.println("review num : "+j);
		    		 
		    		 Document document_fact = globalService.getLastDocument(collectionFactTable);		
		    		 FactTable fact = new FactTable(new ObjectId(document_fact.get("_id").toString()),globalService.getFieldOfDocument(document_fact));
		    		 
		    		 System.out.println("new fact : " + fact.getId());
		    		 facts.add(fact);
				
			}	    		 
				
				
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		
		for (FactTable item : facts )
		{	
			calculateAndAddKPIs(item.getId().toString());
			System.out.println("update done");
		}
				
	}
	
	
	// retrieve all fact table paginated 
	
	@Override
	public List<FactTable> retrieveFactTablePaginated(int page, int size) {
		 List<FactTable> factTableList = new ArrayList<>();

		    // Calculate the number of documents to skip based on the page and size
		    int skip = page * size;

		    // Use the skip and limit methods for pagination
		    // collection !!!! 
		    FindIterable<Document> documents = collectiontest.find().skip(skip).limit(size);

		    for (Document document : documents) {
		        ObjectId id = document.getObjectId("_id");
		        Map<String, Object> fields = new HashMap<>();
		        for (Entry<String, Object> entry : document.entrySet()) {
		            if (!entry.getKey().equals("_id")) {
		                Object value = entry.getValue();
		                if (value instanceof ObjectId) {
		                	fields.put(entry.getKey(), ((ObjectId) value).toHexString());
		                } else {
		                	fields.put(entry.getKey(), value);
		                }
		            }
		        }
		        FactTable factTable = new FactTable(id, fields);
		        factTableList.add(factTable);
		    }

		    return factTableList;
		    
		    }

	
	// update last fact in db 
	@Override
	public void UpdateLastFact() {
		
		Document document = globalService.getLastDocument(collectionFactTable);
		
		 ObjectId id = document.getObjectId("_id");

		       calculateAndAddKPIs(id.toString());			
	
	}

	
	//  Filter using  =>  getFactTablebyField()	
	//  Dynamic Filter function using sum / count / avg / min / max  and different stages 
//	@Override
//	public AggregateIterable<Document> Filter( String DateType ,String aggregationType, String field, ObjectId key, String aggregationStage , String matchValue , String LookupCollection ,String LookupField , String projectField1 , String projectField2 ) {
//	    BsonField accumulator;
//	    List<Bson> additionalStages = null;
//	    if (aggregationType != null)
//	    { 
//	    switch (aggregationType.toLowerCase()) {
//	        case "sum":
//	            accumulator = Accumulators.sum("result", "$" + field);
//	            break;
//	        case "count":
//	            accumulator = Accumulators.sum("result", 1);
//	            break;
//	        case "avg":
//	            accumulator = Accumulators.avg("result", "$" + field);
//	            break;
//	        case "min":
//	            accumulator = Accumulators.min("result", "$" + field);
//	            break;
//	        case "max":
//	            accumulator = Accumulators.max("result", "$" + field);
//	            break;
//	        case "add":
//	        case "subtract":
//	        case "multiply":
//	        case "divide":
//	            accumulator = handleArithmeticOperation(aggregationType, field);
//	            break;
//	        default:
//	            throw new IllegalArgumentException("Unsupported aggregation type: " + aggregationType);
//	    }
//	    }
//	    if (DateType!= null)
//	    {
//	    	switch (DateType.toLowerCase()) {
//	        case "year":
//	        case "month":
//	        case "dayofmonth":
//	        case "hour":
//	        case "minute":
//	        case "second":
//	            accumulator = handleDateOperator(DateType, field);
//	            break;
//	        default:
//	            throw new IllegalArgumentException("Unsupported Date Type : " + DateType);
//	    }
//	    	}
//
//	    if (aggregationStage != null)
//	    {
//		    Bson stage;
//
//	    switch (aggregationStage.toLowerCase()) {
//	        case "group":
//	            // Create the $group stage with the specified accumulator
//	            stage = Aggregates.group(key, Accumulators.addToSet("result", accumulator));
//	            additionalStages.add(stage);
//	            break;
//	        case "match":
//	            // Create the $match stage with the specified filter condition
//	            stage = Aggregates.match(Filters.eq(field, matchValue));
//	            additionalStages.add(stage);
//	            break;
//	        case "sort":
//	            // Example: Sort documents by a specific field in ascending order
//	            stage = Aggregates.sort(Sorts.ascending(field));
//	            additionalStages.add(stage);
//	            break;
//	        case "lookup":
//	            // Example: Perform a lookup with another collection
//	            stage = Aggregates.lookup(LookupCollection, LookupCollection, LookupField , "as");
//	            additionalStages.add(stage);
//	            break;
//	        case "project":
//	            // Example: Project only specific fields
//	            stage = Aggregates.project(Projections.fields(Projections.include(projectField1, projectField2), Projections.excludeId()));
//	            additionalStages.add(stage);
//	            break;
//	        default:
//	            throw new IllegalArgumentException("Unsupported aggregation stage: " + aggregationStage);
//	    }
//	    }
//
//	    // Combine the additional stages with the dynamically created stage
//	    List<Bson> pipeline = new ArrayList<>(Arrays.asList());
//	    pipeline.addAll(additionalStages);
//
//	    // Perform the aggregation
//	    AggregateIterable<Document> results  = collectionFactTable.aggregate(pipeline);	        
//        return results;
//	}
//	
	

	


	
	// Filter function 
	@Override
	public AggregateIterable<Document> Filter(
	        String dateType, String aggregationType, String field, String key,
	        String aggregationStage, String matchValue, String lookupCollection,
	        String lookupField, String projectField1, String projectField2 , boolean ascending) {
		
		
	    BsonField accumulator = null;
	    List<Bson> additionalStages = new ArrayList<>();
	    
	    
	    //	BsonValue getFieldType(String field, MongoCollection<Document> collection, int numFields);	    
		BsonValue firstDistinctValue = globalService.getFieldType(field,collectiontest, 42);
		
		//globalService.createDynamicIndex(collectiontest);
		System.out.println("done index");
		Class<?> javaFieldType;
		
		    	switch (firstDistinctValue.getBsonType()) {
		    	    case STRING:
		    	        javaFieldType = String.class;
		    	        break;
		    	    case DOUBLE:
		    	        javaFieldType = Double.class;
		    	        break;
		    	    case INT32:
		    	        javaFieldType = Integer.class;
		    	        break;
		    	    default:
		    	        javaFieldType = Object.class; 
		    	}
	    

	    if (aggregationType != null) {
	        switch (aggregationType.toLowerCase()) {
	            case "sum":
	                accumulator = Accumulators.sum("result", "$" + field);
	                break;
	            case "count":
	                accumulator = Accumulators.sum("result", 1);
	                break;
	            case "avg":
	                accumulator = Accumulators.avg("result", "$" + field);
	                break;
	            case "min":
	                accumulator = Accumulators.min("result", "$" + field);
	                break;
	            case "max":
	                accumulator = Accumulators.max("result", "$" + field);
	                break;
	            case "add":
	            case "subtract":
	            case "multiply":
	            case "divide":
	                accumulator = handleArithmeticOperation(aggregationType, field);
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported aggregation type: " + aggregationType);
	        }
	    }

	    if (dateType != null) {
	        switch (dateType.toLowerCase()) {
	            case "year":
	            case "month":
	            case "dayofmonth":
	            case "hour":
	            case "minute":
	            case "second":
	                accumulator = handleDateOperator(dateType, field);
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported Date Type: " + dateType);
	        }
	    }

	    if (aggregationStage != null) {
	        Bson stage;

	        switch (aggregationStage.toLowerCase()) {
	            case "group":
	         
	                // Create the $group stage with the specified accumulator based on aggregationType
	                switch (aggregationType.toLowerCase()) {
	                    case "sum":
	                      
	    	                stage = Aggregates.group(
	    	                		"$" + key,  // Group by the "productSKU_key" field
	    	                	    Accumulators.sum("result", "$" + field)
	    	                	);
	    	                additionalStages.add(stage);
	    	                System.out.println("additionalStages group : " + additionalStages);
  	                
	                        break;
	                    case "count":
	                    	
	                    	   stage = Aggregates.group(
		    	                		"$" + key,  // Group by the "productSKU_key" field
		    	                		Accumulators.sum("result", 1)
		    	                	);
		    	                additionalStages.add(stage);
		    	                System.out.println("additionalStages group : " + additionalStages);

	                        break;
	                    case "avg":
	                    	 stage = Aggregates.group(
		    	                		"$" + key,  // Group by the "productSKU_key" field
		    	                		Accumulators.avg("result", "$" + field)
		    	                	);
		    	                additionalStages.add(stage);
		    	                System.out.println("additionalStages group : " + additionalStages);
	                        break;
	                    case "min":
	                    	 stage = Aggregates.group(
		    	                		"$" + key,  // Group by the "productSKU_key" field
		    	                		Accumulators.min("result", "$" + field)
		    	                	);
		    	                additionalStages.add(stage);
		    	                System.out.println("additionalStages group : " + additionalStages);
	                        break;
	                    case "max":
	                    	 stage = Aggregates.group(
		    	                		"$" + key,  // Group by the "productSKU_key" field
		    	                		Accumulators.max("result", "$" + field)
		    	                	);
		    	                additionalStages.add(stage);
		    	                System.out.println("additionalStages group : " + additionalStages);
	                        break;
	                    case "add":
	                    case "subtract":
	                    case "multiply":
	                    case "divide":
	                    	 stage = Aggregates.group(
		    	                		"$" + key,  // Group by the "productSKU_key" field
		    	                		handleArithmeticOperation(aggregationType, field)
		    	                	);
		    	                additionalStages.add(stage);
		    	                System.out.println("additionalStages group : " + additionalStages);
	                        break;
	                    default:
	                        throw new IllegalArgumentException("Unsupported aggregation type: " + aggregationType);
	                }
	                

	             
	                break;

	            case "match":
	   
	            	System.out.println("firstDistinctValue.getBsonType() :: "+ firstDistinctValue.getBsonType());

	            	Object convertedMatchValue;
	            	try {
	            	    if (firstDistinctValue.getBsonType() == BsonType.STRING) {
	            	        convertedMatchValue = matchValue;
	    	            	System.out.println("string convertedMatchValue :: "+convertedMatchValue);

	            	    } else if (firstDistinctValue.getBsonType()== BsonType.INT32) {
	            	        convertedMatchValue = Integer.parseInt(matchValue);
	    	            	System.out.println("int convertedMatchValue :: "+convertedMatchValue);

	            	    } else if (firstDistinctValue.getBsonType()== BsonType.DOUBLE) {
	            	        convertedMatchValue = Double.parseDouble(matchValue);
	    	            	System.out.println(" Double convertedMatchValue :: "+convertedMatchValue);

	            	    } else {            
	            	        convertedMatchValue = matchValue;
	    	            	System.out.println("oops convertedMatchValue :: "+convertedMatchValue);
	            	    }
	            	} catch (NumberFormatException e) {
	            		
	            	    convertedMatchValue = null;
    	            	System.out.println("null  exception convertedMatchValue :: "+convertedMatchValue);

	            	}

	            
	            
	            	System.out.println("field to match :: "+field);
	            	Bson matchStage = Aggregates.match(Filters.exists(field));	            	
	            	System.out.println("match stage"+ matchStage);	            	
	            	additionalStages.add(matchStage);

	            	Bson matchStage2 = Aggregates.match(Filters.eq(field, convertedMatchValue));
	            	additionalStages.add(matchStage2);


	                break;
	            case "sort":
	                Bson matchStage_sort = Aggregates.match(Filters.exists(field));
	                additionalStages.add(matchStage_sort);

	                Bson sortStage;
	                if (ascending) {
	                    sortStage = Aggregates.sort(Sorts.ascending(field));
	                } else {
	                    sortStage = Aggregates.sort(Sorts.descending(field));
	                }
	                additionalStages.add(sortStage);

	                break;

	            case "lookup":
	                stage = Aggregates.lookup(lookupCollection, lookupCollection, lookupField, "as");
	                additionalStages.add(stage);
	                break;
	            case "project":
	                stage = Aggregates.project(Projections.fields(
	                        Projections.include(projectField1, projectField2),
	                        Projections.excludeId()));
	                additionalStages.add(stage);
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported aggregation stage: " + aggregationStage);
	        }
	    }


	    System.out.println("additionalStages  ::  "+ additionalStages);
	    List<Bson> pipeline = new ArrayList<>(additionalStages);
	    
	    System.out.println("pipeline  ::  " + pipeline);


	    // Perform the aggregation
	    AggregateIterable<Document> results = collectiontest.aggregate(pipeline).allowDiskUse(true);
		// AggregateIterable<Document> results = collectionFactTable.aggregate(pipeline).allowDiskUse(true);
	    
	    
	    // Create the explain command
       /* Document explainCommand = new Document("explain", new Document("aggregate", collectiontest)
                .append("pipeline", pipeline)
                .append("allowDiskUse", true));

        // Execute the explain command using runCommand
        Document explainResult = database.runCommand(explainCommand);
        System.out.println("Aggregation Explain Result:\n" + explainResult.toJson());*/
	    
	    System.out.println("results  ::  "+ results);
	    
        logQueryExecutionStats(collectiontest, Filters.exists(field));


	    return results;
	}

	
	 private void logQueryExecutionStats(MongoCollection<Document> collection, Bson filter) {
	        FindIterable<Document> findQuery = collection.find(filter)
	                .projection(Projections.excludeId()); 
	               // .sort(ascending ? Sorts.ascending(field) : Sorts.descending(field));

	        // Log the execution statistics
	        Document executionStats =  findQuery.explain();
	        System.out.println("Query Execution Stats:\n" + executionStats.toJson());
	    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ***********************************************************************************************
	@Override
	public List<String> filterAndPrint(
	        String dateType, String aggregationType, String field, ObjectId key,
	        String aggregationStage, String matchValue, String lookupCollection,
	        String lookupField, String projectField1, String projectField2) {
		
		MongoCollection<Document> collectiontest = database.getCollection("test");


	    AggregateIterable<Document> results = collectiontest.aggregate(buildPipeline(
	            dateType, aggregationType, field, key, aggregationStage,
	            matchValue, lookupCollection, lookupField, projectField1, projectField2));
        System.out.println("results :: "+results);
	    System.out.println("explain !!! "+ results.explain());

	    List<String> resultList = new ArrayList<>();
	  
	    for (Document document : results) {
	    	
	        System.out.println("document : " + document.toJson());

	        resultList.add(document.toJson());
	    }
	    return resultList;
	}

	private List<Bson> buildPipeline(
	        String dateType, String aggregationType, String field, ObjectId key,
	        String aggregationStage, String matchValue, String lookupCollection,
	        String lookupField, String projectField1, String projectField2) {

	    BsonField accumulator = null;
	    List<Bson> additionalStages = new ArrayList<>();

	    if (aggregationType != null) {
	        switch (aggregationType.toLowerCase()) {
	            case "sum":
	                accumulator = Accumulators.sum("result", "$" + field);
	                break;
	            case "count":
	                accumulator = Accumulators.sum("result", 1);
	                break;
	            case "avg":
	                accumulator = Accumulators.avg("result", "$" + field);
	                break;
	            case "min":
	                accumulator = Accumulators.min("result", "$" + field);
	                break;
	            case "max":
	                accumulator = Accumulators.max("result", "$" + field);
	                break;
	            case "add":
	            case "subtract":
	            case "multiply":
	            case "divide":
	                accumulator = handleArithmeticOperation(aggregationType, field);
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported aggregation type: " + aggregationType);
	        }
	    }

	    if (dateType != null) {
	        switch (dateType.toLowerCase()) {
	            case "year":
	            case "month":
	            case "dayofmonth":
	            case "hour":
	            case "minute":
	            case "second":
	                accumulator = handleDateOperator(dateType, field);
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported Date Type: " + dateType);
	        }
	    }

	    if (aggregationStage != null) {
	        Bson stage;

	        switch (aggregationStage.toLowerCase()) {
	            case "group":
	                // Create the $group stage with the specified accumulator
	                stage = Aggregates.group(key, Accumulators.addToSet("result", accumulator));
	                additionalStages.add(stage);
	                break;
	            case "match":
	                // Create the $match stage with the specified filter condition
	                stage = Aggregates.match(Filters.eq(field, matchValue));
	                additionalStages.add(stage);
	                break;
	            case "sort":
	                // Example: Sort documents by a specific field in ascending order
	                stage = Aggregates.sort(Sorts.ascending(field));
	                additionalStages.add(stage);
	                break;
	            case "lookup":
	                // Example: Perform a lookup with another collection
	                stage = Aggregates.lookup(lookupCollection, lookupCollection, lookupField, "as");
	                additionalStages.add(stage);
	                break;
	            case "project":
	                // Example: Project only specific fields
	                stage = Aggregates.project(Projections.fields(
	                        Projections.include(projectField1, projectField2),
	                        Projections.excludeId()));
	                additionalStages.add(stage);
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported aggregation stage: " + aggregationStage);
	        }
	    }
	    return additionalStages;
	}		
	// *****************************************************************************************************************


	
	
	
	
	// handleArithmeticOperation
	
	@Override
	public BsonField handleArithmeticOperation(String operation, String field) {
	    switch (operation.toLowerCase()) {
	        case "add":
	            return Accumulators.addToSet("result", new Document("$add", Arrays.asList("$" + field)));
	        case "subtract":
	            return Accumulators.addToSet("result", new Document("$subtract", Arrays.asList("$" + field)));
	        case "multiply":
	            return Accumulators.addToSet("result", new Document("$multiply", Arrays.asList("$" + field)));
	        case "divide":
	            return Accumulators.addToSet("result", new Document("$divide", Arrays.asList("$" + field)));
	        case "mod":
	            return Accumulators.addToSet("result", new Document("$mod", Arrays.asList("$" + field)));
	        case "sqrt":
	            return Accumulators.addToSet("result", new Document("$sqrt", Arrays.asList("$" + field)));
	        case "pow":
	            return Accumulators.addToSet("result", new Document("$pow", Arrays.asList("$" + field)));

	        default:
	            throw new IllegalArgumentException("Unsupported arithmetic operation: " + operation);
	    }
	}

	
	
	// handleDateOperator
	
	@Override
	public BsonField handleDateOperator(String DateType, String field) {
	    switch (DateType.toLowerCase()) {
	        case "year":
	            return Accumulators.addToSet("result", new Document("$year", new Document("$dateFromString", new Document("dateString", "$" + field))));
	        case "month":
	            return Accumulators.addToSet("result", new Document("$month", new Document("$dateFromString", new Document("dateString", "$" + field))));
	        case "dayofmonth":
	            return Accumulators.addToSet("result", new Document("$dayOfMonth", new Document("$dateFromString", new Document("dateString", "$" + field))));
	        case "hour":
	            return Accumulators.addToSet("result", new Document("$hour", new Document("$dateFromString", new Document("dateString", "$" + field))));
	        case "minute":
	            return Accumulators.addToSet("result", new Document("$minute", new Document("$dateFromString", new Document("dateString", "$" + field))));
	        case "second":
	            return Accumulators.addToSet("result", new Document("$second", new Document("$dateFromString", new Document("dateString", "$" + field))));
	        default:
	            throw new IllegalArgumentException("Unsupported date operator: " + DateType);
	    }
	}

	
	
	
	
	
	
        // execution example 
        
        
//        // Example 1: Group by key and calculate the sum of 'value'
//        AggregateIterable<Document> result1 = yourInstance.filter("sum", field, key, "group", null, null, null, null, null);
//        printResults(result1);
//
//        // Example 2: Match documents where 'value' equals 'someValue'
//        AggregateIterable<Document> result2 = yourInstance.filter("count", field, key, "match", "someValue", null, null, null, null);
//        printResults(result2);
//
//        // Example 3: Sort documents by 'value' in ascending order
//        AggregateIterable<Document> result3 = yourInstance.filter("avg", field, key, "sort", null, null, null, null, null);
//        printResults(result3);
//
//        // Example 4: Lookup documents in 'anotherCollection' using 'value' field
//        AggregateIterable<Document> result4 = yourInstance.filter("min", field, key, "lookup", null, "anotherCollection", "foreignField", null, null);
//        printResults(result4);
//
//        // Example 5: Project only 'field1' and 'field2'
//        AggregateIterable<Document> result5 = yourInstance.filter("max", field, key, "project", null, null, null, "field1", "field2");
//        printResults(result5);
//    }
//
//    private static void printResults(AggregateIterable<Document> results) {
//        for (Document result : results) {
//            System.out.println(result.toJson());
//        }
//    }
	
        
	       


	
	// Filter Functions 
	
	
	// calcul Rate 
//	@Override
//	public double calculateRate(String field, ObjectId key) {
//
//	    // Calculate the total value of the field for each product
//	    AggregateIterable<Document> group = Filter("sum", field, key);
//
//	    // Iterate over the results of the first aggregation
//	    for (Document objectgroup : group) {
//	        ObjectId objectId = objectgroup.getObjectId("_id");
//	        double totalSales = objectgroup.getDouble("result");
//
//	        // Count the number of documents for the current product
//	        long documentCount = collectionFactTable.countDocuments(Filters.eq( key.toHexString() , objectId));
//
//	        // Calculate the rate or percentage
//	        double rate = (documentCount > 0) ? totalSales / documentCount : 0;
//
//	        System.out.println("The rate or percentage of " + field + "for" + objectId + " is " + rate);
//	    }
//		return 0 ; // !!!!!!!!!!!!!!!    to solve 
//	}
	
	
	
	
	
	
	
	
	   
	   // calcul rate 
	 @Override
	    public Map<Double, Document> calculateRate(String field, ObjectId key) {
	        Map<Double, Document> allRates = new HashMap<>();
	        
 //  Filter(DateType, aggregationType, field, key, aggregationStage, matchValue, LookupCollection, LookupField, projectField1, projectField2, ascending)

	        // Calculate the total value of the field for each product
	        AggregateIterable<Document> group = Filter(null,"sum", field, key.toString(), "group", null, null, null, null, null,true); ///  to dooooo 

	        // Iterate over the results of the first aggregation
	        for (Document objectgroup : group) {
	            ObjectId objectId = objectgroup.getObjectId("_id");
	            double totalSales = objectgroup.getDouble("result");

	            // Count the number of documents for the current product
	            long documentCount = collectionFactTable.countDocuments(Filters.eq(key.toHexString(), objectId));
	            // Calculate the rate 
	            double rate = (documentCount > 0) ? totalSales / documentCount : 0;
	            allRates.put(rate, objectgroup);
	            System.out.println("The rate or percentage of " + field + " for " + objectId + " is " + rate);
	        }
	        return allRates;
	    }

	 
	 // calcul average rate 
	 
	 @Override
	    public double calculateAverageRate(Map<Double, Document> rateDocumentMap) {
	    	Set<Double> rates=rateDocumentMap.keySet();
	        double totalRate = 0;

	        for (Double rate : rates) {
	            totalRate += rate;
	        }
	        // Calculate the average rate
	        return (rates.size() > 0) ? totalRate / rates.size() : 0;
	    }




}
