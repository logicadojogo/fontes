package br.com.mvbos.lgj;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.lgj.base.CenarioPadrao;
import br.com.mvbos.lgj.base.Elemento;
import br.com.mvbos.lgj.base.Menu;
import br.com.mvbos.lgj.base.Util;

public class InicioCenario extends CenarioPadrao {

	public InicioCenario(int largura, int altura) {
		super(largura, altura);
	}

	private Menu menuJogo;

	private Pizza pizza;
	private Legume[] inimigos;
	private Elemento superPastilha;

	@Override
	public void carregar() {

		menuJogo = new Menu("Fome");
		menuJogo.addOpcoes("Sem", "Pouca", "Muita");

		Util.centraliza(menuJogo, largura, altura);

		menuJogo.setPy(menuJogo.getPy() + menuJogo.getAltura());

		menuJogo.setAtivo(true);
		menuJogo.setSelecionado(true);

		pizza = new Pizza();
		pizza.setVel(3);
		pizza.setAtivo(true);
		pizza.setDx(-1);
		pizza.setPx(largura + pizza.getLargura());
		pizza.setPy(200);

		superPastilha = new Elemento() {

			@Override
			public void desenha(Graphics2D g) {
				if (!isAtivo())
					return;

				g.drawImage(getImagem().getImage(), getPx(), getPy() + JogoCenario.ESPACO_TOPO, null);
			}
		};

		ImageIcon pepperoni = new ImageIcon("imagens/pepperoni.png");
		superPastilha.setAtivo(true);
		superPastilha.setPx(5);
		superPastilha.setPy(pizza.getPy());
		superPastilha.setImagem(pepperoni);
		superPastilha.setLargura(pepperoni.getIconWidth());
		superPastilha.setAltura(pepperoni.getIconHeight());

		inimigos = new Legume[Legume.Tipo.values().length];
		for (int i = 0; i < inimigos.length; i++) {
			inimigos[i] = new Legume(Legume.Tipo.values()[i]);
			inimigos[i].setVel(3);
			inimigos[i].setAtivo(true);
			inimigos[i].setDx(-1);

			inimigos[i].setPy(pizza.getPy());
			inimigos[i].setPx(largura + (pizza.getLargura() * 2) * (i + 2));
		}
	}

	@Override
	public void descarregar() {
		Jogo.nivel = menuJogo.getOpcaoId();

		pizza = null;
		inimigos = null;
		superPastilha = null;
	}

	@Override
	public void atualizar() {
		if (Jogo.controleTecla[Jogo.Tecla.CIMA.ordinal()] || Jogo.controleTecla[Jogo.Tecla.BAIXO.ordinal()]) {

		} else if (Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()] || Jogo.controleTecla[Jogo.Tecla.DIREITA.ordinal()]) {
			menuJogo.setTrocaOpcao(Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()]);

		}

		Jogo.liberaTeclas();

		if (pizza.getPx() > largura * 2) {
			return;
		}

		pizza.atualiza();
		for (Legume legume : inimigos) {
			legume.atualiza();

			if (Util.colide(pizza, legume)) {
				legume.setModo(Legume.Modo.FANTASMA);
			}
		}

		if (Util.colide(pizza, superPastilha)) {
			pizza.setDx(1);
			pizza.setVel(pizza.getVel() + 2);
			superPastilha.setAtivo(false);
			for (Legume legume : inimigos) {
				legume.setDx(1);
				legume.setModo(Legume.Modo.FUGINDO);
			}
		}

	}

	@Override
	public void desenhar(Graphics2D g) {
		superPastilha.desenha(g);
		pizza.desenha(g);

		for (Legume legume : inimigos) {
			legume.desenha(g);
		}

		menuJogo.desenha(g);

	}

}
