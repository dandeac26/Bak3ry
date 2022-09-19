package prj.pt.assignment4.PresentationLayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import prj.pt.assignment4.Application;
import prj.pt.assignment4.BusinessLayer.DeliveryService;
import prj.pt.assignment4.BusinessLayer.IDeliveryService;
import prj.pt.assignment4.BusinessLayer.PropertyChanged;
import prj.pt.assignment4.DataLayer.Login;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Controller implements Observer<IDeliveryService>{

    @FXML
    private Label loginErrorLable;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private static boolean employeeOnline = false;
    private IDeliveryService deliveryService;
    public Controller(){
        employeeOnline = false;

    }
    public void initDeliveryService(IDeliveryService deliveryService){
        this.deliveryService = deliveryService;
        deliveryService.addObserver(this);
    }

    public void loggingIn (ActionEvent event) throws IOException{
        String usr = usernameField.getText();
        String pass = passwordField.getText();
        Login l = new Login(usr,pass);
        List<Login> list = l.readData();
        if(list!=null){
            for (Login login : list) {
                if(login.getUsername().equals(usr) && login.getPassword().equals(pass)){
                    validateLogin(true);
                }
            }
        }
        validateLogin(false);
    }
    public void registerUser(ActionEvent event) throws IOException{
        String usr = usernameField.getText();
        String pass = passwordField.getText();
        if(!usr.isEmpty() && !pass.isEmpty()) {
            if(usr.length()>=4 && usr.length() <20 && pass.length()>=4 && pass.length()<20){
                Login l = new Login(usr, pass);
                l.writeData();
                loginErrorLable.setText("");
                Alert alert = new Alert(Alert.AlertType.NONE,
                        "Please log in.", ButtonType.OK);
                alert.setTitle("Registered");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.NONE,
                        "username and password need to be between 4 and 20 characters", ButtonType.OK);
                alert.setTitle("Registration failed");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.NONE,
                    "empty username/password field(s)", ButtonType.OK);
            alert.setTitle("Registration failed");
            alert.show();
        }
    }
    private void validateLogin(boolean granted) throws IOException {
        Application app = new Application();
        if(granted){
            deliveryService.setClientID(usernameField.getText());
            loginErrorLable.setText("Logging in...");
            app.changeScene("clientScene.fxml", 445, 740);

        }else if(usernameField.getText().equals("admin") &&
                passwordField.getText().equals("admin")){

            loginErrorLable.setText("Logging in...");
            app.changeScene("adminScene.fxml", 580,1050);
        }
        else if (usernameField.getText().isEmpty() && passwordField.getText().isEmpty())
                loginErrorLable.setText("Text fields are empty");

        else if (usernameField.getText().equals("employee") &&
                 passwordField.getText().equals("employee")){

            loginErrorLable.setText("Logged as employee");
            //app.changeScene("employeeScene.fxml", 450, 610);
            if(!employeeOnline){
                try {
                    createNewStage();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                employeeOnline = true;
            }

        }
        else if (usernameField.getText().equals("client") &&
                    passwordField.getText().equals("client")) {
            deliveryService.setClientID(usernameField.getText());
            loginErrorLable.setText("Logging in...");
            app.changeScene("clientScene.fxml", 445, 740);
        }
        else {
            loginErrorLable.setText("Invalid credentials");
        }
    }
    public void createNewStage() throws IOException {
        Stage stage = new Stage();
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("employeeScene.fxml"));//Application.class.getResource("employeeScene.fxml")); //Objects.requireNonNull(getClass().getResource("employeeScene.fxml"))
        Parent root = loader.load();

        EmployeeScene controller = loader.getController();
        controller.initDeliveryService(deliveryService);


        stage.setTitle("Bak3ry");
        stage.setScene(new Scene(root, 430, 406));
        stage.setX(90);
        stage.setY(250);
        stage.show();
    }

    @Override
    public void update(PropertyChanged<IDeliveryService> args) {

    }
}