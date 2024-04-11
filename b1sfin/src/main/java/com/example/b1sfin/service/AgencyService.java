package com.example.b1sfin.service;

import com.example.b1sfin.model.Agency;
import com.example.b1sfin.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {
    @Autowired
    private AgencyRepository agencyRepository;

    public List<Agency> findAll(){
        return agencyRepository.findAll();
    }

    public Agency findById(String agencyId){
       try{
           return agencyRepository.findById(agencyId).get();
       }catch (Exception e){
           return null;
       }
    }

    public boolean save(Agency agency){
        try{
            if(agencyRepository.existsById(agency.getId())){
                return false;
            }else{
                agencyRepository.save(agency);
                return true;
            }

        }catch (Exception e){
            return false;
        }
    }

    public boolean delete(Agency agency){
        try{
            if(agencyRepository.existsById(agency.getId())){
                agencyRepository.delete(agency);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public boolean update(Agency agency){
        try{
            if(agencyRepository.existsById(agency.getId())){
                agencyRepository.save(agency);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

}
