package net.sf.robocode.battle.maze;
import java.util.ArrayList;
import java.util.Random;

/**
 * Cell data type for use with maze generation.
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

	public Cell(int x, int y) { 
		this.x = x;
		this.y = y;
	}

	public void setVisited() {
		this.visited = true;
	}
	
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
	
	public boolean getNorthWall() {
		return this.northWall;
	}
	
	public boolean getSouthWall() {
		return this.southWall;
	}
	
	public boolean getEastWall() {
		return this.eastWall;
	}
	
	public boolean getWestWall() {
		return this.westWall;
	}

	public boolean isEnd() {
		return end;
	}
	
	public boolean isStart() {
		return start;
	}

	public void setStart() {
		this.start = true;
	}

	public void setEnd() {
		this.end = true;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

	public void setNeighbours(ArrayList<Cell> n) {
		for (int i = 0; i < n.size(); i++) {
			if (n.get(i) == null) {
				n.remove(i);
			}
		}
		this.neighbours = n;
	}

	public boolean isDeadEnd() {
		for (Cell c : neighbours) {
			if (!c.visited) {
				return false;
			}
		}
		return true;
	}

	private ArrayList<Cell> availableNeighbours() {
		ArrayList<Cell> list = new ArrayList<Cell>();
		for (Cell c : neighbours) {
			if (!c.visited)
				list.add(c);
		}
		return list;
	}

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