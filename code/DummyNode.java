/*
* Created by Xiaoqin Zhu
*
* DummyNode keep track of y+z-x value for having arc a->b and the value of node b
* Used in the DFS search: select node b that has largest y+z-x value among possible nodes avaliable to node a
*
*/

public class DummyNode implements Comparable<DummyNode> {
    private int xyzValue;
    private int pos;

    DummyNode(int xyzValue, int pos) {
        this.xyzValue = xyzValue;
        this.pos = pos;
    }

    public int getXyzValue() {
        return xyzValue;
    }

    public int getPos() {
        return pos;
    }

    // want to be in decreasing order of xyzValue
    public int compareTo(DummyNode other) {
        if (xyzValue > other.getXyzValue()) return -1;
        else if (xyzValue < other.getXyzValue())return 1;
        else return 0;
    }

    public String toString() {
        return "{xyzValue: " + this.xyzValue + "  pos: " + this.pos + "}";
    }
}
