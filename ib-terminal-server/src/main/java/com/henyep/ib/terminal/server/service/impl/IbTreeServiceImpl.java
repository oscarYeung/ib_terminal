package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.IbTreeBean;
import com.henyep.ib.terminal.api.dto.request.ib.tree.AddIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.DeleteIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.GetIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.MoveIbTreeRequest;
import com.henyep.ib.terminal.api.dto.response.ib.tree.SearchIbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.tree.web.IbModel;
import com.henyep.ib.terminal.api.dto.response.ib.tree.web.IbTreeData;
import com.henyep.ib.terminal.api.dto.response.ib.tree.web.IbTreeState;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dao.UserRolesDao;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.helper.model.IbTreeNode;
import com.henyep.ib.terminal.server.repository.UserIbTreeRepository;
import com.henyep.ib.terminal.server.service.IbTreeService;

@Service(value = "IbTreeService")
public class IbTreeServiceImpl extends AbstractServiceImpl implements IbTreeService {

	private final transient Log logger = LogFactory.getLog(getClass());

	private IbTreeDao ibTreeDao;
	private UserRolesDao userRolesDao;
	
	@Resource(name = "UserIbTreeRepository")
	private UserIbTreeRepository userIbTreeRepository;

	@Autowired
	public IbTreeServiceImpl(IbTreeDao ibTreeDao, UserRolesDao userRolesDao) {
		this.ibTreeDao = ibTreeDao;
		this.userRolesDao = userRolesDao;
	}

	private List<IbModel> convertToWebIbTreeModel(List<IbTreeBean> list) {

		List<IbModel> ibModelList = new ArrayList<IbModel>();
		try {
			if (list != null && list.size() > 0) {
				Integer last_parent_min_id = 0;
				Integer last_parent_max_id = 0;
				String lastParent = "#";
				String rootParent = "#";
				Map<Integer, IbTreeNode> parentNodeList = new HashMap<Integer, IbTreeNode>();
				for (IbTreeBean ibTreeModel : list) {
					IbModel ib = new IbModel();
					ib.setId(ibTreeModel.getId().toString());
					ib.setText(ibTreeModel.getIb_code());
					IbTreeState state = new IbTreeState();
					state.setOpened(true);
					state.setSelected(false);
					ib.setState(state);
					
					if(ibTreeModel.getMax_id() - ibTreeModel.getMin_id() > 1){
						ib.setHas_child(true);
					}
					else{
						ib.setHas_child(false);
					}
					

					if (ibTreeModel.getMin_id() > last_parent_min_id && ibTreeModel.getMax_id() < last_parent_max_id) {
						ib.setParent(lastParent);
					} else {
						// Search in parentNodeList
						for (Integer i = parentNodeList.size(); i > 0; i--) {
							IbTreeNode parentNode = parentNodeList.get(i);
							if (parentNode.getMin_id() < ibTreeModel.getMin_id() && parentNode.getMax_id() > ibTreeModel.getMax_id()) {
								lastParent = parentNode.getId();
								ib.setParent(lastParent);
								break;
							}
						}

						// First Node
						if (ib.getParent() == null) {
							ib.setParent(rootParent);
							state.setSelected(true);
						}
					}

					// Check if that is a parent / sub parent
					// Set this ib be last parent
					if (ibTreeModel.getMax_id() - ibTreeModel.getMin_id() > 1) {
						last_parent_min_id = ibTreeModel.getMin_id();
						last_parent_max_id = ibTreeModel.getMax_id();
						lastParent = ib.getId();
						IbTreeNode node = new IbTreeNode();
						node.setId(ibTreeModel.getId().toString());
						node.setMin_id(ibTreeModel.getMin_id());
						node.setMax_id(ibTreeModel.getMax_id());
						parentNodeList.put(parentNodeList.size() + 1, node);
					}
					ibModelList.add(ib);
				}
			}
		} catch (Exception e) {
			logger.error(e,e);
		}
		return ibModelList;
	}

