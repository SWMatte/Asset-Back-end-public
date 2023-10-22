package com.vlc2.assets.repository;
import com.vlc2.assets.entity.AssetStatus;
import com.vlc2.assets.entity.Asset;
import com.vlc2.assets.entity.History;
import com.vlc2.assets.entity.Type;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {

    @Query(value = """
            SELECT *
            FROM history
            WHERE asset = :idAsset
            ORDER BY assignment_date desc
            """, nativeQuery = true)
    List<History> findHistoryByIdAsset(int idAsset);

    @Query(value = """
            SELECT *
            FROM history
            WHERE employee = :idEmployee
            ORDER BY assignment_date desc
            """, nativeQuery = true)
    List<History> findHistoryByIdEmployee(int idEmployee);

    @Query(value = """
            SELECT *
            FROM history
            WHERE employee = :idEmployee AND asset = :idAsset AND asset_status = 'ASSEGNATO' AND uploaded_signed_document = true
            ORDER BY assignment_date desc
            LIMIT 1
            """, nativeQuery = true)
    History findLastAssignedHistory(int idEmployee, int idAsset);


 @Query(value = """
            SELECT *
            FROM History AS h
            WHERE h.uploadedSignedDocument = true
            """, nativeQuery = true)
    List<History> findAllSignedDocuments();
}
