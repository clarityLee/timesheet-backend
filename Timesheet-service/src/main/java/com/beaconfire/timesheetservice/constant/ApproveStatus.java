package com.beaconfire.timesheetservice.constant;

public enum ApproveStatus {
    Not_Available("N/A"),
    Not_Approved("Not Approved"),
    Approved("Approved");

    private String type;
    ApproveStatus(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return type;
    }
}
