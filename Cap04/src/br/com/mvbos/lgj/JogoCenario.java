package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;

import br.com.mvbos.lgj.base.CenarioPadrao;
import br.com.mvbos.lgj.base.Elemento;
import br.com.mvbos.lgj.base.Texto;
import br.com.mvbos.lgj.base.Util;

public class JogoCenario extends CenarioPadrao {

	enum Estado {
		JOGANDO, GANHOU, PERDEU, INICIO_ANIMACAO, FIM_ANIMACAO
	}

	private static final int _LARG = 25;

	private static final int RASTRO_INICIAL = 5;

	private int dx, dy;

	private boolean moveu;

	private int temporizador = 0;

	private int contadorRastro;

	private Elemento fruta;

	private Elemento serpente;

	private Elemento[] nivel;

	private Elemento[] rastros;

	private Texto texto = new Texto(new Font("Arial", Font.PLAIN, 25));

	private Random rand = new Random();

	// Frutas para finalizar o level
	private int dificuldade = 10;

	private int contadorNivel;

	private Estado estado = Estado.JOGANDO;

	// Animacao
	private int duracaoAnimacao = 10;

	private int iniAnimacao = 0;
	private int fimAnimacao = Nivel.niveis[0].length + duracaoAnimacao;

	public JogoCenario(int largura, int altura) {
		super(largura, altura);
	}

	@Override
	public void carregar() {

		// define direcao inicial
		dx = -1;
		contadorNivel = 0;
		contadorRastro = RASTRO_INICIAL;
		rastros = new Elemento[dificuldade + RASTRO_INICIAL];

		fruta = new Elemento(0, 0, _LARG, _LARG);
		fruta.setCor(Color.RED);

		serpente = new Elemento(0, 0, _LARG, _LARG);
		serpente.setAtivo(true);
		serpente.setCor(Color.YELLOW);
		serpente.setVel(Jogo.velocidade);

		char[][] nivelSelecionado = Nivel.niveis[Jogo.nivel];

		int total = 0;

		for (int linha = 0; linha < nivelSelecionado.length; linha++) {
			for (int coluna = 0; coluna < nivelSelecionado[0].length; coluna++) {
				if (nivelSelecionado[linha][coluna] == '0') {
					total++;

				} else if (nivelSelecionado[linha][coluna] == '1') {
					serpente.setPx(_LARG * coluna);
					serpente.setPy(_LARG * linha);
				}
			}
		}

		for (int i = 0; i < rastros.length; i++) {
			rastros[i] = new Elemento(serpente.getPx(), serpente.getPy(), _LARG, _LARG);
			rastros[i].setCor(Color.GREEN);
			rastros[i].setAtivo(true);
		}

		nivel = new Elemento[total];

		for (int linha = 0; linha < nivelSelecionado.length; linha++) {
			for (int coluna = 0; coluna < nivelSelecionado[0].length; coluna++) {
				if (nivelSelecionado[linha][coluna] == '0') {

					Elemento e = new Elemento();
					e.setAtivo(true);
					e.setCor(Color.LIGHT_GRAY);

					e.setPx(_LARG * coluna);
					e.setPy(_LARG * linha);

					e.setAltura(_LARG);
					e.setLargura(_LARG);

					nivel[contadorNivel++] = e;
				}
			}
		}
	}

	@Override
	public void descarregar() {
		fruta = null;
		rastros = null;
		serpente = null;
	}

	@Override
	public void atualizar() {

		if (estado == Estado.GANHOU || estado == Estado.PERDEU) {
			return;
		}

		if (estado == Estado.INICIO_ANIMACAO || estado == Estado.FIM_ANIMACAO) {

			if (estado == Estado.INICIO_ANIMACAO) {
				iniAnimacao++;

				if (iniAnimacao == fimAnimacao) {
					descarregar();
					carregar();
					estado = Estado.FIM_ANIMACAO;
				}

			} else {
				iniAnimacao--;

				if (iniAnimacao == 0)
					estado = Estado.JOGANDO;

			}

			return;
		}

		if (!moveu) {
			if (dy != 0) {
				if (Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()]) {
					dx = -1;

				} else if (Jogo.controleTecla[Jogo.Tecla.DIREITA.ordinal()]) {
					dx = 1;
				}

				if (dx != 0) {
					dy = 0;
					moveu = true;
				}

			} else if (dx != 0) {
				if (Jogo.controleTecla[Jogo.Tecla.CIMA.ordinal()]) {
					dy = -1;
				} else if (Jogo.controleTecla[Jogo.Tecla.BAIXO.ordinal()]) {
					dy = 1;
				}

				if (dy != 0) {
					dx = 0;
					moveu = true;
				}
			}
		}

