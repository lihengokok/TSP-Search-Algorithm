package uf;

/**
 * Created by Huanli_Wang on 11/11/16.
 */
public class UF {

    private int[] parent;  // parent[i] = parent of i

    public UF(int n) {
        if (n < 0) throw new IllegalArgumentException();
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }
    }

    public int find(int p) {
        if (parent[p] < 0) {
            return p;
        }
        parent[p] = find(parent[p]);
        return parent[p];
    }

    public boolean connected(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        return rootP == rootQ && rootP != -1;
    }


    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (parent[rootP] < parent[rootQ]) {
            parent[rootP] += parent[rootQ];
            parent[rootQ] = rootP;
        } else {
            parent[rootQ] += parent[rootP];
            parent[rootP] = rootQ;
        }
        return;
    }

}


