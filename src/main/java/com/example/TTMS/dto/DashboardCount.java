package com.example.TTMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardCount {

    private long cityCount;
    private long locationCount;
    private long userCount;
    private long vendorCount;
    private long transportCount;
    private long rideTicketCount;

}
