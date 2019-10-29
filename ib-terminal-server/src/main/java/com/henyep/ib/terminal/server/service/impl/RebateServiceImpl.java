package com.henyep.ib.terminal.server.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henyep.ib.terminal.api.dto.db.ClientPackageDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.db.RebateBean;
import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.db.RebateSupplementaryBean;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.rebate.GetRebateByIbCodeRequestDto;
import com.henyep.ib.terminal.api.dto.request.rebate.IbRebateRequestDto;
import com.henyep.ib.terminal.api.dto.request.rebate.InsertUpdateRebateDetailsDto;
import com.henyep.ib.terminal.api.dto.request.rebate.InsertUpdateRebateSupplementariesDto;
import com.henyep.ib.terminal.api.dto.request.rebate.RebateRequestDto;
import com.henyep.ib.terminal.api.dto.response.clientPackageDetails.ClientPackageSpreadTypeDto;
import com.henyep.ib.terminal.api.dto.response.rebate.IbClientRebateResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.IbClientRebateWithDetails;
import com.henyep.ib.terminal.api.dto.response.rebate.RebateDetailsResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.RebateResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.RebateSupplementasriesResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.dao.ClientPackageDetailsDao;
import com.henyep.ib.terminal.server.dao.IbClientRebateMapDao;
import com.henyep.ib.terminal.server.dao.IbProfileDao;
import com.henyep.ib.terminal.server.dao.RebateDao;
import com.henyep.ib.terminal.server.dao.RebateDetailsDao;
import com.henyep.ib.terminal.server.dao.RebateSupplementaryDao;
import com.henyep.ib.terminal.server.service.RebateService;
import com.henyep.ib.terminal.server.util.RebateUtil;

@Service(value = "RebateService")
public class RebateServiceImpl extends AbstractServiceImpl implements RebateService {

