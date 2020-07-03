package com.wujie.common.dto.pubPay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wujie.common.enums.*;
import com.wujie.common.utils.DESHelper;

import java.io.Serializable;
import java.util.Date;

/**
 * 收银台直接支付PubPayWalletDto
 */
public class PayWalletDto implements Serializable {
	private static final long serialVersionUID = 1L;

    
    /** 主键 */
    private String walletId;	
	/** 支付序列号 */
	private String paymentNo;
	/** 帐务类型 */
	private SettleStyleCode settleStyle;
	/** 费项类型 */
	private FeeItemCode feeItem;
	/** 付款方ID */
	private String buyerId;
	/** 付款人姓名 */
	private String buyer;
	/** 收款方ID */
	private String sellerId;
	/** 收款人姓名 */
	private String seller;
	/** 交易金额 */
	private Double totalFee;
	/** 交易描述 */
	private String body;
	/** 支付状态 */
	private PayStatusCode paymentStatus;
	/** 支付时间 */
	private Date gmtPayment;
	/** 退款状态 */
	private ApplyReplyCode refundStatus;
	/** yes:跨行支付等待中,no:缺省 */
	private ScfYesNoCode khRefundFlag;
    /** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 收款账户号 */
	private String rcvAccountNo;
	
	/** 收款账户名称 */
	private String rcvAccountName;
	
	/** 付款账户号 */
	private String sndAccountNo;
	
	/** 付款账户名称 */
	private String sndAccountName;

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public SettleStyleCode getSettleStyle() {
		return settleStyle;
	}

	public void setSettleStyle(SettleStyleCode settleStyle) {
		this.settleStyle = settleStyle;
	}

	public FeeItemCode getFeeItem() {
		return feeItem;
	}

	public void setFeeItem(FeeItemCode feeItem) {
		this.feeItem = feeItem;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public PayStatusCode getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PayStatusCode paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(Date gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public ApplyReplyCode getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(ApplyReplyCode refundStatus) {
		this.refundStatus = refundStatus;
	}

	public ScfYesNoCode getKhRefundFlag() {
		return khRefundFlag;
	}

	public void setKhRefundFlag(ScfYesNoCode khRefundFlag) {
		this.khRefundFlag = khRefundFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRcvAccountNo() {
		return DESHelper.DoDES(rcvAccountNo, "e5102y1025d0251925108", DESHelper.DECRYPT_MODE);
	}

	public void setRcvAccountNo(String rcvAccountNo) {
		this.rcvAccountNo = rcvAccountNo;
	}

	public String getRcvAccountName() {
		return rcvAccountName;
	}

	public void setRcvAccountName(String rcvAccountName) {
		this.rcvAccountName = rcvAccountName;
	}

	public String getSndAccountNo() {
		return DESHelper.DoDES(sndAccountNo, "e5102y1025d0251925108", DESHelper.DECRYPT_MODE);
	}

	public void setSndAccountNo(String sndAccountNo) {
		this.sndAccountNo = sndAccountNo;
	}

	public String getSndAccountName() {
		return sndAccountName;
	}

	public void setSndAccountName(String sndAccountName) {
		this.sndAccountName = sndAccountName;
	}

}
