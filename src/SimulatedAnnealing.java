import java.util.Collections;
import java.util.*;
import java.util.List;
class SimulatedAnnealing {
	public static TSPAnswer solve(int[][] graph, double temp, double coolingRate, int seed) {
		List<Integer> solution = generateRandomSolution(graph, 1);
		int totalNumber = graph.length;
		Random rand = new Random(seed);
		int curDis = getDis(graph, solution);
		int iterations = 0;
		List<Long> traceList = new ArrayList<Long>();
		final long start = System.currentTimeMillis();
		while (temp > 1.0) {
			iterations += 1;
			if (iterations % 1000 == 0) {
				System.out.println("Iteations: " + iterations);
				System.out.println("Current Dis: " + curDis);
			}
			int pos1 = rand.nextInt(totalNumber);
			int pos2 = rand.nextInt(totalNumber);
			if (pos1 == pos2) {
				pos2 = (pos2 + 1) % totalNumber;
			}

			// Swap two position
			int t = solution.get(pos1);
			solution.set(pos1, solution.get(pos2));
			solution.set(pos2, t);
			int newDis = getDis(graph, solution);
			double prob = acceptProb(newDis, curDis, temp);
			double r = rand.nextDouble();
			temp = temp * (1.0 - coolingRate);

			if (prob < r) {
				t = solution.get(pos1);
				solution.set(pos1, solution.get(pos2));
				solution.set(pos2, t);
				continue;
			}
			long timeStamp = System.currentTimeMillis() - start;
			traceList.add(timeStamp);
			traceList.add((long) newDis);
			curDis = newDis;
		}

		System.out.println("Final Iteations: " + iterations);
		System.out.println("Final Dis: " + curDis);
		System.out.println((double) (System.currentTimeMillis() - start) / 1000);
		solution.add(solution.get(0));

        TSPAnswer myAns = new TSPAnswer();
        myAns.solution = ((List<Integer>) ((ArrayList) solution).clone());
        myAns.trace = traceList;
        return myAns;
	}

	private static double acceptProb(int newDis, int curDis, double temp) {
		if (newDis < curDis) {
			return 1.0;
		}
		return Math.exp((curDis - newDis) / temp);
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
		ans += graph[solution.get(solution.size() - 1)][0];
		return ans;
	}
}