package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Indexed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import tn.esprit.spring.service.DocumentSerializer;



@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Document(collection = "DIM_Clients")
@CompoundIndexes({
    @CompoundIndex(name = "all_fields_index", def = "{'_id': 1}")
})
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

  
    
    @Id
    @JsonSerialize(using = DocumentSerializer.class)
    private ObjectId id;
    
    
    @Field("*")
    @JsonInclude
    private Map<String, Object> fields = new HashMap<>();
    
    public Map<String, Object> getFields() {
        return fields;
    }
    
    public ObjectId getId() {
        return id;
    }
    
    public Client() {
        // no-argument constructor
    }
    
    public Client(ObjectId id, Map<String, Object> fields) {
        this.id = id;
        this.fields = fields;
    }

    
   
    
    
	}
