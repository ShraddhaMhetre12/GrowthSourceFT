package com.gsft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gsft.model.AssetMaster;
import com.gsft.service.AssetMasterService;

//@Controller
//public class AssetMasterController {
//
//	@Autowired
//	AssetMasterService assetMasterservice;
//	
//	@RequestMapping(value="/submitAsset", method=RequestMethod.POST)
//	public String submitAsset(Model model,@ModelAttribute AssetMaster assetMaster) {
//		assetMasterservice.addAsset(assetMaster);
//		model.addAttribute("success",new Boolean(true));
//		return "view/assetResult";
//	}
//	
//	
//}
