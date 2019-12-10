package com.gsft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gsft.model.AssetTable;
import com.gsft.service.AssetTableService;

@Controller
public class AssetMasterRouteController {

	@Autowired
	AssetTableService service;
	
	@RequestMapping("/")
	public String asset(Model model) {
		model.addAttribute("assetTable", new AssetTable());
		return "view/asset";
		
	}
	
	@RequestMapping("/NewFile")
	public String submitAsset(Model model){
		List<AssetTable> list=service.listAll();
		model.addAttribute("assetTable", list);
		return "view/NewFile";
	}
	
	@RequestMapping(value="/submitAsset", method=RequestMethod.POST)
	public String submitAsset(Model model,@ModelAttribute AssetTable assetTable) {
		service.saveData(assetTable);
		model.addAttribute("success",new Boolean(true));
		return "redirect:/";
	}
	
}
