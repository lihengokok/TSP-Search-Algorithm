import java.util.Collections;
import java.util.*;
class SimulatedAnnealing {
	public static List<Integer> solve(int[][] graph) {
		List<Integer> solution = generateRandomSolution(graph, 1);
		return solution;
	}

	private static List<Integer> generateRandomSolution(int[][] graph, long seed) {
		int totalNumber = graph.length;
		List<Integer> solution = new ArrayList<Integer>(totalNumber);

		for(int i = 0; i < totalNumber; i++) solution.add(i);

		Random rand = new Random(seed);
		Collections.shuffle(solution, rand);
		return solution;
	}
}