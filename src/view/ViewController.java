package view;

import controller.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.LineConnector;
import model.TrainPlan;
import model.TrainStation;

public class ViewController {


    @FXML
    Button addNewLineBtn;

    @FXML
    RadioMenuItem clientViewRBtn, adminViewRBtn;

    @FXML
    VBox leftMenu;

    @FXML
    private static VBox mainVBox, leftSide;

    @FXML
    Pane centerPane;

    private static TrainPlan trainPlan;


    @FXML
    private void activateClientView() {
        ClientViewController.activateClientView(leftMenu);

    }

    @FXML
    private void activateAdminView() {
        AdminViewController.loadAdminView(leftMenu);
    }

    @FXML
    private void addNewLine() {
        Pane trainlineCreator = TrainLineController.getteTrainLineCreator(leftMenu);
        /**
         * the "next" button has to manipulate viewControllers variables, which i cant make static, because
         * they would be null else...
         */
        addOKButtonToTrainlineCreator(trainlineCreator);
    }


    private void createStationOnMouseclick(MouseEvent event) {
        Pane stationCreator = StationController.getStationCreator();
        Button okButton = getOKButtonForStation(stationCreator, event);
        stationCreator.getChildren().add(okButton);
        leftMenu.getChildren().add(stationCreator);
    }


    private void addOKButtonToTrainlineCreator(Pane trainlineCreator) {
        Button button = new Button("weiter");
        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                leftMenu.getChildren().remove(trainlineCreator);
                ContentController.addTrainLine(TrainLineController.getTrainlineByTrainlineCreator(trainlineCreator));
                getXYCoordinatesforStation(null);
            }
        });
        ((Pane) trainlineCreator).getChildren().add(button);
    }


    private void getXYCoordinatesforStation(LineConnector connector) {
        centerPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                disableCenterPaneMouseClickListener();
                disableCenterPaneMouseMoveListener();
                createStationOnMouseclick(event);
            }
        });
    }

    private void disableCenterPaneMouseClickListener(){
        centerPane.setOnMouseClicked(null);
    }

    private void disableCenterPaneMouseMoveListener(){
        centerPane.setOnMouseMoved(null);
    }


    private Button getOKButtonForStation(Pane stationCreator, MouseEvent coordinatesEvent) {
        Button button = new Button("weiter");
        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                afterStationCreation(stationCreator, coordinatesEvent);
            }
        });
        return button;
    }

    private void afterStationCreation(Pane stationCreator, MouseEvent coordinatesEvent) {
        TrainStation station = StationController.getStationByClick(stationCreator, coordinatesEvent);
        ContentController.addStationToActualTrainLine(station);
        leftMenu.getChildren().remove(stationCreator);
        renderTrainPlan();
        disableCenterPaneMouseClickListener();
        nextStationOrEndLine();
    }


    private void nextStationOrEndLine(){
        Pane  request =   getNextStationRequest();
        double x = (centerPane.getWidth()/2) - request.getWidth() ;
        double y = (centerPane.getHeight()/2) - request.getHeight() ;
        centerPane.getChildren().add(request);
        request.setLayoutX(x);
        request.setLayoutY(y);
    }

    private Pane getNextStationRequest(){
        Text question = new Text("weitere Station oder Linienende?");
        Pane pane = new Pane();
        Button nextStation = getNextSTationButton(pane);
        Button endLine = getEndLineButton(pane);
        HBox hBox = new HBox(nextStation, endLine);
        VBox stationRequest = new VBox(question, hBox);
        pane.getChildren().add(stationRequest);
        pane.setPadding(new Insets(10));
        pane.getStyleClass().add("nextStationRequest");
        return pane;
    }

    private Button getNextSTationButton(Pane pane){
        Button button = new Button("weitere Station hinzufügen");
        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                disableCenterPaneMouseClickListener();
                removeNextStationRequest(pane);
                drawConnector(ContentController.getLastAdedStation());
            }
        });
        return button;
    }
    private Button getEndLineButton(Pane pane){
        Button button = new Button("Linie beenden");
        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeNextStationRequest(pane);
            }
        });
        return button;
    }

    private void removeNextStationRequest(Pane pane){
        centerPane.getChildren().remove(pane);
    }

    public void drawConnector(TrainStation station){
        LineConnector connector = new LineConnector(station);
        ContentController.addConnectorToActiveLine(connector);
        centerPane.getChildren().add(connector.getNode());
        centerPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                connector.setEndX(event.getX());
                connector.setEndY(event.getY());
            }
        });
        getXYCoordinatesforStation(connector);

    }

    public void renderTrainPlan(){
        centerPane.getChildren().clear();
        centerPane.getChildren().addAll(ContentController.getTrainPlan().getNodes());
        System.out.println("");
    }



    //region getter and setter
    public Pane getCenterPane() {
        return centerPane;
    }

    public static TrainPlan getTrainPlan() {
        return trainPlan;
    }


//region getter and setter


}
