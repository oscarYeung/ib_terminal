package com.henyep.ib.terminal.server.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.henyep.ib.terminal.api.dto.db.ProductSymbolMapBean;

public class ProductSymbolMapUtil {

	private final String KEY_ANY = "*";

	private HashMap<String, List<ProductSymbolMapBean>> symbolMap_exactlyMatch;
	private HashMap<String, List<ProductSymbolMapBean>> symbolMap_partiallyMatch;
	private List<ProductSymbolMapBean> otherProductGroup;

	private List<ProductSymbolMapBean> productSymbolList;

	public ProductSymbolMapUtil(List<ProductSymbolMapBean> productSymbolList) throws Exception {
		this.productSymbolList = productSymbolList;
		symbolMap_exactlyMatch = new HashMap<String, List<ProductSymbolMapBean>>();
		symbolMap_partiallyMatch = new HashMap<String, List<ProductSymbolMapBean>>();
		otherProductGroup = new ArrayList<ProductSymbolMapBean>();
		init();
	}

	private void init() throws Exception {

		for (ProductSymbolMapBean productSymbolMap : productSymbolList) {
			String symbol = productSymbolMap.getSymbol();
			if (symbol.equals(KEY_ANY)) {
				otherProductGroup.add(productSymbolMap);
			} else if (symbol.contains(KEY_ANY)) {
				if (!symbolMap_partiallyMatch.containsKey(symbol))
					symbolMap_partiallyMatch.put(symbol, new ArrayList<ProductSymbolMapBean>());
				symbolMap_partiallyMatch.get(symbol).add(productSymbolMap);
			} else {
				if (!symbolMap_exactlyMatch.containsKey(symbol))
					symbolMap_exactlyMatch.put(symbol, new ArrayList<ProductSymbolMapBean>());
				symbolMap_exactlyMatch.get(symbol).add(productSymbolMap);
			}
		}
	}

	public String getProductGroup(String inputSymbol, Date tradeDate, List<String> availableProductGroups){
		
		if(symbolMap_exactlyMatch.containsKey(inputSymbol)){
			List<ProductSymbolMapBean> beans = symbolMap_exactlyMatch.get(inputSymbol);
			ProductSymbolMapBean highestPriorityBean = null;
			for(ProductSymbolMapBean bean : beans){
				// check trade date and available product groups
				if(isWithinTimerange(tradeDate, bean) && availableProductGroups.contains(bean.getProduct_group())){
					// check priority
					if(highestPriorityBean == null || highestPriorityBean.getPriority() > bean.getPriority()){
						highestPriorityBean = bean;
					}	
				}
			}
			if(highestPriorityBean != null){
				return highestPriorityBean.getProduct_group();
			}
		}
		
		for (String symbol : symbolMap_partiallyMatch.keySet()) {
			String trimedSymbol = StringUtils.remove(symbol, KEY_ANY);
			if (!trimedSymbol.equals("") && inputSymbol.contains(trimedSymbol)) {
				List<ProductSymbolMapBean> beans = symbolMap_partiallyMatch.get(symbol);
				for(ProductSymbolMapBean bean : beans){
					if(isWithinTimerange(tradeDate, bean) && availableProductGroups.contains(bean.getProduct_group()))
						return bean.getProduct_group();
				}
			}
		}
		
		
		for(ProductSymbolMapBean bean : otherProductGroup){
			if(isWithinTimerange(tradeDate, bean))
				return bean.getProduct_group();
		}
		return null;
	}

	private boolean isWithinTimerange(Date tradeDate, ProductSymbolMapBean bean) {
		if (bean.getEnd_date() != null) {
			return bean.getStart_date().before(tradeDate) && bean.getEnd_date().after(tradeDate);
		} else {
			return bean.getStart_date().before(tradeDate);
		}
	}
}
