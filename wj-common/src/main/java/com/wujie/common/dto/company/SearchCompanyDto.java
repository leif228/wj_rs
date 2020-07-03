package com.wujie.common.dto.company;

import com.wujie.common.base.BaseEntity;

public class SearchCompanyDto extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 公司证件号 */
	private String ctCodeStr;
	
	//add
	/** 页码，从1开始  */
	private int pageNum = 1;
	/** 每页条数  */
	private int pageSize = 10;
	

	public String getCtCodeStr() {
		return ctCodeStr;
	}

	public void setCtCodeStr(String ctCodeStr) {
		this.ctCodeStr = ctCodeStr;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getMax() {
		if (this.pageSize <= 0) {
			pageSize = 10;
		}
		return pageSize;
	}

	public Integer getStart() {
		if (this.pageNum <= 0) {
			pageNum = 1;
		}
		return (pageNum-1) * getMax();
	}

}
