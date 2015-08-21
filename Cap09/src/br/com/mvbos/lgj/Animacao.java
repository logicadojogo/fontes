package br.com.mvbos.lgj;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.lgj.base.Elemento;

public class Animacao extends Elemento {

	private short coluna;
	private short temporizador;

	public Animacao(ImageIcon imagem) {
		super.setImagem(imagem);
	}

	@Override
	public void atualiza() {
		if (!isAtivo())
			return;

		if (coluna == 6) {
			setAtivo(false);
		} else if (temporizador == 2) {
			coluna++;
			temporizador = 0;
		} else {
			temporizador++;
		}
	}

	@Override
	public void desenha(Graphics2D g) {
		if (!isAtivo())
			return;

		// Largura da moldura
		int largMoldura = getImagem().getIconWidth() / 6;

		// Largura e altura do recorte da imagem
		int largImg = largMoldura * coluna;
		int altImg = getImagem().getIconHeight();

		g.drawImage(getImagem().getImage(), getPx(), getPy(), getPx() + largMoldura, getPy() + altImg, largImg, 0, largImg + largMoldura, altImg, null);
	}

	@Override
	public void setAtivo(boolean ativo) {
		super.setAtivo(ativo);
		coluna = 0;
		temporizador = 0;
	}
}
