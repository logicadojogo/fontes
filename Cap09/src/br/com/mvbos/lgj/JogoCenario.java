package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import javax.swing.ImageIcon;

import br.com.mvbos.lgj.base.CenarioPadrao;
import br.com.mvbos.lgj.base.Elemento;
import br.com.mvbos.lgj.base.MatUtil;
import br.com.mvbos.lgj.base.Texto;
import br.com.mvbos.lgj.base.Util;

public class JogoCenario extends CenarioPadrao {

	public enum Estado {
		JOGANDO, GANHOU, PERDEU
	}

	private Nave navJogUm;
	private Nave navJogDois;

	private int qtdeInicial = 10;
	private int contadorTiro;
	private int contadorAsteroides;

	private Tiro[] tiros = new Tiro[16];
	private Asteroide[] aerolitos = new Asteroide[qtdeInicial * 4];

	private Animacao colisaoAst;
	private Animacao explosaoAst;

	private ImageIcon fundo;
	private Texto texto = new Texto();
	private Random rand = new Random();
	private Estado estado = Estado.JOGANDO;

	private float graus;

	private boolean ampliar;
	private boolean reduzir = false; // Apenas exemplo
	private final float escala = 5 / 100f; // 5% do tamanho total da tela
	private static final AffineTransform AF_VAZIO = new AffineTransform();

	public JogoCenario(int largura, int altura) {
		super(largura, altura);
	}

	@Override
	public void carregar() {
		fundo = Recursos.getImagem(Recursos.Imagem.FUNDO);

		texto.setCor(Color.WHITE);

		colisaoAst = new Animacao(Recursos.getImagem(Recursos.Imagem.COLISAO_AST));
		explosaoAst = new Animacao(Recursos.getImagem(Recursos.Imagem.EXPLOSAO_AST));

		for (int i = 0; i < tiros.length; i++) {
			tiros[i] = new Tiro();
			tiros[i].setVel(15);
			tiros[i].setImagem(Recursos.getImagem(Recursos.Imagem.TIRO_A));
		}

		for (int i = 0; i < aerolitos.length; i++) {
			aerolitos[i] = new Asteroide();
			aerolitos[i].setImagem(Recursos.getImagem(Recursos.Imagem.AST_A));

			if (i < qtdeInicial) {
				aerolitos[i].setAtivo(true);
				aerolitos[i].setVel(rand.nextInt(3) + 3);
				aerolitos[i].setAngulo(rand.nextInt(360));
				aerolitos[i].setPx(rand.nextInt(largura));
				aerolitos[i].setPy(rand.nextInt(altura));

				contadorAsteroides++;
			}
		}

		navJogUm = new Nave();
		navJogUm.setAtivo(true);
		navJogUm.setImagem(Recursos.getImagem(Recursos.Imagem.JOGADOR_A));

		navJogDois = new Nave();
		navJogDois.setAtivo(Jogo.numeroJogadores > 0);
		navJogDois.setImagem(Recursos.getImagem(Recursos.Imagem.JOGADOR_B));

		Util.centraliza(navJogUm, largura, altura);
		Util.centraliza(navJogDois, largura, altura);

		navJogUm.setPy(navJogUm.getPy() - navJogUm.getAltura() / 2);
		navJogDois.setPy(navJogDois.getPy() + navJogDois.getAltura() / 2);
	}

	@Override
	public void descarregar() {
		navJogUm = null;
		navJogDois = null;
		tiros = null;
		aerolitos = null;
	}

