package com.henyep.ib.terminal.api.dto.request.ib.tree;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;
import com.henyep.ib.terminal.api.global.Constants;

public class AddIbTreeRequestDto extends BaseDateTimeRequestBodyDto implements Serializable {

	private static final long serialVersionUID = -4082665944876678402L;

	@NotNull(message = Constants.ERR_COMMON_BRAND_CODE_IS_BLANK)
	private String brand_code;
	@NotNull(message = Constants.ERR_COMMON_IB_CODE_IS_BLANK)
	private String ib_code;
	@NotNull(message = Constants.ERR_IB_TREE_PARENT_ID_IS_NULL)
	private Integer parent_tree_id;
	@NotNull(message = Constants.ERR_IB_TREE_TEAM_IS_NULL)
	private String team;

	public String getBrand_code() {
		return brand_code;
	}

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public Integer getParent_tree_id() {
		return parent_tree_id;
	}

	public void setParent_tree_id(Integer parent_tree_id) {
		this.parent_tree_id = parent_tree_id;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

}
