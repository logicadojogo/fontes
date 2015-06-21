package br.com.mvbos.lgj.base;

import java.awt.Font;
import java.awt.Graphics2D;

public class Texto extends Elemento {

	private Font fonte;

	public Texto() {
		fonte = new Font("Tahoma", Font.PLAIN, 16);
	}

	public Texto(Font fonte) {
		this.fonte = fonte;
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
