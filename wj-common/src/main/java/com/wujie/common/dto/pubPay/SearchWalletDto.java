package com.wujie.common.dto.pubPay;

import com.wujie.common.base.BaseEntity;
import com.wujie.common.enums.ApplyReplyCode;
import com.wujie.common.enums.FeeItemCode;
import com.wujie.common.enums.PayStatusCode;
import com.wujie.common.enums.SettleStyleCode;

public class SearchWalletDto extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 支付序列号 */
	private String paymentNo;
	/** 费项类型 */
	private FeeItemCode feeItem;
	/** 帐务类型 */
	private SettleStyleCode settleStyle;
	/** 支付状态 */
	private PayStatusCode paymentStatus;
	/** 退款状态 */
	private ApplyReplyCode refundStatus;
	
	//add
	/** 页码，从1开始  */
	private int pageNum = 1;
	/** 每页条数  */
	private int pageSize = 10;

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public FeeItemCode getFeeItem() {
		return feeItem;
	}

	public void setFeeItem(FeeItemCode feeItem) {
		this.feeItem = feeItem;
	}

	public SettleStyleCode getSettleStyle() {
		return settleStyle;
	}

	public void setSettleStyle(SettleStyleCode settleStyle) {
		this.settleStyle = settleStyle;
	}

	public PayStatusCode getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PayStatusCode paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public ApplyReplyCode getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(ApplyReplyCode refundStatus) {
		this.refundStatus = refundStatus;
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
