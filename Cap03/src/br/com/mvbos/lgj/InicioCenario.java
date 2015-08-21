package br.com.mvbos.lgj;

import java.awt.Graphics2D;

import br.com.mvbos.lgj.base.CenarioPadrao;
import br.com.mvbos.lgj.base.Menu;
import br.com.mvbos.lgj.base.Util;

public class InicioCenario extends CenarioPadrao {

	public InicioCenario(int largura, int altura) {
		super(largura, altura);
	}

	private Bola bola;

	private Menu menuModo;

	private Menu menuVeloc;

	@Override
	public void carregar() {
		bola = new Bola();

		menuModo = new Menu("Modo");
		menuModo.addOpcoes("Normal", "Em casa");

		menuVeloc = new Menu("Vel.");
		menuVeloc.addOpcoes("Normal", "Rápido", "Lento");

		Util.centraliza(bola, largura, altura);
		Util.centraliza(menuModo, largura, altura);
		Util.centraliza(menuVeloc, largura, altura);

		menuModo.setPy(menuModo.getPy() + 20);
		menuVeloc.setPy(menuModo.getPy() + menuModo.getAltura());

		bola.setAtivo(true);
		menuModo.setSelecionado(true);
		menuModo.setAtivo(true);
		menuVeloc.setAtivo(true);
	}

	@Override
	public void descarregar() {
		Jogo.velocidade = bola.getVel();
		Jogo.modoNormal = menuModo.getOpcaoId() == 0;
	}

	@Override
	public void atualizar() {
		if (Jogo.controleTecla[Jogo.Tecla.CIMA.ordinal()] || Jogo.controleTecla[Jogo.Tecla.BAIXO.ordinal()]) {
			if (menuModo.isSelecionado()) {
				menuModo.setSelecionado(false);
				menuVeloc.setSelecionado(true);

			} else {
				menuModo.setSelecionado(true);
				menuVeloc.setSelecionado(false);
			}

		} else if (Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()] || Jogo.controleTecla[Jogo.Tecla.DIREITA.ordinal()]) {

			boolean esqueda = Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()];
			menuModo.trocaOpcao(esqueda);
			menuVeloc.trocaOpcao(esqueda);

			if (menuVeloc.getOpcaoId() == 0) {
				bola.setVel(Bola.VEL_INICIAL);

			} else if (menuVeloc.getOpcaoId() == 1) {
				bola.setVel(Bola.VEL_INICIAL * 2);

			} else {
				bola.setVel(Bola.VEL_INICIAL / 2);
			}

		}

		Jogo.liberaTeclas();

		// Controle da bola
		bola.incPx();
		bola.incPy();

		if (Util.colide(menuModo, bola) || Util.colide(menuVeloc, bola)) {
			bola.inverteX();
			bola.inverteY();
		}

		if (bola.getPx() < 0 || bola.getPx() + bola.getLargura() > largura) {
			// Colisao nas laterais da tela
			bola.inverteX();

		} else if (bola.getPy() <= 0 || bola.getPy() + bola.getAltura() >= altura) {
			// Colisao no topo ou base da tela
			bola.inverteY();
		}

		if (bola.getPy() < 0)
			bola.setPy(0);
		else if (bola.getPy() + bola.getAltura() > altura)
			bola.setPy(altura - bola.getAltura());
	}

	@Override
	public void desenhar(Graphics2D g) {
		bola.desenha(g);
		menuModo.desenha(g);
		menuVeloc.desenha(g);
	}

}
