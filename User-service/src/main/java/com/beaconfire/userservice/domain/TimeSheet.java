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
    private String username;
    private LocalDate weekEnding;
    private Integer billingHours;
    private Integer compensatedHours;
    private String submissionStatus;
    private String submissionInfo;
    private String approvalStatus;
    private String comment;
    private String commentInfo;
    private List<DayDetail> dayDetails;
}
