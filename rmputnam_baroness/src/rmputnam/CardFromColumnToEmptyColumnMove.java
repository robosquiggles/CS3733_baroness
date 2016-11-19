package rmputnam;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;

public class CardFromColumnToEmptyColumnMove extends Move {
	
	Column fromCol;
	Column toCol;
	Card floatingCard;

	public CardFromColumnToEmptyColumnMove (Card floatingCard, Column fromCol, Column toCol) {
		super();
		
		this.fromCol = fromCol;
		this.toCol = toCol;
		this.floatingCard = floatingCard;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)) {
			return false;
		}
		
		toCol.add(floatingCard);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		if (toCol.empty()) {
			return false;
		}
		
		fromCol.add(toCol.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return (toCol.empty());
	}

}
