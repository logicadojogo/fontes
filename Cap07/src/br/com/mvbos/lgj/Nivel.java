package br.com.mvbos.lgj;

public class Nivel {

	/** Bloco */
	public static final int BL = -1;
	
	/** Comida normal */
	public static final int CN = -2;
	
	/** Espaco vazio */
	public static final int EV = -3;
	
	/** Ponto inicial do jogador */
	public static final int PI = -4;
	
	/** Super comida */
	public static final int SC = -5;
	
	/** Ponto inicial inimigo 1 */
	public static final int P1 = -6;
	
	/** Ponto inicial inimigo 2 */
	public static final int P2 = -7;
	
	/** Ponto inicial inimigo 1 */
	public static final int P3 = -8;
	
	/** Ponto inicial inimigo 2 */
	public static final int P4 = -9;
	
	/** Ponto de Fuga */
	public static final int PF = -10;
	
	/** Ponto de Volta */
	public static final int PV = -11;
	
	public static final int A0 = 0;
	public static final int A1 = 1;
	public static final int A2 = 2;
	public static final int A3 = 3;
	public static final int A4 = 4;
	public static final int A5 = 5;
	public static final int A6 = 6;
	public static final int A7 = 7;
	
	public static final int B0 = 10;
	public static final int B2 = 12;
	public static final int B3 = 13;
	public static final int B5 = 15;
	public static final int B6 = 16;
	public static final int B7 = 17;
	
	public static final int C0 = 20;
	public static final int C1 = 21;
	public static final int C2 = 22;
	public static final int C3 = 23;
	public static final int C4 = 24;
	public static final int C5 = 25;
	public static final int C6 = 26;
	public static final int C7 = 27;
	
	public static final int D0 = 30;
	public static final int D1 = 31;
	public static final int D2 = 32;
	public static final int D3 = 33;
	public static final int D5 = 35;
	public static final int D6 = 36;
	public static final int D7 = 37;
	
	public static final int E0 = 40;
	public static final int E2 = 42;
	/** Linha */
	public static final int LN = 44;
	public static final int E5 = 45;
	public static final int E6 = 46;
	public static final int E7 = 47;