	@Override
	public List<IbTreeBean> getByIbCode(String ib_code, Date trade_date) throws Exception {
		try {
			return ibTreeDao.getByIbCodeDateRange(ib_code, trade_date, trade_date);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	@Override
	public int addIbToTree(AddIbTreeRequest request) throws Exception {
		Integer parent_tree_id = request.getBody().getParent_tree_id();
		String brand_code = request.getBody().getBrand_code();
		String team = request.getBody().getTeam();
		String ib_code = request.getBody().getIb_code();
		Date start_date = request.getBody().getStart_date();
		Date end_date = request.getBody().getEnd_date();
		
		String sender = this.getSender(request.getHeader());
		int rtn = ibTreeDao.addIbToTree(parent_tree_id, brand_code, team, ib_code, start_date, end_date,sender);
		userIbTreeRepository.clearMap();
		return rtn;
	}

	@Override
	public int deteleIbFromTree(DeleteIbTreeRequest request) throws Exception {
		Integer tree_id = request.getBody().getId();
		String sender = this.getSender(request.getHeader());
		
		int rtn = ibTreeDao.deteleIbFromTree(tree_id , sender);
		userIbTreeRepository.clearMap();
		return rtn;
	}

	@Override
	public int moveIb(MoveIbTreeRequest request) throws Exception {
		Integer from_ib_id = request.getBody().getFrom_ib_id();
		Integer to_ib_id = request.getBody().getTo_ib_id();
		String sender = this.getSender(request.getHeader());		
		int rtn = ibTreeDao.moveIb(from_ib_id, to_ib_id,sender);
		userIbTreeRepository.clearMap();
		return rtn;
	}

	@Override
	public List<IbTreeBean> getByTeamHead(String ib_code, Date trade_date) throws Exception {
		return ibTreeDao.getByTeamHead(ib_code, trade_date, trade_date);
	}

	@Override
	public IbTreeData getByTeamHeadWebFormat(GetIbTreeRequest request, SenderDto sender) throws Exception {
		String ib_code = request.getBody().getIb_code();
		Date trade_date = request.getBody().getTrade_date();
		logger.debug("getByTeamHead - ib_code:" + ib_code + ", trade_date:" + trade_date);
		IbTreeData data = new IbTreeData();
		
		List<IbTreeBean> list = null;
		
		
		if(request.getHeader().getChannelId().equals(Constants.CHANNEL_ADMIN)){
			// admin request
			list = ibTreeDao.getByTeamHead(sender.getSender(), trade_date);
		}
		else{
			// web request
			list = ibTreeDao.getByTeamHead(ib_code, trade_date, trade_date);
		}
		data.setData(convertToWebIbTreeModel(list));

		return data;
	}

	@Override
	public SearchIbResponseDto searchIb(String ib_code, Date trade_date) throws Exception {
		SearchIbResponseDto response = new SearchIbResponseDto();

		List<IbTreeBean> list = ibTreeDao.getByTeamHead(ib_code, trade_date, trade_date);
		if (list.size() > 0) {
			response.setIn_ib_tree(true);
			for (IbTreeBean bean : list) {
				if (bean.getIb_code().equals(ib_code)) {
					response.setIb_tree(bean);
					break;
				}
			}
		} else {
			response.setIn_ib_tree(false);
		}

		return response;
	}
	
	@Override
	public IbTreeData getIbTreeByIb(String ib_code, Date trade_date) throws Exception {

		IbTreeData data = new IbTreeData();
		
		List<IbTreeBean> list = ibTreeDao.getIbTreeByIbCode(ib_code, trade_date, trade_date);
		data.setData(convertToWebIbTreeModel(list));

		return data;
	}

	@Override
	public List<String> validateAddByIbCode(String parent_ib_code, String target_ib_code, Date start_date) throws Exception{
		
		List<String> errorCodes = ibTreeDao.validateAddByIbCode(parent_ib_code, target_ib_code, start_date);
		return errorCodes;
		
	}
	
	@Override
	public int addByIbCode(String parent_ib_code, String target_ib_code, Date start_date, String user) throws Exception{
		return ibTreeDao.addByIbCode(parent_ib_code, target_ib_code, start_date, user);
	}
	
}
