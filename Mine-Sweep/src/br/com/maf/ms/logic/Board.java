package br.com.maf.ms.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {
	private int lines;
	private int columns;
	private int amountOfMines;	
	
	private final List<Camp> camps = new ArrayList<>();

	public Board(int lines, int columns, int amountOfMines) {
		this.lines = lines;
		this.columns = columns;
		this.amountOfMines = amountOfMines;
		
		creatCamps();
		setNeighbours();
		setMineCamps();
		
	}

	private void creatCamps() {
		for (int l = 0; l < lines; l++) {
			for(int c = 0; c < columns; c++) {
				camps.add(new Camp(l,c));
			}
		}	
	}
	
	private void setNeighbours() {
		for (Camp c1: camps) {
			for(Camp c2: camps) {
				c1.addNeighbour(c2);
			}
		}
	}

	private void setMineCamps() {
		 int minesSet = 0;
		 Predicate<Camp> mined = c -> c.isMineField();
		 
		 do { 
			minesSet = (int) camps.stream().filter(mined).count();
			int random = (int) (Math.random() * camps.size());
			camps.get(random).setMine();	
		} 
		while (minesSet < amountOfMines);
	}
	
	public boolean reachObjective() {
		return camps.stream().allMatch(c -> c.reachObjective());
	}
	
	public void restartBoard() {
		camps.stream().forEach(c -> c.restart());
		setMineCamps();
	}
	
	//Getters & setters
	
	public int getLines() {
		return lines;
	}

	public int getColumns() {
		return columns;
	}

	public List<Camp> getCamps() {
		return camps;
	}
}
