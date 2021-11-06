//package com.beaconfire.timesheetservice.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.bson.types.ObjectId;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.List;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@ToString
//@Document(collection = "users")
//public class User {
//
//    @Id
//    private ObjectId id;
//    private String username;
//    @JsonIgnore
//    private String password;
//    private String phone;
//    private String email;
//    private String address;
//    private List<TimeSheet> timeSheets;
//    private List<Contact> contacts;
//}