package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.mvbos.lgj.base.Elemento;

public class Invader extends Elemento {

	enum Tipos {
		PEQUENO, MEDIO, GRANDE, CHEFE
	}

	private Tipos tipo;
	private boolean aberto;

	public Invader(Tipos t) {
		this.tipo = t;

		setLargura(20);
		setAltura(20);
	}

	@Override
	public void atualiza() {
		aberto = !aberto;
	}

	@Override
	public void desenha(Graphics2D g) {

		if (!isAtivo())
			return;

		int larg = getLargura();

		if (tipo == Tipos.PEQUENO) {

			larg = larg - 2;

			g.setColor(Color.BLUE);

			if (aberto) {
				// Desenha um circulo azul com quadrados ao redor
				g.fillOval(getPx(), getPy(), larg, getAltura());

				g.fillRect(getPx() - 5, getPy() - 5, 5, 5);
				g.fillRect(getPx() + larg, getPy() - 5, 5, 5);

				g.fillRect(getPx() - 5, getPy() + getLargura(), 5, 5);
				g.fillRect(getPx() + larg, getPy() + larg, 5, 5);

			} else {
				// Desenha um quadrado azul
				g.fillRect(getPx(), getPy(), larg, getAltura());
			}

		} else if (tipo == Tipos.MEDIO) {
			g.setColor(Color.ORANGE);

			if (aberto)
				// Desenha um quadrado vazio bordas na cor laranja
				g.drawRect(getPx(), getPy(), larg, getAltura());
			else
				// Desenha um quadrado preenchido na cor laranja
				g.fillRect(getPx(), getPy(), larg, getAltura());

		} else if (tipo == Tipos.GRANDE) {

			larg = larg + 4;

			if (aberto) {
				// Desenha um retangulo em pé na cor cinza escuro
				g.setColor(Color.DARK_GRAY);
				g.fillRect(getPx(), getPy(), getAltura(), larg);

			} else {
				// Desenha um retangulo deitado na cor cinza
				g.setColor(Color.GRAY);
				g.fillRect(getPx(), getPy(), larg, getAltura());
			}

		} else {
			// Tenta desenhar algo parecido com um disco voador com luzes
			// piscantes
			larg = larg + 10;

			g.setColor(Color.RED);
			g.fillOval(getPx(), getPy(), larg, getAltura());

			if (aberto) {
				// Tres quadrados brancos
				g.setColor(Color.WHITE);

				g.fillRect(getPx() + 7, getPy() + getAltura() / 2 - 2, 4, 4);
				g.fillRect(getPx() + 13, getPy() + getAltura() / 2 - 2, 4, 4);
				g.fillRect(getPx() + 19, getPy() + getAltura() / 2 - 2, 4, 4);
			}
		}

	}

	public int getPremio() {
		switch (tipo) {
		case PEQUENO:
			return 300;

		case MEDIO:
			return 200;

		case GRANDE:
			return 100;

		default:
			return 1000;
		}
	}
}
