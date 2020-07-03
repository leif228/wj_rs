package com.wujie.common.dto.order;

import com.wujie.common.enums.OrderPayStatus;
import com.wujie.common.enums.ScfRoleCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 运单支付任务DTO
 */
public class PayTaskDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 任务ID */
	private String taskId;
	/** 运单ID */
	private String orderId;
	/** 付款人ID */
	private String payerId;
	/** 付款人角色 */
	private ScfRoleCode payerRole;
	/** 付款人 */
	private String payer;
	/** 收款人ID */
	private String payeeId;
	/** 收款人 */
	private String payee;
	/** 实际装载吨数 */
	private Double realTons;
	/** 实际运费；约定运费 */
	private Double realCost;
	/** 滞期费率 */
	private Double demurrage;
	/** 滞期时间 */
	private Integer demurrageTime;
	/** 码头单证 */
	private String fileUrl;
	/** 总费用；待支付运费 */
	private Double totalCost;
	/** APP显示 */
	private String appShow;
	/** 推送时间 */
	private Date pushTime;
	/** 反馈时间 */
	private Date feedbackTime;
	/** 支付状态 */
	private OrderPayStatus payStatus;
	/** 任务状态；枚举OrderTaskStatus */
	private Integer taskStatus;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public ScfRoleCode getPayerRole() {
		return payerRole;
	}

	public void setPayerRole(ScfRoleCode payerRole) {
		this.payerRole = payerRole;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public Double getRealTons() {
		return realTons;
	}

	public void setRealTons(Double realTons) {
		this.realTons = realTons;
	}

	public Double getRealCost() {
		return realCost;
	}

	public void setRealCost(Double realCost) {
		this.realCost = realCost;
	}

	public Double getDemurrage() {
		return demurrage;
	}

	public void setDemurrage(Double demurrage) {
		this.demurrage = demurrage;
	}

	public Integer getDemurrageTime() {
		return demurrageTime;
	}

	public void setDemurrageTime(Integer demurrageTime) {
		this.demurrageTime = demurrageTime;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public String getAppShow() {
		return appShow;
	}

	public void setAppShow(String appShow) {
		this.appShow = appShow;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Date getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public OrderPayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(OrderPayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

}
