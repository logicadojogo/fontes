package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.ImageIcon;

import br.com.mvbos.lgj.base.CenarioPadrao;
import br.com.mvbos.lgj.base.Menu;
import br.com.mvbos.lgj.base.Texto;
import br.com.mvbos.lgj.base.Util;

public class InicioCenario extends CenarioPadrao {

	private ImageIcon fundo;
	private Random rand = new Random();
	private Asteroide[] aerolitos = new Asteroide[10];

	private int novaPy;
	private Texto texto = new Texto();

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

		novaPy++;
		if (novaPy + 100 > altura)
			novaPy = -100;

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

		g.setColor(Color.WHITE);
		texto.desenha(g, String.format("Tupã | %04d", Jogo.jogadorPontos[0]), largura / 4, novaPy);
		texto.desenha(g, String.format("Îasy | %04d", Jogo.jogadorPontos[1]), largura / 2 + 100, novaPy);

		menuJogo.desenha(g);
	}

}
