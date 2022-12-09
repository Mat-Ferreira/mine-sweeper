package br.com.maf.ms.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Camp {
	private boolean mined = false;
	private boolean openCamp = false;
	private boolean marked = false;
	
	private List<Camp> neighbours = new ArrayList<>();
	
	private final int line;
	private final int column;
	
	public Camp(int linha, int coluna) {
		column = coluna;
		line = linha;
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

	public void showNeighbours() {
		Consumer<Camp> showInfo = c -> {
			System.out.printf("Campo(%d, %d) %n", c.line, c.column);
		};
		neighbours.stream().forEach(showInfo);
	}
}
