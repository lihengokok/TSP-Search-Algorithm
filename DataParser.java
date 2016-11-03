// Parse input data xxx.tsp into a 2D-array of double
import java.util.Scanner;
import java.io.*;
class DataParser {
	/**
	 * @param: String filename "/Data/xxx.tsp"
	 * @return: double[] 2D-array of double
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
		return parseNodesTo2DArray(nodeList, size);
	}

	private static double[][] parseNodesTo2DArray(double[] nodes, int size) {
		double[][] nodesMap = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				nodesMap[i][j] = getEucDis(nodes[i * 2], nodes[i * 2 + 1], nodes[j * 2], nodes[j * 2 + 1]);
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
}