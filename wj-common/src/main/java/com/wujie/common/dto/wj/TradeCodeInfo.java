package com.wujie.common.dto.wj;

public class TradeCodeInfo {
    //
    private Long id;

    //
    private String class1st;

    //
    private String class2nd;

    //
    private String class3rd;

    //
    private String class4th;

    //
    private String tradeCode;

    //
    private String className;

    //
    private String explain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClass1st() {
        return class1st;
    }

    public void setClass1st(String class1st) {
        this.class1st = class1st == null ? null : class1st.trim();
    }

    public String getClass2nd() {
        return class2nd;
    }

    public void setClass2nd(String class2nd) {
        this.class2nd = class2nd == null ? null : class2nd.trim();
    }

    public String getClass3rd() {
        return class3rd;
    }

    public void setClass3rd(String class3rd) {
        this.class3rd = class3rd == null ? null : class3rd.trim();
    }

    public String getClass4th() {
        return class4th;
    }

    public void setClass4th(String class4th) {
        this.class4th = class4th == null ? null : class4th.trim();
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode == null ? null : tradeCode.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }
}