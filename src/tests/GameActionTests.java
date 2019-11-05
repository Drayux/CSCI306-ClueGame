package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.ConfigType;
import clueGame.Player;

class GameActionTests {	

	private static Board board;


	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();

		board.setConfig(ConfigType.BOARD, "config/board.csv");
		board.setConfig(ConfigType.LEGEND, "config/legend.txt");
		board.setConfig(ConfigType.PLAYER, "config/players.txt");
		board.setConfig(ConfigType.WEAPON, "config/weapons.txt");

		board.initialize();

	}


	@Test
	public void targetLocationTests() {

	ComputerPlayer comp1 =  null;//new ComputerPlayer();
	double random = Math.random();
	
	//row 3 col 15 2 step
	Map <BoardCell, Integer> countMap = new HashMap<BoardCell, Integer>();
	
	for(int i = 0; i < 500; i ++ ) {
		
		
	
		
	}
	
	
	
	
	
	// row 5 , col 3 4 step
	//run 1 select room
	
	// row 5 , col 3 4 step
	//run2 select other room 
	
	// row 5 , col 3 4 step
	//run 3 


	}
	
	
	@Test
	public void checkingSuggestionTest() {
		
	}
	
	
	
	@Test 
	public void disprovingSuggestionTest () {
	
	}
	
	@Test
	public void handlingSuggestionTest() {
		
	}
	
	@Test 
	public void creatingSuggestionTest() {
		
	}
	}

}

