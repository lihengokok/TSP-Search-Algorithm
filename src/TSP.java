import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.io.*;

// args[0] city, args[1] algorithm, args[2] cutoff time, args[3] seed

class TSP {
	public static final double TEMPERATURE = 100000000.0;
	public static final double COOLINGRATE = 0.000001;
	public static void main(String args[]) throws FileNotFoundException {
    
		double[][] geoMap = DataParser.parse("../DATA/" + args[0] + ".tsp");
		int[][] newMap = DataParser.parseNodesTo2DIntArray(geoMap);
<<<<<<< HEAD
		List<Integer> ans = new ArrayList<Integer>();
		if (args[1].equals("SA")) {
			int seed = Integer.valueOf(args[3]);
			ans = SimulatedAnnealing.solve(newMap, TEMPERATURE, COOLINGRATE, seed);
		}
		if (args[1].equals("TO")) {
			int seed = Integer.valueOf(args[3]);
			ans = TwoOptExchange.solve(newMap, seed);
=======
		TSPAnswer newAns = new TSPAnswer();
		if (args[0].equals("SA")) {
			int seed = Integer.valueOf(args[1]);
			newAns = SimulatedAnnealing.solve(newMap, TEMPERATURE, COOLINGRATE, seed);
			DataParser.writeTrace(newAns.trace, args);
			DataParser.writeOutput(newAns.solution, newMap, args);
		}
		if (args[0].equals("TO")) {
			int seed = Integer.valueOf(args[1]);
			newAns = TwoOptExchange.solve(newMap, seed);
			DataParser.writeTrace(newAns.trace, args);
			DataParser.writeOutput(newAns.solution, newMap, args);
>>>>>>> origin/master
		}
		if (args[1].equals("NN")) {
			double startDC = System.nanoTime();
			ans = nearestNeighbor(newMap);
			double finishDC = System.nanoTime();
			double runningTimeDC = (finishDC - startDC) / 1000000000;
		    System.out.println(runningTimeDC);
		    DataParser.writeOutput(ans, geoMap, args);
			// writeOutput(tsp,graph,"../results/"+city+"_Heur.tour");
		}
		if (args[1].equals("MSTApprox")) {
			TSPMSTApproximation tspMst = new TSPMSTApproximation();
			ans = tspMst.getTspMSTApproximation(geoMap);
			DataParser.writeOutput(ans, geoMap, args);
		}
		// for branch and bound:
        if (args[1].equals("BnB")) {
            int timeLimit = 600; // 10 minutes
            // check if a time limit is given
            if (args[2] != null) timeLimit = Integer.valueOf(args[2]);
            BnB bnb = new BnB();
            bnb.getBnB(args[0], newMap, timeLimit);
        }

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
}

