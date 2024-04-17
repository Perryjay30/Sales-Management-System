package com.challengetwo.salesmanagementsystem.reporting.dto.response;

import com.challengetwo.salesmanagementsystem.reporting.dto.request.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ClientReportResponse {
    private int totalClients;
    private List<UserDTO> topSpendingClients;
    private Map<String, Integer> clientActivity; // Map<Month-Year, Total Sales>
    private Map<String, Integer> clientLocationStatistics; // Map<Location, Total Clients>
}
