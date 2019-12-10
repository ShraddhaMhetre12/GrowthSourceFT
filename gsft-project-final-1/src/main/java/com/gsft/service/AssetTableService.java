package com.gsft.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsft.AssetCreationDto;
import com.gsft.model.AssetTable;
import com.gsft.repository.AssetTableDao;

@Service
public class AssetTableService {

	@Autowired
	private AssetTableDao dao;
	
	public void saveData(AssetCreationDto assetTableBo) {
	
	List<AssetTable> assetTables =  Optional.ofNullable(assetTableBo.getAsset())
			.orElse(Collections.emptyList()).stream().filter(Objects::nonNull).map(assetui -> 
	{
		AssetTable assetTable = new AssetTable();
		assetTable.setAssetname(assetui.getAssetname());
		assetTable.setLtv(assetui.getLtv());
		assetTable.setRate(assetui.getRate());
		assetTable.setValue(assetui.getValue());
		assetTable.setTenure(assetui.getTenure());
		return assetTable;
		}).collect(Collectors.toList());
	dao.saveAll(assetTables);
}
	public List<AssetTable> listAll(){
	 return dao.findAll();
	}
}
