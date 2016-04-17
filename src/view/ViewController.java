package view;

import controller.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

import java.util.ArrayList;

public class ViewController {


    @FXML
    Button addNewLineBtn, selectStartStationBtn, selectEndStationBtn;

    @FXML
    RadioMenuItem clientViewRBtn, adminViewRBtn;

    @FXML
    VBox leftMenu, ClientViewMainContainer, leftMenuMainContainer;

    @FXML
    private static VBox mainVBox, leftSide;

    @FXML
    Pane centerPane;

    @FXML
    Text ClientStartStationName,ClientEndStationName,priceTxt;

    private static TrainPlan trainPlan;


    @FXML
    private void activateClientView() {
        ClientViewController.activateClientView(leftMenu, ClientViewMainContainer, leftMenuMainContainer);
    }

    @FXML
    private void activateAdminView() {
        AdminViewController.loadAdminView(leftMenu, ClientViewMainContainer, leftMenuMainContainer);
        /**
         * man muss wieder alle Mouse Icon listener aktiwieren. an dieser stelle ist der workaround = einfach die datei neu laden.
         */

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

    @FXML
    private void saveTrainPlan() {
        ContentController.saveTrainPlan();
    }

    @FXML
    private void loadTrainPlan() {
        ContentController.loadTrainPlan();
        renderTrainPlan();
    }

    @FXML
    private void selectStartStation() {
        activateStationIconClientClickListener(true);
    }

    @FXML
    private void selectEndStation() {
        activateStationIconClientClickListener(false);
    }


    @FXML
    private void calculateRoute(){
        TrainStation startStation = ContentController.getStartStation();
        TrainStation endStation = ContentController.getEndStation();
        ArrayList<TrainStation> allStations = ContentController.getAllStations();
        System.out.println("berechne route von "+ startStation.getName() + " bis " + endStation.getName());
        ArrayList<TrainStation> route = RouteController.getRoute(startStation, endStation, allStations);
        for (TrainStation station:route){
            System.out.println(station.getName());
        }
        double price = RouteController.calculate_price(route);
        priceTxt.setText(String.valueOf(price));
    }
    private boolean addStationCreator = true;


    private void createStationOnMouseclick(MouseEvent event, StationConnector connector) {
        Pane stationCreator = StationController.getStationCreator();
        Button okButton = getOKButtonForStation(stationCreator, event, connector);
        stationCreator.getChildren().add(okButton);
        /**
         * if existing station is klicked on, then we dont want to add station creator to left side
         */
        if (addStationCreator) {
            leftMenu.getChildren().add(stationCreator);
        }
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


    private void getXYCoordinatesforStation(StationConnector connector) {
        activateCentralPaneClickListener(connector);
        ArrayList<TrainStation> stations = ContentController.getAllStations();
        activateStationIconClickListener(stations, connector);
    }

    private void activateStationIconClickListener(ArrayList<TrainStation> stations, StationConnector connector) {
        for (TrainStation station : stations) {
            station.getNode().toFront();
            station.getIcon().getNode().toFront();
            station.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    centerPane.getChildren().add(getAddExistingTrainStaionAsNeighborRequest(station, connector));
                    /**
                     * die click Funktion von dem centerpane sollte eigentlich von den StationIcon nodes
                     * überdeckt werden, da sie "toFront" hatten.
                     * das ist schmutziger "deadline kommt näher" zwischenhack ...
                     *
                     *17.04.2016
                     * p.s. glaube habe es behoben indem ich icon-getNode to fron gemacht habe.
                     * wer will darf es gerne testen
                     */
                    addStationCreator = false;
                }
            });
        }
    }

    private void activateStationIconClientClickListener(boolean startStation) {
        for (TrainStation station : ContentController.getAllStations()) {
            station.getIcon().getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ContentController.setSelectedStation(station);
                    System.out.println("client view listener !");
                    station.getIcon().getNode().setOnMouseClicked(null);
                    if (startStation){
                        ContentController.setStartStation(station);
                        ClientStartStationName.setText(station.getName());

                    } else {
                        ContentController.setEndStation(station);
                        ClientEndStationName.setText(station.getName());
                    }
                }
            });
        }
    }


    private Node getAddExistingTrainStaionAsNeighborRequest(TrainStation station, StationConnector connector) {
        VBox mainBox = TrainStationController.getAddExsistingTrainStaion(station);
        mainBox.getChildren().add(getExsistinStatoinRequestButtons(mainBox, station, connector));
        return mainBox;
    }

    private HBox getExsistinStatoinRequestButtons(VBox mainBox, TrainStation station, StationConnector connector) {
        Button okBtn = getAddExistingTrainStationOKButton(station, connector);
        Button noBtn = getAddExistingTrainStationNOButton(mainBox, connector);
        return new HBox(okBtn, noBtn);
    }

    private Button getAddExistingTrainStationOKButton(TrainStation station, StationConnector connector) {
        Button button = new Button("Ja");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addStationCreator = true;
                TrainStationController.disableAllStationClickListeners();
                finishAddStationToLineProcess(station, connector);
            }
        });
        return button;

    }

    private void removeStationCreator() {
        leftMenu.getChildren().remove(1);
    }

    private Button getAddExistingTrainStationNOButton(Node mainBox, StationConnector connector) {
        Button button = new Button("Nein");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addStationCreator = true;
                removeNodeFromCenterPane(connector.getNode());
                removeNodeFromCenterPane(mainBox);
                ContentController.removeActiveConnector();
                TrainStationController.disableAllStationClickListeners();
                nextStationOrEndLine();
            }
        });
        return button;
    }

    private void removeNodeFromCenterPane(Node node) {
        centerPane.getChildren().remove(node);
    }

    private void activateCentralPaneClickListener(final StationConnector connector) {
        centerPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addAnotherStationToTheLine(event, connector);
            }
        });
    }

    public void disableAllCenterPaneListeners() {
        disableCenterPaneMouseClickListener();
        disableCenterPaneMouseMoveListener();
        TrainStationController.disableAllStationClickListeners();
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
                centerPane.toBack();
                TrainStation station = StationController.getStationByClick(stationCreator, coordinatesEvent, centerPane);
                leftMenu.getChildren().remove(stationCreator);
                finishAddStationToLineProcess(station, connector);
            }
        });
        return button;
    }

    private void finishAddStationToLineProcess(TrainStation station, StationConnector connector) {
        ContentController.addStationToActualTrainLine(station);
        connector = mergeStationToConnector(connector, station);
        addConectorIfhesValide(station, connector);
        ContentController.setActiveConnector(connector);
        renderTrainPlan();
        disableCenterPaneMouseClickListener();
        nextStationOrEndLine();
    }


    /**
     * the verz first station of the line will have one invalid connector.
     * feel free to fix it !
     *
     * @param station
     * @param connector
     */
    private void addConectorIfhesValide(TrainStation station, StationConnector connector) {
        if (connector.getStation2() != null) {
            station.addConnector(connector);
        }
    }


    /**
     * i can't move this function into "StationConnector" because it can be also null at this place, by very first run.
     *
     * @param connector
     * @param station
     */
    private StationConnector mergeStationToConnector(StationConnector connector, TrainStation station) {
        /**
         * ich glaube hier ist die stelle, die den überflüssigen connector für erste
         * line produziert, um es abzufangen hab ich extra diese "isValide blabla"
         * methode geschrieben. bin mir aber nicht sicher, es ist nach
         * mitternacht ... mal wieder ...
         */
        if (connector == null) {
            connector = new StationConnector(station);
        } else {
            connector.addNextStation(station);
        }
        return connector;
    }


    private void nextStationOrEndLine() {
        TrainStationController.disableAllStationClickListeners();
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
        ArrayList<Node> nodes = ContentController.getTrainPlan().getNodes();
/**
 * falls die selbe station in mehreren linien vorhanden ist, wird versucht eine node doppelt
 * einzufügen was zu einer exception führt. diese Methode filters es vorher aus
 */
        for (Node node : nodes) {
            if (!centerPane.getChildren().contains(node)) {
                centerPane.getChildren().add(node);
            }
        }
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
