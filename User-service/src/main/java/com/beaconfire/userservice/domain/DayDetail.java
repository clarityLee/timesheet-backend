package com.beaconfire.userservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DayDetail {
    private String start;
    private String end;
    private Integer totalHours;
    private Boolean floating;
    private Boolean holiday;
    private Boolean vacation;
}
