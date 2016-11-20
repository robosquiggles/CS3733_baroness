package rmputnam;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestBaroness extends KSTestCase {
	
	Baroness baroness;
	GameWindow gw;
	
	public void setUp() {
		baroness = new Baroness();
		gw = Main.generateWindow(baroness, Deck.OrderBySuit);
	}
	
	public void tearDown() {
		gw.dispose();
	}
	
	public void testInitiate () {		
		assertEquals(47, baroness.deck.count());
		for (int colNum = 0; colNum <= 4; colNum++) {
			assertFalse(baroness.columns[colNum].empty());
		}
	}
	
	public void testDealNextRoundMove () {
		DealNextRoundMove dnrm = new DealNextRoundMove(baroness.deck, baroness.columns);
		
		assertTrue(dnrm.valid(baroness));
		
		dnrm.doMove(baroness);
		
		assertEquals(42, baroness.deck.count());
		assertEquals(42, baroness.getNumLeft().getValue());
		
		dnrm.undo(baroness);
		
		assertEquals(47, baroness.deck.count());
		assertEquals(47, baroness.getNumLeft().getValue());
		
		for (int i = 0; i < 9; i++) {
			dnrm.doMove(baroness);
		}
		
		assertEquals(2, baroness.deck.count());
		assertEquals(2, baroness.getNumLeft().getValue());
		assertTrue(dnrm.valid(baroness));
		
		dnrm.doMove(baroness);
		assertEquals(0, baroness.deck.count());
		assertEquals(0, baroness.getNumLeft().getValue());
		assertFalse(dnrm.valid(baroness));
	}
	
	public void testThirteenFromKingMove () {
		
		ThirteenFromKingMove tfkm = new ThirteenFromKingMove(baroness.columns[0],baroness.discardPile);
		
		assertTrue(tfkm.valid(baroness));
		
		tfkm.doMove(baroness);
		
		assertEquals(2, baroness.getScoreValue());
		
		tfkm.undo(baroness);
		
		assertEquals(0, baroness.getScoreValue());
	}
	
	public void testThirteenFromTwoMove () {
		ThirteenFromTwoMove tftm = new ThirteenFromTwoMove(baroness.columns[1].peek(),baroness.columns[1], baroness.columns[2], baroness.discardPile);
		
		assertFalse(tftm.valid(baroness));
		
		tftm.doMove(baroness);
		
		DealNextRoundMove dnrm = new DealNextRoundMove(baroness.deck, baroness.columns);
		dnrm.doMove(baroness);
		
		ThirteenFromTwoMove tftm2 = new ThirteenFromTwoMove(baroness.columns[1].get(),baroness.columns[1], baroness.columns[2], baroness.discardPile);
		
		assertTrue(tftm2.valid(baroness));
		
		tftm2.doMove(baroness);
		
		assertEquals(2, baroness.getScoreValue());
		
		tftm2.undo(baroness);
		
		assertEquals(0, baroness.getScoreValue());
	}
	
	public void testCardFromColumnToEmptyColumnMove () {
		ThirteenFromKingMove tfkm = new ThirteenFromKingMove(baroness.columns[0],baroness.discardPile); 
		tfkm.doMove(baroness);
		
		CardFromColumnToEmptyColumnMove c2cm = new CardFromColumnToEmptyColumnMove(baroness.columns[1].get(), baroness.columns[1], baroness.columns[0]);
		
		assertTrue(c2cm.valid(baroness));
		
		c2cm.doMove(baroness);
		
		assertFalse(c2cm.valid(baroness));
		
		c2cm.undo(baroness);
		
		assertTrue(c2cm.valid(baroness));
		
	}
	
	public void testBaronessColumnController() {
		MouseEvent pr = createPressed(baroness, baroness.columnViews[0], 0, 0);
		baroness.columnViews[0].getMouseManager().handleMouseEvent(pr);
		assertEquals(2, baroness.getScoreValue());
		
		DealNextRoundMove dnrm = new DealNextRoundMove(baroness.deck, baroness.columns);
		dnrm.doMove(baroness);
		
		MouseEvent pr2 = createPressed(baroness, baroness.columnViews[1], 0, 25);
		baroness.columnViews[1].getMouseManager().handleMouseEvent(pr2);
		
		MouseEvent r = createReleased(baroness, baroness.columnViews[2], 0, 25);
		baroness.columnViews[2].getMouseManager().handleMouseEvent(r);
		assertEquals(4, baroness.getScoreValue());
	}
	
	
}
