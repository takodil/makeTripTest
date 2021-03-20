package com.testtribe.e2etest.models;

public class GuestBO {
    public int roomNum;
    public int adultNum;
    public int[] childrenAge;

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public void setChildrenAge(int[] childrenAge) {
        this.childrenAge = childrenAge;
    }

    public void setAdultNum(int adultNum) {
        this.adultNum = adultNum;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public int getAdultNum() {
        return adultNum;
    }

    public int[] getChildrenAge() {
        return childrenAge;
    }
}
