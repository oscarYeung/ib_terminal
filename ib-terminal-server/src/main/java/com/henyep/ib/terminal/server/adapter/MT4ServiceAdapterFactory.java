package com.henyep.ib.terminal.server.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.schedule.task.GetAllMT5SymbolRequestDto;
import com.henyep.ib.terminal.api.dto.request.schedule.task.GetAllSymbolRequestDto;
import com.henyep.ib.terminal.server.dao.SystemParamsDao;
import com.henyep.ib.terminal.server.dto.mt4.model.Mt4WebServiceConnectionModel;
import com.henyep.white.label.api.dto.request.BaseRequestBodyDto;
import com.henyep.white.label.api.dto.request.balance.transfer.DepositMT4RequestDto;
import com.henyep.white.label.api.dto.request.user.GetUserProfileRequestDto;

@Component(value = "MT4ServiceAdapterFactory")
public class MT4ServiceAdapterFactory {

	protected final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "GetUserProfileServiceAdapter")
	private GetUserProfileServiceAdapter getUserProfileServiceAdapter;

	@Resource(name = "GetAllSymbolsServiceAdapter")
	private GetAllSymbolsServiceAdapter getAllSymbolsServiceAdapter;
	
	@Resource(name = "GetAllMT5SymbolsServiceAdapter")
	private GetAllMT5SymbolsServiceAdapter getAllMT5SymbolsServiceAdapter;
	
	@Resource(name = "DepositServiceAdapter")
	private DepositServiceAdapter depositServiceAdapter;

	@Resource(name = "SystemParamsDao")
	SystemParamsDao systemParamsDao;

	private Mt4WebServiceConnectionModel _connectionModel;
	// private static MT4ServiceAdapterFactory instance = null;
	private Map<Class<?>, MT4ServiceAdapter> list;

	public MT4ServiceAdapterFactory() {

	}

	private void initList() {
		if (list == null) {
			list = new HashMap<Class<?>, MT4ServiceAdapter>();
			list.put(GetUserProfileRequestDto.class, getUserProfileServiceAdapter);
			list.put(GetAllSymbolRequestDto.class, getAllSymbolsServiceAdapter);
			list.put(DepositMT4RequestDto.class, depositServiceAdapter);
			list.put(GetAllMT5SymbolRequestDto.class, getAllMT5SymbolsServiceAdapter);
		}
	}

	// public static MT4ServiceAdapterFactory getInstance() {
	// if (instance == null) {
	// synchronized (MT4ServiceAdapterFactory.class) {
	// instance = new MT4ServiceAdapterFactory();
	// }
	// }
	// return instance;
	// }

	public MT4ServiceAdapter getAdapter(Object data) {
		initList();
		if (list.containsKey(data.getClass())) {
			return list.get(data.getClass());
		}
		return null;
	}

	public Object getResponseObject(Object request, String json) {
		MT4ServiceAdapter adapter = getAdapter(request);
		if (adapter != null)
			return adapter.convertToObject(json);

		return null;
	}

	public String sendRequest(BaseRequestBodyDto data) {
		try {
			if (_connectionModel == null)
				_connectionModel = systemParamsDao.getMt4ServiceConnectionModel();
			
			return sendRequest(_connectionModel, data);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return null;
	}

	public String sendRequest(Mt4WebServiceConnectionModel model, BaseRequestBodyDto data) {
		MT4ServiceAdapter adapter = getAdapter(data);
		if (adapter != null)
			return adapter.sendRequest(model.getIp(), model.getPort(), model.getLogin(), model.getPassword(), model.getConnection(), data);

		return null;
	}
}
