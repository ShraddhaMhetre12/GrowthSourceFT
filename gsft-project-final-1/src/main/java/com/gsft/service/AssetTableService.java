package com.gsft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsft.model.AssetTable;
import com.gsft.repository.AssetTableDao;

@Service
public class AssetTableService {

	@Autowired
	private AssetTableDao dao;
	
	public void saveData(AssetTable assetTable) {
	dao.save(assetTable);
}
	public List<AssetTable> listAll(){
	 return dao.findAll();
	}
}
