package net.sf.robocode.battle.maze;
import java.util.ArrayList;
import java.util.Random;

/**
 * Cell data type for use with maze generation.
 * Each cell has 4 walls, one in each cardinal direction.
 * All walls start intact.
 * 
 * @author Jack Toohey
 *
 */
public class Cell {
	private boolean visited = false; //if cell was visited by the DFS
	private boolean start = false; //cell is start cell
	private boolean end = false; //cell is end cell
	private boolean northWall = true; //north wall exists
	private boolean eastWall = true; //east wall exists
	private boolean southWall = true; //south wall exists
	private boolean westWall = true; //west wall exists
	private ArrayList<Cell> neighbours; //neighbours of this cell
	private int x; //x coordinate of this cell in the maze
	private int y; //y coordinate of this cell in the maze

	/**
	 * Create a new cell at position (x, y).
	 * 
	 * @param x	x coordinate of the cell
	 * @param y	y coordinate of the cell
	 */
	public Cell(int x, int y) { 
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set cell to be visited (for use in Depth first search maze generation).
	 */
	public void setVisited() {
		this.visited = true;
	}
	
	/**
	 * Break walls between this cell and cell n.
	 * 
	 * @param n	cell to break walls between.
	 */
	public void breakWall(Cell n) {
		if (x == n.x) {
			if (y < n.y) {
				n.southWall = false;
				this.northWall = false;
			}
			if (y > n.y) {
				n.northWall = false;
				this.southWall = false;
			}
		} else if (y == n.y) {
			if (x < n.x) {
				this.eastWall = false;
				n.westWall = false;
			}
			if (x > n.x) {
				this.westWall = false;
				n.eastWall = false;
			}
		}
	}
	
	/**
	 * Return status of north wall.
	 */
	public boolean getNorthWall() {
		return this.northWall;
	}
	
	/**
	 * Return status of south wall.
	 */
	public boolean getSouthWall() {
		return this.southWall;
	}
	
	/**
	 * Return status of east wall.
	 */
	public boolean getEastWall() {
		return this.eastWall;
	}
	
	/**
	 * Return status of west wall.
	 */
	public boolean getWestWall() {
		return this.westWall;
	}

	/**
	 * Check if cell is end cell or not.
	 */
	public boolean isEnd() {
		return end;
	}
	
	/**
	 * Check if cell is start cell or not.
	 */
	public boolean isStart() {
		return start;
	}
	
	/**
	 * Set cell to be start cell.
	 */
	public void setStart() {
		this.start = true;
	}
	
	/**
	 * Set cell to be end cell.
	 */
	public void setEnd() {
		this.end = true;
	}
	
	/**
	 * Return x coordinate of cell
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Return y coordinate of cell
	 */
	public int getY(){
		return y;
	}

	/**
	 * Set neighbours of this cell to be cells inside n.
	 * Remove any null cells inside n first.
	 * 
	 * @param n ArrayList of cells adjacent to this cell.
	 */
	public void setNeighbours(ArrayList<Cell> n) {
		for (int i = 0; i < n.size(); i++) {
			if (n.get(i) == null) {
				n.remove(i);
			}
		}
		this.neighbours = n;
	}

	/**
	 * Check if cell is a dead end.
	 * If there are no non-visited neighbours, cell is a dead end.	 * 
	 */
	public boolean isDeadEnd() {
		for (Cell c : neighbours) {
			if (!c.visited) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return ArrayList of available neighbours.
	 * Check neighbours for any cells that have not yet been visited.
	 */
	private ArrayList<Cell> availableNeighbours() {
		ArrayList<Cell> list = new ArrayList<Cell>();
		for (Cell c : neighbours) {
			if (!c.visited)
				list.add(c);
		}
		return list;
	}

	/** 
	 * Choose a random neighbour from the list of available neighbours.
	 * Return cell describing this neighbour.
	 */
	public Cell randomNeighbour() {
		Random randGen = new Random();
		ArrayList<Cell> list = new ArrayList<Cell>();
		Cell randCell;
		list = availableNeighbours();
		if (!list.isEmpty()) {
			randCell = list.get(randGen.nextInt(list.size()));
		} else {
			randCell = null;
		}
		return randCell;
		
	}
}