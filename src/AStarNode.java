
public class AStarNode {
	
	private int row;
	private int column;
	private int binaryValue;
	private double heuristicCost;
	private double realCost;
	private double totalCost;
	private boolean visited;
	private AStarNode parent;
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getBinaryValue() {
		return binaryValue;
	}
	public void setBinaryValue(int binaryValue) {
		this.binaryValue = binaryValue;
	}
	public double getHeuristicCost() {
		return heuristicCost;
	}
	public void setHeuristicCost(double heuristicCost) {
		this.heuristicCost = heuristicCost;
	}
	public double getRealCost() {
		return realCost;
	}
	public void setRealCost(double realCost) {
		this.realCost = realCost;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public AStarNode getParent() {
		return parent;
	}
	public void setParent(AStarNode parent) {
		this.parent = parent;
	}
	
	
	
}
