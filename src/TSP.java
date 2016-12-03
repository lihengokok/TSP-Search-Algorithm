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
		DataParser.writeOutput(ans, geoMap, args);
	}

	//Approximation Algorithm: Nearest Neighbor
	public static List<Integer> nearestNeighbor(int[][] graph) {
		List<Integer> res = new ArrayList<>();
		HashSet<Integer> set = new HashSet<>();
		int cur = 1;
		res.add(1);
		for(int i = 2; i <= graph.length; i++) set.add(i);
		
		while(!set.isEmpty()) {
			int next = (int) set.toArray()[0];
			for(int tmp : set) {
				if(graph[cur-1][tmp-1] < graph[cur-1][next-1]) next = tmp;
			}
			res.add(next);
			set.remove((Integer)next);
			cur = next;
		}
		return res;
	}
}

