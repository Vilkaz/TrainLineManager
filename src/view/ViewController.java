package view;

import controller.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.StationConnector;
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


    private void createStationOnMouseclick(MouseEvent event, StationConnector connector) {
        Pane stationCreator = StationController.getStationCreator();
        Button okButton = getOKButtonForStation(stationCreator, event, connector);
        stationCreator.getChildren().add(okButton);
        leftMenu.getChildren().add(stationCreator);
    }


    private void addOKButtonToTrainlineCreator(Pane trainlineCreator) {
        Button button = new Button("weiter");
        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                trainlineCreator.getStyleClass().add("disable");
                ContentController.addTrainLine(TrainLineController.getTrainlineByTrainlineCreator(trainlineCreator));
                getXYCoordinatesforStation(null);
            }
        });
        ((Pane) trainlineCreator).getChildren().add(button);
    }


    private void getXYCoordinatesforStation(StationConnector connector) {
        centerPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addAnotherStationToTheLine(event, connector);
            }
        });



    }

    private void addAnotherStationToTheLine(MouseEvent event, StationConnector connector) {
        disableCenterPaneMouseClickListener();
        disableCenterPaneMouseMoveListener();
        createStationOnMouseclick(event, connector);
    }

    private void disableCenterPaneMouseClickListener() {
        centerPane.setOnMouseClicked(null);
    }

    private void disableCenterPaneMouseMoveListener() {
        centerPane.setOnMouseMoved(null);
    }


    private Button getOKButtonForStation(Pane stationCreator, MouseEvent coordinatesEvent, StationConnector connector) {
        Button button = new Button("weiter");
        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                afterStationCreation(stationCreator, coordinatesEvent, connector);
            }
        });
        return button;
    }

    private void afterStationCreation(Pane stationCreator, MouseEvent coordinatesEvent, StationConnector connector) {
        TrainStation station = StationController.getStationByClick(stationCreator, coordinatesEvent, centerPane);
        ContentController.addStationToActualTrainLine(station);
        connector = mergeStationToConnector(connector, station);
        station.addConnector(connector);
        ContentController.setActiveConnector(connector);
        leftMenu.getChildren().remove(stationCreator);
        renderTrainPlan();
        disableCenterPaneMouseClickListener();
        nextStationOrEndLine();
    }


    /**
     * i can't move this function into "StationConnector" because it can be also null at this place, by very first run.
     * @param connector
     * @param station
     */
    private StationConnector mergeStationToConnector(StationConnector connector, TrainStation station) {
        if (connector == null) {
            connector = new StationConnector(station);
        } else {
            connector.addNextStation(station);
        }
        return connector;
    }


    private void nextStationOrEndLine() {
        Pane request = getNextStationRequest();
        centerPane.getChildren().add(request);
    }

    private Pane getNextStationRequest() {
        Text question = new Text("weitere Station oder Linienende?");
        Pane pane = new Pane();
        Button nextStation = getNextStationButton(pane);
        Button endLine = getEndLineButton(pane);
        HBox hBox = new HBox(nextStation, endLine);
        VBox stationRequest = new VBox(question, hBox);
        pane.getChildren().add(stationRequest);
        pane.getStyleClass().add("nextStationRequest");
        return pane;
    }

    private Button getNextStationButton(Pane pane) {
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

    private Button getEndLineButton(Pane pane) {
        Button button = new Button("Linie beenden");
        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ContentController.setActualStationAsEndstationInLine();
                removeNextStationRequest(pane);
                renderTrainPlan();
                ContentController.getActiveConnector();
            }
        });
        return button;
    }

    private void removeNextStationRequest(Pane pane) {
        centerPane.getChildren().remove(pane);
    }

    public void drawConnector(TrainStation station) {
        StationConnector connector = new StationConnector(station);
        station.addConnector(connector);
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

    public void renderTrainPlan() {
        centerPane.getChildren().clear();
        centerPane.getChildren().addAll(ContentController.getTrainPlan().getNodes());
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
