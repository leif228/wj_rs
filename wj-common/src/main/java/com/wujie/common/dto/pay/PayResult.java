/**
 * Copyright (C), 2016-2019, 广州航运电子商务有限公司
 * FileName: PayResult
 * Author:   Guoqiang
 * Date:     2019/2/20 上午9:07
 * Description: 支付结果消息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.dto.pay;

import java.io.Serializable;

/**
 * 支付结果消息
 *
 * @author Guoqiang
 * @since 2019/2/20
 * @version 1.0.0
 */
public class PayResult implements Serializable {
    private String orderId;//运单ID
    private String walletId;//订单ID
    private String payNo;//支付编号
    private String orderPayId;//任务ID
    private String result;//支付结果PayKhsdCode
    private String feeItem;//支付费项

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getOrderPayId() {
        return orderPayId;
    }

    public void setOrderPayId(String orderPayId) {
        this.orderPayId = orderPayId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFeeItem() {
        return feeItem;
    }

    public void setFeeItem(String feeItem) {
        this.feeItem = feeItem;
    }
}
