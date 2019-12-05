package com.gsft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsft.model.AssetMaster;
import com.gsft.model.MasterTable;
import com.gsft.repository.MasterTableRepository;


@Service
public class AssetMasterService {

	@Autowired
	private MasterTableRepository repo;

//	
//	public void saveData(AssetMaster assetMaster) {
//		repo1.save(assetMaster);
//	}
	public List<MasterTable> listAlll(){
	 return repo.findAll();
	}
//	
//	public List<AssetMaster> listAll(){
//		return repo1.findAll();
//	}

//	public AssetMaster create(AssetMaster assetMaster) {
//		
//		return repo.save(assetMaster);
//		
//	}
	
	
}
