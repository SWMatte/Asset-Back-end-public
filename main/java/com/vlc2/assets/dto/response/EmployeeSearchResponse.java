package com.vlc2.assets.dto.response;

import com.vlc2.assets.entity.EmployeeRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSearchResponse {
    private int idEmployee;
    private String firstName;
    private String lastName;
    private EmployeeRole role;
    private String company;
}