	public static int[][] cenario = {
			{ A0, A1, A1, A1, A1, A1, A1, A1, A1, A1, A1, A1, A1, A2, A0, A1, A1, A1, A1, A1, A1, A1, A1, A1, A1, A1, A1, A2 },
			{ B0, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, B2, B0, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, B2 },
			{ B0, CN, A3, A4, A4, A5, CN, A3, A4, A4, A4, A5, CN, B2, B0, CN, A3, A4, A4, A4, A5, CN, A3, A4, A4, A5, CN, B2 },
			{ B0, SC, B3, EV, EV, B5, CN, B3, EV, EV, EV, B5, CN, B2, B0, CN, B3, EV, EV, EV, B5, CN, B3, EV, EV, B5, SC, B2 },
			{ B0, CN, C3, C4, C4, C5, CN, C3, C4, C4, C4, C5, CN, C6, C7, CN, C3, C4, C4, C4, C5, CN, C3, C4, C4, C5, CN, B2 },
			{ B0, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, B2 },
			{ B0, CN, D5, D6, D6, D7, CN, A6, A7, CN, D5, D6, D6, D6, D6, D6, D6, D7, CN, A6, A7, CN, D5, D6, D6, D7, CN, B2 },
			{ B0, CN, E5, E6, E6, E7, CN, B6, B7, CN, E5, E6, E6, E6, E6, E6, E6, E7, CN, B6, B7, CN, E5, E6, E6, E7, CN, B2 },
			{ B0, CN, CN, CN, CN, CN, CN, B6, B7, CN, CN, CN, CN, A6, A7, CN, CN, CN, CN, B6, B7, CN, CN, CN, CN, CN, CN, B2 },
			{ C0, C1, C1, C1, C1, D0, CN, B6, B7, D5, D6, D7, CN, B6, B7, CN, D5, D6, D7, B6, B7, CN, D2, C1, C1, C1, C1, C2 },
			{ EV, EV, EV, EV, EV, B0, CN, B6, B7, E5, E6, E7, CN, C6, C7, CN, E5, E6, E7, B6, B7, CN, B2, EV, EV, EV, EV, EV },
			{ EV, EV, EV, EV, EV, B0, CN, B6, B7, EV, EV, EV, EV, PF, P1, EV, EV, EV, EV, B6, B7, CN, B2, EV, EV, EV, EV, EV },
			{ EV, EV, EV, EV, EV, B0, CN, B6, B7, EV, A3, A4, A4, LN, LN, A4, A4, A5, EV, B6, B7, CN, B2, EV, EV, EV, EV, EV },
			{ A1, A1, A1, A1, A1, D1, CN, C6, C7, EV, B3, EV, EV, EV, EV, EV, EV, B5, EV, C6, C7, CN, D3, A1, A1, A1, A1, A1 },
			{ CN, CN, CN, CN, CN, CN, CN, CN, CN, EV, B3, EV, EV, PV, EV, EV, EV, B5, EV, CN, CN, CN, CN, CN, CN, CN, CN, CN },
			{ C1, C1, C1, C1, C1, D0, CN, A6, A7, EV, B3, P2, EV, P3, EV, P4, EV, B5, EV, A6, A7, CN, D2, C1, C1, C1, C1, C1 },
			{ EV, EV, EV, EV, EV, B0, CN, B6, B7, EV, C3, C4, C4, C4, C4, C4, C4, C5, EV, B6, B7, CN, B2, EV, EV, EV, EV, EV },
			{ EV, EV, EV, EV, EV, B0, CN, B6, B7, EV, EV, EV, EV, PI, EV, EV, EV, EV, EV, B6, B7, CN, B2, EV, EV, EV, EV, EV },
			{ EV, EV, EV, EV, EV, B0, CN, B6, B7, CN, D5, D6, D6, D6, D6, D6, D6, D7, CN, B6, B7, CN, B2, EV, EV, EV, EV, EV },
			{ A0, A1, A1, A1, A1, D1, CN, C6, C7, CN, E5, E6, E6, E6, E6, E6, E6, E7, CN, C6, C7, CN, D3, A1, A1, A1, A1, A2 },
			{ B0, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, A6, A7, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, B2 },
			{ B0, CN, D5, C1, C1, E2, CN, D5, D6, D6, D6, D7, CN, B6, B7, CN, D5, D6, D6, D6, D7, CN, E0, C1, C1, D7, CN, B2 },
			{ B0, CN, E5, A1, A2, B0, CN, E5, E6, E6, E6, E7, CN, C6, C7, CN, E5, E6, E6, E6, E7, CN, B2, A0, A1, E7, CN, B2 },
			{ B0, SC, CN, CN, B2, B0, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, B2, B0, CN, CN, SC, B2 },
			{ C0, C1, D7, CN, B2, B0, CN, A6, A7, CN, D5, D6, D6, D6, D6, D6, D6, D7, CN, A6, A7, CN, B2, B0, CN, D5, C1, C2 },
			{ A0, A1, E7, CN, C6, C7, CN, B6, B7, CN, E5, E6, E6, E6, E6, E6, E6, E7, CN, B6, B7, CN, C6, C7, CN, E5, A1, A2 },
			{ B0, CN, CN, CN, CN, CN, CN, C6, C7, CN, CN, CN, CN, A6, A7, CN, CN, CN, CN, C6, C7, CN, CN, CN, CN, CN, CN, B2 },
			{ B0, CN, D5, D6, D6, D6, D6, D6, D6, D6, D6, D7, CN, B6, B7, CN, D5, D6, D6, D6, D6, D6, D6, D6, D6, D7, CN, B2 },
			{ B0, CN, E5, E6, E6, E6, E6, E6, E6, E6, E6, E7, CN, C6, C7, CN, E5, E6, E6, E6, E6, E6, E6, E6, E6, E7, CN, B2 },
			{ B0, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, CN, B2 },
			{ C0, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C1, C2 } };

}