	private final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "RebateDao")
	private RebateDao rebateDao;

	@Resource(name = "RebateDetailsDao")
	private RebateDetailsDao rebateDetailsDao;

	@Resource(name = "RebateSupplementaryDao")
	private RebateSupplementaryDao rebateSupplementaryDao;

	@Resource(name = "IbClientRebateMapDao")
	private IbClientRebateMapDao ibClientRebateMapDao;

	@Resource(name = "IbProfileDao")
	private IbProfileDao ibProfileDao;
	
	@Resource(name = "ClientPackageDetailsDao")
	private ClientPackageDetailsDao clientPackageDetailsDao;

	@Override
	public IbClientRebateResponseDto getRebateByIbCodeWithDateRangeRequest(IbRequestDto<IbRebateRequestDto> request) {
		IbClientRebateResponseDto responseDto = new IbClientRebateResponseDto();

		try {
			List<IbClientRebateMapBean> ibRebateMapList = ibClientRebateMapDao.getIbClientRebateMapByIbCodeWithDateRange(
					request.getBody().getBrand_code(), request.getBody().getIb_code(), request.getBody().getStart_date(),
					request.getBody().getEnd_date());

			responseDto.setIb_code(request.getBody().getIb_code());
			
			// set client package details map
			List<ClientPackageDetailsBean> clientPackageDetailsBeanList = clientPackageDetailsDao.getAllClientPackageDetailss();
			HashMap<String, Double> clientPackageMaps = new HashMap<String, Double>();
			for(ClientPackageDetailsBean clientPackage : clientPackageDetailsBeanList){
				if(!clientPackageMaps.containsKey(clientPackage.getClient_package_code())){
					clientPackageMaps.put(clientPackage.getClient_package_code(), clientPackage.getBase_commission());
				}
			}

			if (ibRebateMapList != null && ibRebateMapList.size() > 0) {

				responseDto.setIbClientRebateList(new ArrayList<IbClientRebateWithDetails>());

				for (IbClientRebateMapBean ibClientRebate : ibRebateMapList) {
					List<RebateDetailsBean> rebateDetails = rebateDetailsDao.getRebateDetailsByRebateCodeWithDateRange(
							ibClientRebate.getRebate_code(), request.getBody().getStart_date(), request.getBody().getEnd_date());

					for(RebateDetailsBean rebateDetail : rebateDetails){
						String clientPackageCode = rebateDetail.getClient_package_code();
						if(clientPackageMaps.containsKey(clientPackageCode)){
							Double baseFixCommission = clientPackageMaps.get(clientPackageCode);
							rebateDetail.setClient_fix_commission(baseFixCommission);
						}
					}
					
					
					Map<String, List<RebateDetailsBean>> packageRebateList = new HashMap<String, List<RebateDetailsBean>>();
					for (RebateDetailsBean rebateDetail : rebateDetails) {
						List<RebateDetailsBean> rebateList;
						String clientPackage = rebateDetail.getClient_package_code();
						String spreadType = rebateDetail.getSpread_type();
						if (!packageRebateList.containsKey(clientPackage + spreadType)) {
							rebateList = new ArrayList<RebateDetailsBean>();
							packageRebateList.put(clientPackage + spreadType, rebateList);
						}
						rebateList = packageRebateList.get(clientPackage + spreadType);
						rebateList.add(rebateDetail);
					}

					Object[] keys = packageRebateList.keySet().toArray();
					Arrays.sort(keys);
					for (Object key : keys) {
						IbClientRebateWithDetails details = new IbClientRebateWithDetails();
						details.setRebate_Code(packageRebateList.get(key).get(0).getRebate_code());
						details.setClient_package_code(packageRebateList.get(key).get(0).getClient_package_code());
						details.setSpread_type(packageRebateList.get(key).get(0).getSpread_type());
						details.setRebateDetails(packageRebateList.get(key));
						responseDto.getIbClientRebateList().add(details);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			responseDto = null;
		}

		return responseDto;
	}

	

	@Override
	public RebateResponseDto getRebateByIbCodeRequest(IbRequestDto<GetRebateByIbCodeRequestDto> request) {
		String ibCode = request.getBody().getIb_code();
		String rebateCode = RebateUtil.getRebateCode(ibCode);
		RebateRequestDto dto = new RebateRequestDto();
		dto.setRebate_code(rebateCode);
		
		IbRequestDto<RebateRequestDto> rebateRequest = new IbRequestDto<RebateRequestDto>();
		rebateRequest.setBody(dto);
		
		return getRebateByRebateCodeRequestNoDateRange(rebateRequest);
	}
	
	
	@Override
	public RebateResponseDto getRebateByRebateCodeRequest(IbRequestDto<RebateRequestDto> request) {
		Date startDate = request.getBody().getStart_date();
		if (startDate == null) {
			return getRebateByRebateCodeRequestNoDateRange(request);
		} else {
			return getRebateByRebateCodeRequestWithDateRange(request);
		}

	}

	private RebateResponseDto getRebateByRebateCodeRequestNoDateRange(IbRequestDto<RebateRequestDto> request) {
		RebateResponseDto responseDto = new RebateResponseDto();
		try {
			String rebateCode = request.getBody().getRebate_code();

			// rebate
			RebateBean rebate = rebateDao.getRebateByRebateCode(rebateCode);
			responseDto.setRebate(rebate);

			// ib client rebate map
			List<String> rebateCodeList = new ArrayList<String>();
			rebateCodeList.add(rebateCode);
			List<IbClientRebateMapBean> ibClientRebateMaps = ibClientRebateMapDao.getByRebateCodeList(rebateCodeList);
			responseDto.setIbClientRebateMaps(ibClientRebateMaps);

			// rebate detail list
			List<RebateDetailsBean> rebateDetailsList = rebateDetailsDao.getRebateDetailsByRebateCode(rebateCode);
			if (rebateDetailsList != null && rebateDetailsList.size() > 0) {
				responseDto.setRebateDetailsList(rebateDetailsList);
			}

			// rebate supplementary
			List<RebateSupplementaryBean> rebateSupplementaryBeanList = rebateSupplementaryDao.getRebateSupplementaryByRebateCode(rebateCode);

			if (rebateSupplementaryBeanList.size() > 0) {
				responseDto.setRebateSupplementaryList(rebateSupplementaryBeanList);
			}

		} catch (Exception e) {
			logger.error(e);
			responseDto = null;
		}

		return responseDto;
	}

	private RebateResponseDto getRebateByRebateCodeRequestWithDateRange(IbRequestDto<RebateRequestDto> request) {
		RebateResponseDto responseDto = new RebateResponseDto();
		try {
			String rebateCode = request.getBody().getRebate_code();
			Date startDate = request.getBody().getStart_date();
			Date endDate = request.getBody().getEnd_date();

			// rebate
			RebateBean rebate = rebateDao.getRebateByRebateCode(rebateCode);
			responseDto.setRebate(rebate);

			// ib client rebate map
			List<IbClientRebateMapBean> ibClientRebateMaps = ibClientRebateMapDao.getIbClientRebateMapByRebateCodeWithDateRange(rebateCode, startDate,
					endDate);
			responseDto.setIbClientRebateMaps(ibClientRebateMaps);

			// rebate detail list
			List<RebateDetailsBean> rebateDetailsList = rebateDetailsDao.getRebateDetailsByRebateCodeWithDateRange(rebateCode, startDate, endDate);

			if (rebateDetailsList != null && rebateDetailsList.size() > 0) {
				responseDto.setRebateDetailsList(rebateDetailsList);
			}

			// rebate supplementary
			List<RebateSupplementaryBean> rebateSupplementaryBeanList = rebateSupplementaryDao
					.getRebateSupplementaryByRebateCodeWithDateRange(rebateCode, startDate, endDate);

			if (rebateSupplementaryBeanList.size() > 0) {
				responseDto.setRebateSupplementaryList(rebateSupplementaryBeanList);
			}

		} catch (Exception e) {
			logger.error(e);
			responseDto = null;
		}

		return responseDto;
	}

	
	//insert and update rebate details


	@Override
	@Transactional(rollbackFor = Exception.class)
	public RebateDetailsResponseDto updateRebateDetailsRequest(IbRequestDto<InsertUpdateRebateDetailsDto> request) throws Exception {
		
		String ibCode = request.getBody().getIb_code();
		String rebateCode = RebateUtil.getRebateCode(ibCode);
		
		String clientPackageCode = request.getBody().getClient_package_code();
		String spreadType = request.getBody().getSpread_type();
		
		
		logger.info("Start updating rebate details");
		// delete original rebate details
		logger.info("Deleting rebate details, rebateCode: " + rebateCode + " clientPackageCode: " + clientPackageCode + " spreadType: " + spreadType);
		rebateDetailsDao.deleteRebateDetailsByRebateCodeClientPackageCode(rebateCode, clientPackageCode, spreadType);

		// insert the new rebate details
		List<RebateDetailsBean> rebateDetailsBeanList = request.getBody().getRebateDetails();
		for(RebateDetailsBean rebateDetailBean : rebateDetailsBeanList){
			rebateDetailBean.setLast_update_user(getSender(request.getHeader()));
			rebateDetailBean.setLast_update_time(new Date());
			rebateDetailBean.setRebate_code(rebateCode);
		}
		if(rebateDetailsBeanList.size() > 0){
			logger.info("Inserting rebate details");
			rebateDetailsDao.saveRebateDetails(rebateDetailsBeanList);
		}
		
		// creating rebate response
		RebateDetailsResponseDto dto = new RebateDetailsResponseDto();
		List<RebateDetailsBean> dbRebateDetailBeans = rebateDetailsDao.getRebateDetailsByRebateCode(rebateCode);
		dto.setRebateDetailsList(dbRebateDetailBeans);
		
		dto.setIb_code(ibCode);
		
		return dto;
	}
	
	

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RebateSupplementasriesResponseDto updateRebateSupplementariesRequest(IbRequestDto<InsertUpdateRebateSupplementariesDto> request) throws Exception {

		String ibCode = request.getBody().getIb_code();
		String rebateCode = RebateUtil.getRebateCode(ibCode);
		
		
		logger.info("Start updating rebate supplementary");
		// update rebate detail
		logger.info("Deleting rebate supplementary, rebateCode: " + rebateCode);
		rebateSupplementaryDao.deleteRebateSupplementary(rebateCode);

		List<RebateSupplementaryBean> rebateSupplementaries = request.getBody().getRebateSupplementaries();
		if(rebateSupplementaries.size() > 0){
			logger.info("Inserting rebate supplementaries");
			for(RebateSupplementaryBean model : rebateSupplementaries){
				model.setLast_update_user(getSender(request.getHeader()));
				model.setLast_update_time(new Date());
			}
			rebateSupplementaryDao.saveRebateSupplementary(rebateSupplementaries);		
		}
		
		// creating rebate 
		RebateSupplementasriesResponseDto dto = new RebateSupplementasriesResponseDto();
		List<RebateSupplementaryBean> dbRebateSupplementaries = rebateSupplementaryDao.getRebateSupplementaryByRebateCode(rebateCode);
		dto.setRebateSupplementaryList(dbRebateSupplementaries);
		dto.setIb_code(ibCode);
		return dto;
	}

	
	// validate rebate details
	@Override
	public List<String> validateInsertRebateDetailsRequest(IbRequestDto<InsertUpdateRebateDetailsDto> request)
			throws Exception {
		List<String> errMsg = new ArrayList<String>();

		
		errMsg.addAll(validateUpdateRebateDetail(request.getBody().getIb_code(), request.getBody().getRebateDetails()));

		// check rebate detail already inserted or not
		if (errMsg.size() == 0) {
			String rebateCode = RebateUtil.getRebateCode(request.getBody().getIb_code());
			
			List<ClientPackageSpreadTypeDto> insertedClientPackageCode = rebateDetailsDao.getClientPackageCodesByRebateCode(rebateCode);
			for(RebateDetailsBean rebateDetailsBean : request.getBody().getRebateDetails()){
				String clientPackageCode = rebateDetailsBean.getClient_package_code();
				String spreadType = rebateDetailsBean.getSpread_type();
				for(ClientPackageSpreadTypeDto clientPackageSpreadType : insertedClientPackageCode){
					if(clientPackageSpreadType.getSpread_type().equals(spreadType) && clientPackageSpreadType.getClient_package_code().equals(clientPackageCode)){
						errMsg.add(Constants.ERR_REBATE_DETAILS_CLIENT_PACKAGE_SPREAD_CODE_ALREADY_INSERTED);
						break;
					}
				}
			}
		}

		return errMsg;
	}

	@Override
	public List<String> validateUpdateRebateDetailsRequest(IbRequestDto<InsertUpdateRebateDetailsDto> request)
			throws Exception {
		List<String> errMsg = new ArrayList<String>();
	
		List<RebateDetailsBean> rebateDetailList = request.getBody().getRebateDetails();
		
		errMsg.addAll(validateUpdateRebateDetail(request.getBody().getIb_code(), rebateDetailList));

		return errMsg;
	}
	

	public List<String> validateUpdateRebateDetail(String ibCode, List<RebateDetailsBean> rebateDetailsBeans) throws Exception {

		List<String> errMsg = new ArrayList<String>();

		String rebateCode = RebateUtil.getRebateCode(ibCode);
		// check ibCode exist
		List<IbProfileBean> ibProfiles = ibProfileDao.getIbProfileByIbCode(ibCode);
		if (ibProfiles.size() <= 0) {
			errMsg.add(Constants.ERR_COMMON_IB_PROFILE_NOT_EXIST);
		}
		
		for (RebateDetailsBean rebateDetailBean : rebateDetailsBeans) {
			if (!rebateDetailBean.getRebate_code().equals(rebateCode)) {
				errMsg.add(Constants.ERR_REBATE_CODE_NOT_SAME);
				break;
			}
		}
		
		for (RebateDetailsBean rebateDetailBean : rebateDetailsBeans) {
			
			if (rebateDetailBean.getEnd_date() != null) {
				if (rebateDetailBean.getStart_date().after(rebateDetailBean.getEnd_date())) {
					errMsg.add(Constants.ERR_COMMON_START_DATE_AFTER_END_DATE);
					break;
				}
			}
		}

		if (errMsg.size() == 0) {

			// group by rebate code, product group and client package code
			HashMap<String, ArrayList<Object>> rebateDetailsBeanMap = new HashMap<String, ArrayList<Object>>();
			for (RebateDetailsBean rebateDetailBean : rebateDetailsBeans) {
				String key = rebateDetailBean.getRebate_code() + rebateDetailBean.getProduct_group() + rebateDetailBean.getClient_package_code() + rebateDetailBean.getSpread_type();
				if (!rebateDetailsBeanMap.containsKey(key)) {
					rebateDetailsBeanMap.put(key, new ArrayList<Object>());
				}
				rebateDetailsBeanMap.get(key).add(rebateDetailBean);
			}

			for (String mapKey : rebateDetailsBeanMap.keySet()) {

				List<Object> rebateDetailBeans = rebateDetailsBeanMap.get(mapKey);
				Method getStartDateMethod = RebateDetailsBean.class.getMethod("getStart_date");
				Method getEndDateMethod = RebateDetailsBean.class.getMethod("getEnd_date");
				boolean overlapExist = checkObjBeanDateTimeOverlap(rebateDetailBeans, getStartDateMethod, getEndDateMethod);
				if (overlapExist) {
					errMsg.add(Constants.ERR_REBATE_DETAIL_DATE_RANGE_OVERLAP);
					break;
				}
			}
		}

		return errMsg;
	}


	// validate rebate supplementaries

	@Override
	public List<String> validateInsertRebateSupplementariesRequest(
			IbRequestDto<InsertUpdateRebateSupplementariesDto> request) throws Exception {
		
		List<String> errMsg = new ArrayList<String>();
		
		String ibCode = request.getBody().getIb_code();
		String rebateCode = RebateUtil.getRebateCode(ibCode);
		List<RebateSupplementaryBean> beanList = rebateSupplementaryDao.getRebateSupplementaryByRebateCode(rebateCode);
		if(beanList.size() > 0){
			errMsg.add(Constants.ERR_REBATE_SUPPLEMENTARY_ALREADY_EXIST);
		}
		
		if(errMsg.size() == 0){
			errMsg.addAll(validateUpdateRebateSupplementariesRequest(request));
		}
		
		return errMsg;
	}

	@Override
	public List<String> validateUpdateRebateSupplementariesRequest(
			IbRequestDto<InsertUpdateRebateSupplementariesDto> request) throws Exception {
		
		List<String> errMsg = new ArrayList<String>();
		// check ibCode exist
		List<IbProfileBean> ibProfiles = ibProfileDao.getIbProfileByIbCode(request.getBody().getIb_code());
		if (ibProfiles.size() <= 0) {
			errMsg.add(Constants.ERR_COMMON_IB_PROFILE_NOT_EXIST);
		}

		String ibCode = request.getBody().getIb_code();
		String rebateCode = RebateUtil.getRebateCode(ibCode);
		List<RebateSupplementaryBean> rebateSupplementaryBeanList = request.getBody().getRebateSupplementaries();
		errMsg.addAll(validateUpdateRebateSupplementary(rebateCode, rebateSupplementaryBeanList));

		return errMsg;
	}
	

	private List<String> validateUpdateRebateSupplementary(String rebateCode, List<RebateSupplementaryBean> rebateSupplementaryBeans)
			throws Exception {

		List<String> errMsg = new ArrayList<String>();

		// check date time valid
		for (RebateSupplementaryBean rebateSupplementaryBean : rebateSupplementaryBeans) {
			if (!rebateSupplementaryBean.getRebate_code().equals(rebateCode)) {
				errMsg.add(Constants.ERR_REBATE_CODE_NOT_SAME);
				break;
			} else if (rebateSupplementaryBean.getEnd_date() != null) {
				if (rebateSupplementaryBean.getStart_date().after(rebateSupplementaryBean.getEnd_date())) {
					errMsg.add(Constants.ERR_COMMON_START_DATE_AFTER_END_DATE);
				}
			}
		}

		// group by rebate code
		HashMap<String, ArrayList<Object>> beanMap = new HashMap<String, ArrayList<Object>>();
		for (RebateSupplementaryBean rebateSupplementaryBean : rebateSupplementaryBeans) {
			String key = rebateSupplementaryBean.getRebate_code() + "_" + rebateSupplementaryBean.getGroup_code();
			if (!beanMap.containsKey(key)) {
				beanMap.put(key, new ArrayList<Object>());
			}
			beanMap.get(key).add(rebateSupplementaryBean);
		}

		for (String mapKey : beanMap.keySet()) {

			List<Object> beans = beanMap.get(mapKey);
			Method getStartDateMethod = RebateSupplementaryBean.class.getMethod("getStart_date");
			Method getEndDateMethod = RebateSupplementaryBean.class.getMethod("getEnd_date");
			boolean overlapExist = checkObjBeanDateTimeOverlap(beans, getStartDateMethod, getEndDateMethod);
			if (overlapExist) {
				errMsg.add(Constants.ERR_REBATE_SUPPLEMENTARY_DATE_RANGE_OVERLAP);
				break;
			}

		}

		return errMsg;
	}
	

	public boolean checkObjBeanDateTimeOverlap(List<Object> objBeans, Method getStartDateMethod, Method getEndDateMethod) throws Exception {

		// check overlap
		Date accumlatedStartTime = null;
		Date accumlatedEndTime = null;
		boolean overlapExist = false;
		for (Object objBean : objBeans) {

			if (accumlatedStartTime == null) {
				// first rebatedetailBean
				accumlatedStartTime = (Date) getStartDateMethod.invoke(objBean);
				accumlatedEndTime = (Date) getEndDateMethod.invoke(objBean);
			} else {
				Date startTime = (Date) getStartDateMethod.invoke(objBean);
				Date endTime = (Date) getEndDateMethod.invoke(objBean);
				// overlap exist: (StartA <= EndB) and (EndA >= StartB)
				if (endTime != null && accumlatedEndTime != null) {
					if (startTime.compareTo(accumlatedEndTime) <= 0 && endTime.compareTo(accumlatedStartTime) >= 0) {
						overlapExist = true;
						break;
					}
				} else if ((endTime == null && accumlatedEndTime == null) || (endTime == null && accumlatedEndTime.after(startTime))
						|| (accumlatedEndTime == null && endTime.after(accumlatedStartTime))) {
					overlapExist = true;
					break;
				} else {
					// no overlap, so update the accumulated start end time
					if (startTime.before(accumlatedStartTime)) {
						accumlatedStartTime = startTime;
					}
					if (endTime == null || (accumlatedEndTime != null && endTime.after(accumlatedEndTime))) {
						accumlatedEndTime = endTime;
					}
				}
			}
		}

		return overlapExist;
	}

}
