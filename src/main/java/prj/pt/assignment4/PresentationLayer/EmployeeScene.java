package prj.pt.assignment4.PresentationLayer;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import prj.pt.assignment4.BusinessLayer.IDeliveryService;
import prj.pt.assignment4.BusinessLayer.MenuItem;
import prj.pt.assignment4.BusinessLayer.Order;
import prj.pt.assignment4.BusinessLayer.PropertyChanged;


import java.util.HashMap;

import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeScene implements Observer<IDeliveryService> {

    @FXML
    private ListView<String> staffOrderList;

    private Map<String, Order> deleter = new HashMap<>();
    private static Integer deleteID = 0;

    private IDeliveryService deliveryService;

    public EmployeeScene() {

    }

    public void initDeliveryService(IDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        deliveryService.addObserver(this);
    }

    public void orderFinished(ActionEvent event){
        String toRemove = staffOrderList.getSelectionModel().getSelectedItem();
        staffOrderList.getItems().remove(toRemove);
        deliveryService.removeOrder(deleter.get(toRemove));
    }
    public void showOrders(ActionEvent event){
        deliveryService.removeOrder(new Order());
    }

    @Override
    public void update(PropertyChanged<IDeliveryService> args) {
        //System.out.println(args.toString());
        staffOrderList.setItems(FXCollections.observableArrayList(
                deliveryService.getOrders().entrySet().stream()
                        .map(order -> {
                            String menuItems = order.getValue().stream()
                                    .map(MenuItem::getTitle)
                                    .collect(Collectors.joining("\n"));
                            String total = deliveryService.getOrderTotal(order.getKey()).toString();

                            String result = String.format("======Order %s:======\n%s\nTotal: %s$", order.getKey().getOrderID(), menuItems, total);

                            deleter.put(result, order.getKey());

                            return result;
                        })
                        .collect(Collectors.toList()))
        );

    }

}
