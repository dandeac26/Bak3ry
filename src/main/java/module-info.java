module prj.pt.assignment4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens prj.pt.assignment4 to javafx.fxml;
    exports prj.pt.assignment4;
    exports prj.pt.assignment4.PresentationLayer;
    opens prj.pt.assignment4.PresentationLayer to javafx.fxml;
}