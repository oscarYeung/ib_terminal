package com.henyep.ib.terminal.server.adapter;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.server.dto.mt4.request.BaseRequestModel;
import com.henyep.ib.terminal.server.dto.mt4.request.GetUserProfileRequest;
import com.henyep.ib.terminal.server.dto.mt4.response.GetUserProfileResponse;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.white.label.api.dto.request.user.GetUserProfileRequestDto;


@Component(value = "GetUserProfileServiceAdapter")
public class GetUserProfileServiceAdapter  extends AbstractMT4ServiceAdapter<GetUserProfileRequestDto, GetUserProfileResponse> {

	public GetUserProfileServiceAdapter() {
		super(GetUserProfileRequestDto.class, GetUserProfileResponse.class);
	}
	
	@Override
	protected BaseRequestModel getMT4RequestModel(String token, GetUserProfileRequestDto data) {

		GetUserProfileRequest request = new GetUserProfileRequest();
		request.setLogin(data.getLogin_id());
		return request;
	}

	@Override
	protected String getServiceLink() {
		return Constants.MT4_WEB_SERVICE_GET_USER_PROFILE_LINK;
	}




}