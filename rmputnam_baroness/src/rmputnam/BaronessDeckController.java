package rmputnam;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;

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
	public void mouseReleased (java.awt.event.MouseEvent me) {
		
		// Attempt DealNextRoundMove
		Move mNR = new DealNextRoundMove (deck, columns);
		if (mNR.doMove(theGame)) {
			theGame.pushMove (mNR);     // Successful DealFour Move
			theGame.refreshWidgets(); // refresh updated widgets.
		}
	}

}
