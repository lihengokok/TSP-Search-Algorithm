import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/*
* Created by Xiaoqin Zhu
*
* Actual branch and bound algorithm: find an initial upper bound then use DFS to find opt solution within time limit
*/

public class MyBranchAndBound  {
    private int[][] costMatrix; // original cost matrix
    private int n;
    private int[][] reducedCost; //a matrix to be reduced after each node is added or to check lower bound
    private int lowerBound;
    private int upperBound; // updated if better solution is found
    private List<Integer> bnbSoln; // keep track the path of a solution
    private long startTime;
    private double currentTime;
    private double TIMELIMIT;
    private List<Integer> solnList; // keep track when a better solution is found
    private List<Double> timeList; // time when a better solution is found

    public MyBranchAndBound (int[][] costMatrix, int timeLimit) {
        this.costMatrix = costMatrix;
        this.n = costMatrix.length;
        this.reducedCost = new int[n][n];
        this.lowerBound = 0;
        this.upperBound = Integer.MAX_VALUE;
        this.bnbSoln = new ArrayList<Integer>();

        this.startTime = System.nanoTime();
        this.currentTime = 0.00;
        this.TIMELIMIT = timeLimit;
        this.solnList = new ArrayList<Integer>();
        this.timeList = new ArrayList<Double>();

        resetMatrix(this.costMatrix); // set the diagonal cost to -1

        reduceMatrix();  //initial reduction of cost matrix

    }

    /* return the total cost of final solution when finished or reached time limit */
    public int getTotalCost() {
        return upperBound;
    }

    /* return the path of the final solution */
    public List<Integer> getSoln() {
        return bnbSoln;
    }

    /* return the running time this algorithm */
    public double getTime() {
        return (System.nanoTime() - startTime)/1000000000.00 ;
    }

    /* return the solutions found within the time limit */
    public List<Integer> getSolnList() {
        return solnList;
    }

    /* return the time of when solutions were found within the time limit */
    public List<Double> getTimeList() {
        return timeList;
    }

    /* find the best solution for branch and bound algorithm whitin the time limit */
    public void mybnb() {
        // get initial upper bound:
        getInitialUB();
        solnList.add(getTotalCost());
        timeList.add(getTime());

        List<Integer> initialPath = new ArrayList<Integer>();
        initialPath.add(0);

        int[][] initialReducedMatrix = reducedCost;
        Node firstNode = new Node(lowerBound, 0, initialPath, initialReducedMatrix);

        getBNB(firstNode);
    }

    /* DFS to go through possible path and use upper bound to prune a node or path */
    public void getBNB(Node currentNode) {
        currentTime = (System.nanoTime() - startTime)/1000000000.00;

        if (currentTime >= TIMELIMIT) return;

        List<Integer> path = currentNode.getCurrentPath();
        int prevPos = path.get(path.size() - 1), newPos = 0, newCost = 0, lb = 0;
        reducedCost = currentNode.getCurrentReducedMatrix();
        lowerBound = currentNode.getLowerBound();
        List<DummyNode> list = getXYZ(prevPos);
        Node myNode;
        int[][] tempMatrix;

        for (int i = 0; i < list.size(); i++) {
            tempMatrix = copyMatrix(currentNode.getCurrentReducedMatrix());
            reducedCost = tempMatrix;

            path = new ArrayList<Integer>(currentNode.getCurrentPath()); //currentPath of new configuration
            newPos = list.get(i).getPos();
            path.add(newPos); // update the currenPath for new configuration
            newCost = currentNode.getTotalCost() + costMatrix[prevPos][newPos];

            if (newCost >= upperBound) return;

            if (path.size() == n) {
                if (newCost + costMatrix[newPos][0] < upperBound) {
                    path.add(0);
                    bnbSoln = path;
                    upperBound = newCost + costMatrix[newPos][0];  // a better solution is found
                    solnList.add(getTotalCost());
                    timeList.add(getTime());
                }
                return;
            }

            getMatrixReduced(prevPos, newPos);
            reduceMatrix();
            lb = lowerBound;
            lowerBound = currentNode.getLowerBound();
            reducedCost = currentNode.getCurrentReducedMatrix();

            if (lb >= upperBound) return;  // when lowerbound is too high

            myNode = new Node(lb, newCost, path, tempMatrix);

            getBNB(myNode);
        }
    }

    /* find an inital upper bound for the DFS */
    public void getInitialUB() {
        List<Integer> res = new ArrayList<Integer>();
        int st = 0, des = 0, totalCost = 0;
        res.add(0); // to store the totalcost
        res.add(st);

        while (res.size() < n) {
            des = selectNode(st);
            res.add(des);
            getMatrixReduced(st, des);
            reduceMatrix();
            totalCost += costMatrix[st][des];
            st = des;
        }

        for (int j = 0; j < n; j++) {
            if (j != 0 && reducedCost[st][j] != -1) des = j;
        }

        res.add(des);
        totalCost += costMatrix[st][des];
        getMatrixReduced(st, des);
        st = des;
        getMatrixReduced(st, 0);
        totalCost += costMatrix[st][0];
        res.add(0); //add the last node, to make it a complete path
        res.set(0, totalCost); //put in the totalcost in index 0

        upperBound = totalCost;
        res.remove(0);
        bnbSoln = res;

        lowerBound = 0;
        resetMatrix(costMatrix); // reset the reducedCost matrix
        reduceMatrix();
    }

