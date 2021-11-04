package com.beaconfire.userservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "timesheets")
public class TimeSheet {
    @Id
    private ObjectId id;
    private ObjectId user_id;
    private LocalDate weekEnding;
    private Integer billingHouse;
    private Integer compensatedHours;
    private String submissionStatus;
    private String approvalStatus;
    private String comment;
    private String submissionInfo;
    private String commentInfo;
    private List<DayDetail> dayDetails;
}
