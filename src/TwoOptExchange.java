import java.util.Collections;
import java.util.*;
class TwoOptExchange {
    public static List<Integer> solve(int[][] graph, int seed) {
        List<Integer> solution = generateRandomSolution(graph, seed);
        int parentBestDis = getDis(graph, solution);
        System.out.println(parentBestDis);
        Boolean updated = true;
        while (updated) {
            updated = false;
            int iterBestDis = parentBestDis;
            List<Integer> iterBestSolution = solution;
            for (int i = 0; i < solution.size(); i++) {
                for (int j = i + 1; j < solution.size(); j++) {
                    List<Integer> newList = ((List<Integer>) ((ArrayList) solution).clone());

                    // Reverse the list from i to j
                    int c = 0;
                    for (int k = i; k <= ((i + j) / 2); k++) {
                        int t = newList.get(k);
                        newList.set(k, newList.get(j - c));
                        newList.set(j - c, t);
                        c++;
                    }
                    int dis = getDis(graph, newList);
                    if (dis < iterBestDis) {
                        iterBestDis = dis;
                        iterBestSolution = newList;
                    }
                }
            }
            if (iterBestDis < parentBestDis) {
                updated = true;
                parentBestDis = iterBestDis;
                solution = iterBestSolution;
            }
        }
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
}