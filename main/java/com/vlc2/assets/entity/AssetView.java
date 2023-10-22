package com.vlc2.assets.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "v_asset")
public class AssetView {

    @Id
    @Column(name = "asset_id")
    private int idAsset;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type")
    private Type type;

    @Column(name = "asset_brand")
    private String brand;

    @Column(name = "asset_serial_number")
    private String serialNumber;

    @Column(name = "asset_purchase_date")
    private LocalDate purchaseDate;


    @Column(name = "asset_has_antivirus")
    private boolean hasAntivirus;

    @Column(name = "asset_antivirus_expiration_date")
    private LocalDate antivirusExpirationDate;

    @Column(name = "asset_sim_number")
    private String simNumber;

    @Column(name = "asset_os")
    private String os;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "employee_first_name")
    private String employeeFirstName;

    @Column(name = "employee_last_name")
    private String employeeLastName;

    @Column(name = "employee_id_employee")
    private Integer employeeIdEmployee;

    @Column(name = "history_assignment_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate historyAssignmentDate;

    @Column(name = "history_id_history")
    private int history_id_history;

    @Enumerated(EnumType.STRING)
    @Column(name = "history_asset_status")
    private AssetStatus historyAssetStatus;


    @Column(name = "history_effective_assignment_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate historyEffectiveAssignmentDate;

    @Column(name = "history_signed_document")
    private String historySignedDocument;

    @Column(name = "history_uploaded_signed_document")
    private boolean historyUploadedSignedDocument;

}