    /* set the reduced matrix back to a specific state */
    public void resetMatrix(int[][] tempReduced) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) reducedCost[i][j] = -1;
                else reducedCost[i][j] = tempReduced[i][j];
            }
        }
    }

    /* make a copy of given matrix */
    public int[][] copyMatrix(int[][] temp) {
        int[][] dummy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dummy[i][j] = temp[i][j];
            }
        }

        return dummy;
    }

    /* find the minimum value in row i */
    public int getRowMin(int i) {
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            if (reducedCost[i][j] != -1 && reducedCost[i][j] < min) {
                min = reducedCost[i][j];
            }
        }
        if (min == Integer.MAX_VALUE) min = 0;

        return min;
    }

    /* find the minimum value in col j */
    public int getColMin(int j) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (reducedCost[i][j] != -1 && reducedCost[i][j] < min) {
                min = reducedCost[i][j];
            }
        }
        if (min == Integer.MAX_VALUE) min = 0;

        return min;
    }

    /* find the second min value in row i, exclude element [i][k], will be used to find the y + z - x value */
    public int getRowMin(int i, int k) {
        int min = Integer.MAX_VALUE, secMin = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            if (reducedCost[i][j] != -1 && costMatrix[i][j] < min) {
                min = costMatrix[i][j];
            }
        }

        for (int j = 0; j < n; j++) {
            if (reducedCost[i][j] != -1 && costMatrix[i][j] > min && costMatrix[i][j] < secMin) {
                secMin = costMatrix[i][j];
            }
        }

        return secMin;
    }

    /* find the second min value in col j, exclude element [k][j], will be used to find the y + z - x value */
    public int getColMin(int j, int k) {
        int min = Integer.MAX_VALUE, secMin = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (reducedCost[i][j] != -1 && costMatrix[i][j] < min) {
                min = costMatrix[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            if (reducedCost[i][j] != -1 && costMatrix[i][j] > min && costMatrix[i][j] < secMin) {
                secMin = costMatrix[i][j];
            }
        }

        return secMin;
    }

    /* find the minimum value in an array */
    public int getRowMin(int[] temp) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != -1 && temp[i] < min) {
                min = temp[i];
            }
        }
        return min;
    }

    /* reduce matrix for having arc i->j: set row i to -1, col j to -1, and element [j][i] to -1 */
    public void getMatrixReduced(int i, int j) {
        for (int k = 0; k < n; k++) {
            reducedCost[i][k] = -1;
            reducedCost[k][j] = -1;
        }
        reducedCost[j][i] = -1;
    }

    /* matrix reduction: find min in each row and col, minus them */
    public void reduceMatrix() {
        int rowMin = 0, colMin = 0;
        for (int i = 0; i < n; i++){
            rowMin = getRowMin(i);
            lowerBound += rowMin;
            for (int j = 0; j < n; j++) {
                if (reducedCost[i][j] != -1) {
                    reducedCost[i][j] -= rowMin;
                }
            }
        }

        for (int j = 0; j < n; j++) {
            colMin = getColMin(j);
            lowerBound += colMin;
            for (int i = 0; i < n; i++) {
                if (reducedCost[i][j] != -1) {
                    reducedCost[i][j] -= colMin;
                }
            }
        }
    }

    /* list of xyz value of possible arc from i in decreasing order */
    public List<DummyNode> getXYZ(int i) {
        List<DummyNode> list = new ArrayList<DummyNode>();

        DummyNode dummy;
        for (int j = 1; j < n; j++) {
            if (reducedCost[i][j] != -1) {
                dummy = new DummyNode(getRowMin(i, j) + getColMin(j, i) - costMatrix[i][j], j);
                list.add(dummy);
            }
        }

        // sort the list in decreasing xyz value
        Collections.sort(list, new Comparator<DummyNode>() {
            public int compare(DummyNode o1, DummyNode o2) {
                DummyNode p1 = o1;
                DummyNode p2 = o2;
                int ret = -1;

                if (p1.getXyzValue() == p2.getXyzValue()) {
                    ret = 0;
                } else if (p1.getXyzValue() < p2.getXyzValue()) {
                    ret = 1;
                } else if (p1.getXyzValue() > p2.getXyzValue()) {
                    ret = -1;
                }

                return ret;
            }
        });

        return list;
    }

    /* select the node to be added to form arc i-> pos, check row i and select the largest y + z - x value */
    public int selectNode(int i) {
        int[] temp = new int[n];
        int pos = -1, max = Integer.MIN_VALUE;

        for (int j =0; j < n; j++) {
            if (j == 0 || reducedCost[i][j] == -1) {
                temp[j] = -1;
            } else {
                temp[j] = getRowMin(i, j) + getColMin(j, i) - costMatrix[i][j];
            }

            if (temp[j] != -1 && temp[j] > max) {
                max = temp[j];
                pos = j;
            }
        }

        // keep track of the positon, if multiple nodes have the same maximum y+z-x value
        List<Integer> nodes = new ArrayList<Integer>();
        for (int j = 0; j < n; j++) {
            if (temp[j] == max && j != pos) {
                nodes.add(j);
            }
        }

        // choose the node with lower LowerBound if they all hae same y+z-x value
        if (!nodes.isEmpty()) {
            int minLowBound = getLowerBound(i, pos), tempLowBound = 0;
            for (Integer node: nodes){
                tempLowBound = getLowerBound(i, node);
                if (tempLowBound < minLowBound) {
                    minLowBound = tempLowBound;
                    pos = node;
                }
            }
        }

        return pos;
    }

    /* find the lower bound after include arc i->j */
    public int getLowerBound(int a, int b) {
        // use a new matrix, not decided to add to solution yet, don't want to reduce the current matrix
        int[][] temp = copyMatrix(reducedCost);
        int tempLB = lowerBound, lowBound = 0;

        getMatrixReduced(a,b);
        reduceMatrix();

        lowBound = lowerBound;
        lowerBound = tempLB;
        reducedCost = temp;

        return lowBound;
    }
}