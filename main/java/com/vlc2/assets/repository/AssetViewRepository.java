package com.vlc2.assets.repository;

import com.vlc2.assets.entity.AssetStatus;
import com.vlc2.assets.entity.AssetView;
import com.vlc2.assets.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssetViewRepository extends JpaRepository<AssetView, Integer> {

    @Query("""
            SELECT v
            FROM AssetView as v
            WHERE
             v.type LIKE :type
             AND
            ( v.employeeFirstName LIKE %:search%
             OR v.employeeLastName LIKE %:search%
             OR v.brand LIKE %:search%
             OR v.serialNumber LIKE %:search%
             OR v.simNumber LIKE %:search%
             OR v.os LIKE %:search% )
            """)
    List<AssetView> listAssetView(Type type, String search);


    @Query("""
    SELECT v
    FROM AssetView as v
    WHERE
     v.type LIKE :type
    AND
      v.historyAssetStatus IN :status
      AND
    (
      v.employeeFirstName LIKE %:search%
     OR v.employeeLastName LIKE %:search%
     OR v.brand LIKE %:search%
     OR v.serialNumber LIKE %:search%
     OR v.simNumber LIKE %:search%
     OR v.os LIKE %:search%
    )
""")
    List<AssetView> listAssetViewWithStatus(Type type, List<AssetStatus> status, String search);

}



