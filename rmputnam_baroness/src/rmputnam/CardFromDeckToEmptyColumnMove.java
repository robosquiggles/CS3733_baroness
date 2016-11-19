package rmputnam;

import ks.common.games.Solitaire;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;

public class CardFromDeckToEmptyColumnMove extends Move {
	
	Deck deck;
	Column column;
	int theColumn;

	public CardFromDeckToEmptyColumnMove (Deck deck, Column column) {
		super();
		
		this.deck = deck;
		this.column = column;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		
		//validate
		if (!valid(game))
			return false;
		
		//execute
		column.add(deck.get());
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		//validate
		if (column.count() != 1) return false;
		
		//execute
		deck.add(column.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		//validation
		if (column.empty() && !deck.empty()) {
			return true;
		}
		return false;
	}

}
