package com.gsft.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsft.AssetCreationDto;
import com.gsft.model.AssetMaster;
import com.gsft.model.CustomerInputDetails;
import com.gsft.model.CustomerInputSummary;
import com.gsft.model.LoanCompanyMaster;
import com.gsft.service.AssetTableService;

@Controller
public class AssetMasterRouteController {

	
	private Log log=LogFactory.getLog(AssetMasterRouteController.class);
	
	@Autowired
	AssetTableService service;

	@RequestMapping("/")
	public String asset(Model model) {
		model.addAttribute("assetTable", new AssetCreationDto());
		return "view/submitasset";

	}

	@RequestMapping("/optimizedCal")
	public String submitAsset(Model model) {
		List<CustomerInputDetails> list = service.listAll();
		model.addAttribute("assetTable", list);
		return "view/optimizedCal";
	}
	
	@RequestMapping(value = "/submitAsset", method = RequestMethod.POST)
	public String submitAsset(Model model, @ModelAttribute AssetCreationDto assetTable) {
		
		CustomerInputSummary customerSummary = service.saveData(assetTable);
		customerSummary.getCustomerInputDetails();

		Map<String, List<LoanCompanyMaster>> loanCompanyMap = service
				.getLoanCompanyMasterForCustomerInput(customerSummary.getCustomerInputDetails());

		model.addAttribute("assetTable", customerSummary.getCustomerInputDetails());
		model.addAttribute("loanCompanyMap", loanCompanyMap);
		model.addAttribute("summaryId", customerSummary.getId());
		return "view/optimizedCal";
	}

