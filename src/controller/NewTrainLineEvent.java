package controller;

import javafx.scene.Node;
import model.HasNode;


/**
 * Created by Vilkazzz on 19/03/2016.
 */
public class NewTrainLineEvent implements HasNode{
  private Node node;

    public NewTrainLineEvent(Node node){
        this.node = node;
    }

    @Override
    public Node getNode() {
        return this.node;
    }

}
