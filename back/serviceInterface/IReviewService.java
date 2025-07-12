package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Review;

public interface IReviewService {
	List<Review> retrieveAllReviews();

	List<Review> getReviewbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addReview(Map<String, Object> fields);

	void updateReview(ObjectId id, Map<String, Object> fields);

	void DeleteReview(ObjectId id);

	void AddFieldToReview(ObjectId id, String fieldName, String fieldValue);
}
