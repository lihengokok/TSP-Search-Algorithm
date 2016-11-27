import java.util.Collections;
import java.util.*;
class SimulatedAnnealing {
	public static List<Integer> solve(int[][] graph, double initTemp, double coolingRate) {
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

	private static int getDis(int[][] graph, List<Integer> solution) {
		int ans = 0;
		for (int i = 0; i < solution.size() - 1; i++) {
			ans += graph[solution.get(i)][solution.get(i + 1)];
		}
		return ans;
	}

	private static int newDisAfterSwap(int[][] graph, int i, int j, int currentDis) {
		if (i > 0 && i < graph.length - 1) {
			// Remove two edgess
			currentDis -= graph[i][i + 1];
			currentDis -= graph[i - 1][i];
			// Add two edges
			currentDis += graph[j][i + 1];
			currentDis += graph[i - 1][j];
		} else if (i == 0) {
			currentDis -= graph[i][i + 1];
			currentDis += graph[j][i + 1];
		} else {
			currentDis -= graph[i - 1][i];
			currentDis += graph[i - 1][j];
		}

		if (j > 0 && j < graph.length - 1) {
			// Remove two edgess
			currentDis -= graph[j][j + 1];
			currentDis -= graph[j - 1][j];
			// Add two edges
			currentDis += graph[i][j + 1];
			currentDis += graph[j- 1][i];
		} else if (j == 0) {
			currentDis -= graph[j][j + 1];
			currentDis += graph[i][j + 1];
		} else {
			currentDis -= graph[j - 1][j];
			currentDis += graph[j - 1][i];
		}

		return  currentDis;
	}
}