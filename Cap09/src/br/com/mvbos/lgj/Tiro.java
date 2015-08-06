package br.com.mvbos.lgj;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.lgj.base.Elemento;
import br.com.mvbos.lgj.base.MatUtil;

public class Tiro extends Elemento {

	private Nave nave;
	private float angulo;

	@Override
	public void atualiza() {
		if (!isAtivo())
			return;

		// TODO java bug fix
		if (getLargura() != getImagem().getIconWidth()) {
			setAltura(getImagem().getIconHeight());
			setLargura(getImagem().getIconWidth());
		}

		MatUtil.move(this, angulo, getVel());
	}

	@Override
	public void desenha(Graphics2D g) {
		if (!isAtivo())
			return;

		/*
		 * Utilizando dois AffineTransform para guardamos a tranformacao e uma
		 * para aplicarmos a tranformacao.
		 * 
		 * Pior pratica neste caso.
		 */

		afAnterior = g.getTransform();

		af.setTransform(afAnterior);// Agregando transformacoes anteriores
		af.rotate(Math.toRadians(angulo), getLargura() / 2 + getPx(), getAltura() / 2 + getPy());
		af.translate(getPx(), getPy());

		g.setTransform(af);

		g.drawImage(getImagem().getImage(), 0, 0, null);

		g.setTransform(afAnterior);
	}

	@Override
	public void setImagem(ImageIcon img) {
		super.setImagem(img);
		super.setAltura(img.getIconHeight());
		super.setLargura(img.getIconWidth());
	}

	public float getAngulo() {
		return angulo;
	}

	public void setAngulo(float angulo) {
		this.angulo = angulo;
	}

	public Nave getNave() {
		return nave;
	}

	public void setNave(Nave nave) {
		this.nave = nave;
	}

}
