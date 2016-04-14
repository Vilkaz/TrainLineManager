package model;

import controller.JsonController;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vilkazzz on 13/03/2016.
 */
public class TrainPlan {
    private int id;
    private String name = "default Trainplane";
    private List<TrainLine> lines = new ArrayList<TrainLine>();
    private int linesStartingNumber = GeneralSettings.getLinesStartingNumber();
    private int maxNumberOfLines = GeneralSettings.getMaxNumberOfLines();

    public ArrayList<Integer> getUsedTrainLineNumbers() {
        ArrayList<Integer> usedNumbers = new ArrayList<>();
        for (TrainLine trainLine : lines) {
            usedNumbers.add(trainLine.getNumber());
        }
        return usedNumbers;
    }

    public ArrayList<Integer> getFreeTrainLineNumbers() {
        ArrayList<Integer> usedNumbers = getUsedTrainLineNumbers();
        ArrayList<Integer> listOfFreeNumbers = new ArrayList<>();
        for (int i = linesStartingNumber; i <= maxNumberOfLines; i++) {
            if (!usedNumbers.contains(i)) {
                listOfFreeNumbers.add(i);
            }
        }
        return listOfFreeNumbers;
    }

    public void addTrainLine(TrainLine trainLine) {
        lines.add(trainLine);
        System.out.println("");
    }

    public ChoiceBox getChoiceBox() {
        ArrayList possibleLineNumbers = getPossibleLineNumbers();
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll(possibleLineNumbers);
        choiceBox.setValue(choiceBox.getItems().get(0));
        return choiceBox;
    }

    private ArrayList<Integer> getPossibleLineNumbers() {
        ArrayList<Integer> usedLineNumbers = getUsedTrainLineNumbers();
        ArrayList<Integer> lineNumberList = new ArrayList<Integer>();
        for (int i = GeneralSettings.getLinesStartingNumber();
             i <= GeneralSettings.getMaxNumberOfLines();
             i++) {
            if (!usedLineNumbers.contains(i)) {
                lineNumberList.add(i);
            }
        }
        return lineNumberList;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TrainLine> getLines() {
        return lines;
    }

    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        for (TrainLine trainLine : lines) {
            for (Node node : trainLine.getNodes()) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    public String toJson() {
        String json = "{";
        json += JsonController.getJson("id", this);
        json += JsonController.getJson("name", this);
        json += "\"lines\":[";
        for (TrainLine line : lines) {
            json += line.toJson();
            if (line.getId()!= lines.size()-1){
                json+=",";
            }
        }
        json += "]}";
        return json;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLines(List<TrainLine> lines) {
        this.lines = lines;
    }

    public int getLinesStartingNumber() {
        return linesStartingNumber;
    }

    public void setLinesStartingNumber(int linesStartingNumber) {
        this.linesStartingNumber = linesStartingNumber;
    }

    public int getMaxNumberOfLines() {
        return maxNumberOfLines;
    }

    public void setMaxNumberOfLines(int maxNumberOfLines) {
        this.maxNumberOfLines = maxNumberOfLines;
    }
}