	@Override
	public void atualizar() {

		if (estado != Estado.JOGANDO) {
			return;
		}

		controlaJogadorUm();
		controlaJogadorDois();

		if (Util.colide(navJogUm, navJogDois)) {
			ampliar = true;
			navJogUm.setAngulo(MatUtil.corrigeGraus(navJogUm.getAngulo() + 90));
			navJogDois.setAngulo(MatUtil.corrigeGraus(navJogDois.getAngulo() - 90));

		} else
			ampliar = false;

		navJogUm.atualiza();
		navJogDois.atualiza();

		colisaoAst.atualiza();
		explosaoAst.atualiza();

		corrigePosicao(navJogUm);
		corrigePosicao(navJogDois);

		for (int i = 0; i < contadorAsteroides; i++) {
			Asteroide ast = aerolitos[i];

			if (!ast.isAtivo())
				continue;

			for (Tiro tiro : tiros) {
				if (Util.colide(ast, tiro)) {
					tiro.setAtivo(false);

					explosaoAst.setAtivo(true);
					explosaoAst.setPx(ast.getPx());
					explosaoAst.setPy(ast.getPy());

					if (ast.divide())
						novaParteAsteroide(ast);
					else
						ast.setAtivo(false);

					tiro.getNave().somaPontos(ast.getPonto());

					short pontos = tiro.getNave().getPontos();
					if (navJogUm == tiro.getNave() && pontos > Jogo.jogadorPontos[0])
						Jogo.jogadorPontos[0] = pontos;
					else if (navJogDois == tiro.getNave() && pontos > Jogo.jogadorPontos[1])
						Jogo.jogadorPontos[1] = tiro.getNave().getPontos();

					break;
				}
			}

			if (Util.colide(ast, navJogUm)) {
				navJogUm.danos();

				colisaoAst.setAtivo(true);
				colisaoAst.setPx(navJogUm.getPx());
				colisaoAst.setPy(navJogUm.getPy());

				if (ast.divide())
					novaParteAsteroide(ast);
				else {
					ast.setAtivo(false);
					continue;
				}

			} else if (Util.colide(ast, navJogDois)) {
				navJogDois.danos();
				colisaoAst.setAtivo(true);
				colisaoAst.setPx(navJogDois.getPx());
				colisaoAst.setPy(navJogDois.getPy());

				if (ast.divide())
					novaParteAsteroide(ast);
				else {
					ast.setAtivo(false);
					continue;
				}
			}

			ast.atualiza();
			corrigePosicao(ast);
		}

		for (Tiro tiro : tiros) {
			if (!tiro.isAtivo())
				continue;

			if (Util.saiu(tiro, largura, altura, 20)) {
				tiro.setAtivo(false);
				tiro.getNave().errou();
			} else {
				tiro.atualiza();

				if (navJogUm != tiro.getNave() && Util.colide(tiro, navJogUm)) {
					tiro.setAtivo(false);
					navJogUm.danos();
					navJogUm.setAngulo(MatUtil.corrigeGraus(navJogUm.getAngulo() + 90));

				} else if (navJogDois != tiro.getNave() && Util.colide(tiro, navJogDois)) {
					tiro.setAtivo(false);
					navJogDois.danos();
					navJogDois.setAngulo(MatUtil.corrigeGraus(navJogDois.getAngulo() - 90));

				}
			}

		}

	}

	public void controlaJogadorUm() {
		graus = navJogUm.getAngulo();

		if (Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()])
			graus -= Nave.ROTACAO_VEL;
		else if (Jogo.controleTecla[Jogo.Tecla.DIREITA.ordinal()])
			graus += Nave.ROTACAO_VEL;

		navJogUm.setAngulo(graus);

		if (Jogo.controleTecla[Jogo.Tecla.CIMA.ordinal()]) {
			float vx = navJogUm.getVelEmX();
			float vy = navJogUm.getVelEmY();

			vx += MatUtil.moveAnguloEmX(navJogUm.getAngulo());
			vy += MatUtil.moveAnguloEmY(navJogUm.getAngulo());

			navJogUm.setVelEmX(vx);
			navJogUm.setVelEmY(vy);

		}

