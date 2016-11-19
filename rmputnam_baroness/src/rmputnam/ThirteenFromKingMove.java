package rmputnam;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;

public class ThirteenFromKingMove extends Move{
	
	Column column;
	Pile discardPile;
	Card undoCard;
	
	public ThirteenFromKingMove (Column column, Pile discard) {
		super();
		
		this.column = column;
		this.discardPile = discard;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game))
			return false;
		
		discardPile.add (column.get());
		undoCard = column.peek();
		game.updateScore(+2);
		
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		if (discardPile.peek() == undoCard) 
			return false;
		
		column.add(discardPile.get());
		game.updateScore(-2);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return column.peek().getRank() == Card.KING;
	}
	
	
}
