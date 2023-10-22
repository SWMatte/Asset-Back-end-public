package com.vlc2.assets.service;

import com.vlc2.assets.entity.Employee;
import com.vlc2.assets.entity.History;
import com.vlc2.assets.entity.AssetStatus;

import java.util.List;

public interface HistoryService {

    List<History> findHistoryByIdAsset(int idAsset);

    List<History> findHistoryByIdEmployee(int idEmployee);
	
	History findHistoryById(int id);

    List<History> findAllSignedDocuments();

    History save(History history);

    History findLastAssignedHistory(int idEmployee, int idAsset);

}
