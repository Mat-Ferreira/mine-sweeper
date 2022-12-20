package br.com.maf.ms.view;

import java.util.Scanner;

import br.com.maf.ms.exception.ExitExcepetion;
import br.com.maf.ms.exception.ExplosionException;
import br.com.maf.ms.logic.Board;


public class BoardConsole {
	private Board board;
	private Scanner scn;
	boolean playing = true;
	
	public BoardConsole(Board b) {
		// TODO Auto-generated constructor stub
		this.board = b;	
		scn = new Scanner(System.in);
		runGame();
		scn.close();
	}

	private void runGame() {
		System.out.println("Game started! \n");
		
		// Game loop start
		do {gameCicle();} while (playing);	
	}
	
	private void gameCicle() {
		//...
		
		try {
			System.out.println(board.toString());
			int choice = showControlOptions();
			runChosenOption(choice);		
			
			if (board.reachObjective()) {
				System.out.println("You did it, congratulations!");
				wantToPlayAgain();
			}

		} catch (ExitExcepetion e) {
			exitGame();
		}
	}

	private void exitGame() {
		playing = false;
		System.out.println("You exited the game");
	}

	private int showControlOptions() {	
		System.out.print("[1] - Open field, [2] - Mark Field or [0] - Exit Game\n>>> ");
		return scn.nextInt();
		
	}
	
	private void runChosenOption(int choice) {
		switch (choice) {
			case 1: {
				// Open a field of the board
				try {
					openOneField();
				} 
				catch (ExplosionException e) {
					board.showAllCamps();
					System.out.println("You hit a bomb, game over!");
					System.out.println(board.toString());
					
					try {
						wantToPlayAgain();
					} catch (ExitExcepetion e1) {
						exitGame();
					}
				}
				break;
			}
			
			case 2: {
				// Mark a field of the board
				markOneField();
				break;
			}
			
			case 0: {
				// Sair do jogo
				throw new ExitExcepetion();
			}
			
			// In case of invalid option, ask again
			default:
				System.out.println("Invalid Option");
				choice = showControlOptions();
				runChosenOption(choice);
		}
	}
	
	private void openOneField(){
		int l = campLineChoice();
		int c = campColumnChoice();
		
		board.openCamp(l, c);
		
	}
	private void markOneField(){
		int l = campLineChoice();
		int c = campColumnChoice();
		
		board.switchMark(l, c);
	}
	
	private int campLineChoice() {
		System.out.print("Enter a line number [1-"+(board.getLines())+"]: ");
		int input = scn.nextInt()-1;
		
		if (input > board.getLines()-1) {
			System.out.println("Invalid line value");
			return campLineChoice();
		}
		return input;
	}
	
	private int campColumnChoice() {
		System.out.print("Enter a line number [1-"+(board.getColumns())+"]: ");
		int input = scn.nextInt()-1;
		
		if (input > board.getColumns()-1) {
			System.out.println("Invalid column value");
			return campLineChoice();
		}
		return input;
	}
	
	private void wantToPlayAgain() {
		System.out.println("Do you want to play again? [y] / [n]");
		String choice = scn.next();
		
		if (choice.equalsIgnoreCase("y")) {
			board.restartBoard();
		}
		else if (choice.equalsIgnoreCase("n")) {
			throw new ExitExcepetion();
		}
		else {
			System.out.println("Invalid choice! \n");
		}
	}
}
