package com.beaconfire.timesheetservice.constant;

public enum SubmissionStatus {
    Not_Started("Not Started"),
    Incomplete("Incomplete"),
    Complete("Complete");

    private String type;
    SubmissionStatus(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return type;
    }
}
