package com.vlc2.assets.service;
import com.vlc2.assets.entity.AssetStatus;
import com.vlc2.assets.entity.Employee;
import com.vlc2.assets.entity.History;
import com.vlc2.assets.repository.HistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService{

    private HistoryRepository historyRepository;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository){
        this.historyRepository = historyRepository;
    }

    @Override
    public History findHistoryById(int id){
        log.info("Start findHistoryById in HistoryServiceImpl... with id: " + id);
        try{
            Optional<History> found = historyRepository.findById(id);
            log.info("End findHistoryById in HistoryServiceImpl");
            return found.orElseThrow();
        }catch(Exception e){
            log.error("Error find by id in HistoryServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error finding the history", e);
        }
    }

    @Override
    public History save(History history) {
        log.info("Start save in HistoryServiceImpl... with history: " + history);
        try{
            History saved = historyRepository.save(history);
            log.info("End save in HistoryServiceImpl");
            return saved;
        }catch(Exception e){
            log.error("Error save id in HistoryServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error saving the history", e);
        }
    }

    @Override
    public History findLastAssignedHistory(int idEmployee, int idAsset) {
        log.info("Start findLastAssignedHistoryToUpdate in HistoryServiceImpl... with idAsset: " + idAsset);
        try{
            History found = historyRepository.findLastAssignedHistory(idEmployee, idAsset);
            log.info("End findLastAssignedHistoryToUpdate in HistoryServiceImpl");
            return found;
        }catch(Exception e){
            log.error("Error findLastAssignedHistoryToUpdate in HistoryServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error finding the history", e);
        }
    }

    @Override
    public List<History> findHistoryByIdAsset(int idAsset) {
        log.info("Start findHistoryByIdAsset in HistoryServiceImpl... with idAsset: " + idAsset);
        try{
            List<History> found = historyRepository.findHistoryByIdAsset(idAsset);
            log.info("End findHistoryByIdAsset in HistoryServiceImpl");
            return found;
        }catch(Exception e){
            log.error("Error save in HistoryServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error finding the history", e);
        }
    }

    @Override
    public List<History> findHistoryByIdEmployee(int idEmployee) {
        log.info("Start findHistoryByIdEmployee in HistoryServiceImpl... with idEmployee: " + idEmployee);
        try{
            List<History> found = historyRepository.findHistoryByIdEmployee(idEmployee);
            log.info("End findHistoryByIdEmployee in HistoryServiceImpl");
            return found;
        }catch(Exception e){
            log.error("Error findHistoryByIdEmployee in HistoryServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error finding the history", e);
        }
    }

    @Override
    public List<History> findAllSignedDocuments() {
        List<History> allSignedDocument;
        try{
            log.info("Getting all signed documents name");
            allSignedDocument = historyRepository.findAllSignedDocuments();
        } catch (Exception e){
            log.error("Something went wrong..." + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return allSignedDocument;
    }


}
