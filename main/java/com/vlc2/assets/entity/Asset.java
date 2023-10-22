package com.vlc2.assets.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset")
    private int idAsset;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "brand")
    private String brand;
    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "additional_software")
    private String additionalSoftware;

    @Column(name = "additional_hardware")
    private String additionalHardware;

    @Column(name = "hw_features")
    private String hwFeatures;
    @Column(name = "has_antivirus")
    private boolean hasAntivirus;

    @Column(name = "antivirus_expiration_date")
    private LocalDate antivirusExpirationDate;

    @Column(name = "sim_number")
    private String simNumber;

    private String processor;

    private String ram;

    private String storage;

    private String os;

    private String note;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

}
