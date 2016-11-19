package rmputnam;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class ThirteenFromTwoMove extends Move {
	
	Column fromCol;
	Column toCol;
	Pile discard;
	Card floatingCard;

	public ThirteenFromTwoMove (Card floatingCard, Column fromCol, Column toCol, Pile discard) {
		super();
		
		this.fromCol = fromCol;
		this.toCol = toCol;
		this.discard = discard;
		this.floatingCard = floatingCard;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		
		//validate
		if (!valid(game))
			return false;
		
		//execute
		discard.add(floatingCard);
		discard.add(toCol.get());
		game.updateScore(+2);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		//validate
		if (discard.count() < 2) return false;
		
		//execute
		toCol.add(discard.get());
		fromCol.add(discard.get());
		game.updateScore(-2);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		//validation
		if ((toCol != fromCol) && (!toCol.empty()) && 
				(floatingCard.getRank() + toCol.peek().getRank() == 13)) {
			return true;
		}
		return false;
	}

}
