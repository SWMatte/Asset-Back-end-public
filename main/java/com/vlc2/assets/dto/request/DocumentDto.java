package com.vlc2.assets.dto.request;

import com.vlc2.assets.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {
    private int idEmployee;
    private String company;
    private int idAsset;
    private int idHistory;
    private Type type;
    private String effectiveAssignmentDate;
    private String model;
    private String brand;
    private String simNumber;
}
