package model;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vilkazzz on 13/03/2016.
 */
public class TrainPlan {
    private int id;
    private String name;
    private static List<TrainLine> trainLines = new ArrayList<TrainLine>();
    private static int linesStartingNumber = GeneralSettings.getLinesStartingNumber();
    private static int maxNumberOfLines = GeneralSettings.getMaxNumberOfLines();

    public static ArrayList<Integer> getUsedTrainLineNumbers() {
        ArrayList<Integer> usedNumbers = new ArrayList<>();
        for (TrainLine trainLine : trainLines) {
            usedNumbers.add(trainLine.getNumber());
        }
        return usedNumbers;
    }

    public static ArrayList<Integer> getFreeTrainLineNumbers(){
        ArrayList<Integer> usedNumbers = getUsedTrainLineNumbers();
        ArrayList<Integer> listOfFreeNumbers = new ArrayList<>();
        for (int i = linesStartingNumber; i<= maxNumberOfLines; i++){
            if(!usedNumbers.contains(i)){
                listOfFreeNumbers.add(i);
            }
        }
        return listOfFreeNumbers;
    }

    public void addTrainLine(TrainLine trainLine) {
        trainLines.add(trainLine);
    }

    public ChoiceBox getChoiceBox() {
        ArrayList possibleLineNumbers = getPossibleLineNumbers();
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll(possibleLineNumbers);
        choiceBox.setValue(choiceBox.getItems().get(0));
        return choiceBox;
    }

    private ArrayList<Integer> getPossibleLineNumbers() {
        ArrayList<Integer> usedLineNumbers = TrainPlan.getUsedTrainLineNumbers();
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


    //region getter and setter

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TrainLine> getTrainLines() {
        return trainLines;
    }

    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        for (TrainLine trainLine : trainLines){
            for (Node node: trainLine.getNodes()){
                nodes.add(node);
            }
        }
        return nodes;
    }


    //endregion getter and setter
}
