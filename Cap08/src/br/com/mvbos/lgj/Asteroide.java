package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.mvbos.lgj.base.Elemento;

public class Asteroide extends Elemento {

	private float angulo;
	private float anguloRotacao;

	public Asteroide() {
	}

	public Asteroide(int larg, int alt) {
		super(0, 0, larg, alt);
	}

	@Override
	public void atualiza() {
		if (!isAtivo())
			return;

		float cos = (float) Math.cos(Math.toRadians(angulo));
		float sen = (float) Math.sin(Math.toRadians(angulo));

		setPx(getMovPx() + cos * getVel());
		setPy(getMovPy() + sen * getVel());

		anguloRotacao++;
		if (anguloRotacao == 360)
			anguloRotacao = 0;
	}

	@Override
	public void desenha(Graphics2D g) {
		if (!isAtivo())
			return;

		afAnterior = g.getTransform();

		af.setToIdentity();
		af.rotate(anguloRotacao, getLargura() / 2 + getPx(), getAltura() / 2 + getPy());
		g.setTransform(af);

		g.setColor(Color.RED);
		g.fillRect(getPx(), getPy(), getLargura(), getAltura());

		g.setTransform(afAnterior);
	}

	public float getAngulo() {
		return angulo;
	}

	public void setAngulo(float angulo) {
		this.angulo = angulo;
	}
}
