package com.ssafy.fitpass.friend;

public class Friend {
    private int id, fromUser, toUser, status;

    public Friend() {}
    public Friend(int fromUser, int toUser, int status){
        setFromUser(fromUser);
        setToUser(toUser);
        setStatus(status);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getFromUser() {
        return fromUser;
    }
    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }
    public int getToUSer() {
        return toUser;
    }
    public void setToUser(int toUser) {
        this.toUser = toUser;
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
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                ", status=" + status +
                '}';
    }
}
