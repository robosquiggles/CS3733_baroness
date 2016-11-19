package rmputnam;

import java.awt.event.MouseAdapter;

import heineman.Klondike;
import heineman.klondike.DealCardMove;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;

public class BaronessDeckController extends SolitaireReleasedAdapter {

	protected Baroness theGame;
	protected Column[] columns = new Column[5];
	protected Deck deck;

	public BaronessDeckController(Baroness theGame, Deck d, Column[] columns) {
		super(theGame);

		this.theGame = theGame;
		this.columns = columns;
		this.deck = d;
	}

	/**
	 * Coordinate reaction to the beginning of a Drag Event. In this case,
	 * no drag is ever achieved, and we simply deal upon the pres.
	 */
	public void mousePressed (java.awt.event.MouseEvent me) {

		// Attempt CardFromDeckToEmptyColumnMove
		for (int i = 0; i <= 4; i++) {
			Move mD2C = new CardFromDeckToEmptyColumnMove(deck, columns[i]);
			if (mD2C.doMove(theGame)) {
				theGame.pushMove (mD2C);     // Successful DealFour Move
				theGame.refreshWidgets(); // refresh updated widgets.
				return;
			}
		}
		
		// Attempt DealNextRoundMove
		Move mNR = new DealNextRoundMove (deck, columns);
		if (mNR.doMove(theGame)) {
			theGame.pushMove (mNR);     // Successful DealFour Move
			theGame.refreshWidgets(); // refresh updated widgets.
		}
	}

}