		if (Jogo.controleTecla[Jogo.Tecla.BC.ordinal()]) {
			adicionarTiro(navJogUm);
			Jogo.liberaTecla(Jogo.Tecla.BC);
		}
	}

	public void controlaJogadorDois() {

		if (!navJogDois.isAtivo()) {
			if (Jogo.controleTecla[Jogo.Tecla.MOUSE_A.ordinal()]) {
				navJogDois.setAtivo(true);
				Jogo.liberaTecla(Jogo.Tecla.MOUSE_A);
			}

			return;
		}

		graus = MatUtil.calculaDirecao(Jogo.pxyMouse.x, Jogo.pxyMouse.y, navJogDois.getPx(), navJogDois.getPy());
		graus = MatUtil.corrigeGraus(graus);
		navJogDois.setAngulo(graus);

		if (Jogo.controleTecla[Jogo.Tecla.MOUSE_A.ordinal()]) {
			adicionarTiro(navJogDois);
			Jogo.liberaTecla(Jogo.Tecla.MOUSE_A);
		}

		if (Jogo.controleTecla[Jogo.Tecla.MOUSE_B.ordinal()]) {
			float vx = navJogDois.getVelEmX();
			float vy = navJogDois.getVelEmY();

			vx += MatUtil.moveAnguloEmX(navJogDois.getAngulo());
			vy += MatUtil.moveAnguloEmY(navJogDois.getAngulo());

			navJogDois.setVelEmX(vx);
			navJogDois.setVelEmY(vy);
		}
	}

	private void adicionarTiro(Nave jogador) {
		if (contadorTiro > 1)
			contadorTiro--;
		else
			contadorTiro = tiros.length - 1;

		Tiro t = tiros[contadorTiro];
		if (t.isAtivo())
			return;

		t.setNave(jogador);
		t.setAngulo(jogador.getAngulo());

		Util.centraliza(t, jogador);

		if (navJogUm == jogador)
			t.setImagem(Recursos.getImagem(Recursos.Imagem.TIRO_A));
		else
			t.setImagem(Recursos.getImagem(Recursos.Imagem.TIRO_B));

		t.setAtivo(true);
	}

	private void novaParteAsteroide(Asteroide ast) {

		Asteroide novoAst = aerolitos[contadorAsteroides];

		novoAst.setAtivo(true);
		novoAst.setPx(ast.getPx());
		novoAst.setPy(ast.getPy());

		novoAst.setImagem(ast.getImagem());
		novoAst.setTamanho(ast.getTamanho());

		novoAst.setVel(rand.nextInt(3) + 5);

		float angulo = ast.getAngulo();

		ast.setAngulo(MatUtil.corrigeGraus(angulo + 90));
		novoAst.setAngulo(MatUtil.corrigeGraus(angulo - 90));

		contadorAsteroides++;
	}

	/**
	 * Teletransporte
	 */
	public void corrigePosicao(Elemento el) {
		Util.corrigePosicao(el, largura, altura);
	}

	@Override
	public void desenhar(Graphics2D g) {
		g.setTransform(AF_VAZIO); // Comentar para um zoom cada vez maior

		if (ampliar) {
			g.scale(1 + escala, 1 + escala); // 1,05
			g.translate(-largura * escala / 2f + 1, -altura * escala / 2f + 1); // Centralizar

		} else if (reduzir) {
			// Apenas exemplo
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, largura, altura);
			g.scale(1 - escala, 1 - escala); // 1 - 0,05 = 0,95
			g.translate(largura * escala / 2f + 1, altura * escala / 2f + 1); // Centralizar
		}

		g.drawImage(fundo.getImage(), 0, 0, null);

		// texto.desenha(g, "Tupã | " + navJogUm.getPontos(), 10, 20);
		// texto.desenha(g, "Îasy | " + navJogDois.getPontos(), largura - 120,
		// 20);

		texto.desenha(g, String.format("Tupã | %04d", navJogUm.getPontos()), 10, 20);
		texto.desenha(g, String.format("Îasy | %04d", navJogDois.getPontos()), largura - 120, 20);

		if (navJogUm.getSeguidos() > 2)
			texto.desenha(g, "x" + navJogUm.getSeguidos(), 10, 40);

		if (navJogDois.getSeguidos() > 2)
			texto.desenha(g, "x" + navJogDois.getSeguidos(), largura - 120, 40);

		for (int i = 0; i < tiros.length; i++) {
			if (tiros[i].isAtivo())
				tiros[i].desenha(g);
		}

		for (int i = 0; i < contadorAsteroides; i++) {
			if (aerolitos[i].isAtivo())
				aerolitos[i].desenha(g);
		}

		navJogUm.desenha(g);
		navJogDois.desenha(g);

		colisaoAst.desenha(g);
		explosaoAst.desenha(g);

	}

}
