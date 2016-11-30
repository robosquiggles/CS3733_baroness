package rmputnam;

import ks.common.games.Solitaire;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;

public class DealNextRoundMove extends Move {
	
	Deck deck;
	Column[] columns = new Column[5];
	int numDealt;

	public DealNextRoundMove (Deck deck, Column[] columns) {
		super();
		
		this.deck = deck;
		this.columns = columns;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		
		//validate
		if (!valid(game))
			return false;
		
		//execute
		if (deck.count() < 5) {
			numDealt = deck.count();
		} else {
			numDealt = 5;
		}
		for (int colNum = 0; colNum <= numDealt-1; colNum ++) {
			columns[colNum].add (deck.get());
			game.updateNumberCardsLeft (-1);
		}
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		//validate
		for (int colNum = 0; colNum <= numDealt-1; colNum ++) {
			if (columns[colNum].empty()) return false;
		}
		
		//execute
		for (int colNum = numDealt-1; colNum >= 0; colNum --) {
			deck.add (columns[colNum].get());
			game.updateNumberCardsLeft (+1);
		}
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		//validation
		boolean noEmptyColumns = true;
		boolean notEnoughCards = true;
		for (int colNum = 0; colNum <= 4; colNum ++) {
			if (columns[colNum].empty()) {					//if any column is empty
				noEmptyColumns =  false;
			}
			if (columns[colNum].count() > 1) {
				notEnoughCards = false;
			}
		}
		
		return (noEmptyColumns || notEnoughCards);
	}

}
