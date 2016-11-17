package ks.statistics;

import heineman.Klondike;
import ks.common.games.Solitaire;

/**
 * Sample usage for Statistics generator.
 *  
 * @author George Heineman
 */
public class Statistics {
	
	public static void main(String[] args) {
		
		// sample usage
		Solitaire s = new Klondike();
		StatsFrame sf = new StatsFrame(s);
		sf.setVisible(true);

	}
}
