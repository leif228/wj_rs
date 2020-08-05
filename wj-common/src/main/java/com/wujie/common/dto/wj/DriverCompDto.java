package com.wujie.common.dto.wj;

public class DriverCompDto {
    //
    private Long id;

    //
    private String compCn;

    //
    private String compEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompCn() {
        return compCn;
    }

    public void setCompCn(String compCn) {
        this.compCn = compCn == null ? null : compCn.trim();
    }

    public String getCompEn() {
        return compEn;
    }

    public void setCompEn(String compEn) {
        this.compEn = compEn == null ? null : compEn.trim();
    }
}