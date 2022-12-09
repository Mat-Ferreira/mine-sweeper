package br.com.maf.ms.logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

}
