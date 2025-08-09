package hw3;

import static api.Direction.*;
import static api.Orientation.*;

import java.util.ArrayList;

import api.Cell;
import api.Direction;
import api.Move;

/**
 * Represents a board in the game. A board contains a 2D grid of cells and a
 * list of boulders that slide over the cells.
 */
public class Board {
	/**
	 * 2D array of cells, the indexes signify (row, column) with (0, 0) representing
	 * the upper-left corner of the board.
	 */
	private Cell[][] grid;

	/**
	 * A list of boulders that are positioned on the board.
	 */
	private ArrayList<Boulder> boulders;

	/**
	 * A list of moves that have been made in order to get to the current position
	 * of boulders on the board.
	 */
	private ArrayList<Move> moveHistory;

	/**
	 * Constructs a new board from a given 2D array of cells and list of boulders. The
	 * cells of the grid should be updated to indicate which cells have boulders
	 * placed over them (i.e., setBoulder() method of Cell). The move history should
	 * be initialized as empty.
	 * 
	 * @param grid   a 2D array of cells which is expected to be a rectangular shape
	 * @param boulders list of boulders already containing row-column position which
	 *               should be placed on the board
	 */
	
	
	/**
	 * This variable keeps track of the boulder that is grabbed currently.
	 */
	private Boulder grabbedBoulder;
	
	/**
	 * This variable keeps track of whether the game is over or not.
	 */
	private boolean isGameOver;
	
	/**
	
	 * Constructs a new board from a given 2D array of Cells and a ArrayList of boulders.
	 * The given constructor also initialize the movesHistroy,
	 * the grabbed boulder and the isGameOver variables
	 * 
	 * @param grid 2D array of Cells
	 * @param boulders An ArrayList of boulders
	 * 
	 */ 
	 public Board(Cell[][] grid, ArrayList<Boulder> boulders) {
		this.grid = grid;
		this.boulders = boulders;
		reset();
			
		
		this.moveHistory = new ArrayList<Move>();
		this.grabbedBoulder = null;
		isGameOver= false;
	}

	/**
	 * DO NOT MODIFY THIS CONSTRUCTOR
	 * <p>
	 * Constructs a new board from a given 2D array of String descriptions.
	 * 
	 * @param desc 2D array of descriptions
	 */
	public Board(String[][] desc) {
		this(GridUtil.createGrid(desc), GridUtil.findBoulders(desc));
	}

	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return number of rows
	 */
	public int getRowSize() {
		return grid.length;
	}

	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return number of columns
	 */
	public int getColSize() {
		// TODO
		return grid[0].length;
	}

	/**
	 * Returns the cell located at a given row and column.
	 * 
	 * @param row the given row
	 * @param col the given column
	 * @return the cell at the specified location
	 */
	public Cell getCellAt(int row, int col) {
		
		return grid[row][col];
		
		
	}

	/**
	 * Returns the total number of moves (calls to moveGrabbedBoulder which
	 * resulted in a boulder being moved) made so far in the game.
	 * 
	 * @return the number of moves
	 */
	public int getMoveCount() {
		
		return moveHistory.size();
	}

	/**
	 * Returns a list of all boulders on the board.
	 * 
	 * @return a list of all boulders
	 */
	public ArrayList<Boulder> getBoulders() {
		// TODO
		return boulders;
	}

	/**
	 * Returns true if the player has completed the puzzle by positioning a boulder
	 * over an exit, false otherwise.
	 * 
	 * @return true if the game is over
	 */
	public boolean isGameOver() {
		for(int  i =0; i < getRowSize(); i++) {
			for( int j = 0; j <getColSize();j++) {
				if(grid[i][j].isExit() && grid[i][j].hasBoulder()) {
					return true;
					
				}
			}
		}
		return false;
	}
	
	/**
	 * Models the user grabbing (mouse button down) a boulder over the given row and
	 * column. The purpose of grabbing a boulder is for the user to be able to drag
	 * the boulder to a new position, which is performed by calling
	 * moveGrabbedBoulder().
	 * <p>
	 * This method should find which boulder has been grabbed (if any) and record
	 * that boulder as grabbed in some way.
	 * 
	 * @param row row to grab the boulder from
	 * @param col column to grab the boulder from
	 */
	public void grabBoulderAt(int row, int col) {
		if(row< getRowSize() && 
				col < getColSize() && 
				row >=0 && col >= 0 && grid[row][col].getBoulder() != null ) {
			grabbedBoulder = grid[row][col].getBoulder();
		}
	}

