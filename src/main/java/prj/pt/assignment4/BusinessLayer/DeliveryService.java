package prj.pt.assignment4.BusinessLayer;

import prj.pt.assignment4.PresentationLayer.Observer;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryService extends Observable<IDeliveryService> implements IDeliveryService {

    private List<BaseProduct> itemsInMenu;
    private final Map<Order, List<MenuItem>> map = new HashMap<>();
    private Integer clientID;

    private DeliveryService() {
        itemsInMenu = ReadCSVFile.getData(Path.of("src", "main", "resources", "products.csv"));
    }

    private static DeliveryService instance;

    /// make delivery service singleton
    public static DeliveryService getInstance() {
        if (instance == null) {
            instance = new DeliveryService();
        }
        return instance;
    }

    @Override
    public List<BaseProduct> getMenuItems() {
        return itemsInMenu;
    }

    @Override
    public void addOrder(Order order, List<MenuItem> list) {
        map.put(order, list);
        notifyObservers(this, "map", map);
    }

    @Override
    public Map<Order, List<MenuItem>> getOrders() {
        return new HashMap<>(map); // this is to return a shallow copy instead of the real map
    }

    @Override
    public void addObserver(Observer<IDeliveryService> observer) {
        super.addObserver(observer);
    }

    @Override
    public Integer getOrderTotal(Order order) {
        Integer sum = 0;
        for (MenuItem mi : map.get(order)) {
            sum += mi.getPrice();
        }
        return sum;
    }

    @Override
    public void removeOrder(Order order) {
        map.remove(order);
        notifyObservers(this, "map", map);
    }

    @Override
    public void removeBaseProduct(BaseProduct baseProduct) {
        itemsInMenu.remove(baseProduct);
//        notifyObservers(this, "itemsInMenu", itemsInMenu);
    }
    @Override
    public void removeItem(BaseProduct baseProduct) {
        itemsInMenu.remove(baseProduct);
        notifyObservers(this, "itemsInMenu", itemsInMenu);
    }

    @Override
    public void setClientID(String name) {
        clientID = name.length() + (int)name.toCharArray()[0];
    }

    @Override
    public Integer getClientID() {
        return clientID;
    }

    @Override
    public void addBaseProduct(BaseProduct baseProduct) {
        boolean duplicate = false;
        for (BaseProduct inMenu : itemsInMenu) {
            if(inMenu.getTitle().equals(baseProduct.getTitle())){
                duplicate=true;
                break;
            }
        }
        if(!duplicate){
            itemsInMenu.add(baseProduct);
            notifyObservers(this, "itemsInMenu", itemsInMenu);
        }

    }

    @Override
    public void setMenuItems(List<BaseProduct> list) {
        itemsInMenu = list;
        notifyObservers(this, "itemsInMenu", itemsInMenu);
    }
}
