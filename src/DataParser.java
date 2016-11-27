// Parse input data xxx.tsp into a 2D-array of int
import java.util.Scanner;
import java.util.*;
import java.io.*;
class DataParser {
	/**
	 * @param: String filename "/Data/xxx.tsp"
	 * @return: int[] 2D-array of int
	 */
	public static double[][] parse(String filename) throws FileNotFoundException {
		// Init file
		File inputFile = new File(filename);
		Scanner sc = new Scanner(inputFile);

		// Skip 2 lines
		String name = sc.nextLine();
		String comment = sc.nextLine();
		String dimension = sc.nextLine();

		// parse dimension
		int size = parseDimension(dimension);

		// Skip 2 lines
		String weightType = sc.nextLine();
		String nodeSection = sc.nextLine();

		// Read node list
		double[] nodeList = new double[size * 2];
		for (int i = 0; i < size; i++) {
			String[] splitedLine = sc.nextLine().split(" ");
			nodeList[i * 2] = Double.parseDouble(splitedLine[1]);
			nodeList[i * 2 + 1] = Double.parseDouble(splitedLine[2]);
		}
		return parseNodesTo2DDoubleArray(nodeList, size);
	}

	private static double[][] parseNodesTo2DDoubleArray(double[] nodes, int size) {
		double[][] nodesMap = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				nodesMap[i][j] = getEucDis(nodes[i * 2], nodes[i * 2 + 1], nodes[j * 2], nodes[j * 2 + 1]);
			}
		}
		return nodesMap;
	}

	public static int[][] parseNodesTo2DIntArray(double[][] geoMap) {
		int size = geoMap.length;
		int[][] nodesMap = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				nodesMap[i][j] = (int) Math.round(geoMap[i][j]);
			}
		}
		return nodesMap;
	}

	private static double getEucDis(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	private static int parseDimension(String line) {
		String num = line.split(": ")[1];
		return Integer.parseInt(num);
	}

	/**
     * write result into file
     * @param tsp tsp traversal of a given graph
     * @param geoMap origianl graph
     * @param outputPath output file
     */
    public static void writeOutput(List<Integer> tsp, double[][] geoMap, String name) {
        int weight = (int) getWeight(tsp, geoMap);
        PrintWriter output;
        String outputPath = new String("../Results/" + name + ".tour");
        try {
            output = new PrintWriter(outputPath, "UTF-8");
            output.println(weight);
            for (int i = 1; i < tsp.size(); i++) {
                int source = tsp.get(i - 1);
                int target = tsp.get(i);
                output.println(source + "\t" + target + "\t" + (int) geoMap[source][target]);
            }
            System.out.println("Write finished");
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * calculate the weight of a given tsp
     * @param tsp tsp traversal of a given graph
     * @param geoMap origianl graph
     * @return the weight of a given tsp
     */
    private static double getWeight(List<Integer> tsp, double[][] geoMap) {
        int numOfTrip = tsp.size();
        double res = 0;
        for (int i = 1; i < numOfTrip; i++) {
            res += geoMap[tsp.get(i - 1)][tsp.get(i)];
        }
        return res;
    }
}
