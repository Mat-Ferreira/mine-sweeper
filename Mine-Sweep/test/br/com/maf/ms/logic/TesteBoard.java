package br.com.maf.ms.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.maf.ms.exception.ExplosionException;

class TesteBoard {
	
	private Board board;
	
	@BeforeEach
	void setUp() {
		board = new Board(5,5,5);
	}

	@Test
	void TestTotalAmountOfCampsOnBoard() {
		int totalCamps = board.getCamps().size();
		int finalValue = board.getColumns() * board.getLines();
		assertEquals(finalValue, totalCamps);
	}
	
	@Test
	void TestRestartBoardWorking() {
		List<Camp> firstMines = new ArrayList<>();
		List<Camp> newMines = new ArrayList<>();
		boolean result = false;
		
		
		board.getCamps().stream()
		.filter(c -> c.isMineField())
		.forEach(c -> firstMines.add(c));
		
		board.restartBoard();
		
		board.getCamps().stream()
		.filter(c -> c.isMineField())
		.forEach(c -> newMines.add(c));
		
		for (int i = 0; i < firstMines.size(); i++) {
			Camp a = firstMines.get(i);
			Camp b = newMines.get(i);
			
			if (!(a.getColumn() == b.getColumn()) || !(a.getLine() == b.getLine())) {
				result = true;
				break;
			}
		}
		
		assertTrue(result);
	}
	
	@Test
	void TestDoesntReachObjectiveWithBaseBoard() {
		assertFalse(board.reachObjective());
	}

	@Test
	void TestBoardToStringWorking() {
		board = new Board(1,3,0);
		String result = board.toString();
		assertEquals(" ?  ?  ? \n", result);
	}
	
	@Test
	void TestOpeningClosedCampWorking() {
		boolean result = false;
		try {
		result = board.openCamp(3, 3);
		} catch (ExplosionException e) {
			result = true;
		} finally {
			assertTrue(result);
		} 

	
	}
	
	@Test
	void TestMarkingCamp() {
		board = new Board(1,3,1);
		board.switchMark(0, 2);
		String result = board.toString();
		assertEquals(" ?  ?  x \n", result);
	}
}
