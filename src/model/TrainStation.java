package model;

import controller.ColorController;
import controller.ContentController;
import controller.JsonController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vilkazzz on 13/03/2016.
 */
public class TrainStation implements HasNode {
    private int id;
    private Color color;
    private StationIcon icon;
    private String name;
    private int zone;
    private boolean endStation;
    private int lineNr;
    private ArrayList<Neighbor> neighbors = new ArrayList<Neighbor>();
    private ArrayList<StationConnector> connectors = new ArrayList<StationConnector>();
    private Pane node;
    private Pane centerPane;
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();

    public TrainStation() {
    }

    public TrainStation(int id, String name, int zone, boolean endStation, Color color, int lineNr, MouseEvent event, Pane centerPane) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.endStation = endStation;
        this.color = color;
        this.lineNr = lineNr;
        this.centerPane = centerPane;
        this.node = new Pane();
        this.icon = new StationIcon(this);
        this.node.getChildren().addAll(getText(this.name), icon.getNode());
        node.setLayoutX(event.getX());
        node.setLayoutY(event.getY());
        y.setValue(node.layoutYProperty().getValue() + icon.getNode().layoutYProperty().getValue());
        x.setValue(node.layoutXProperty().getValue() + icon.getNode().layoutXProperty().getValue());
    }

    public TrainStation(int id, String name, int zone, boolean endStation, Color color, int lineNr, double x, double y) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.endStation = endStation;
        this.color = color;
        this.lineNr = lineNr;
        this.node = new Pane();
        this.icon = new StationIcon(this);
        this.node.getChildren().addAll(getText(this.name), icon.getNode());
        node.setLayoutX(x);
        node.setLayoutY(y);
        this.y.setValue(node.layoutYProperty().getValue() + icon.getNode().layoutYProperty().getValue());
        this.x.setValue(node.layoutXProperty().getValue() + icon.getNode().layoutXProperty().getValue());
    }


    private VBox getText(String stationName) {
        Text text = new Text(stationName);
        VBox textContainer = new VBox(text);
        text.setOnMouseClicked(getTextOnMouseClickedEventHandler(text));
        return textContainer;
    }

    public EventHandler<? super MouseEvent> getTextOnMouseClickedEventHandler(Text text) {
        VBox textContainer = (VBox) text.getParent();
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /**
                 * quite dirty but working method to prevent more then one text Drag options to be
                 * active at same time
                 */
                if (!ContentController.isActiveTextDrag()) {
                    ContentController.setActiveTextDrag(true);
                    activateTextDragEvent(text, textContainer);
                }
            }
        };
        return event;
    }

    private void activateTextDragEvent(Text text, final VBox textContainer) {
        Slider slider = new Slider(-180, 180, 0);
        slider.valueProperty().bindBidirectional(text.rotateProperty());
        ImageView image = new ImageView("img/move.png");
        image.setFitWidth(GeneralSettings.getMOVE_ICON_WIDTH());
        image.setFitHeight(GeneralSettings.getMOVE_ICON_HEIGTH());
        centerPane = (Pane) node.getParent();
        image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                centerPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        textContainer.setLayoutX(event.getX() - node.getLayoutX());
                        textContainer.setLayoutY(event.getY() - node.getLayoutY());
                    }
                });
                centerPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ContentController.setActiveTextDrag(false);
                        centerPane.setOnMouseMoved(null);
                        textContainer.getChildren().removeAll(slider, image);
                    }
                });
            }
        });
        textContainer.getChildren().addAll(slider, image);
    }

    public String toJson() {
        String json = "{";
        json += JsonController.getJson("id", this);
        json += JsonController.getJson("color", ColorController.getColorHex(color));
        json += JsonController.getJson("name", this);
        json += JsonController.getJson("zone", this);
        json += JsonController.getJson("endStation", this);
        json += JsonController.getJson("lineNr", this);
        json += JsonController.getJson("x", this);
        json += JsonController.getJson("y", this);
        json += JsonController.putJsonQuotes("icon") + icon.toJson() + ",";
        json += JsonController.putJsonQuotes("text") + getTextJson() + ",";
        json += getNeighborJsons() + ",";
        json += getConnectorSaveJsons();
        json += "}";
        return json;
    }


    /**
     * ich würde es gerne mit proppertis lösen. einfach proppertys in
     * der klasse deklarieren und sie an den Text proppertys binden.
     * aber es ist 23:33 .... ich bin zu müde um experimente zu machen
     * (was ist wenn text propertys von den am anfang nicht gesetzten
     * klassen variablen beeinflusst werden ?)
     * und mache daher was stupides.... hardcoding ist immer scheise :/
     * gn8...
     *
     * @return
     */
    private String getTextJson() {
        VBox textContainer = (VBox) node.getChildren().get(0);
        Text text = (Text) textContainer.getChildren().get(0);
        String json = "{";
        json += JsonController.getJson("X", text.getLayoutX());
        json += JsonController.getJson("Y", text.getLayoutY());
        json += JsonController.getJson("rotation", text.getRotate(), false);
        return json + "}";
    }


    private String getConnectorSaveJsons() {
        String json = JsonController.putJsonQuotes("connectors") + "[";
        for (int i = 0; i <= connectors.size() - 1; i++) {
            json += connectors.get(i).toSaveJson();
            json += (i == connectors.size() - 1) ? "" : ",";
        }
        return json + "]";
    }

    private String getNeighborJsons() {
        String json = JsonController.putJsonQuotes("neighbors") + "[";
        for (int i = 0; i <= neighbors.size() - 1; i++) {
            json += neighbors.get(i).toJson();
            json += (i == neighbors.size() - 1) ? "" : ",";
        }
        return json + "]";

    }


    public void addConnector(StationConnector connector) {
        this.getConnectors().add(connector);
    }

    public void addNeighbor(Neighbor neighbor) {
        this.neighbors.add(neighbor);
    }

    public Pane getNode() {
        return node;
    }

    //region getter and setter


    public List getNeighbors() {
        return neighbors;
    }

    public ArrayList<StationConnector> getConnectors() {
        return connectors;
    }

    public Pane getCenterPane() {
        return centerPane;
    }

    public void setEndStation(boolean endStation) {
        this.endStation = endStation;
    }

    public int getLineNr() {
        return lineNr;
    }

    public void setNode(Pane node) {
        this.node = node;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public StationIcon getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public int getZone() {
        return zone;
    }

    public boolean isEndStation() {
        return endStation;
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setIcon(StationIcon icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public void setLineNr(int lineNr) {
        this.lineNr = lineNr;
    }

    public void setNeighbors(ArrayList<Neighbor> neighbors) {
        this.neighbors = neighbors;
    }

    public void setConnectors(ArrayList<StationConnector> connectors) {
        this.connectors = connectors;
    }

    public void setCenterPane(Pane centerPane) {
        this.centerPane = centerPane;
    }




//endregion getter and setter


}