	/**
	 * Models the user releasing (mouse button up) the currently grabbed boulder
	 * (if any). Update the object accordingly to indicate no boulder is
	 * currently being grabbed.
	 */
	public void releaseBoulder() {
		grabbedBoulder = null;
	}

	/**
	 * Returns the currently grabbed boulder. If there is no currently grabbed
	 * boulder the method return null.
	 * 
	 * @return the currently grabbed boulder or null if none
	 */
	public Boulder getGrabbedBoulder() {
		// TODO
		return grabbedBoulder;
	}

	/**
	 * Returns true if the cell at the given row and column is available for a
	 * boulder to be placed over it. Boulders can only be placed over ground
	 * and exits. Additionally, a boulder cannot be placed over a cell that is
	 * already occupied by another boulder.
	 * 
	 * @param row row location of the cell
	 * @param col column location of the cell
	 * @return true if the cell is available for a boulder, otherwise false
	 */
	public boolean isAvailable(int row, int col) {
		return (row< getRowSize() && 
				col < getColSize() && 
				row >=0 && col >= 0 &&!(grid[row][col].isWall() ||grid[row][col].hasBoulder()));
	}

	/**
	 * Moves the currently grabbed boulder by one cell in the given direction. A
	 * horizontal boulder is only allowed to move right and left and a vertical boulder
	 * is only allowed to move up and down. A boulder can only move over a cell that
	 * is a floor or exit and is not already occupied by another boulder. The method
	 * does nothing under any of the following conditions:
	 * <ul>
	 * <li>The game is over.</li>
	 * <li>No boulder is currently grabbed by the user.</li>
	 * <li>A boulder is currently grabbed by the user, but the boulder is not allowed to
	 * move in the given direction.</li>
	 * </ul>
	 * If none of the above conditions are meet, the method does at least the following:
	 * <ul>
	 * <li>Moves the boulder object by calling its move() method.</li>
	 * <li>Calls placeBoulder() for the grid cell that the boulder is being moved into.</li>
	 * <li>Calls removeBoulder() for the grid cell that the boulder is being moved out of.</li>
	 * <li>Adds the move (as a Move object) to the end of the move history list.</li>
	 * <li>Increments the count of total moves made in the game.</li>
	 * </ul>
	 * 
	 * @param dir the direction to move
	 */
	public void moveGrabbedBoulder(Direction dir) {
		if(!isGameOver && getGrabbedBoulder() != null) {
			int row    = getGrabbedBoulder().getFirstRow();
			int col    = getGrabbedBoulder().getFirstCol();
			int length = getGrabbedBoulder().getLength();
			if(getGrabbedBoulder().getOrientation() == VERTICAL) {
				if (dir == UP && 
				isAvailable(row-1,col)) {
					grid[row+length-1][col].removeBoulder();
					grid[row-1][col].placeBoulder(getGrabbedBoulder());
					getGrabbedBoulder().move(dir);
					moveHistory.add(new Move(getGrabbedBoulder(),dir));
				}
				else if (dir == DOWN && 
				isAvailable(row+length,col)) {
					grid[row][col].removeBoulder();
					grid[row+length][col].placeBoulder(getGrabbedBoulder());
					getGrabbedBoulder().move(dir);
					moveHistory.add(new Move(getGrabbedBoulder(),dir));
				}		
				
				
			}
			else if(getGrabbedBoulder().getOrientation() == HORIZONTAL) {
				if (dir == LEFT && 
				isAvailable(row,col-1)) {
					grid[row][col+length-1].removeBoulder();
					grid[row][col-1].placeBoulder(getGrabbedBoulder());
					getGrabbedBoulder().move(dir);
					moveHistory.add(new Move(getGrabbedBoulder(),dir));
				}
				else if (dir == RIGHT && 
				isAvailable(row,col+length)) {
					grid[row][col].removeBoulder();
					grid[row][col+length].placeBoulder(getGrabbedBoulder());
					getGrabbedBoulder().move(dir);
					moveHistory.add(new Move(getGrabbedBoulder(),dir));
				}		
				
				
			}
			isGameOver = isGameOver();
		}
	}

