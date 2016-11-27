class TSPUtil {
	public static int getEucDis(double x1, double y1, double x2, double y2) {
		return (int) Math.round(Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
	}
}