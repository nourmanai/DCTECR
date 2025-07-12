package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.BsonField;

import tn.esprit.spring.entities.Ad;
import tn.esprit.spring.entities.Campaign;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.FactTable;
import tn.esprit.spring.entities.Order;
import tn.esprit.spring.entities.ProductSKU;
import tn.esprit.spring.entities.Review;
import tn.esprit.spring.entities.Sales;
import tn.esprit.spring.entities.Shipment;

public interface IFactTableService {
	
	// retrieve all 
	List<FactTable> retrieveAllFactTable();

	
	// retrieve by field 
	List<FactTable> getFactTablebyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
	
	
	// get object by key 
	Document GetObjectFromFactTable(FactTable item, MongoCollection<Document> collection, String fieldName ) ;
	
	// Calcul Functions 

	double  Calcul_Spend(Order order, Campaign campaign);

	float Calcul_nbrlikes(Ad ad, Review review);


	int Calcul_nbrReviews( Ad ad , Review review, int reviews );

	int Calcul_nbrViews(Ad ad);

	int Calcul_nbrPurchasesbyOrder(Order order);
	int Calcul_nbrPurchasesbyClient(Client client);

	int Calcul_FulfilledOrder(Order order, int fulfilledOrders);

	int Calcul_ReturnedOrder(Order order, int ReturnedOrders);

	public int Calcul_SatisfiedClients (Client client, int satisfiedClients);
	 public int Calcul_SatisfiedResellers (Client client, int satisfiedResellers);
	 float Calcul_productionCapacity( Order order , ProductSKU productSKU);
	 
	 int Calcul_Availability(Shipment shipment, int late_days);
	 
	 int Calcul_nbrNewResellers (Client client ,int nbr_newReselers);
	 float Calcul_Net_ProfitMargin(Sales sales , Campaign campaign );

	 float Calcul_Gross_ProfitMargin(Sales sales, double Total_Spend, Campaign campaign);

	float Calcul_TotalProfit_Sales(Sales sales, double Total_Spend);

	float Calcul_TotalProfit_Campaign(Campaign campaign, double Total_Spend);

	int calcul_RepeatPurchaseRate(Client customer, int repeatCustomers);

	float calcul_PurchaseFrequency(Client client, float totalPurchases);

	float Calcul_TotalRevenu(Sales sales, Campaign campaign);

	float Calcul_TotalPurchases(Client client, int totalPurchases);

	float calcul_AveragePurchaseValue(float totalRevenue, float totalPurchases);

	float calcul_PurchaseCycle(float purchaseFrequency);

	float calcul_CustomerLifespan(float purchaseCycle);

	float calcul_CLV(Client client, float averagePurchaseValue, float purchaseFrequency, float customerLifespan);

	int Calcul_UnsatisfiedClients(Client client, int unsatisfiedClients);

	int Calcul_UnsatisfiedResellers(Client client, int unsatisfiedResellers);

	boolean isQualifiedLead(Client lead);

	int calcul_QualifiedLeads(Client client, int qualifiedLeads);

	int calcul_ActiveClients(Shipment shipment, int days, int activeClients);

	float Calcul_MRR(int activeClients, Client client, float averagePurchaseValue);

	float calcul_ROI_Sales(float TotalProfit_Sales, double Total_Spend);

	float calcul_ROI_Campaigns(float TotalProfit_Campaign, double Total_Spend);
	
	//  Calcul and add field 

	void calculateAndAddKPIs( String id);

	
	// update Fact table 

	public void Update_FactTable (String id);


	void AddFact() ;


	AggregateIterable<Document> Filter(String DateType ,String aggregationType, String field, String key, String aggregationStage , String matchValue , String LookupCollection ,String LookupField , String projectField1 , String projectField2,boolean ascending );

	Map<Double, Document> calculateRate(String field, ObjectId key);


	Document getLastFact();


	double calculateAverageRate(Map<Double, Document> rateDocumentMap);


	FactTable getFactById(ObjectId id);


	List<FactTable> retrieveFactTablePaginated(int page, int size);


	void UpdateLastFact();


	BsonField handleDateOperator(String DateType, String field);


	BsonField handleArithmeticOperation(String operation, String field);


	List<String> filterAndPrint(String dateType, String aggregationType, String field, ObjectId key,
			String aggregationStage, String matchValue, String lookupCollection, String lookupField,
			String projectField1, String projectField2);


}
