package rmputnam;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestBaroness extends TestCase {
	public void testInitiate () {
		Baroness baroness = new Baroness();
		GameWindow gw = Main.generateWindow(baroness, Deck.OrderBySuit);
		
		assertEquals(47, baroness.deck.count());
		for (int colNum = 0; colNum <= 4; colNum++) {
			assertFalse(baroness.columns[colNum].empty());
		}
	}
	
	public void testDealNextRoundMove () {
		Baroness baroness = new Baroness();
		GameWindow gw = Main.generateWindow(baroness, Deck.OrderBySuit);
		
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
		Baroness baroness = new Baroness();
		GameWindow gw = Main.generateWindow(baroness, Deck.OrderByRank);
		
		ThirteenFromKingMove tfkm = new ThirteenFromKingMove(baroness.columns[0],baroness.discardPile);
		
		assertTrue(tfkm.valid(baroness));
		
		tfkm.doMove(baroness);
		
		assertEquals(2, baroness.getScoreValue());
		
		tfkm.undo(baroness);
		
		assertEquals(0, baroness.getScoreValue());
	}
}
