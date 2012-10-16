package net.sf.robocode.battle.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import robocode.BattleRules;
import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.peer.ObstaclePeer;

/** 
 * Generates a maze for the given battlefield using obstacles.
 * 
 * @author Jack Toohey
 *
 */
public class Maze {
	private ArrayList<Cell> cellMatrix = new ArrayList<Cell>();
	private int cellWidth = 64;
	private int cellHeight = 64;
	private int wallHeight = 32;
	private int wallWidth = 32;
	private int numCells = 0;
	private int lastCellY = 0;
	private int lastCellX = 0;
	private Cell curCell;
	private Cell nextCell;
	private Stack<Cell> cellStack = new Stack<Cell>();
	
	public Maze(BattleProperties bp, int cellWidth, int cellHeight, int wallWidth, int wallHeight) {
		this.cellHeight = cellHeight;
		this.cellWidth = cellWidth;
		this.wallWidth = wallWidth;
		this.wallHeight = wallHeight;
		this.initMaze(bp);
		this.initNeighbours(bp);
		this.cellMatrix.get(0).isStart();
		this.cellMatrix.get(numCells - 1).isEnd();
		this.curCell = this.cellMatrix.get(0);
		this.curCell.setVisited();
		this.nextCell = this.curCell.randomNeighbour();
		this.nextCell.setVisited();
		this.curCell.breakWall(nextCell);
		this.cellStack.push(this.curCell);
		this.curCell = this.nextCell;
		this.recurseCell();
	}
	
	private void initMaze(BattleProperties bp) {
		for (int y = cellHeight / 2; y < bp.getBattlefieldHeight();) {
			for (int x = cellWidth / 2; x < bp.getBattlefieldWidth();) {
				this.cellMatrix.add(new Cell(x, y));
				numCells++;
				x += cellWidth;
				if (x >= bp.getBattlefieldWidth()) {
					lastCellX = x - cellWidth;
					break;
				} else {
					x += wallWidth;
				}
			}
			y += cellHeight;
			if (y >= bp.getBattlefieldHeight()) {
				lastCellY = y - cellHeight;
				break;
			} else {
				y += wallHeight;
			}
		}
	}
	
	private void recurseCell() {	
		while (!this.cellStack.isEmpty()) {
			 if (this.curCell.isDeadEnd() || this.curCell.isEnd() || this.curCell.isStart()) {
				 this.nextCell = this.cellStack.pop();				 
				 this.curCell = this.nextCell;
			 } else {
				 this.nextCell = this.curCell.randomNeighbour();
				 this.nextCell.setVisited();
				 this.curCell.breakWall(nextCell);
				 this.cellStack.push(this.curCell);
				 this.curCell = this.nextCell;
			 }
		}
			return;
	}
	
	
	private void initNeighbours(BattleProperties bp) {
		for (int y = cellHeight / 2; y < bp.getBattlefieldHeight();) {
			for (int x = cellWidth / 2; x < bp.getBattlefieldWidth();) {
				ArrayList<Cell> n = new ArrayList<Cell>();
				Cell c = getCell(x, y);
				if (y != cellHeight) {
					n.add(getCell(x, y - cellHeight - wallHeight));
				}
				if (y != lastCellY - 1) {
					n.add(getCell(x, y + cellHeight + wallHeight));
				}
				if (x != cellWidth) {
					n.add(getCell(x - cellWidth - wallWidth, y));
				}
				if (x != lastCellX - 1) {
					n.add(getCell(x + cellWidth + wallWidth, y));
				}
				for (int i = 0; i < n.size(); i++) {
					if (n.get(i) == null) {
						n.remove(i);
					}
				}
				c.setNeighbours(n);
				x += cellWidth;
				if (x >= bp.getBattlefieldWidth()) {
					break;
				} else {
					x += wallWidth;
				}
			}
			y += cellHeight;
			if (y >= bp.getBattlefieldHeight()) {
				break;
			} else {
				y += wallHeight;
			}
		}
	}
	
	public Cell getCell(int x, int y) {
		for (Cell c : cellMatrix) {
			if ((c.getX() == x) && (c.getY() == y)) {
				return c;
			}
		}
		return null;
	}
	
	public List<ObstaclePeer> wallList(BattleRules battleRules, Battle battle) {
		List<ObstaclePeer> walls = new ArrayList<ObstaclePeer>();
		int id = 0;
		Cell cell;
		ObstaclePeer newWall;
		for (int i = 0; i < this.cellMatrix.size(); i++) {
				cell = this.cellMatrix.get(i);
				if (cell.getNorthWall()) {
					newWall = new ObstaclePeer(battle, battleRules, id); id++;
					newWall.setWidth(cellWidth + 2 * wallWidth);
					newWall.setHeight(wallHeight);
					newWall.setX(cell.getX());
					newWall.setY(cell.getY() + (cellHeight / 2) + (wallHeight / 2));
					walls.add(newWall);
				}
				if (cell.getSouthWall()) {
					newWall = new ObstaclePeer(battle, battleRules, id); id++;
					newWall.setWidth(cellWidth + 2 * wallWidth);
					newWall.setHeight(wallHeight);
					newWall.setX(cell.getX());
					newWall.setY(cell.getY() - (cellHeight / 2) - (wallHeight / 2));
					walls.add(newWall);
				}
				if (cell.getEastWall()) {
					newWall = new ObstaclePeer(battle, battleRules, id); id++;
					newWall.setHeight(cellHeight + 2 * wallHeight);
					newWall.setWidth(wallWidth);
					newWall.setX(cell.getX() + (cellWidth / 2) + (wallWidth / 2));
					newWall.setY(cell.getY());
					walls.add(newWall);
				}
				if (cell.getWestWall()) {
					newWall = new ObstaclePeer(battle, battleRules, id); id++;
					newWall.setHeight(cellHeight + 2 * wallHeight);
					newWall.setWidth(wallWidth);
					newWall.setX(cell.getX() - (cellWidth / 2) - (wallWidth / 2));
					newWall.setY(cell.getY());
					walls.add(newWall);
				}
			}
		return walls;
	}
	
}
