package prj.pt.assignment4;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import prj.pt.assignment4.BusinessLayer.*;
import prj.pt.assignment4.PresentationLayer.AdminScene;
import prj.pt.assignment4.PresentationLayer.ClientScene;
import prj.pt.assignment4.PresentationLayer.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Application extends javafx.application.Application {
    private static Stage stage;
    private final IDeliveryService deliveryService = DeliveryService.getInstance();

    @Override
    public void start(Stage loginStage) throws IOException {
        stage = loginStage;
        loginStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Parent root = loader.load();

//        tester();

        Controller controller = loader.getController();
        controller.initDeliveryService(deliveryService);

        loginStage.setTitle("Bak3ry");
        loginStage.getIcons().add(new Image("file:src/main/resources/prj/pt/assignment4/Unsdftitled.png"));
        loginStage.setScene(new Scene(root, 600, 250));
        loginStage.show();
    }

    public void changeScene(String fxml, int height, int width) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource(fxml)));
        Parent pane = loader.load();

//        tester();

        if (fxml.equals("clientScene.fxml")) {
            ClientScene controller = loader.getController();
            controller.initDeliveryService(deliveryService);
        } else if (fxml.equals("hello-view.fxml")) {
            Controller controller = loader.getController();
            controller.initDeliveryService(deliveryService);
        } else if (fxml.equals("adminScene.fxml")) {
            AdminScene controller = loader.getController();
            controller.initDeliveryService(deliveryService);
        }

        stage.getScene().setRoot(pane);
        //stage.set
        stage.setHeight(height);
        stage.setWidth(width);
    }



    public static void main(String[] args) {
        launch();
    }
}