		if (temporizador >= 20) {
			temporizador = 0;
			moveu = false;

			int x = serpente.getPx();
			int y = serpente.getPy();

			serpente.setPx(serpente.getPx() + _LARG * dx);
			serpente.setPy(serpente.getPy() + _LARG * dy);

			if (Util.saiu(serpente, largura, altura)) {
				serpente.setAtivo(false);
				estado = Estado.PERDEU;

			} else {

				// colisao com cenario
				for (int i = 0; i < contadorNivel; i++) {
					if (Util.colide(serpente, nivel[i])) {
						serpente.setAtivo(false);
						estado = Estado.PERDEU;
						break;
					}
				}

				// colisao com o rastro
				for (int i = 0; i < contadorRastro; i++) {
					if (Util.colide(serpente, rastros[i])) {
						serpente.setAtivo(false);
						estado = Estado.PERDEU;
						break;
					}
				}
			}

			if (Util.colide(fruta, serpente)) {
				// Adiciona uma pausa
				temporizador = -10;
				contadorRastro++;
				fruta.setAtivo(false);

				if (contadorRastro == rastros.length) {
					serpente.setAtivo(false);

					if (Jogo.nivel + 1 == Nivel.niveis.length) {
						estado = Estado.GANHOU;
					} else {
						Jogo.nivel++;
						estado = Estado.INICIO_ANIMACAO;
					}

				}

			}

			for (int i = 0; i < contadorRastro; i++) {
				Elemento rastro = rastros[i];
				int tx = rastro.getPx();
				int ty = rastro.getPy();

				rastro.setPx(x);
				rastro.setPy(y);

				x = tx;
				y = ty;
			}

		} else {
			temporizador += serpente.getVel();
		}

		// Adicionando frutas
		if (estado == Estado.JOGANDO && !fruta.isAtivo()) {
			int x = rand.nextInt(largura / _LARG);
			int y = rand.nextInt(altura / _LARG);

			fruta.setPx(x * _LARG);
			fruta.setPy(y * _LARG);
			fruta.setAtivo(true);

			// colisao com a serpente
			if (Util.colide(fruta, serpente)) {
				fruta.setAtivo(false);
				return;
			}

			// colisao com rastro
			for (int i = 0; i < contadorRastro; i++) {
				if (Util.colide(fruta, rastros[i])) {
					fruta.setAtivo(false);
					return;
				}
			}

			// colisao com cenario
			for (int i = 0; i < contadorNivel; i++) {
				if (Util.colide(fruta, nivel[i])) {
					fruta.setAtivo(false);
					return;
				}
			}

		}

	}

	@Override
	public void desenhar(Graphics2D g) {

		if (fruta.isAtivo()) {
			fruta.desenha(g);
		}

		for (Elemento e : nivel) {
			if (e == null)
				break;

			e.desenha(g);
		}

		for (int i = 0; i < contadorRastro; i++) {
			rastros[i].desenha(g);
		}

		serpente.desenha(g);

		texto.desenha(g, String.valueOf(rastros.length - contadorRastro), largura - 35, altura);

		if (estado == Estado.INICIO_ANIMACAO || estado == Estado.FIM_ANIMACAO) {

			char[][] nivelSelecionado = Nivel.niveis[Jogo.nivel];

			int limite = 0;
			g.setColor(Color.LIGHT_GRAY);

			for (int linha = 0; linha < nivelSelecionado.length; linha++) {
				limite++;

				if (limite >= iniAnimacao)
					break;

				for (int coluna = 0; coluna < nivelSelecionado[0].length; coluna++) {
					g.fillRect(_LARG * coluna, _LARG * linha, _LARG, _LARG);
				}
			}

		} else if (estado == Estado.GANHOU || estado == Estado.PERDEU) {

			if (estado == Estado.GANHOU)
				texto.desenha(g, "Ganhou!", 180, 180);
			else
				texto.desenha(g, "Vixe!", 180, 180);
		}

		if (Jogo.pausado)
			Jogo.textoPausa.desenha(g, "PAUSA", largura / 2 - Jogo.textoPausa.getFonte().getSize(), altura / 2);
	}
}