	/**
	 * Resets the state of the game back to the start, which includes the move
	 * count, the move history, and whether the game is over. The method calls the
	 * reset method of each boulder object. It also updates each grid cells by calling
	 * their setBoulder method to either set a boulder if one is located over the cell
	 * or set null if no boulder is located over the cell.
	 */
	public void reset() {
		int row;
		int col;
		int length;
		
		for(int  i =0; i < getRowSize(); i++) {
			for( int j = 0; j <getColSize();j++) {
				grid[i][j].removeBoulder();
			}
		}
		
		for(Boulder boulder: this.boulders) {
			boulder.reset();
			row = boulder.getFirstRow();
			col = boulder.getFirstCol();
			length = boulder.getLength();
			
			if(boulder.getOrientation() == HORIZONTAL && isAvailable(row,col) && isAvailable(row,col+length-1) ) {
				for( int i = 0; i < boulder.getLength(); i ++) {
					this.grid[boulder.getFirstRow()][boulder.getFirstCol()+i].placeBoulder(boulder);
				}
			}
			else if(boulder.getOrientation() == VERTICAL && isAvailable(row,col) && isAvailable(row+ length-1,col)) {
				for( int i = 0; i < boulder.getLength(); i ++) {
					this.grid[boulder.getFirstRow()+i][boulder.getFirstCol()].placeBoulder(boulder);
				}
			}
		}
		if(getMoveHistory()!= null) {
			getMoveHistory().clear();
		}
		isGameOver = false;
		
			
		
	 }


	/**
	 * Returns a list of all legal moves that can be made by any boulder on the
	 * current board.
	 * 
	 * @return a list of legal moves
	 */
	public ArrayList<Move> getAllPossibleMoves() {
		
		ArrayList<Move> possibleMoves = new ArrayList<>();
        if(!isGameOver) {
        	for(Boulder boulder:getBoulders()) {
    			int row    = boulder.getFirstRow();
    			int col    = boulder.getFirstCol();
    			if(boulder.getOrientation() == VERTICAL) {
    				if (isAvailable(row-1,col)) {
    					possibleMoves.add(new Move(boulder,UP));
    				}
    				if (isAvailable(row+boulder.getLength(),col)) {
    					possibleMoves.add(new Move(boulder,DOWN));
    				}		
    				
    				
    			}
    			else if(boulder.getOrientation() == HORIZONTAL) {
    				if (isAvailable(row,col-1)) {
    					possibleMoves.add(new Move(boulder,LEFT));
    				}
    				if (isAvailable(row,col+boulder.getLength())) {
    					possibleMoves.add(new Move(boulder,RIGHT));
    				}		
    				
    				
    			}
    		}
        }
		
		return possibleMoves;
	}

	/**
	 * Gets the list of all moves performed to get to the current position on the
	 * board.
	 * 
	 * @return a list of moves performed to get to the current position
	 */
	public ArrayList<Move> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * EXTRA CREDIT 5 POINTS
	 * <p>
	 * This method is only used by the Solver.
	 * <p>
	 * Undo the previous move. The method gets the last move on the moveHistory list
	 * and performs the opposite actions of that move, which are the following:
	 * <ul>
	 * <li>if required, sets is game over to false</li>
	 * <li>grabs the moved boulder and calls moveGrabbedBoulder passing the opposite
	 * direction</li>
	 * <li>decreases the total move count by two to undo the effect of calling
	 * moveGrabbedBoulder twice</li>
	 * <li>removes the move from the moveHistory list</li>
	 * </ul>
	 * If the moveHistory list is empty this method does nothing.
	 */
	public void undoMove() {
		if(getMoveCount()>0) {
			isGameOver= false;
			
			Boulder boulder = moveHistory.get(getMoveCount()-1).getBoulder();
			grabBoulderAt(boulder.getFirstRow(),boulder.getFirstCol());
			Direction dir = moveHistory.get(getMoveCount()-1).getDirection();
			moveHistory.remove(getMoveCount()-1);
		
			if(dir==UP) {
				moveGrabbedBoulder(DOWN);
			}
			else if(dir==DOWN) {
				moveGrabbedBoulder(UP);
			}
			else if(dir==LEFT) {
				moveGrabbedBoulder(RIGHT);
			}
			else  {
				moveGrabbedBoulder(LEFT);
			}
			
			moveHistory.remove(getMoveCount()-1);
			
			
		}
		
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		for (Cell row[] : grid) {
			if (!first) {
				buff.append("\n");
			} else {
				first = false;
			}
			for (Cell cell : row) {
				buff.append(cell.toString());
				buff.append(" ");
			}
		}
		return buff.toString();
	}
}
