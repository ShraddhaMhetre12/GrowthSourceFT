package com.gsft.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsft.AssetCreationDto;
import com.gsft.model.AssetMaster;
import com.gsft.model.CustomerInputDetails;
import com.gsft.model.CustomerInputSummary;
import com.gsft.model.LoanCompanyMaster;
import com.gsft.service.AssetTableService;

@Controller
public class AssetMasterRouteController {

	@Autowired
	AssetTableService service;
	
	@RequestMapping("/")
	public String asset(Model model) {
		model.addAttribute("assetTable", new AssetCreationDto());
		return "view/submitasset";
		
	}
	
	@RequestMapping("/NewFile")
	public String submitAsset(Model model){
		List<CustomerInputDetails> list=service.listAll();
		model.addAttribute("assetTable", list);
		return "view/NewFile";
	}
	
	@RequestMapping(value="/submitAsset", method=RequestMethod.POST)
	public String submitAsset(Model model,@ModelAttribute AssetCreationDto assetTable) {
		//System.out.println("Selected assets size :: "+assetTable.getAsset().size());
		CustomerInputSummary customerSummary=service.saveData(assetTable);
		customerSummary.getCustomerInputDetails();
		//model.addAttribute("success",new Boolean(true));
		model.addAttribute("assetTable", customerSummary.getCustomerInputDetails());
		
		List<AssetMaster> assetMasterList=new ArrayList<>();	
		for(CustomerInputDetails customerInputDetails :customerSummary.getCustomerInputDetails())
		{
			assetMasterList.add(customerInputDetails.getAssetId());
		}
		
		//Map<String, List<LoanCompanyMaster>> loanCompanyMap=service.getLoanCompanyMaster();
		Map<String, List<LoanCompanyMaster>> loanCompanyMap=service.getLoanCompanyMasterForAssets(assetMasterList);
		
		System.out.println("loanCompanyMap:"+loanCompanyMap);
		
		model.addAttribute("loanCompanyMap",loanCompanyMap);
		model.addAttribute("summaryId", customerSummary.getId());
		return "view/NewFile";
	}
	
	@RequestMapping(value="api/calculate/{id}", method=RequestMethod.POST )
	public @ResponseBody String calculate(@PathVariable Long id ) {
		System.out.println("Inside Cal   "+id);	
		return "success";
		
	}
	
}
