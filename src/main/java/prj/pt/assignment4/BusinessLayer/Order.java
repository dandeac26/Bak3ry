package prj.pt.assignment4.BusinessLayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Order {
    private Integer orderID;
    private Integer clientID;
    private Date date;

    public Order(Integer orderID, Integer clientID, Date date) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.date = date;
    }
    public Order(){}

    public int hashCode(){

        return 0;
    }
    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", clientID=" + clientID +
                ", date=" + date +
                '}';
    }
}
