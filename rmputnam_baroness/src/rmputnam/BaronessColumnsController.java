package rmputnam;

import java.awt.event.MouseEvent;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;

public class BaronessColumnsController extends SolitaireReleasedAdapter {

	protected Baroness theGame;
	protected Column column;
	protected Deck deck;
	protected Pile discardPile;
	
	protected ColumnView src;

	public BaronessColumnsController(Baroness theGame, Column column, ColumnView columnView, Pile discardPile, Deck deck) {
		super(theGame);

		this.theGame = theGame;
		this.column = column;
		this.discardPile = discardPile;
		this.deck = deck;
		this.src = columnView;
	}
	
	public void mousePressed(MouseEvent me) {
		
		// Attempt ThirteenFromKingMove
		Move m = new ThirteenFromKingMove (column, discardPile);
		if (m.doMove(theGame)) {
			theGame.pushMove (m);     // Successful DealFour Move
			theGame.refreshWidgets(); // refresh updated widgets.
			return;
		}
		
		//otherwise do the dragging stuff
		Container c = theGame.getContainer();
		
		Column column = (Column) src.getModelElement();
		if (column.count() == 0) {
			c.releaseDraggingObject();
			return;
		}
		
		CardView cardView = src.getCardViewForTopCard (me);
		
		if (cardView == null) {
			c.releaseDraggingObject();
			return;
		}
		
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("error here");
			return;
		}
		
		c.setActiveDraggingObject (cardView, me);
		
		c.setDragSource (src);
	
		src.redraw();
	}
	
	
	public void mouseReleased(MouseEvent me) {
		Container c = theGame.getContainer();
		
		//if no card being drag
		Widget draggingWidget = c.getActiveDraggingObject();
		if (draggingWidget == Container.getNothingBeingDragged()) {
			c.releaseDraggingObject();		
			return;
		}
		
		//if there was no drag??
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("error again");
			c.releaseDraggingObject();
			return;
		}
		
		Column toColumn = (Column) src.getModelElement();
		Column fromColumn = (Column) fromWidget.getModelElement();
		
		//if more crazy stuff?
		if (fromColumn == null) {
			System.err.println ("errors everywheres");
			c.releaseDraggingObject();			
			return;
		}
		
		//Attempt CardFromColumnToEmptyColumnMove  //Attempt ThirteenFromTwoMove
		Move m13 = new ThirteenFromTwoMove ((Card) c.getActiveDraggingObject().getModelElement(), fromColumn, toColumn, discardPile);
		Move mC2C = new CardFromColumnToEmptyColumnMove((Card) c.getActiveDraggingObject().getModelElement(), fromColumn, toColumn);
		if (m13.doMove (theGame)) {
			theGame.pushMove (m13);
		} else if (mC2C.doMove (theGame)) {
			theGame.pushMove (mC2C);
		} else {
			fromWidget.returnWidget (draggingWidget);
		}
		
		c.releaseDraggingObject();
		
		c.repaint();
	
	}

}
