package com.wujie.common.dto.pay;

import com.wujie.common.dto.BaseData;
import com.wujie.common.enums.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScfWalletData extends BaseData {
	private static final long serialVersionUID = 1L;

	private String id; // 主键id

	private SettleStyleCode settleStyle = null; // 帐务类型
	private String paymentNo = ""; // 支付序列号：客户号10位＋日期8位＋序列号8位

	private FeeItemCode feeItem = null; // 费项类型
	private String orderId; // 订单ID

	private String buyerId; //买家【付款方】
	private ScfUserData buyerData = null; // 买家
	private String sndAccountName = ""; // 付款帐户名
	private String sndCardNo = ""; // 付款帐号
	private ScfRoleCode buyerRole = ScfRoleCode.cargo; //付款方角色

	private String brokerId; // 中间人ID
	private ScfUserData brokerData = null; // 中间人
	private String brokerAccountName = ""; // 收款帐户名
	private String brokerCardNo = ""; // 收款帐号

	private String sellerId; // 卖家【收款方】
	private ScfUserData sellerData = null; // 卖家
	private String rcvAccountName = ""; // 收款帐户名
	private String rcvCardNo = ""; // 收款帐号

	private String subject = ""; // 交易标题
	private String body = ""; // 交易描述

	private Double totalFee = 0.00D; // 交易金额
	private Double middleFee = 0.00D; // 代理人佣金
	private Double serviceFee = 0.00D; // 平台服务费
	private Double handFee = 0.00D; // 提现手续费

	private String totalFeeChinese = ""; // 交易金额中文
	private String middleFeeChinese = ""; // 代理人佣金中文
	private String serviceFeeChinese = ""; // 平台服务费中文
	private String handFeeChinese = ""; // 提现手续费中文

	private PayStatusCode paymentStatus = null; // 支付状态
	private String gmtPayment = ""; // 支付时间

	private Integer suretyDays = 0; // 资金托管天数
	private String gmtSurety = ""; // 资金托管时间

	private ApplyReplyCode refundStatus = null; // 退款状态
	private String gmtRefund = ""; // 退款时间

	private ScfYesNoCode khFlag = ScfYesNoCode.no; // 是否跨行收单
	private String bankCardNo4 = ""; // 银行卡号后4位
	private String plantBankName = ""; // 银行名称

	private ScfYesNoCode khRefundFlag = ScfYesNoCode.no; // yes:跨行支付等待中,no:缺省

	private ScfYesNoCode errorStatus = ScfYesNoCode.no;
	private List<WalletZjjzData> walletZjjzDatas = null;
	private ScfYesNoCode khErrorStatus = ScfYesNoCode.no;
	private List<WalletKhsdData> walletKhsdDatas = null;

	// 当前用户可进行的操作
	private Map<String, Boolean> ops = new HashMap<String, Boolean>();

	private Map<String, String> temps = new HashMap<String, String>();

	private ScfPayModeCode payMode = ScfPayModeCode.unknown; //支付方式
	private String callbackUrl = ""; //回调地址
	private String projectId = ""; //业务项目ID
	private String aeskey = ""; //DES_KEY

	public ScfWalletData() {
		super();
	}

	@SuppressWarnings("unchecked")
	public ScfWalletData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = (String) params.get("id");
			if (params.get("settleStyle") != null)
				this.settleStyle = SettleStyleCode.valueOf((String) params.get("settleStyle"));
			this.paymentNo = (String) params.get("paymentNo");
			if (params.get("feeItem") != null)
				this.feeItem = FeeItemCode.valueOf((String) params.get("feeItem"));
			this.orderId = (String) params.get("orderId");
			this.buyerId = (String) params.get("buyerId");
			if (params.get("buyerRole") != null)
				this.buyerRole = ScfRoleCode.valueOf((String) params.get("buyerRole"));
			this.buyerData = new ScfUserData((Map<String, Object>) params.get("buyerData"));
			this.sndAccountName = (String) params.get("sndAccountName");
			this.sndCardNo = (String) params.get("sndCardNo");
			this.brokerId = (String) params.get("brokerId");
			this.brokerData = new ScfUserData((Map<String, Object>) params.get("brokerData"));
			this.brokerAccountName = (String) params.get("brokerAccountName");
			this.brokerCardNo = (String) params.get("brokerCardNo");
			this.sellerId = (String) params.get("sellerId");
			this.sellerData = new ScfUserData((Map<String, Object>) params.get("sellerData"));
			this.rcvAccountName = (String) params.get("rcvAccountName");
			this.rcvCardNo = (String) params.get("rcvCardNo");
			this.subject = (String) params.get("subject");
			this.body = (String) params.get("body");
			this.totalFee = (Double) params.get("totalFee");
			this.middleFee = (Double) params.get("middleFee");
			this.serviceFee = (Double) params.get("serviceFee");
			this.handFee = (Double) params.get("handFee");
			this.totalFeeChinese = (String) params.get("totalFeeChinese");
			this.middleFeeChinese = (String) params.get("middleFeeChinese");
			this.serviceFeeChinese = (String) params.get("serviceFeeChinese");
			this.handFeeChinese = (String) params.get("handFeeChinese");
			if (params.get("paymentStatus") != null)
				this.paymentStatus = PayStatusCode.valueOf((String) params.get("paymentStatus"));
			this.gmtPayment = (String) params.get("gmtPayment");
			this.suretyDays = ((Double) params.get("suretyDays")).intValue();
			this.gmtSurety = (String) params.get("gmtSurety");
			if (params.get("refundStatus") != null)
				this.refundStatus = ApplyReplyCode.valueOf((String) params.get("refundStatus"));
			this.gmtRefund = (String) params.get("gmtRefund");
			if (params.get("khFlag") != null)
				this.khFlag = ScfYesNoCode.valueOf((String) params.get("khFlag"));
			this.bankCardNo4 = (String) params.get("bankCardNo4");
			this.plantBankName = (String) params.get("plantBankName");
			if (params.get("khRefundFlag") != null)
				this.khRefundFlag = ScfYesNoCode.valueOf((String) params.get("khRefundFlag"));
			if (params.get("errorStatus") != null)
				this.errorStatus = ScfYesNoCode.valueOf((String) params.get("errorStatus"));
			this.walletZjjzDatas = new ArrayList<>();
			List<Map<String, Object>> walletZjjzDatasMap = (List<Map<String, Object>>) params.get("walletZjjzDatas");
			if (walletZjjzDatasMap != null && walletZjjzDatasMap.size() > 0) {
				for (Map<String, Object> map : walletZjjzDatasMap) {
					WalletZjjzData data = new WalletZjjzData(map);
					this.walletZjjzDatas.add(data);
				}
			}
			if (params.get("khErrorStatus") != null)
				this.khErrorStatus = ScfYesNoCode.valueOf((String) params.get("khErrorStatus"));
			this.walletKhsdDatas = new ArrayList<>();
			List<Map<String, Object>> walletKhsdDatasMap = (List<Map<String, Object>>) params.get("walletKhsdDatas");
			if (walletKhsdDatasMap != null && walletKhsdDatasMap.size() > 0) {
				for (Map<String, Object> map : walletKhsdDatasMap) {
					WalletKhsdData data = new WalletKhsdData(map);
					this.walletKhsdDatas.add(data);
				}
			}
			
			this.ops = (Map<String, Boolean>) params.get("ops");

			this.temps = (Map<String, String>) params.get("temps");			

			this.payMode = ScfPayModeCode.valueOf((String) params.get("payMode"));
			this.callbackUrl = (String) params.get("callbackUrl");
			this.projectId = (String) params.get("projectId");
			this.aeskey = (String) params.get("aeskey");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SettleStyleCode getSettleStyle() {
		return settleStyle;
	}

	public void setSettleStyle(SettleStyleCode settleStyle) {
		this.settleStyle = settleStyle;
	}

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public ScfUserData getBuyerData() {
		return buyerData;
	}

	public void setBuyerData(ScfUserData buyerData) {
		this.buyerData = buyerData;
	}

	public String getSndAccountName() {
		return sndAccountName;
	}

	public void setSndAccountName(String sndAccountName) {
		this.sndAccountName = sndAccountName;
	}

	public String getSndCardNo() {
		return sndCardNo;
	}

	public void setSndCardNo(String sndCardNo) {
		this.sndCardNo = sndCardNo;
	}

	public String getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}

	public ScfUserData getBrokerData() {
		return brokerData;
	}

	public void setBrokerData(ScfUserData brokerData) {
		this.brokerData = brokerData;
	}

	public String getBrokerAccountName() {
		return brokerAccountName;
	}

	public void setBrokerAccountName(String brokerAccountName) {
		this.brokerAccountName = brokerAccountName;
	}

	public String getBrokerCardNo() {
		return brokerCardNo;
	}

	public void setBrokerCardNo(String brokerCardNo) {
		this.brokerCardNo = brokerCardNo;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public ScfUserData getSellerData() {
		return sellerData;
	}

	public void setSellerData(ScfUserData sellerData) {
		this.sellerData = sellerData;
	}

	public String getRcvAccountName() {
		return rcvAccountName;
	}

	public void setRcvAccountName(String rcvAccountName) {
		this.rcvAccountName = rcvAccountName;
	}

	public String getRcvCardNo() {
		return rcvCardNo;
	}

	public void setRcvCardNo(String rcvCardNo) {
		this.rcvCardNo = rcvCardNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Double getMiddleFee() {
		return middleFee;
	}

	public void setMiddleFee(Double middleFee) {
		this.middleFee = middleFee;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Double getHandFee() {
		return handFee;
	}

	public void setHandFee(Double handFee) {
		this.handFee = handFee;
	}

	public String getTotalFeeChinese() {
		return totalFeeChinese;
	}

	public void setTotalFeeChinese(String totalFeeChinese) {
		this.totalFeeChinese = totalFeeChinese;
	}

	public String getMiddleFeeChinese() {
		return middleFeeChinese;
	}

	public void setMiddleFeeChinese(String middleFeeChinese) {
		this.middleFeeChinese = middleFeeChinese;
	}

	public String getServiceFeeChinese() {
		return serviceFeeChinese;
	}

	public void setServiceFeeChinese(String serviceFeeChinese) {
		this.serviceFeeChinese = serviceFeeChinese;
	}

	public String getHandFeeChinese() {
		return handFeeChinese;
	}

	public void setHandFeeChinese(String handFeeChinese) {
		this.handFeeChinese = handFeeChinese;
	}

	public PayStatusCode getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PayStatusCode paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public Integer getSuretyDays() {
		return suretyDays;
	}

	public void setSuretyDays(Integer suretyDays) {
		this.suretyDays = suretyDays;
	}

	public String getGmtSurety() {
		return gmtSurety;
	}

	public void setGmtSurety(String gmtSurety) {
		this.gmtSurety = gmtSurety;
	}

	public ApplyReplyCode getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(ApplyReplyCode refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getGmtRefund() {
		return gmtRefund;
	}

	public void setGmtRefund(String gmtRefund) {
		this.gmtRefund = gmtRefund;
	}

	public ScfYesNoCode getKhFlag() {
		return khFlag;
	}

	public void setKhFlag(ScfYesNoCode khFlag) {
		this.khFlag = khFlag;
	}

	public String getBankCardNo4() {
		return bankCardNo4;
	}

	public void setBankCardNo4(String bankCardNo4) {
		this.bankCardNo4 = bankCardNo4;
	}

	public String getPlantBankName() {
		return plantBankName;
	}

	public void setPlantBankName(String plantBankName) {
		this.plantBankName = plantBankName;
	}

	public ScfYesNoCode getKhRefundFlag() {
		return khRefundFlag;
	}

	public void setKhRefundFlag(ScfYesNoCode khRefundFlag) {
		this.khRefundFlag = khRefundFlag;
	}

	public ScfYesNoCode getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(ScfYesNoCode errorStatus) {
		this.errorStatus = errorStatus;
	}

	public List<WalletZjjzData> getWalletZjjzDatas() {
		return walletZjjzDatas;
	}

	public void setWalletZjjzDatas(List<WalletZjjzData> walletZjjzDatas) {
		this.walletZjjzDatas = walletZjjzDatas;
	}

	public ScfYesNoCode getKhErrorStatus() {
		return khErrorStatus;
	}

	public void setKhErrorStatus(ScfYesNoCode khErrorStatus) {
		this.khErrorStatus = khErrorStatus;
	}

	public List<WalletKhsdData> getWalletKhsdDatas() {
		return walletKhsdDatas;
	}

	public void setWalletKhsdDatas(List<WalletKhsdData> walletKhsdDatas) {
		this.walletKhsdDatas = walletKhsdDatas;
	}

	public Map<String, Boolean> getOps() {
		return ops;
	}

	public void setOps(Map<String, Boolean> ops) {
		this.ops = ops;
	}

	public Map<String, String> getTemps() {
		return temps;
	}

	public void setTemps(Map<String, String> temps) {
		this.temps = temps;
	}

	public ScfPayModeCode getPayMode() {
		return payMode;
	}

	public void setPayMode(ScfPayModeCode payMode) {
		this.payMode = payMode;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getAeskey() {
		return aeskey;
	}

	public void setAeskey(String aeskey) {
		this.aeskey = aeskey;
	}

	public ScfRoleCode getBuyerRole() {
		return buyerRole;
	}

	public void setBuyerRole(ScfRoleCode buyerRole) {
		this.buyerRole = buyerRole;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*public void setDescByLoginUser(ScfUserData userData) {
		Long userId = userData.getId();
		ScfAccountData zjjzAccountData = userData.getAccountData();

		String fdt[] = CalendarUtil.toHumanFormat(CalendarUtil.parseYYYY_MM_DD_HH_MM_SS(gmtPayment)).split(" ");
		String payDate = fdt[0];
		String payTime = fdt[1];
		String bankIcon = "";
		String bankIconlng = "";
		String tradeDesc = "";
		String bankDesc = "";
		String statusDesc = "";

		switch (this.settleStyle) {
		case fill:
			tradeDesc += "用户充值 ¥:" + this.totalFee + "元。";
			if (this.paymentStatus == PayStatusCode.TRADE_ING && this.khRefundFlag == ScfYesNoCode.yes)
				statusDesc += "充值中...";
			else
				statusDesc += "" + this.paymentStatus.getRemark2();
			break;
		case fetch:
			if (this.plantBankName != null && this.plantBankName.indexOf("平安银行") >= 0) {
				this.handFee = 0D;
				tradeDesc += "用户提现" + this.totalFee + "元，免提现手续费。";
			} else {
				tradeDesc += "用户提现 ¥:" + this.totalFee + "元。（手续费 ¥:" + this.handFee + "元）";
			}
			this.handFeeChinese = CapitalizednumbersUtil.convertToChineseNumber(this.totalFee + this.handFee);

			statusDesc += "" + this.paymentStatus.getRemark();
			break;
		case pay:
			if (userId.equals(this.buyerId)) {
				if (FeeItemCode.bona == this.feeItem)
					tradeDesc += "针对"+this.orderId+"号运单，作为" + this.buyerRole.getDescription() + "交纳成约金 ¥:" + this.totalFee + "元。（手续费 ¥:"+this.serviceFee+"元）";
				else if (FeeItemCode.guarantee == this.feeItem)
					tradeDesc += "针对"+this.orderId+"号运单，作为" + ScfRoleCode.cargo.getDescription() + "交纳担保金 ¥:" + this.totalFee + "元。";
				else if (FeeItemCode.charges == this.feeItem)
					tradeDesc += "针对"+this.orderId+"号运单，作为" + ScfRoleCode.ship.getDescription() + "交纳佣金 ¥:" + this.totalFee + "元。";
			}
			
			if (this.paymentStatus == PayStatusCode.TRADE_ING && this.khRefundFlag == ScfYesNoCode.yes) {
				statusDesc += "支付中...";
			} else {
				statusDesc += "" + this.paymentStatus.getDescription();
				if (this.paymentStatus == PayStatusCode.TRADE_BACK) {
					BigDecimal b1 = new BigDecimal(this.totalFee.toString());
					BigDecimal b2 = new BigDecimal(this.serviceFee.toString());
					statusDesc += "，退款金额 ¥:" + b1.subtract(b2).doubleValue() + "元";
				}
			}

			break;
		case collect:
			if (userId.equals(this.buyerId)) {
				tradeDesc += "资金归集，从支账户 转账 ¥:"+ this.totalFee + "元到 收账户。";
			}
			if (this.paymentStatus == PayStatusCode.TRADE_ING && this.khRefundFlag == ScfYesNoCode.yes) {
				statusDesc += "支付中...";
			} else {
				statusDesc += "" + this.paymentStatus.getDescription();
			}

			break;
		case refund:
			if (userId.equals(this.sellerId)) {
				tradeDesc += "平台退回成约金手续费 ¥:"+ this.totalFee + "元。";
			}
			if (this.paymentStatus == PayStatusCode.TRADE_FINISHED) {
				statusDesc += "退款成功";
			} else if (this.paymentStatus == PayStatusCode.TRADE_CLOSED) {
				statusDesc += "退款失败";
			} else if (this.paymentStatus == PayStatusCode.WAIT_PAYMENT) {
				statusDesc += "未退款";
			}

			break;
		case back:
			tradeDesc += "参与活动平台返现" + this.totalFee + "元。";
			if (this.paymentStatus == PayStatusCode.WAIT_PAYMENT && this.khRefundFlag == ScfYesNoCode.yes)
				statusDesc += "返现中...";
			else
				statusDesc += "" + this.paymentStatus.getRemark2();
			break;
		default:
			break;
		}

		if (this.paymentStatus != PayStatusCode.WAIT_PAYMENT) {
			if (this.plantBankName != null && !"".equals(this.plantBankName) && this.bankCardNo4 != null
					&& !"".equals(this.bankCardNo4)) {// 银行卡付
				bankDesc += this.plantBankName + "(*" + this.bankCardNo4 + ")";

				BankCode2 bc = BankCode2.getByName(this.plantBankName);
				if (bc != null) {
					bankIcon = bc.getIcon();
					bankIconlng = bc.getIconlng();
				}
			} else {// 钱包付
				this.plantBankName = "平安见证宝";
				this.bankCardNo4 = zjjzAccountData.getAccountNo()
						.substring(zjjzAccountData.getAccountNo().length() - 4);
				bankDesc += this.plantBankName + "(*" + this.bankCardNo4 + ")";

				bankIcon = "/img/bank2/pinganzjjz.jpg";
				bankIconlng = "/img/bank2/pinganzjjz.jpg";
			}
		} else {
			bankIcon = "/img/bank2/bank.png";
			bankIconlng = "/img/bank2/bank.png";
		}
		if ("".equals(bankIcon))
			;// 默认图标

		this.temps.put("payDate", payDate);
		this.temps.put("payTime", payTime);
		this.temps.put("bankIcon", bankIcon);
		this.temps.put("bankIconlng", bankIconlng);
		this.temps.put("tradeDesc", tradeDesc);
		this.temps.put("bankDesc", bankDesc);
		this.temps.put("statusDesc", statusDesc);
	}*/

}
