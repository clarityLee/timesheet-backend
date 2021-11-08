package com.beaconfire.timesheetservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "timesheets")
public class TimeSheet {
    @Id
    private ObjectId id;
    private String username;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate weekEnding;
    private Integer billingHours;
    private Integer compensatedHours;

    private String submissionStatus;
    private String submissionInfo;
    private String approvalStatus;

    private String comment;
    private String commentInfo;
    private String uploadType;

    private Integer floatingRequired;
    private Integer vacationRequired;

    @JsonIgnore
    private Binary uploadFile;
    private List<DayDetail> dayDetails;
}
