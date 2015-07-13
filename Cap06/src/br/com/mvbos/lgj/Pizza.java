package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.mvbos.lgj.JogoCenario.Direcao;
import br.com.mvbos.lgj.base.Elemento;

public class Pizza extends Elemento {

	public enum Modo {
		PRESO, ATIVO, INATIVO, FANTASMA, CACANDO, FUGINDO;
	}

	private int dx, dy;
	private Modo modo = Modo.PRESO;
	private Direcao direcao = Direcao.OESTE;

	public Pizza(int px, int py, int largura, int altura) {
		super(px, py, largura, altura);
	}

	@Override
	public void atualiza() {
		incPx(getVel() * getDx());
		incPy(getVel() * getDy());
	}

	@Override
	public void desenha(Graphics2D g) {
		if (modo == Modo.FUGINDO)
			g.setColor(Color.LIGHT_GRAY);
		else
			g.setColor(getCor());

		if (modo == Modo.FANTASMA)
			g.drawOval(getPx(), getPy() + JogoCenario.ESPACO_TOPO, getLargura(), getAltura());
		else
			g.fillOval(getPx(), getPy() + JogoCenario.ESPACO_TOPO, getLargura(), getAltura());
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public Direcao getDirecao() {
		return direcao;
	}

	public void setDirecao(Direcao direcao) {
		this.direcao = direcao;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

}
