package com.vlc2.assets.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_history")
    private int idHistory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee")
    private Employee employee;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asset")
    private Asset asset;


    @Enumerated(EnumType.STRING)
    @Column(name = "asset_status")
    private AssetStatus assetStatus;

    @Column(name = "assignment_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate assignmentDate;

    @Column(name = "effective_assignment_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate effectiveAssignmentDate;

    @Column(name = "signed_document")
    private String signedDocument;

    @Column(name = "uploaded_signed_document")
    private boolean uploadedSignedDocument;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "created_by")
    private User createdBy;

}
