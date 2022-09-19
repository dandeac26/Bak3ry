package prj.pt.assignment4.PresentationLayer;

import javafx.collections.FXCollections;
import javafx.css.Match;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import prj.pt.assignment4.Application;
import prj.pt.assignment4.BusinessLayer.MenuItem;
import prj.pt.assignment4.BusinessLayer.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ClientScene implements Initializable, Observer<IDeliveryService> {

    @FXML
    private Button clientLogOut;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalPrice;
    @FXML
    private ListView<String> productList = new ListView<String>();
    @FXML
    private ListView<String> orderListView;// = new ListView<String>();
    @FXML
    private ChoiceBox<String> choiceBox;

    private IDeliveryService deliveryService;
    private List<BaseProduct> baseProductsList;

    private List<String> orderList = new ArrayList<>();
    private List<BaseProduct> myList = new ArrayList<>();
    //private static Integer clientID;
    private static Integer orderNumber = 0;

    private Integer total;

    public ClientScene() {
        total = 0;
        Random random = new Random();
        //clientID = deliveryService.getClientID();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll("Rating", "Rating asc", "Calories asc", "Calories desc", "Price asc", "Price desc");

    }

    public void initDeliveryService(IDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        deliveryService.addObserver(this);
        this.baseProductsList = deliveryService.getMenuItems();
    }

    public void startSearch(ActionEvent event) throws IOException {
        myList = findProduct(searchField.getText());
        searchMyList();
    }

    public void searchMyList() throws IOException {
        if (!myList.isEmpty()) {
            Set<String> nameSet = new HashSet<>();
            for (BaseProduct baseProduct : myList) {
                if (baseProduct != null)
                    nameSet.add(baseProduct.getTitle());
            }
            productList.setItems(FXCollections.observableArrayList(
                    nameSet));
        }
    }

    public List<BaseProduct> findProduct(final String name) {
        List<BaseProduct> test;

        test = baseProductsList.stream().map(p -> {
            if (p.getTitle().toUpperCase().contains(name.toUpperCase())) {
                return p;
            }
            return null;
        }).toList();
        boolean hasItem = false;
        for (BaseProduct baseProduct : test) {
            if (baseProduct != null)
                hasItem = true;
        }
        if (!hasItem) {
            test = baseProductsList.stream().map(p -> {
                String[] title = name.split(" ");
                for (String s : title) {
                    if (p.getTitle().toUpperCase().contains(s.toUpperCase())) {
                        return p;
                    }
                }
                return null;
            }).toList();
        }
        return test;
    }

    public void clientLoggingOut(ActionEvent event) throws IOException {
        Application app = new Application();
        app.changeScene("hello-view.fxml", 280, 600);
    }

    public void addingItemToOrder(ActionEvent event) throws IOException {
        String name = productList.getSelectionModel().getSelectedItem();
        String[] s = new String[3];
        if (name == null || name.equals("") || productList.getItems().isEmpty())
            return;
        for (BaseProduct b : myList
        ) {
            if (b != null) {
                if (name.contains("[")) {
                    s = name.split("->");
                }

                if (s[0] != null && s[0].equals(b.getTitle()) || b.getTitle().equals(name)) {
                    total += b.getPrice();
                }
            }

        }
        orderList.add(name);
        //orderListView = new ListView<String>();
        if (orderList != null || !orderList.isEmpty())
            orderListView.setItems(FXCollections.observableArrayList(
                    orderList));
        totalPrice.setText(total.toString() + "$");
    }

    public void clearContent(ActionEvent event) throws IOException {
        orderListView.getItems().clear();
        //orderListView = new ListView<>();
        orderList.clear();
        total = 0;
        totalPrice.setText("");
    }

    public void sortProductList(ActionEvent event) throws IOException {
        if (choiceBox == null || choiceBox.getValue() == null || choiceBox.getValue().isEmpty()) {
            return;
        }
        Set<BaseProduct> setList = new HashSet<>();
        for (BaseProduct baseProduct : myList) {
            if (baseProduct != null) {
                setList.add(baseProduct);
            }
        }
        List<BaseProduct> newList = new ArrayList<>();
        for (BaseProduct baseProduct : setList) {
            if (baseProduct != null) {
                newList.add(baseProduct);
            }
        }
        //myList = newList;
        if (newList != null && !newList.isEmpty()) {
            //System.out.println("ok");
            if (choiceBox.getValue().equals("Calories asc"))
                newList.sort(Comparator.comparingInt(BaseProduct::getCalories));
            else if (choiceBox.getValue().equals("Price asc"))
                newList.sort(Comparator.comparingInt(BaseProduct::getPrice));
            else if (choiceBox.getValue().equals("Calories desc"))
                newList.sort(Comparator.comparingInt(BaseProduct::getCalories).reversed());
            else if (choiceBox.getValue().equals("Price desc"))
                newList.sort(Comparator.comparingInt(BaseProduct::getPrice).reversed());
            else if (choiceBox.getValue().equals("Rating asc"))
                newList.sort(Comparator.comparingDouble(BaseProduct::getRating));
            else
                newList.sort(Comparator.comparingDouble(BaseProduct::getRating).reversed());

            /// Set<String> nameSet = new HashSet<>();
            List<String> nameSet = new ArrayList<>();
            for (BaseProduct baseProduct : newList) {
                if (baseProduct != null)
                    if (choiceBox.getValue().equals("Price desc") || choiceBox.getValue().equals("Price asc"))
                        nameSet.add(baseProduct.getTitle() + "->[Price:" + baseProduct.getPrice().toString() + "]");
                    else if (choiceBox.getValue().equals("Rating") || choiceBox.getValue().equals("Rating asc"))
                        nameSet.add(baseProduct.getTitle() + "->[Rating:" + baseProduct.getRating().toString() + "]");
                    else if (choiceBox.getValue().equals("Calories asc") || choiceBox.getValue().equals("Calories desc"))
                        nameSet.add(baseProduct.getTitle() + "->[Cal:" + baseProduct.getCalories().toString() + "]");
            }
            productList.setItems(FXCollections.observableArrayList(
                    nameSet));
        }
    }

    public void submitOrder(ActionEvent event) throws IOException {
        if (orderListView == null || orderListView.getItems().isEmpty()) {
            totalPrice.setText(total.toString() + "$ No items present!");
        } else {
            boolean ok = true;
            totalPrice.setText(total.toString() + "$ Order processed.");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String dateTime = formatter.format(date);

            try {
                CreateBill cb = new CreateBill(orderList, total + "$", date);
                System.out.println(deliveryService.getClientID());
                Order order = new Order(orderNumber, deliveryService.getClientID(), new Date());
                orderNumber++;
                List<MenuItem> menuItems = new ArrayList<>();
                String s[] = new String[3];
                for(String name : orderListView.getItems()){
                    for (BaseProduct base : myList
                    ) {
                        if (base != null) {
                            if (name.contains("[")) {
                                s = name.split("->");
                            }

                            if (s[0] != null && s[0].equals(base.getTitle()) || base.getTitle().equals(name)) {
                                menuItems.add(base);
                            }
                        }

                    }
                }
                deliveryService.addOrder(order, menuItems);
                cb.createBillFile();
                ok = cb.checkSate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ok) {
                Alert alert = new Alert(Alert.AlertType.NONE,
                        "Order sent successfully!\n" + "(" + dateTime + ")", ButtonType.OK);
                alert.setTitle("Success");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Order might still be processed!\n" + "(" + dateTime + ")", ButtonType.OK);
                alert.setTitle("Bill couldn't be created");
                alert.show();
            }
        }
    }

    @Override
    public void update(PropertyChanged<IDeliveryService> args) {

    }
}
