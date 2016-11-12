import java.util.Scanner;
import java.io.*;
class TSP {
	public static void main(String args[]) throws FileNotFoundException {
		int[][] newMap = DataParser.parse("./DATA/Boston.tsp");
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
