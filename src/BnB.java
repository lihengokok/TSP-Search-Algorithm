import java.io.*;
import java.util.stream.*;
import java.util.regex.*;
import java.util.List;
import java.util.ArrayList;

/*
* Created by Xiaoqin Zhu
*/

public class BnB {
    /*
    * To run the branch and bound algorithm and then output the trace and final solution
    * @param fileName name of the tsp file in "xxx.tsp"
    * @param costMatrix distances matrix between cities
    * @timeLimit time constaint to end the algorithm in seconds
    */
    public void getBnB(String fileName, int[][] costMatrix, int timeLimit) {
        try {
            long startTime = System.nanoTime();
            long endTime = 0;
            MyBranchAndBound b = new MyBranchAndBound(costMatrix, timeLimit);
            b.mybnb();

            String[] name = fileName.split("\\.");
            PrintWriter outputSol, outputTrace;
            String outputFileSol = "../output/" + name[0] + "_BnB_" + timeLimit + ".sol";
            String outputFileTrace = "../output/" + name[0] + "_BnB_" + timeLimit + ".trace";
            PrintWriter outputDP, outputDC;
            outputSol = new PrintWriter(outputFileSol, "UTF-8");
            outputTrace = new PrintWriter(outputFileTrace, "UTF-8");

            List<Double> timeList = b.getTimeList();
            List<Integer> solnList = b.getSolnList();
            List<Integer> soln = b.getSoln();

            for (int i = 0; i < solnList.size(); i++) {
                outputTrace.println(String.format("%.2f", timeList.get(i)) + "," + solnList.get(i));
            }

            outputSol.println(b.getTotalCost());
            int u = 0, v = 0;
            for (int i = 0; i < soln.size() - 1; i++) {
                u = soln.get(i);
                v = soln.get(i + 1);
                outputSol.println(u + " " + v + " " + costMatrix[u][v]);
            }

            outputTrace.close();
            outputSol.close();
            endTime = System.nanoTime();
            System.out.println("time: " + b.getTime()); // time takes to find the best soln within time limit
            System.out.println("total time: " + (endTime - startTime)/1000000000.00); // total running time -- include time takes to output the results
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    /*
    public static void main(String[] args) throws FileNotFoundException {
        //int[][] mymap = DataParser.parse("../DATA/UKansasState.tsp");
        //int[][] mymap = DataParser.parse("../DATA/Cincinnati.tsp");
        //int[][] mymap = DataParser.parse("../DATA/Atlanta.tsp");
        //int[][] mymap = DataParser.parse("../DATA/Philadelphia.tsp");
        int[][] mymap = DataParser.parse("../DATA/SanFrancisco.tsp");
        //int[][] mymap = DataParser.parse("../DATA/Boston.tsp");
        //int[][] mymap = DataParser.parse("../DATA/Roanoke.tsp");
        //int[][] mymap = DataParser.parse("../DATA/Toronto.tsp");
        //int[][] mymap = DataParser.parse("../DATA/Champaign.tsp");
        //int[][] mymap = DataParser.parse("../DATA/Denver.tsp");
        //int[][] mymap = DataParser.parse("../DATA/NYC.tsp");
        //int[][] mymap = DataParser.parse("../DATA/Umissouri.tsp");

        BnB bnb = new BnB();

        //bnb.getBnB("UKansasState.tsp", mymap, 3600);
        //bnb.getBnB("Cincinnati.tsp", mymap, 3600);
        //bnb.getBnB("Atlanta.tsp", mymap, 3600);
        //bnb.getBnB("Philadelphia.tsp", mymap, 3600);
        bnb.getBnB("SanFrancisco.tsp", mymap, 10800); // 3 hours. NEED MORE TIME - no change at all for one hour
        //bnb.getBnB("Boston.tsp", mymap, 3600);
        //bnb.getBnB("Roanoke.tsp", mymap, 3600);
        //bnb.getBnB("Toronto.tsp", mymap, 3600); // 1 hours. NEED MORE TIME - no change at all for one hour
        //bnb.getBnB("Champaign.tsp", mymap, 10800); // 3 hours
        //bnb.getBnB("Denver.tsp", mymap, 3600);
        //bnb.getBnB("NYC.tsp", mymap, 3600);
        //bnb.getBnB("Umissouri.tsp", mymap, 3600);
    }
    */
}
