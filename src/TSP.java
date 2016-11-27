import java.util.*;
import java.io.*;
class TSP {
	public static final double TEMPERATURE = 1000000.0;
	public static final double COOLINGRATE = 0.001;
	public static void main(String args[]) throws FileNotFoundException {
		int[][] newMapInteger = DataParser.parse("../DATA/Boston.tsp");
		int[][] newMap = DataParser.parse("./DATA/Boston.tsp");
		if(args[0].equals("SA")) {
			SimulatedAnnealing SA = new SimulatedAnnealing();
			int seed = Integer.valueOf(args[1]);
			List<Integer> ans = SA.solve(newMapInteger, TEMPERATURE, COOLINGRATE, seed);
		}
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

