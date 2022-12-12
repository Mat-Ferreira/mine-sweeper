package br.com.maf.ms.logic;

import java.util.ArrayList;
import java.util.List;

import br.com.maf.ms.exception.ExplosionException;

public class Camp {
	private boolean isMineField = false;
	private boolean open = false;
	private boolean marked = false;
	
	private List<Camp> neighbours = new ArrayList<>();
	
	private final int line;
	private final int column;
	
	Camp(int linha, int coluna) {
		column = coluna;
		line = linha;
	}
	
	void switchMarked() {
		if(!open) {
			marked = !marked;
		}
	}
	
	boolean openField() {
		if(!open && !marked) {
			open = true;
			if (isMineField) {
				throw new ExplosionException();
			}
			if (safeNeighbours()) {
				neighbours.forEach(c -> c.openField());
			}
			return true;
		}
		return false;
	}
	
	boolean safeNeighbours() {
		
		return neighbours.stream().noneMatch(c -> c.isMineField);
	}
	
	boolean addNeighbour(Camp vizinho) {
		
		// Board limitations
		boolean lineWithinBoard = vizinho.line <= 10 && vizinho.line >= 0;
		boolean columnWithinBoard = vizinho.column <= 10 && vizinho.column >= 0;
		boolean campWithinBoard = lineWithinBoard && columnWithinBoard;
		
		boolean isDiferentLine = line != vizinho.line;
		boolean isDiferentColumn = column != vizinho.column;
		boolean isDiagonal = isDiferentLine && isDiferentColumn;
		
		int deltaLine = Math.abs(line - vizinho.line);
		int deltaColumn = Math.abs(column - vizinho.column);
		int deltaGeral = deltaLine + deltaColumn;
		
		
		if (campWithinBoard) {
			// If neighbour is over, under, left or right side of the Camp
			if (deltaGeral == 1 && !isDiagonal) {
				this.neighbours.add(vizinho);
				return true;
			}
			// 
			if (deltaGeral == 2 && isDiagonal) {
				this.neighbours.add(vizinho);
				return true;
			}
		}
		return false;
	}
	
	void setMine() {
		isMineField = true;
	}
	
	//Getters and setters
	public boolean isMarked() {
		return marked;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setIsOpen(boolean option) {
		open = option;
	}
}