	@RequestMapping(value = "api/calculate/{id}", method = RequestMethod.POST)
	public @ResponseBody String calculate(@PathVariable Long id) {

		Double rateAmtTotalGSFT = 0.0;
		Double ltvAmtTotalGSFT = 0.0;
		Double tenuerAmtTotalGSFT = 0.0;

		Double rateAmtTotalABCD = 0.0;
		Double ltvAmtTotalABCD = 0.0;
		Double tenuerAmtTotalABCD = 0.0;

		Double amtTotalCustInputGSFT = 0.0;
		Double amtTotalCustInputABCD = 0.0;


		Double rateWeightedAvgABCD = 0.0;
		Double ltvWeightedAvgABCD = 0.0;
		Double tenuerWeightedAvgABCD = 0.0;

		Double rateWeightedAvgGSFT = 0.0;
		Double ltvWeightedAvgGSFT = 0.0;
		Double tenuerWeightedAvgGSFT = 0.0;

		CustomerInputSummary customerInputSummary = service.getInputSummaryById(id);
		List<CustomerInputDetails> customerInputDetailsLst = customerInputSummary.getCustomerInputDetails();

		Map<String, List<LoanCompanyMaster>> loanCompanyMap = service
				.getLoanCompanyMasterForCustomerInput(customerInputDetailsLst);

		List<LoanCompanyMaster> abcdLoanMasterLst = loanCompanyMap.containsKey("ABCD") ? loanCompanyMap.get("ABCD")
				: new ArrayList<LoanCompanyMaster>();
		List<LoanCompanyMaster> gsftLoanMasterLst = loanCompanyMap.containsKey("GSFT") ? loanCompanyMap.get("GSFT")
				: new ArrayList<LoanCompanyMaster>();

		// map for GSFT loan master
		Map<Long, CustomerInputDetails> customerInputDetailsMap = customerInputDetailsLst.stream()
				.collect(Collectors.toMap(customerInputDetails -> customerInputDetails.getAssetId().getId(),
						customerInputDetails -> customerInputDetails));

		// map for ABCD loan master
		Map<Long, LoanCompanyMaster> abcdLoanMasterMap = abcdLoanMasterLst.stream().collect(Collectors.toMap(
				loanCompanyMaster -> loanCompanyMaster.getAssetId().getId(), loanCompanyMaster -> loanCompanyMaster));

		// map for GSFT loan master
		Map<Long, LoanCompanyMaster> gsftLoanMasterMap = gsftLoanMasterLst.stream().collect(Collectors.toMap(
				loanCompanyMaster -> loanCompanyMaster.getAssetId().getId(), loanCompanyMaster -> loanCompanyMaster));

		CustomerInputDetails customerInputDetailstmp = null;
		LoanCompanyMaster loanCompanyMasterABCDtmp = null;
		LoanCompanyMaster loanCompanyMasterGSFTtmp = null;

		for (Long assetId : customerInputDetailsMap.keySet()) {

			customerInputDetailstmp = customerInputDetailsMap.get(assetId);
			loanCompanyMasterABCDtmp = abcdLoanMasterMap.containsKey(assetId) ? abcdLoanMasterMap.get(assetId) : null;
			loanCompanyMasterGSFTtmp = gsftLoanMasterMap.containsKey(assetId) ? gsftLoanMasterMap.get(assetId) : null;

			
			
			
			//Multiply And Add rate,ltv,tenure to value for GSFT
			if (loanCompanyMasterGSFTtmp != null) {
				rateAmtTotalGSFT += customerInputDetailstmp.getValue() * loanCompanyMasterGSFTtmp.getRate();
				ltvAmtTotalGSFT += customerInputDetailstmp.getValue() * loanCompanyMasterGSFTtmp.getLtv();
				tenuerAmtTotalGSFT += customerInputDetailstmp.getValue() * loanCompanyMasterGSFTtmp.getTenure();
				//Sum of Value for GSFT
				amtTotalCustInputGSFT += customerInputDetailstmp.getValue();

			}
			//Multiply And Add rate,ltv,tenure to value for ABCD
			if (loanCompanyMasterABCDtmp != null) {
				//Sum of Value for abcd
				amtTotalCustInputABCD += customerInputDetailstmp.getValue();
				rateAmtTotalABCD += customerInputDetailstmp.getValue() * loanCompanyMasterABCDtmp.getRate();
				ltvAmtTotalABCD += customerInputDetailstmp.getValue() * loanCompanyMasterABCDtmp.getLtv();
				tenuerAmtTotalABCD += customerInputDetailstmp.getValue() * loanCompanyMasterABCDtmp.getTenure();
			}

		}
		
		//Weighted Avg for ABCD
		rateWeightedAvgABCD = (rateAmtTotalABCD / amtTotalCustInputABCD);
		ltvWeightedAvgABCD = (ltvAmtTotalABCD / amtTotalCustInputABCD);
		tenuerWeightedAvgABCD = (tenuerAmtTotalABCD / amtTotalCustInputABCD);

		//Weighted Avg for GSFT
		rateWeightedAvgGSFT = (rateAmtTotalGSFT / amtTotalCustInputGSFT);
		ltvWeightedAvgGSFT = (ltvAmtTotalGSFT / amtTotalCustInputGSFT);
		tenuerWeightedAvgGSFT = (tenuerAmtTotalGSFT / amtTotalCustInputGSFT);

		Map<String, String> weightedAvgMap = new HashMap<>();

		DecimalFormat df = new DecimalFormat("00.00");

		weightedAvgMap.put("rateWeightedAvgABCD", df.format(rateWeightedAvgABCD));
		weightedAvgMap.put("ltvWeightedAvgABCD", df.format(ltvWeightedAvgABCD));
		weightedAvgMap.put("tenuerWeightedAvgABCD", df.format(tenuerWeightedAvgABCD));

		weightedAvgMap.put("rateWeightedAvgGSFT", df.format(rateWeightedAvgGSFT));
		weightedAvgMap.put("ltvWeightedAvgGSFT", df.format(ltvWeightedAvgGSFT));
		weightedAvgMap.put("tenuerWeightedAvgGSFT", df.format(tenuerWeightedAvgGSFT));

		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;

		try {
			json = objectMapper.writeValueAsString(weightedAvgMap);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;

	}
	@RequestMapping(value = "api/asset/", method = RequestMethod.POST)
	public @ResponseBody String getAssetDetails() {
		List<AssetMaster> assetMaster = service.getAssetMasterDetails();
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;

		try {
		json = objectMapper.writeValueAsString(assetMaster);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
		
	}

}
	