import java.io.*;
import java.util.*;

public class hw1 {
	
	public static void main(String args[]){
		
		//definitions
		String line = null;
		List<AStarNode[]> binaryMatrix = new ArrayList<AStarNode[]>();			//create binary matrix
		int rowCounter=0;
		
		try {
        	//READİNG FİLE
			FileReader fileReader = new FileReader(args[0]);  					// FileReader reads text files in the default encoding.
			BufferedReader bufferedReader = new BufferedReader(fileReader);		// Always wrap FileReader in BufferedReader.
            
        	//WRİTİNG FİLE
        	FileWriter fileWriter = new FileWriter(args[1]);					// Assume default encoding.
        	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);		// Always wrap FileWriter in BufferedWriter.
        	
        	
        	while((line = bufferedReader.readLine()) != null) {					//read each line and pass fillBinaryMatrix function
            	
            	fillBinaryMatrix(binaryMatrix, line, rowCounter++);
            }
        	
        	AStarNode shortestPath = findShortestPathWithAStarSearchAlgorithm(binaryMatrix);	// call A Star Search Algorithm and return goal path
        	
        	printShortestPath(shortestPath, bufferedWriter);									//display path row and column in output text
        	
        	bufferedReader.close();			// Always close files ;)
            bufferedWriter.close();
        }
        catch(IOException ex) {
        	
            System.out.println("Error reading or writing!");
        }
		
	}//end main function
	
	//this function is created nodes and fill matrix
	public static void fillBinaryMatrix(List<AStarNode[]> binaryMatrix, String line, int row){
		
		String[] lineArray = line.split(" ");							//split line by space
		
		AStarNode[] rowArray = new AStarNode[lineArray.length];			//create row for binaryMatrix 
		
		for(int column=0; column<lineArray.length; column++){			//create AStarNode for every cell and
																		//define variables
				AStarNode newAStarNode = new AStarNode();
				
				newAStarNode.setRow(row);
				newAStarNode.setColumn(column);
				newAStarNode.setBinaryValue( Integer.parseInt(lineArray[column]) );
				newAStarNode.setHeuristicCost(0.0);
				newAStarNode.setRealCost(0.0);
				newAStarNode.setTotalCost(0.0);
				newAStarNode.setVisited(false);
				newAStarNode.setParent(null);
				
				rowArray[column] = newAStarNode;						//add AStarNode in row array
			
		}
		binaryMatrix.add(rowArray);										//add row in binary matrix
		
	}//end fillBinaryMatrix function
	
	//this function is A Star Search Algorithm
	public static AStarNode findShortestPathWithAStarSearchAlgorithm(List<AStarNode[]> binaryMatrix){
		
		//definitions
		List<AStarNode> openList = new ArrayList<AStarNode>();
		int startRow=0, startColum=0, endRow=binaryMatrix.size()-1, endColumn=binaryMatrix.get(0).length-1;
		
		
		openList.add(binaryMatrix.get(startRow)[startColum]); 											//add start node in open list
		
		while( openList.size() > 0 ){
			
			AStarNode current = getLowestTotalCostNodeInOpenList( openList );			//current node is lowest total cost in open list
			
			if( current.getRow() == endRow && current.getColumn() == endColumn ){		//if current node is goal node the found shortest path
				
				return binaryMatrix.get(endRow)[endColumn];								//return goal node
			}
			
			removeCurrentFromOpenList(openList, current);								//remove current in open list
			
			binaryMatrix.get( current.getRow() )[current.getColumn()].setVisited(true);	//current is visited
			
			
			
			List<AStarNode> neighbors = new ArrayList<AStarNode>();						//create neighbors list
        	
        	if(current.getColumn()+1 <= endColumn){		//add right neighbor
        		
        		neighbors.add( binaryMatrix.get(current.getRow())[current.getColumn()+1] );
        	}
        	
        	if(current.getRow()+1 <= endRow){			//add bottom neighbor
        		
        		neighbors.add( binaryMatrix.get(current.getRow()+1)[current.getColumn()] );
        	}	
        	
        	if(current.getColumn()-1 >= 0){				//add left neighbor
        		
        		neighbors.add( binaryMatrix.get(current.getRow())[current.getColumn()-1] );
        	}
        	
        	if(current.getRow()-1 >= 0){				//add top neighbor
        		
        		neighbors.add( binaryMatrix.get(current.getRow()-1)[current.getColumn()] );
        	}
        	
        	for( int i=0; i<neighbors.size(); i++ ){			// for each neighbor
        		
        		AStarNode neighbor = neighbors.get(i);			//create neighbor node
        		
        		if(neighbor.getBinaryValue() == 1 || neighbor.isVisited()==true ){		//if neighbor is wall or visited
        			continue;
        		}
        		
        		double realCost = current.getRealCost()+1;		//real cost that a node from another node is 1
        		boolean realCostIsBest = false;
        		
        		if( !openList.contains(neighbor) ){				//if neighbor is not in open list
        			realCostIsBest = true;
        			neighbor.setHeuristicCost( Math.sqrt( Math.pow( (neighbor.getRow()-endRow) , 2.0) + Math.pow( (neighbor.getColumn()-endColumn) , 2.0) ) );
        			openList.add(neighbor);
        		}
        		else if(realCost < neighbor.getRealCost()){		
        			realCostIsBest = true;
        		}
        		
        		if(realCostIsBest){			//if real cost is best
        			
        			neighbor.setParent(current);	            // This path is the best until now. Record it!
        			neighbor.setRealCost( realCost );
        			neighbor.setTotalCost(neighbor.getHeuristicCost()+neighbor.getRealCost() );
        			
        		}
        		
        	}// end for
        	
			
		}//end while
		
		return null;			//if return null then there is not path start node from goal node
		
	}//end findShortestPathWithAStarSearchAlgorithm function
	
	//this function return lowest cost node in open list
	public static AStarNode getLowestTotalCostNodeInOpenList(List<AStarNode> openList){
		
		AStarNode lowestNode = openList.get(0);
		
		for(AStarNode node : openList){			//for each node in open list
			
			if( node.getHeuristicCost()+node.getRealCost() < lowestNode.getHeuristicCost()+lowestNode.getRealCost() ){	//if total cost is than better
				
				lowestNode = node;
			}
		}
		
		return lowestNode;
	}//end getLowestTotalCostNodeInOpenList function
	
	//this function remove current node in open list
	public static void removeCurrentFromOpenList( List<AStarNode> openList, AStarNode current  ){
		
		int i=0;	//current node index
		
		for(int j=0; j<openList.size(); j++){		//find current node in open list
    		if( current.getRow() == openList.get(j).getRow() && current.getColumn() == openList.get(j).getColumn() ){
    			i = j;
    			break;
    		}
    	}
    	openList.remove(i);
		
	}// end removeCurrentFromOpenList function
	
	//this function display shortest path's row and column on output file
	public static void printShortestPath(AStarNode shortestPath, BufferedWriter bufferedWriter){
		
		List<String> path = new ArrayList<String>();		//shortest path list
		
		
		while(shortestPath.getParent() != null){			//start node from goal node
			
			path.add( Integer.toString(shortestPath.getRow()+1)+" "+Integer.toString( (shortestPath.getColumn()+1) ) );
			shortestPath = shortestPath.getParent();
		}
		
		path.add("1 1");									//add start node in list
		
		for(int y=path.size()-1; y>=0; y--){				//display row and column
			try {
				bufferedWriter.write(path.get(y));
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}//end printShortestPath function

}