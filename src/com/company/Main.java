package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    private final static File FILE_URL = new File("C:\\LICENCE 3\\Algorithmique 2\\formule-2-sat.txt");

    public static void main(String[] args) throws IOException {
        // Lire le fichier text et donné (l1,l2), ajouter des arcs -l1 --> l2 & -l2 --> l1 dans la graphe
        FileReader fr = new FileReader(FILE_URL);
        BufferedReader br = new BufferedReader(fr);
        String line;
        br.readLine();
        line = br.readLine();
        String [] content = line.split(" ");
        int size = Integer.parseInt(content[2]) * 2;
        Graph<Integer> graph = new Graph<>(size);
        int index = 0;
        while ((line = br.readLine()) != null) {
            String[] arg = line.split(" ");
            int source1 = -Integer.parseInt(arg[0]);
            int dest1 = Integer.parseInt(arg[1]);

            int source2 = -Integer.parseInt(arg[1]);
            int dest2 = Integer.parseInt(arg[0]);

            graph.addArc(literalToVertex(source1), literalToVertex(dest1), index++);
            graph.addArc(literalToVertex(source2), literalToVertex(dest2), index++);

        }
        //Afficher des graphes et des composantes fortement connexes, vérifier la formule P est satisfiable ou non
        Graph graphTrans = graph.graphTranspose();
        System.out.println("Graph G-origine: ");
        System.out.println(graph);
        System.out.println("Graph G-trans: ");
        System.out.println(graphTrans.toString());
        System.out.println(is2Satisfiable(graphTrans.DFS_2(graph.DFS_1())));

    }
    public static int  literalToVertex(int l){
        if (l >= 0)
            return 2*(l-1);
        return -2*l-1;
    }
    public static int vertexToLiteral(int s){
        if (s%2==0)
            return (s/2)+1;
        return -(s-1)/2;
    }
    //La méthode pour vérifier la 2-Satisfiabilité
    public  static boolean is2Satisfiable(ArrayList<LinkedList<Integer>> components){
        for (LinkedList<Integer> component : components) {
            for (Integer e : component) {
                //LA FORMULE EST INSATISFAISANTE s'il existe -x et x dans le même SCC
                if (component.contains(-vertexToLiteral(e))) {
                    System.out.println("The given expression is unsatisfiable");
                    return false;
                }
            }
        }
        //LA FORMULE EST SATISFAISANTE s'il n'existe pas -x et x dans le même SCC
        System.out.println("The given expression is satisfiable");
        return true;
    }

}
