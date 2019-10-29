package com.henyep.ib.terminal.server.dto.mt4.response;




import com.google.gson.annotations.SerializedName;
import com.henyep.ib.terminal.server.dto.mt4.model.UserProfileRecord;


public class GetUserProfileResponse extends BaseResponseModel {

	@SerializedName("Model")
	private UserProfileRecord model;

	public UserProfileRecord getModel() {
		return model;
	}

	public void setModel(UserProfileRecord model) {
		this.model = model;
	}

}
