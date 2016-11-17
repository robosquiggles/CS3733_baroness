package ks.statistics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Stack;

import ks.client.gamefactory.GameWindow;
import ks.common.games.Solitaire;
import ks.common.games.SolvableSolitaire;
import ks.common.model.Move;
import ks.launcher.Main;

public class StartTrialController implements ActionListener {

	StatsFrame frame;
	
	public StartTrialController (StatsFrame sf) {
		this.frame = sf;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		frame.getStartButton().setEnabled(false);
		
		int nt = 100;
		
		try {
			nt = Integer.valueOf(frame.getNumberTrialsField().getText());
		} catch (Exception ex1) {
			
		}
		
		int mss = 500;
		try {
			mss = Integer.valueOf(frame.getStateSearchField().getText());
		} catch (Exception ex1) {
			
		}
		
		// run in own thread so we don't interfere with GUI stuff
		final int numTrials = nt;
		final int maxStateSearch = mss;
		Thread thread = new Thread() {
			@Override
			public void run() {
				frame.getWinningSeeds().setText("");
				int numWins = 0;
				int numGames = 0;
				for (int i = 1; i < numTrials; i++) {
					numGames++;
					if (trial (i, frame.getVariation(), maxStateSearch)) {
						numWins++;
						frame.getWinningSeeds().append(i + "\n");
					}
					frame.getStatusField().setText("" + numWins + "/" + numGames);
				}
				
				frame.getStartButton().setEnabled(true);
			}
		};
		
		thread.start();
	}


	public static boolean trial(int seed, Solitaire variation, int maxNumberStatesToSearch) {
		if (! (variation instanceof SolvableSolitaire)) {
			System.err.println("Given variation does not implement SolvableSolitaire.");
			return false;
		}
		
		GameWindow window = Main.generateWindow(variation, seed);
		variation.resetHand();
		
		Stack<Enumeration<Move>> holding = new Stack<Enumeration<Move>>();
		Enumeration<Move> en = ((SolvableSolitaire)variation).availableMoves();
		holding.push(en);
		
		// haven't won and still have something to check.
		int numSearched = 0;
		while (!variation.hasWon() && !holding.isEmpty() && numSearched++ < maxNumberStatesToSearch) {
			Enumeration<Move> topSet = holding.peek();
			if (!topSet.hasMoreElements()) {
				holding.pop();
				variation.undoMove(); // undo last move
				continue;
			} else {
				Move m = topSet.nextElement();
				if (m.valid(variation)) {
					m.doMove(variation);
					variation.pushMove(m);
					en = ((SolvableSolitaire)variation).availableMoves();
					holding.push(en);
				}
			}
		}
		
		window.dispose();
		return variation.hasWon();
	}
	
	
}
