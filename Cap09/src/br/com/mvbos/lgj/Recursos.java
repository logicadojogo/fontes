package br.com.mvbos.lgj;

import javax.swing.ImageIcon;

public class Recursos {

	public enum Imagem {
		FUNDO, JOGADOR_A, JOGADOR_B, TIRO_A, TIRO_B, AST_A, AST_B, AST_C, EXPLOSAO_AST, COLISAO_AST
	}

	private static final String DIR_IMG = "imagens/";
	// private static final String DIR_SOM = "som/";

	private static ImageIcon[] imagens = new ImageIcon[Imagem.values().length];

	public static void carregarImagens() {
		imagens[Imagem.FUNDO.ordinal()] = new ImageIcon(DIR_IMG + "fundo.png");
		imagens[Imagem.JOGADOR_A.ordinal()] = new ImageIcon(DIR_IMG + "nave_jogador_1.png");
		imagens[Imagem.JOGADOR_B.ordinal()] = new ImageIcon(DIR_IMG + "nave_jogador_2.png");
		imagens[Imagem.TIRO_A.ordinal()] = new ImageIcon(DIR_IMG + "tiro.png");
		imagens[Imagem.TIRO_B.ordinal()] = new ImageIcon(DIR_IMG + "tiro_b.png");

		imagens[Imagem.AST_A.ordinal()] = new ImageIcon(DIR_IMG + "asteroide_grande.png");
		imagens[Imagem.AST_B.ordinal()] = new ImageIcon(DIR_IMG + "asteroide_medio.png");
		imagens[Imagem.AST_C.ordinal()] = new ImageIcon(DIR_IMG + "asteroide_pequeno.png");

		imagens[Imagem.EXPLOSAO_AST.ordinal()] = new ImageIcon(DIR_IMG + "explosao_asteroid.png");
		imagens[Imagem.COLISAO_AST.ordinal()] = new ImageIcon(DIR_IMG + "colisao_asteroid.png");

	}

	public static ImageIcon getImagem(Imagem img) {
		return imagens[img.ordinal()];
	}
}
