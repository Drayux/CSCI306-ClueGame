package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	private IntBoard board;
	@Before
    public void beforeAll() {
       board = new IntBoard();
    }
	
	
	//Test for top left corner
	 
	@Test
	public void testAdjacency0_0() {
		BoardCell cell = board.getBoardCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getBoardCell(0, 1)));
		assertTrue(testList.contains(board.getBoardCell(1, 0)));
		assertEquals(2, testList.size());
	}

	
	
	//Test for bottom right corner
	 
	@Test
	public void testAdjacency3_3() {
		BoardCell cell = board.getBoardCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getBoardCell(2, 3)));
		assertTrue(testList.contains(board.getBoardCell(3, 2)));
		assertEquals(2, testList.size());
	}
	
	 
	//Test for right edge
	 
	@Test
	public void testAdjacency1_3() {
		BoardCell cell = board.getBoardCell(1,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getBoardCell(0, 3)));
		assertTrue(testList.contains(board.getBoardCell(2, 3)));
		assertTrue(testList.contains(board.getBoardCell(1, 2)));
		assertEquals(3, testList.size());
	}
	
	
	//Tests for Bottom left corner 
	 
	@Test
	public void testAdjacency3_0() {
		BoardCell cell = board.getBoardCell(3,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getBoardCell(2, 0)));
		assertTrue(testList.contains(board.getBoardCell(3, 1)));
		assertEquals(2, testList.size());
	}
	
	
	//Test  for the middle board squares, (1,1) and (2,2)
	@Test
	public void testAdjacency1_1() {
		BoardCell cell = board.getBoardCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getBoardCell(0, 1)));
		assertTrue(testList.contains(board.getBoardCell(1, 0)));
		assertTrue(testList.contains(board.getBoardCell(1, 2)));
		assertTrue(testList.contains(board.getBoardCell(2, 1)));
		assertEquals(4, testList.size());
	}
	@Test
	public void testAdjacency2_2() {
		BoardCell cell = board.getBoardCell(2,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getBoardCell(2, 1)));
		assertTrue(testList.contains(board.getBoardCell(1, 2)));
		assertTrue(testList.contains(board.getBoardCell(2, 3)));
		assertTrue(testList.contains(board.getBoardCell(3, 2)));
		assertEquals(4, testList.size());
	}
	
	  
	//Test (1,1) and a if you roll 3
	 
	@Test
	public void testTargets1_1_3()
	{
		BoardCell cell = board.getBoardCell(1, 1);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
	

		assertTrue(targets.contains(board.getBoardCell(3, 0)));
		assertTrue(targets.contains(board.getBoardCell(0, 3)));
		assertTrue(targets.contains(board.getBoardCell(1, 0)));
		assertTrue(targets.contains(board.getBoardCell(0, 1)));
		assertTrue(targets.contains(board.getBoardCell(2, 1)));
 		assertTrue(targets.contains(board.getBoardCell(2, 3)));
		assertTrue(targets.contains(board.getBoardCell(3, 2)));
		assertEquals(8,targets.size());
	}
	

	//tests where player is (2, 2) and if you roll of 2
	@Test
	public void testTargets2_2_2()
	{
		BoardCell cell = board.getBoardCell(2, 2);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7,targets.size());
		assertTrue(targets.contains(board.getBoardCell(2, 0)));
		assertTrue(targets.contains(board.getBoardCell(0, 2)));
		assertTrue(targets.contains(board.getBoardCell(1, 1)));
		assertTrue(targets.contains(board.getBoardCell(3, 1)));
		assertTrue(targets.contains(board.getBoardCell(1, 3)));
		assertTrue(targets.contains(board.getBoardCell(3, 3)));
		

	}
	

}