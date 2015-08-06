package br.com.mvbos.lgj;

import br.com.mvbos.lgj.base.Elemento;

public class Tiro extends Elemento {

	private float angulo;

	public Tiro(int larg, int alt) {
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
	}

	public float getAngulo() {
		return angulo;
	}

	public void setAngulo(float angulo) {
		this.angulo = angulo;
	}

}
