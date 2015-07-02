package br.com.mvbos.lgj;

import java.awt.Color;

public class Peca {
	public static Color[] Cores = { Color.GREEN, Color.ORANGE, Color.YELLOW, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.WHITE };

	public static final int[][][] PECAS = { 
			{ 
				{ 0, 1, 0 }, 
				{ 0, 1, 0 }, 
				{ 1, 1, 0 } },
			{ 
				{ 0, 1, 0 }, 
				{ 0, 1, 0 }, 
				{ 0, 1, 1 } },				
			{ 
				{ 1, 1, 1 }, 
				{ 0, 1, 0 }, 
				{ 0, 0, 0 } },
			{ 
				{ 1, 0, 0 }, 
				{ 1, 1, 0 }, 
				{ 0, 1, 0 } }, 
			{ 	
				{ 0, 0, 1 }, 
				{ 0, 1, 1 }, 
				{ 0, 1, 0 } },
			{ 
				{ 1, 1 }, 
				{ 1, 1 } }, 
			{ 
				{ 0, 1, 0, 0 },
				{ 0, 1, 0, 0 }, 
				{ 0, 1, 0, 0 }, 
				{ 0, 1, 0, 0 } } 
	};

}
