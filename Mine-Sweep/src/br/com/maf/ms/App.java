package br.com.maf.ms;

import br.com.maf.ms.logic.Board;
import br.com.maf.ms.view.BoardConsole;

public class App {

	public static void main(String[] args) {
		Board board = new Board(5,6,5);
		new BoardConsole(board);
	}
}
