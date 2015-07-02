package br.com.mvbos.lgj.base;

import java.awt.Font;
import java.awt.Graphics2D;

public class Texto extends Elemento {

	private Font fonte;

	public Texto() {
		this(16);
	}

	public Texto(int tamanho) {
		this("Tahoma", tamanho);
	}

	public Texto(String nomeFonte, int tamanho) {
		fonte = new Font(nomeFonte, Font.PLAIN, tamanho);
	}

	public void desenha(Graphics2D g, String texto) {
		desenha(g, texto, getPx(), getPy());
	}

	public void desenha(Graphics2D g, String texto, int px, int py) {
		if (getCor() != null)
			g.setColor(getCor());

		g.setFont(fonte);
		g.drawString(texto, px, py);
	}

	public Font getFonte() {
		return fonte;
	}

	public void setFonte(Font fonte) {
		this.fonte = fonte;
	}

}
