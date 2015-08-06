package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.mvbos.lgj.base.Elemento;

public class Nave extends Elemento {

	private float angulo;
	private boolean inverteCor;

	public Nave(int px, int py, int largura, int altura) {
		super(px, py, largura, altura);
	}

	@Override
	public void desenha(Graphics2D g) {
		afAnterior = g.getTransform();

		float rad = (float) Math.toRadians(getAngulo());
		af.setToIdentity();
		af.rotate(rad, getLargura() / 2 + getPx(), getAltura() / 2 + getPy());
		g.setTransform(af);

		if (inverteCor)
			g.setColor(Color.BLUE);
		else
			g.setColor(Color.YELLOW);

		g.fillRect(getPx(), getPy(), getLargura(), getAltura());

		g.setColor(Color.GREEN);
		g.fillRect(getPx() + getLargura() / 2, getPy() + getAltura() / 4, getLargura() / 2, getAltura() / 2);

		g.setTransform(afAnterior);
	}

	public float getAngulo() {
		return angulo;
	}

	public void setAngulo(float angulo) {
		this.angulo = angulo;
	}

	public void inverteCor() {
		inverteCor = !inverteCor;
	}

}
