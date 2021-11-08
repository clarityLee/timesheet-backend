package com.beaconfire.userservice.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UpdateTimesheetRequest {
    private String username;
    private Integer floatingChange;
    private Integer vacationChange;
    private List<TimeSheet> timeSheetList;
}
