package br.com.mvbos.lgj.base;

public class MatUtil {

	public static float moveAnguloEmX(float angulo) {
		return (float) Math.cos(Math.toRadians(angulo));
	}

	public static float moveAnguloEmY(float angulo) {
		return (float) Math.sin(Math.toRadians(angulo));
	}

	public static void move(Elemento el, float angulo, int vel) {
		float cos = (float) Math.cos(Math.toRadians(angulo));
		float sen = (float) Math.sin(Math.toRadians(angulo));

		el.setPx(el.getMovPx() + cos * vel);
		el.setPy(el.getMovPy() + sen * vel);
	}

	public static float corrigeGraus(float graus) {
		if (graus < 0)
			graus += 360;
		else if (graus > 360)
			graus -= 360;

		return graus;
	}

	public static float calculaDirecao(int xDestino, int yDestino, int xOrigem, int yOrigem) {
		return (float) Math.toDegrees(Math.atan2(yDestino - yOrigem, xDestino - xOrigem));
	}

	public static float calculaDirecao(Elemento origem, Elemento destino) {
		return (float) Math.toDegrees(Math.atan2(destino.getPy() - origem.getPy(), destino.getPx() - origem.getPx()));
	}

}
