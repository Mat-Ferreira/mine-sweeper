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
		do {
			System.out.println(board.toString());
			
			try {
				int choice = showControlOptions();
				runChosenOption(choice);		
				if (board.reachObjective()) {
					System.out.println("You did it, congratulations!");
					wantToPlayAgain();
				}

			} catch (ExplosionException e) {
				System.out.println("You hit a bomb, game over!");
				try {
					wantToPlayAgain();
				} catch (ExitExcepetion e1) {
					exitGame();
				}
				
			} catch (ExitExcepetion e) {
				exitGame();
			}
			
		} while (playing);
		// Game loop ends
		
	}

	private void exitGame() {
		// TODO Auto-generated method stub
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
				// Abrir um campo
				openOneField();
				break;
			}
			case 2: {
				// Marcar um campo
				markOneField();
				break;
			}
			case 0: {
				// Sair do jogo
				throw new ExitExcepetion();
			}
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
		return scn.nextInt()-1;
	}
	
	private int campColumnChoice() {
		System.out.print("Enter a line number [1-"+(board.getColumns())+"]: ");
		return scn.nextInt() - 1;
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
