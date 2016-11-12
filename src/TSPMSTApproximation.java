import uf.UF;

import java.io.*;
import java.util.*;

/**
 * Created by Huanli_Wang on 11/11/2016.
 */
public class TSPMSTApproximation {
    class Edge {
        int node1;
        int node2;
        double weight;

        public Edge(int node1, int node2, double weight) {
            this.node1 = node1;
            this.node2 = node2;
            this.weight = weight;
        }
    }

    /**
     * Generates a tour of a completion connected graph using the
     * MST-TSP 2-competitive approximation algorithm.
     *
     * @param city the city you find to find a tsp traversal
     *
     * @throws FileNotFoundException if the input file is not exist or can not be found
     */
    public void getTspMSTApproximation(String city) throws FileNotFoundException {
        double[][] geoMap = DataParser.parse("../DATA/" + city + ".tsp");
        String outputPath = "./TSP-Search-Algorithm/results/" + city + "_MST_TSP"+ ".tour";
        List[] mst = getMST(geoMap);
        List<Integer> tsp = getTsp(mst);
        writeOutput(tsp, geoMap, outputPath);
    }


    /**
     * get the minimum spanning tree of a given graph
     * @param geoMap store cities' information
     * @return mst minmium spaning tree
     */
    private List<Integer>[] getMST(double[][] geoMap) {
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight <= o2.weight ? o1.weight == o2.weight ? 0 : -1 : 1;
            }
        });
        UF set = new UF(geoMap.length);

        /* Add all edges into priority queue */
        for (int i = 0; i < geoMap.length; i++) {
            for (int j = i + 1; j < geoMap[0].length; j++) {
                Edge edge = new Edge(i, j, geoMap[i][j]);
                pq.add(edge);
            }
        }

        /* Kruskal Algorithm */
        int numOfNodes = geoMap.length, numOfEdge = 0;
        List<Integer>[] mst = new List[numOfNodes];
        for (int i = 0; i < numOfNodes; i++) {
            mst[i] = new ArrayList<Integer>();
        }
        while (numOfEdge < numOfNodes - 1) {
            Edge edge = pq.remove();
            int node1 = edge.node1, node2 = edge.node2;
            if (!set.connected(node1, node2)) {
                mst[node1].add(node2);
                mst[node2].add(node1);
                set.union(node1, node2);
                numOfEdge++;
            }
        }
        return mst;
    }

    /**
     * get the 2-approximation tsp result from a given minimum spanning tree
     * @param mst a minimum spanning tree
     * @return List<Integer> a list contains cities in traversal order
     */
    private List<Integer> getTsp(List[] mst) {
        List<Integer> tsp = new ArrayList<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        tsp.add(0);
        dfs(mst, 0, visited, tsp);
        tsp.add(0);
        return tsp;
    }

    /**
     * do a Depth-First-Search for a given minimum spanning tree
     * @param mst a minimum spanning tree
     * @param start starter
     * @param visited a set contains cities have been visited
     * @param tsp a list stores result
     */
    private void dfs(List<Integer>[] mst, int start, Set<Integer> visited, List<Integer> tsp) {
        visited.add(start);
        for (int i = 0; i < mst[start].size(); i++) {
            int next = mst[start].get(i);
            if (!visited.contains(next)) {
                tsp.add(next);
                dfs(mst, next, visited, tsp);
            }
        }
    }

    /**
     * write result into file
     * @param tsp tsp traversal of a given graph
     * @param geoMap origianl graph
     * @param outputPath output file
     */
    private void writeOutput(List<Integer> tsp, double[][] geoMap, String outputPath) {
        double weight = getWeight(tsp, geoMap);
        PrintWriter output;
        try {
            output = new PrintWriter(outputPath, "UTF-8");
            output.println(weight);
            for (int i = 1; i < tsp.size(); i++) {
                int source = tsp.get(i - 1);
                int target = tsp.get(i);
                output.println(source + "\t" + target + "\t" + geoMap[source][target]);
            }
            System.out.println("Write finished");
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * calculate the weight of a given tsp
     * @param tsp tsp traversal of a given graph
     * @param geoMap origianl graph
     * @return the weight of a given tsp
     */
    private double getWeight(List<Integer> tsp, double[][] geoMap) {
        int numOfTrip = tsp.size();
        double res = 0;
        for (int i = 1; i < numOfTrip; i++) {
            res += geoMap[tsp.get(i - 1)][tsp.get(i)];
        }
        return res;
    }

    public static void main(String[] args) throws FileNotFoundException {
        double[][] geoMap = DataParser.parse("../DATA/" + args[0] + ".tsp");
        String outputPath = "./TSP-Search-Algorithm/results/" + args[0] + ".tour";
        TSPMSTApproximation tspMstApproximation = new TSPMSTApproximation();
        List[] mst = tspMstApproximation.getMST(geoMap);
        List<Integer> tsp = tspMstApproximation.getTsp(mst);
        tspMstApproximation.writeOutput(tsp, geoMap, outputPath);
    }
}
