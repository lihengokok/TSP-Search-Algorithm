import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.io.*;


class TSP {
	public static final double TEMPERATURE = 100000000.0;
	public static final double COOLINGRATE = 0.001;
	public static void main(String args[]) throws FileNotFoundException {
    
		double[][] geoMap = DataParser.parse("../DATA/" + args[2] + ".tsp");
		int[][] newMap = DataParser.parseNodesTo2DIntArray(geoMap);
		List<Integer> ans = new ArrayList<Integer>();
		if (args[0].equals("SA")) {
			int seed = Integer.valueOf(args[1]);
			ans = SimulatedAnnealing.solve(newMap, TEMPERATURE, COOLINGRATE, seed);
		}
		if (args[0].equals("TO")) {
			int seed = Integer.valueOf(args[1]);
			ans = TwoOptExchange.solve(newMap, seed);
		}
		if (args[0].equals("NN")) {
			double startDC = System.nanoTime();
			ans = nearestNeighbor(newMap);
			double finishDC = System.nanoTime();
			double runningTimeDC = (finishDC - startDC) / 1000000000;
		    System.out.println(city+" "+runningTimeDC+" "+getWeight(tsp, graph));
			// writeOutput(tsp,graph,"../results/"+city+"_Heur.tour");
		}
		if (args[0].equals("MSTApprox")) {
			TSPMSTApproximation tspMst = new TSPMSTApproximation();
			ans = tspMst.getTspMSTApproximation(geoMap);
		}
		DataParser.writeOutput(ans, geoMap, args);

	}

	/**
	 *Approximation Algorithm: Nearest Neighbor
	 * @param origianl graph
	 */
	public static List<Integer> nearestNeighbor(int[][] graph) {
		List<Integer> res = new ArrayList<>();
		HashSet<Integer> set = new HashSet<>();
		int cur = 0;
		res.add(0);
		for(int i = 1; i < graph.length; i++) set.add(i);
		
		while(!set.isEmpty()) {
			int next = (int) set.toArray()[0];
			for(int tmp : set) {
				if(graph[cur][tmp] < graph[cur][next]) next = tmp;
			}
			res.add(next);
			set.remove((Integer)next);
			cur = next;
		}
        res.add(0);
		return res;
	}


	/**
     * write result into file
     * @param tsp tsp traversal of a given graph
     * @param geoMap origianl graph
     * @param outputPath output file
     */
    private static void writeOutput(List<Integer> tsp, int[][] geoMap, String outputPath) {
        int weight = getWeight(tsp, geoMap);
        PrintWriter output;
        try {
            output = new PrintWriter(outputPath, "UTF-8");
            output.println(weight);
            for (int i = 1; i < tsp.size(); i++) {
                int source = tsp.get(i - 1);
                int target = tsp.get(i);
                output.println(source + "\t" + target + "\t" + geoMap[source][target]);
            }
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
    private static int getWeight(List<Integer> tsp, int[][] geoMap) {
        int numOfTrip = tsp.size();
        int res = 0;
        for (int i = 1; i < numOfTrip; i++) {
            res += geoMap[tsp.get(i - 1)][tsp.get(i)];
        }
        return res;
    }
}

