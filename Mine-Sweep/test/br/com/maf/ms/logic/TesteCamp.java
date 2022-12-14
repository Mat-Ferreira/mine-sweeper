package br.com.maf.ms.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.maf.ms.exception.ExplosionException;

class TesteCamp {

	private Camp campo;

	@BeforeEach
	void generateCamp() {
		campo = new Camp(3,3);
	}
	
	@Test
	void addedTopSideNeighbourSucessfully() {
		Camp vizinho = new Camp(2,3);
		boolean result = campo.addNeighbour(vizinho);
		assertTrue(result);
	}
	
	@Test
	void addedUnderSideNeighbourSucessfully() {
		Camp vizinho = new Camp(4,3);
		boolean result = campo.addNeighbour(vizinho);
		assertTrue(result);
	}
	
	@Test
	void addedLeftSideNeighbourSucessfully() {
		Camp vizinho = new Camp(3,2);
		boolean result = campo.addNeighbour(vizinho);
		assertTrue(result);
	}	
	
	@Test
	void addedRightSideNeighbourSucessfully() {
		Camp vizinho = new Camp(3,4);
		boolean result = campo.addNeighbour(vizinho);
		assertTrue(result);
	}
	
	@Test
	void addedDiagonalNeighbourSucessfully() {
		Camp vizinho = new Camp(2,2);
		boolean result = campo.addNeighbour(vizinho);
		assertTrue(result);
	}
	
	@Test
	void failToAddCampThatIsNotNeighbour() {
		Camp vizinho = new Camp(5,7);
		boolean result = campo.addNeighbour(vizinho);
		assertFalse(result);
	}
	
	@Test
	void defaultValueOfIsMarkedField() {
		assertFalse(campo.isMarked());
	}
	
	@Test
	void alternateIsMarkedField() {
		campo.switchMarked();
		assertTrue(campo.isMarked());
	}
	@Test
	void alternateIsMarkedFieldTwice() {
		campo.switchMarked();
		campo.switchMarked();
		assertFalse(campo.isMarked());
	}

	@Test
	void openSafeCamp() {
		assertTrue(campo.openField());
	}
	
	@Test
	void openSafeOpenedCamp() {
		campo.setIsOpen(true);
		assertFalse(campo.openField());
	}
	
	@Test
	void openSafeMarkedCamp() {
		campo.switchMarked();
		assertFalse(campo.openField());
	}

	@Test
	void openMinedMarkedCamp() {
		campo.switchMarked();
		campo.setMine();
		assertFalse(campo.openField());
	}
	
	@Test
	void openMinedUnmarkedCamp() {
		campo.setMine();
		assertThrows(ExplosionException.class, () -> {
			campo.openField();			
		});
	}
	
	@Test
	void openSafeNeighbours() {
		Camp vizinho1 = new Camp(2,2);
		Camp vizinho2 = new Camp(1,1);
		Camp vizinho3 = new Camp(0,0);
		
		campo.addNeighbour(vizinho1);
		vizinho1.addNeighbour(vizinho2);
		vizinho2.addNeighbour(vizinho3);
		
		campo.openField();
		
		assertTrue(vizinho1.isOpen() && vizinho2.isOpen());
	}
	
	@Test
	void openNonNeighbours() {
		Camp vizinho1 = new Camp(2,2);
		Camp vizinho2 = new Camp(1,1);
		Camp vizinho3 = new Camp(7,2);
				
		campo.addNeighbour(vizinho1);
		vizinho1.addNeighbour(vizinho2);
		vizinho2.addNeighbour(vizinho3);
		
		assertFalse(vizinho2.isOpen() && vizinho3.isOpen());
	}
	
	@Test
	void restartSetsEveryCampAtributeToFalse() {
		campo.setMine();
		campo.switchMarked();
		campo.setIsOpen(true);
		
		campo.restart();
		
		assertFalse(campo.isMineField() || campo.isMarked() || campo.isOpen());
	}
		
	@Test
	void markedCampReturnsXfromToString() {
		campo.switchMarked();
		String result = campo.toString();
		assertEquals("x", result);
	}
	
	@Test
	void openMinedCampReturnsStarFromToString() {
		campo.openField();
		campo.setMine();
		String result = campo.toString();
		assertEquals("*", result);
	}
	
	@Test
	void openCampWithMinedNeighbourReturnsBombNumbersFromToString() {
		Camp n1 = new Camp(2,2);
		n1.setMine();
		Camp n2 = new Camp(2,3);
		n1.setMine();
		Camp n3 = new Camp(4,4);
		
		n1.setMine();
		n2.setMine();
		n3.setMine();
		
		campo.addNeighbour(n1);
		campo.addNeighbour(n2);
		campo.addNeighbour(n3);
		campo.openField();
		
		String result = campo.toString();
		assertEquals("3", result);
	}
	
	@Test
	void openCampWithNoMinedNeighbourReturnsBlankFromToString() {
		Camp n1 = new Camp(2,2);
		Camp n2 = new Camp(2,3);
		Camp n3 = new Camp(4,4);
		
		campo.addNeighbour(n1);
		campo.addNeighbour(n2);
		campo.addNeighbour(n3);
		
		campo.openField();
		String result = campo.toString();
		assertEquals(" ", result);
	}
	
	@Test
	void closedCampReturnsInterrogationFromToString() {
		String result = campo.toString();
		assertEquals("?", result);
	}
	
	@Test
	void openAndSafeCampReachedTheObjective() {
		campo.setIsOpen(true);
		assertTrue(campo.reachObjective());
	}
	
	@Test
	void markedMinedCampReachedTheObjective() {
		campo.setMine();
		campo.switchMarked();
		assertTrue(campo.reachObjective());
	}
	
	@Test
	void closedCampoDidntReachObjective() {
		assertFalse(campo.reachObjective());
	}
}
