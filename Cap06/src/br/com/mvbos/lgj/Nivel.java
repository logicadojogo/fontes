package br.com.mvbos.lgj;

public class Nivel {
	
	/** Bloco */
	public static final int BL = 0;
	/** Comida normal */
	public static final int CN = 1;
	/** Espaco vazio */
	public static final int EV = 2;
	/** Ponto inicial do jogador */
	public static final int PI = 3;
	/** Linha */
	public static final int LN = 4;
	/** Super comida */
	public static final int SC = 5;
	/** Ponto inicial inimigo 1 */
	public static final int P1 = 6;
	/** Ponto inicial inimigo 2 */
	public static final int P2 = 7;
	/** Ponto de Fuga */
	public static final int PF = 8;
	/** Ponto de Volta */
	public static final int PV = 9;
	
	/** Fruta */
	public static final int FT = 10;

	public static int[][] cenario = {
		{ BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL },
		{ BL, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, BL, BL, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, BL },
		{ BL, CN, BL, BL, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, BL, BL, CN, BL },
		{ BL, SC, BL, EV, EV, BL, CN, BL, EV, EV, EV, BL, CN, BL, BL, CN, BL, EV, EV, EV, BL, CN, BL, EV, EV, BL, SC, BL },
		{ BL, CN, BL, BL, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, BL, BL, CN, BL },
		{ BL, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, BL },
		{ BL, CN, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, CN, BL },
		{ BL, CN, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, CN, BL },
		{ BL, CN, CN, CN, CN, CN, CN, BL, BL, CN, CN, CN, CN, BL, BL, CN, CN, CN, CN, BL, BL, CN, CN, CN, CN, CN, CN, BL },
		{ BL, BL, BL, BL, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, BL, BL, BL, BL },
		{ EV, EV, EV, EV, EV, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, EV, EV, EV, EV, EV },
		{ EV, EV, EV, EV, EV, BL, CN, BL, BL, EV, EV, EV, EV, PF, P1, EV, EV, EV, EV, BL, BL, CN, BL, EV, EV, EV, EV, EV },
		{ EV, EV, EV, EV, EV, BL, CN, BL, BL, EV, BL, BL, BL, LN, LN, BL, BL, BL, EV, BL, BL, CN, BL, EV, EV, EV, EV, EV },
		{ BL, BL, BL, BL, BL, BL, CN, BL, BL, EV, BL, EV, EV, EV, EV, EV, EV, BL, EV, BL, BL, CN, BL, BL, BL, BL, BL, BL },
		{ CN, CN, CN, CN, CN, CN, CN, CN, CN, EV, BL, EV, EV, EV, EV, EV, EV, BL, EV, CN, CN, CN, CN, CN, CN, CN, CN, CN },
		{ BL, BL, BL, BL, BL, BL, CN, BL, BL, EV, BL, EV, P2, PV, EV, EV, EV, BL, EV, BL, BL, CN, BL, BL, BL, BL, BL, BL },
		{ EV, EV, EV, EV, EV, BL, CN, BL, BL, EV, BL, BL, BL, BL, BL, BL, BL, BL, EV, BL, BL, CN, BL, EV, EV, EV, EV, EV },
		{ EV, EV, EV, EV, EV, BL, CN, BL, BL, EV, EV, EV, EV, PI, FT, EV, EV, EV, EV, BL, BL, CN, BL, EV, EV, EV, EV, EV },
		{ EV, EV, EV, EV, EV, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, EV, EV, EV, EV, EV },
		{ BL, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, BL },
		{ BL, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, BL, BL, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, BL },
		{ BL, CN, BL, BL, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, BL, BL, CN, BL },
		{ BL, CN, BL, BL, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, CN, BL, BL, BL, BL, CN, BL },
		{ BL, SC, CN, CN, BL, BL, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, BL, BL, CN, CN, SC, BL },
		{ BL, BL, BL, CN, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, CN, BL, BL, BL },
		{ BL, BL, BL, CN, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, CN, BL, BL, BL },
		{ BL, CN, CN, CN, CN, CN, CN, BL, BL, CN, CN, CN, CN, BL, BL, CN, CN, CN, CN, BL, BL, CN, CN, CN, CN, CN, CN, BL },
		{ BL, CN, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, CN, BL },
		{ BL, CN, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, CN, BL, BL, CN, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, CN, BL },
		{ BL, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, BL },
		{ BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL, BL } };

}
