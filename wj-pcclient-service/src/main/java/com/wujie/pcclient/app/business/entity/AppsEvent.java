package com.wujie.pcclient.app.business.entity;

public class AppsEvent {
    //
    private Long id;

    //
    private String event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }
}