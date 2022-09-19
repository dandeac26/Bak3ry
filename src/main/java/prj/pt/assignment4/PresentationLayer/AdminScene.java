package prj.pt.assignment4.PresentationLayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import prj.pt.assignment4.Application;
import prj.pt.assignment4.BusinessLayer.*;
import prj.pt.assignment4.BusinessLayer.MenuItem;

import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class AdminScene implements Observer<IDeliveryService> {

    @FXML
    private Button adminLogOut;
    @FXML
    private TextField productTitle;
    @FXML
    private TextField resourceTextField = new TextField();
    @FXML
    private Label successLabel = new Label();
    @FXML
    private ListView<BaseProduct> menuListView = new ListView<>();
    @FXML
    private TextField productRating;
    @FXML
    private TextField productCal;
    @FXML
    private TextField productSod;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productFat;
    @FXML
    private TextField productProt;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField startHour;
    @FXML
    private TextField endHour;
    @FXML
    private TextField orderCount;
    @FXML
    private TextField clientOrderCount;
    @FXML
    private TextField clientValueCount;
    @FXML
    private ListView<MenuItem> reportListView;
    @FXML
    private ListView<Integer> clientIdList;

    private IDeliveryService deliveryService;

    public AdminScene() {

    }

    public void updateMethod(ActionEvent event) {
        BaseProduct baseProduct = menuListView.getSelectionModel().getSelectedItem();
        deliveryService.removeBaseProduct(baseProduct);
        if(baseProduct!=null){
            baseProduct.setTitle(productTitle.getText());
            baseProduct.setCalories(Integer.parseInt(productCal.getText()));
            baseProduct.setRating(Double.parseDouble(productRating.getText()));
            baseProduct.setSodium(Integer.parseInt(productSod.getText()));
            baseProduct.setFat(Integer.parseInt(productFat.getText()));
            baseProduct.setPrice(Integer.parseInt(productPrice.getText()));
            baseProduct.setProtein(Integer.parseInt(productProt.getText()));
        }

        deliveryService.addBaseProduct(baseProduct);
        menuListView.getSelectionModel().clearSelection();
    }
    public void deleteMethod(ActionEvent event){
        BaseProduct baseProduct = menuListView.getSelectionModel().getSelectedItem();
        deliveryService.removeItem(baseProduct);
    }
    public void addMethod(ActionEvent event){
        BaseProduct baseProduct = new BaseProduct(
                productTitle.getText(),
                Double.parseDouble( productRating.getText()),
                Integer.parseInt(productCal.getText()),
                Integer.parseInt(productProt.getText()),
                Integer.parseInt(productFat.getText()),
                Integer.parseInt(productSod.getText()),
                Integer.parseInt(productPrice.getText())
        );
        deliveryService.addBaseProduct(baseProduct);
    }

    public void LoggingOut(ActionEvent event) throws IOException {
        Application app = new Application();
        app.changeScene("hello-view.fxml", 285, 610);
    }

    public void initDeliveryService(IDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        deliveryService.addObserver(this);
    }

    public void importCSV(ActionEvent event) {
        if (!resourceTextField.getText().isEmpty()) {
            deliveryService.setMenuItems(ReadCSVFile.getData(Path.of("src", "main", "resources", resourceTextField.getText())));
            successLabel.setText("Success!");

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Field is empty!\n", ButtonType.OK);
            alert.setTitle("Couldn't import file");
            alert.show();
        }
    }
    public void createReport1(ActionEvent event){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Set<MenuItem> result = new HashSet<>();
        Set<Order> shallowOrderList = deliveryService.getOrders().keySet();
        for(Order order : shallowOrderList){
            //System.out.println(formatter.format(order.getDate()) + " vs " + dateTextField.getText());
            if(formatter.format(order.getDate()).equals(dateTextField.getText())){
                result.addAll(deliveryService.getOrders().get(order));
            }
        }

        reportListView.setItems(FXCollections.observableArrayList(result));


    }
    public void createReport2(ActionEvent event){
        SimpleDateFormat formatter = new SimpleDateFormat("HH");

        Set<MenuItem> result = new HashSet<>();
        Set<Order> shallowOrderList = deliveryService.getOrders().keySet();
        for(Order order : shallowOrderList){
            //System.out.println(formatter.format(order.getDate()) + " vs " + dateTextField.getText());
            Integer hour = Integer.parseInt(formatter.format(order.getDate()));
            if( hour <= Integer.parseInt( endHour.getText()) &&
                    hour >= Integer.parseInt(startHour.getText()) ){
                result.addAll(deliveryService.getOrders().get(order));
            }
        }

        reportListView.setItems(FXCollections.observableArrayList(result));

    }
    public void createReport3(ActionEvent event){
        Set<MenuItem> result = new HashSet<>();
        Set<MenuItem> intermediar = new HashSet<>();
        List<MenuItem> resultList = new ArrayList<>();

        for(Order order : deliveryService.getOrders().keySet()){
            resultList.addAll(deliveryService.getOrders().get(order));
            intermediar.addAll(deliveryService.getOrders().get(order));
        }

        Map<MenuItem, Integer> counter = new HashMap<>();
        for(MenuItem mi : resultList){

            if(intermediar.contains(mi)){
                counter.merge(mi, 1, Integer::sum);
            }

        }
        for(MenuItem mi : counter.keySet()){
            if(counter.get(mi) >Integer.parseInt(orderCount.getText()))
              result.add(mi);
        }
        reportListView.setItems(FXCollections.observableArrayList(result));
    }
    public void createReport4(ActionEvent event){
        Set<Integer> result = new HashSet<>();
        Map<Integer, Integer> counter = new HashMap<>();
        Map<Integer, Integer> counter2 = new HashMap<>();
        for(Order order : deliveryService.getOrders().keySet()){
            counter.merge(order.getClientID(), 1, Integer::sum);
            counter2.merge(order.getClientID(), deliveryService.getOrderTotal(order), Integer::sum);
        }
        for(Integer i : counter.keySet()){
            if(counter.get(i) > Integer.parseInt(clientOrderCount.getText()) && counter2.get(i) >= Integer.parseInt( clientValueCount.getText())){
                result.add(i);
            }
        }
        clientIdList.setItems(FXCollections.observableArrayList(result));
    }


    @Override
    public void update(PropertyChanged<IDeliveryService> args) {
        menuListView.setItems(FXCollections.observableArrayList(deliveryService.getMenuItems()));
        menuListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BaseProduct>() {
            @Override
            public void changed(ObservableValue<? extends BaseProduct> observable, BaseProduct oldValue, BaseProduct newValue) {
                if (newValue != null) {
                    productTitle.setText(newValue.getTitle());
                    productRating.setText(newValue.getRating().toString());
                    productCal.setText(newValue.getCalories().toString());
                    productProt.setText(newValue.getProtein().toString());
                    productSod.setText(newValue.getSodium().toString());
                    productFat.setText(newValue.getFat().toString());
                    productPrice.setText(newValue.getPrice().toString());
                }
            }
        });
    }
}
