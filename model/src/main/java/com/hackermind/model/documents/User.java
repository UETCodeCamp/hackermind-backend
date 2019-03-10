package com.hackermind.model.documents;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tab_user")
public class User extends BaseDocument {


}
