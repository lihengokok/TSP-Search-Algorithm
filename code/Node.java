import java.util.List;
import java.util.ArrayList;

/*
* Created by Xiaoqin Zhu
*
* To keep track the state of the current path, lower bound, and reduced matrix after a node is added
*/

public class Node {
    private int lowerBound;
    private int totalCost;
    private List<Integer> currentPath;
    private int[][] currentReducedMatrix;

    Node(int lowerBound, int totalCost, List<Integer> currentPath, int[][] currentReducedMatrix) {
        this.lowerBound = lowerBound; // need to update after reduction
        this.totalCost = totalCost;
        this.currentPath = currentPath;
        this.currentReducedMatrix = currentReducedMatrix; // need do reduction and update this matrix

    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public List<Integer> getCurrentPath() {
        return currentPath;
    }

    public int[][] getCurrentReducedMatrix() {
        return currentReducedMatrix;
    }
}
