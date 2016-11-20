package rmputnam;

import ks.client.gamefactory.GameWindow;
import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.launcher.Main;

public class Baroness extends Solitaire {
	
	//entities
	Deck deck;
	Column[] columns = new Column[5];
	Pile discardPile;

	//boundaries
	DeckView deckView;
	ColumnView[] columnViews = new ColumnView[5];
	PileView discardPileView;
	IntegerView scoreView;
	IntegerView numLeftView;

	@Override
	public String getName() {
		return "rmputnam-Baroness";
	}

	@Override
	public boolean hasWon() {
		for (int i = 0; i <= 4; i ++) {
			if (!columns[i].empty()) return false;
		}
		return deck.empty();
	}

	@Override
	public void initialize() {
		// initialize model
		initializeModel(getSeed());
		initializeView();
		initializeControllers();
		
		//deal the cards
		for (int colNum=0; colNum <= 4; colNum++) {
			Card c = deck.get();
			c.setFaceUp (true);
			columns[colNum].add (c);
		}
	}

	private void initializeModel(int seed) {
		//make deck
		deck = new Deck("d");
		deck.create(seed);
		
		//make columns
		for (int i = 0; i<=4; i++) {
			columns[i] = new Column ("column" + i);
			model.addElement (columns[i]);
		} 
		
		//make discardPile
		discardPile = new Pile ("discard");
		model.addElement (discardPile);
		
		//set cards left to 52
		this.updateNumberCardsLeft(47);
	}
	
	private void initializeView() {
		CardImages ci = getCardImages();
		
		deckView = new DeckView (deck);
		deckView.setBounds (20, 30, ci.getWidth(), ci.getHeight());
		container.addWidget (deckView);
		
		for (int colNum = 0; colNum <=4; colNum++) {
			columnViews[colNum] = new ColumnView(columns[colNum]);
			columnViews[colNum].setBounds (20*(colNum+2) + (colNum+1)*ci.getWidth(), 30, ci.getWidth(), 13*ci.getHeight());
			container.addWidget (columnViews[colNum]);
		}
		
		discardPileView = new PileView (discardPile);
		discardPileView.setBounds (20*7 + 6*ci.getWidth() + 30, 30, ci.getWidth(), ci.getHeight());
		container.addWidget (discardPileView);
		
		scoreView = new IntegerView (getScore());
		scoreView.setFontSize (24);
		scoreView.setBounds (20*6+6*ci.getWidth(), 0, 30, 70);
		container.addWidget (scoreView);

		numLeftView = new IntegerView (getNumLeft());
		numLeftView.setFontSize (14);
		numLeftView.setBounds (20 + ci.getWidth()/4, 10, ci.getWidth(), 20);
		container.addWidget (numLeftView);
	}

	private void initializeControllers() {
		// Initialize Controllers for DeckView
		deckView.setMouseAdapter(new BaronessDeckController (this, deck, columns));
		deckView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		deckView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		// Initialize Controllers for DeckView
		for (int i = 0; i <= 4; i++) {
			columnViews[i].setMouseAdapter (new BaronessColumnsController (this, columns[i], columnViews[i], discardPile, deck));
			columnViews[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
			columnViews[i].setUndoAdapter (new SolitaireUndoAdapter(this));
		}
		
		numLeftView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		numLeftView.setMouseAdapter (new SolitaireReleasedAdapter(this));
		numLeftView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		scoreView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		scoreView.setMouseAdapter (new SolitaireReleasedAdapter(this));
		scoreView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		discardPileView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		discardPileView.setMouseAdapter (new SolitaireReleasedAdapter(this));
		discardPileView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
	}
	
	//launch!!
	public static void main (String []args) {
		Baroness baroness = new Baroness();
		GameWindow gw = Main.generateWindow(baroness, Deck.OrderBySuit);
//		DealNextRoundMove dnrm = new DealNextRoundMove(baroness.deck, baroness.columns);
//		dnrm.doMove(baroness);
	}

}
