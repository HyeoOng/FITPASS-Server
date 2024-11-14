package com.ssafy.fitpass.model.dto;

public class Friend {
    private int id, from, to, status;

    public Friend() {}
    public Friend(int from, int to, int status){
        this.from = from;
        this.to = to;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getFrom() {
        return from;
    }
    public void setFrom(int from) {
        this.from = from;
    }
    public int getTo() {
        return to;
    }
    public void setTo(int to) {
        this.to = to;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", status=" + status +
                '}';
    }
}
