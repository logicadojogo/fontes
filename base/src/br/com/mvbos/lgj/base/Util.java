package br.com.mvbos.lgj.base;

public class Util {

	public static boolean colide(Elemento a, Elemento b) {
		if (!a.isAtivo() || !b.isAtivo())
			return false;

		final int plA = a.getPx() + a.getLargura();
		final int plB = b.getPx() + b.getLargura();
		final int paA = a.getPy() + a.getAltura();
		final int paB = b.getPy() + b.getAltura();

		if (plA > b.getPx() && a.getPx() < plB && paA > b.getPy() && a.getPy() < paB) {
			return true;
		}

		return false;
	}

	public static boolean colideX(Elemento a, Elemento b) {
		if (!a.isAtivo() || !b.isAtivo())
			return false;

		if (a.getPx() + a.getLargura() >= b.getPx() && a.getPx() <= b.getPx() + b.getLargura()) {
			return true;
		}

		return false;
	}

	public static void centraliza(Elemento el, int larg, int alt) {
		if (alt > 0)
			el.setPy(alt / 2 - el.getAltura() / 2);

		if (larg > 0)
			el.setPx(larg / 2 - el.getLargura() / 2);

	}

	public static void centraliza(Elemento el, Elemento elReferente) {
		el.setPx(elReferente.getPx() + elReferente.getLargura() / 2 - el.getLargura() / 2);
		el.setPy(elReferente.getPy() + elReferente.getAltura() / 2 - el.getAltura() / 2);
	}

	public static boolean saiu(Elemento e, int largura, int altura) {
		return saiu(e, largura, altura, 0);
	}

	public static boolean saiu(Elemento e, int largura, int altura, int margem) {
		if (e.getPx() < -margem || e.getPx() + e.getLargura() > largura + margem)
			return true;

		if (e.getPy() < -margem || e.getPy() + e.getAltura() > altura + margem)
			return true;

		return false;
	}

	/**
	 * Teletransporte
	 */
	public static void corrigePosicao(Elemento el, int limiteX, int limitY) {
		float nx = el.getMovPx(); // Nova posição x
		float ny = el.getMovPy(); // Nova posição y

		if (nx + el.getLargura() < 0)
			nx = limiteX;
		else if (nx > limiteX)
			nx = -el.getLargura();

		if (ny + el.getAltura() < 0)
			ny = limitY;
		else if (ny > limitY)
			ny = -el.getAltura();

		el.setPx(nx);
		el.setPy(ny);
	}

}
