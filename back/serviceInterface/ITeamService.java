package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.Team;

public interface ITeamService {
	List<Team> retrieveAllTeams();

	List<Team> getTeambyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addTeam(Map<String, Object> fields);

	void updateTeam(ObjectId id, Map<String, Object> fields);

	void DeleteTeam(ObjectId id);

	void AddFieldToTeam(ObjectId id, String fieldName, String fieldValue);
}
