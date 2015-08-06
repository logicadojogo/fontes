package br.com.mvbos.lgj;

import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.ImageIcon;

import br.com.mvbos.lgj.base.CenarioPadrao;
import br.com.mvbos.lgj.base.Menu;
import br.com.mvbos.lgj.base.Util;

public class InicioCenario extends CenarioPadrao {

	private ImageIcon fundo;
	private Random rand = new Random();
	private Asteroide[] aerolitos = new Asteroide[10];

	public InicioCenario(int largura, int altura) {
		super(largura, altura);
	}

	private Menu menuJogo;

	@Override
	public void carregar() {
		fundo = Recursos.getImagem(Recursos.Imagem.FUNDO);

		for (int i = 0; i < aerolitos.length; i++) {
			aerolitos[i] = new Asteroide();
			aerolitos[i].setImagem(Recursos.getImagem(Recursos.Imagem.AST_A));

			aerolitos[i].setAtivo(true);
			aerolitos[i].setVel(rand.nextInt(3) + 3);
			aerolitos[i].setAngulo(rand.nextInt(360));
			aerolitos[i].setPx(rand.nextInt(largura));
			aerolitos[i].setPy(rand.nextInt(altura));

		}

		menuJogo = new Menu("Jogadores");
		menuJogo.addOpcoes("Um", "Dois");

		Util.centraliza(menuJogo, largura, altura);

		menuJogo.setPy(menuJogo.getPy() + menuJogo.getAltura());

		menuJogo.setAtivo(true);
		menuJogo.setSelecionado(true);
	}

	@Override
	public void descarregar() {
		fundo = null;
		aerolitos = null;
		Jogo.numeroJogadores = menuJogo.getOpcaoId();
	}

	@Override
	public void atualizar() {

		for (Asteroide a : aerolitos) {
			a.atualiza();
			Util.corrigePosicao(a, largura, altura);
		}

		if (Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()] || Jogo.controleTecla[Jogo.Tecla.DIREITA.ordinal()]) {
			menuJogo.setTrocaOpcao(Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()]);

		}

		Jogo.liberaTeclas();

	}

	@Override
	public void desenhar(Graphics2D g) {
		g.drawImage(fundo.getImage(), 0, 0, null);

		for (Asteroide a : aerolitos) {
			a.desenha(g);
		}

		menuJogo.desenha(g);
	}

}
