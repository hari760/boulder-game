package hw3;

import static api.Orientation.*;
import static api.CellType.*;

import java.util.ArrayList;

import api.Cell;

/**
 * Utilities for parsing string descriptions of a grid.
 */
public class GridUtil {
	/**
	 * Constructs a 2D grid of Cell objects given a 2D array of cell descriptions.
	 * String descriptions are a single character and have the following meaning.
	 * <ul>
	 * <li>"*" represents a wall.</li>
	 * <li>"e" represents an exit.</li>
	 * <li>"." represents a floor.</li>
	 * <li>"[", "]", "^", "v", or "#" represent a part of a boulder. A boulder is not a
	 * type of cell, it is something placed on a cell floor. For these descriptions
	 * a cell is created with CellType of FLOOR. This method does not create any
	 * boulders or set boulders on cells.</li>
	 * </ul>
	 * The method only creates cells and not boulders. See the other utility method
	 * findBoulders which is used to create the boulders.
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a 2D array of cells the represent the grid without any boulders present
	 */
	public static Cell[][] createGrid(String[][] desc) {
		int rows =desc.length;
		int cols = desc[0].length;
		Cell[][] board = new Cell[rows][cols];
		for(int i =0; i < rows; i ++ ) {
			for( int j=0; j < cols; j++) {
				if(desc[i][j].equals("*")) {
					board[i][j] = new Cell(i,j,WALL);
				}
				else if(desc[i][j].equals("e")) {
					board[i][j] = new Cell(i,j,EXIT);
				}
				else {
					board[i][j] = new Cell(i,j,GROUND);
				}
			}
		}
		return board;
	}

	/**
	 * Returns a list of boulders that are constructed from a given 2D array of cell
	 * descriptions. String descriptions are a single character and have the
	 * following meanings.
	 * <ul>
	 * <li>"[" the start (left most column) of a horizontal boulder</li>
	 * <li>"]" the end (right most column) of a horizontal boulder</li>
	 * <li>"^" the start (top most row) of a vertical boulder</li>
	 * <li>"v" the end (bottom most column) of a vertical boulder</li>
	 * <li>"#" inner segments of a boulder, these are always placed between the start
	 * and end of the boulder</li>
	 * <li>"*", ".", and "e" symbols that describe cell types, meaning there is not
	 * boulder currently over the cell</li>
	 * </ul>
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a list of boulders found in the given grid description
	 */
	public static ArrayList<Boulder> findBoulders(String[][] desc) {
		ArrayList<Boulder> boulders = new ArrayList<>();
		int rows =desc.length;
		int cols = desc[0].length;
		for(int i =0; i < rows; i ++ ) {
			for( int j=0; j < cols; j++) {
				int length = 1;
				if(desc[i][j].equals("[")) {
					while(!(desc[i][j+length].equals("]"))){
						length++;
					}
					boulders.add(new Boulder(i ,j,length+1,HORIZONTAL));
				}
				else if(desc[i][j].equals("^")) {
					while(!(desc[i+length][j].equals("v"))){
						length++;
					}
					boulders.add(new Boulder(i ,j,length+1,VERTICAL));
				}
			}
		}
		
		return boulders;
	}
}
