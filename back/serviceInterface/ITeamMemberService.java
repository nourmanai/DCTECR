package tn.esprit.spring.serviceInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import tn.esprit.spring.entities.TeamMember;

public interface ITeamMemberService {
	List<TeamMember> retrieveAllTeamMembers();

	List<TeamMember> getTeamMemberbyField(String fieldName, String value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	void addTeamMember(Map<String, Object> fields);

	void updateTeamMember(ObjectId id, Map<String, Object> fields);

	void DeleteTeamMember(ObjectId id);

	void AddFieldToTeamMember(ObjectId id, String fieldName, String fieldValue);
}
