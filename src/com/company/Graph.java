package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph<Label> {

    private class Edge {
        public int source;
        public int destination;
        public Label label;

        public Edge(int from, int to, Label label) {
            this.source = from;
            this.destination = to;
            this.label = label;
        }
    }
    private int cardinal;
    private ArrayList<LinkedList<Edge>> incidency;
    private ArrayList<Integer> visited = new ArrayList<Integer>();

    public Graph(int size) {
        cardinal = size;
        incidency = new ArrayList<LinkedList<Edge>>(size+1);
        for (int i = 0;i<cardinal;i++) {
            incidency.add(i, new LinkedList<Edge>());
        }
    }

    public int order() {
        return cardinal;
    }

    public void addArc(int source, int dest, Label label) {
        incidency.get(source).addLast(new Edge(source,dest,label));
    }

    public String toString() {
        String result = "";
        result = result + cardinal + "\n";
        for (int i = 0; i < cardinal; i++) {
            for (Edge e : incidency.get(i)) {
                result = result + e.source + " " + e.destination + " "
                        + e.label.toString() + "\n";
            }
        }
        return result;
    }

    //Parcours en profondeur(DFS)
    public void Explore(Edge e, LinkedList<Integer> result) {
        int v = e.destination;
        if (!visited.contains(v)) {
            visited.add(v);
            for (Edge edge : incidency.get(v)){
                Explore(edge, result);
            }
            result.addFirst(v);
        }
    }
    //Parcours en profondeur du graphe transposé
    public Graph graphTranspose() {
        Graph<Integer> graph = new Graph(cardinal);
        int index = 0;
        for (int i = 0; i < this.incidency.size(); i++) {
            LinkedList<Edge> edges = incidency.get(i);
            for (Edge edge : edges) {
                graph.addArc(edge.destination, i, index++);
            }
        }
        return graph;
    }
    //ÉTAPE 1 : l’algorithme de Kosaraju qui effectue DFS sur le graphique d’origine
    public LinkedList<Integer> DFS_1() {
        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < cardinal; i++) {
            if (!visited.contains(i)) {
                visited.add(i);
                LinkedList<Edge> destinations = incidency.get(i);
                for (Edge edge : destinations) {
                    Explore(edge, result);
                }
                result.addFirst(i);
            }
        }
        return result;
    }
    //ÉTAPE 2 : l’algorithme de Kosaraju qui effectue DFS sur le graphe inverse.
    //Cela permettra d’identifier les chaînes fortement connectées
    public ArrayList<LinkedList<Integer>> DFS_2(LinkedList<Integer> order) {
        ArrayList<LinkedList<Integer>> result = new ArrayList<>();
        for (Integer element : order) {
            if (!visited.contains(element)) {
                visited.add(element);
                LinkedList<Integer> components = new LinkedList<>();
                components.add(element);
                for (Edge edge : incidency.get(element)) {
                    if(!visited.contains(edge.destination)) {
                        Explore(edge, components);
                    }
                }
                result.add(components);
            }
        }
        System.out.println("Composantes fortement connexes: " + result);
        return result;
    }

}

