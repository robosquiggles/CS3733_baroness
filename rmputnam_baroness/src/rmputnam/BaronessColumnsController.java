package rmputnam;

import java.awt.event.MouseAdapter;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;

public class BaronessColumnsController extends SolitaireReleasedAdapter {

	protected Baroness theGame;
	protected Column column;
	protected Deck deck;
	protected Pile discardPile;

	public BaronessColumnsController(Baroness theGame, Column column, Pile discardPile, Deck deck) {
		super(theGame);

		this.theGame = theGame;
		this.column = column;
		this.discardPile = discardPile;
		this.deck = deck;
	}

	/**
	 * Coordinate reaction to the beginning of a Drag Event. In this case,
	 * no drag is ever achieved, and we simply deal upon the pres.
	 */
	public void mouseClicked (java.awt.event.MouseEvent me) {

		// Attempt CardFromDeckToEmptyColumnMove
		Move mD2C = new CardFromDeckToEmptyColumnMove(deck, column);
		if (mD2C.doMove(theGame)) {
			theGame.pushMove (mD2C);     // Successful DealFour Move
			theGame.refreshWidgets(); // refresh updated widgets.
			return;
		}
		
		// Attempt ThirteenFromKingMove
		Move m = new ThirteenFromKingMove (column, discardPile);
		if (m.doMove(theGame)) {
			theGame.pushMove (m);     // Successful DealFour Move
			theGame.refreshWidgets(); // refresh updated widgets.
		}
	}

}
