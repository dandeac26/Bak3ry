package prj.pt.assignment4.BusinessLayer;

import prj.pt.assignment4.PresentationLayer.Observer;

import java.util.List;
import java.util.Map;

public interface IDeliveryService {
    List<BaseProduct> getMenuItems();

    void addOrder(Order order, List<MenuItem> list);

    Map<Order, List<MenuItem>> getOrders();

    void addObserver(Observer<IDeliveryService> observer);

    Integer getOrderTotal(Order order);

    void removeOrder(Order order);

    void removeBaseProduct(BaseProduct baseProduct);

    void addBaseProduct(BaseProduct baseProduct);

    void setMenuItems(List<BaseProduct> list);

    void removeItem(BaseProduct baseProduct);

    void setClientID(String name);

    Integer getClientID();
}
