package com.henyep.ib.terminal.server.service;

import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbTreeBean;
import com.henyep.ib.terminal.api.dto.request.ib.tree.AddIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.DeleteIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.GetIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.MoveIbTreeRequest;
import com.henyep.ib.terminal.api.dto.response.ib.tree.SearchIbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.tree.web.IbTreeData;
import com.henyep.ib.terminal.server.dto.security.SenderDto;

public interface IbTreeService {

	public List<IbTreeBean> getByIbCode(String ib_code, Date trade_date) throws Exception;

	public int addIbToTree(AddIbTreeRequest request) throws Exception;

	public int deteleIbFromTree(DeleteIbTreeRequest request) throws Exception;

	public List<IbTreeBean> getByTeamHead(String ib_code, Date trade_date) throws Exception;

	public IbTreeData getByTeamHeadWebFormat(GetIbTreeRequest request, SenderDto sender) throws Exception;

	public SearchIbResponseDto searchIb(String ib_code, Date trade_date) throws Exception;

	public int moveIb(MoveIbTreeRequest request) throws Exception;
	
	public IbTreeData getIbTreeByIb(String ib_code, Date trade_date) throws Exception;
	
	public List<String> validateAddByIbCode(String parent_ib_code, String target_ib_code, Date start_date) throws Exception;
	
	public int addByIbCode(String parent_ib_code, String target_ib_code, Date start_date, String user) throws Exception;
}
