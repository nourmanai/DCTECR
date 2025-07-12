package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.News;

public interface INewsService {
	List<News> retrieveAllNews();

	List<News> getNewsbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addNews(Map<String, Object> fields);

	void updateNews(ObjectId id, Map<String, Object> fields);

	void DeleteNews(ObjectId id);

	void AddFieldToNews(ObjectId id, String fieldName, String fieldValue);
}
