package com.wujie.common.dto.sqlite;

public class Room {
    private String RoomName;
    private String RoomNum;

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getRoomNum() {
        return RoomNum;
    }

    public void setRoomNum(String roomNum) {
        RoomNum = roomNum;
    }

    @Override
    public String toString() {
        return "Room{" +
                "RoomName='" + RoomName + '\'' +
                ", RoomNum='" + RoomNum + '\'' +
                '}';
    }
}
