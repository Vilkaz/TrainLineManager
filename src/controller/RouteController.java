package controller;

import model.TrainStation;

import java.util.ArrayList;

/**
 *  Created by Hanno
 */

public class RouteController {
    public static ArrayList<TrainStation> getRoute(TrainStation start, TrainStation end, ArrayList<TrainStation> V) {

        ArrayList<TrainStation> Route = new ArrayList<TrainStation>();

        // gehe alle Knoten durch, setze die Distanz zu allen Knoten/Haltestellen die nicht
        // der Startpunkt sind auf "unendlich" und die Distanz zum Startpunkt 0
        for (int i = 0; i < V.size(); i++) {

            if (V.get(i).getId().equals(start.getId())) {
                V.get(i).setDistance_to_route_startpoint(0);
            } else {
                // Interger.Max_Value darf ich hier nicht nehmen weil später eine distanz dazuaddiert wird.
                V.get(i).setDistance_to_route_startpoint(1000000);
            }

            // Keine Station hat einen Vorgänger
            V.get(i).setVorgänger(null);
        }

        // Für jeden Knoten
        for (int j = 0; j < V.size() - 1; j++)

            // gehe alle Kanten durch
            for (int u = 0; u < V.size(); u++) {

                TrainStation NodeU = V.get(u);

                for (int v = 0; v < NodeU.getNeighbors().size(); v++) {

                    // Id des Nachbarn holen
                    String  id = NodeU.getNeighbors().get(v).getId();
                    TrainStation NodeV = null;

                    // Benachbarte Station holen
                    for (int k = 0; k < V.size(); k++) {
                        if (V.get(k).getId().equals(id)) {
                            NodeV = V.get(k);
                        }
                    }

                    // Prüfe ob die Distanz zu dem Nachbar V kürzer ist als die bisher bekannte Distanz.
                    if (NodeU.getDistance_to_route_startpoint() + NodeU.getNeighbors().get(v).getDistance() < NodeV.getDistance_to_route_startpoint()) {

                        // Aktualisiere die Entfernung
                        NodeV.setDistance_to_route_startpoint(NodeU.getDistance_to_route_startpoint() + NodeU.getNeighbors().get(v).getDistance());

                        // und setze den neuen Vorgänger, die vorhergehende Station
                        NodeV.setVorgänger(NodeU);
                    }
                }
            }

        // Normalerweise Erfolgt im Bellman-Ford Alg. nun die Überprüfung ob es negative Zyklen/Kreise gibt.
        // Diese habe ich erstmal weggelassen,
        // weil wir logischerweise keine negativen Kantengewichte haben können.

        // Anlegen der Route, fange am Ende an und gehe zum jeweiligen Vorgänger bis die Startstation erreicht ist.
        TrainStation Node = end;

        while (Node.getId() != start.getId()) {

            // Füge Vorgänger am Anfang der Arraylist an
            Route.add(0, Node);

            Node = Node.getVorgänger();

        }
        // Füge die Startstation hinzu.
        Route.add(0, start);

        return Route;
    }

    public static int calculate_price(ArrayList<TrainStation> route) {

        int price = 2;

        if (route.size() == 2) {
            price = 1;
        } else {
            int zone = route.get(0).getZone();

            for (int i = 0; i < route.size(); i++) {

                if (zone != route.get(i).getZone()) {

                    price += 1;
                    zone = route.get(i).getZone();
                }

                if (route.get(i).isEndStation()) {

                    price += 1;
                }
            }
        }


        return price;
    }

}