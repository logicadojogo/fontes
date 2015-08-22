package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import br.com.mvbos.lgj.base.CenarioPadrao;
import br.com.mvbos.lgj.base.Texto;
import br.com.mvbos.lgj.base.Util;

public class JogoCenario extends CenarioPadrao {

	public enum Estado {
		JOGANDO, GANHOU, PERDEU
	}

	private Nave nave;
	private Tiro[] tiros = new Tiro[25];
	private Asteroide[] aerolitos = new Asteroide[50];

	private Texto texto = new Texto();
	private Random rand = new Random();
	private Estado estado = Estado.JOGANDO;

	private int pontos;
	private int adiciona = 2;
	private int contadorTiro;
	private int intervalo = 60;
	private int temporizador = 0;

	private float graus;

	public JogoCenario(int largura, int altura) {
		super(largura, altura);
	}

	@Override
	public void carregar() {

		texto.setCor(Color.WHITE);

		for (int i = 0; i < tiros.length; i++) {
			tiros[i] = new Tiro(5, 5);
			tiros[i].setVel(5);
		}

		for (int i = 0; i < aerolitos.length; i++) {
			aerolitos[i] = new Asteroide();
		}

		nave = new Nave(0, 0, 40, 40);
		nave.setAtivo(true);
		nave.setCor(Color.YELLOW);

		Util.centraliza(nave, largura, altura);
	}

	@Override
	public void descarregar() {
		nave = null;
		tiros = null;
		aerolitos = null;
	}

	@Override
	public void atualizar() {

		if (estado != Estado.JOGANDO) {
			return;
		}

		if (nave.getLargura() < 5) {
			estado = Estado.PERDEU;
			return;
		}

		if (temporizador == intervalo) {
			temporizador = 0;
			maisAerolitos();

		} else
			temporizador++;

		if (Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()])
			graus -= 10;
		else if (Jogo.controleTecla[Jogo.Tecla.DIREITA.ordinal()])
			graus += 10;

		if (graus < 0)
			graus += 360;
		else if (graus > 360)
			graus -= 360;

		nave.setAngulo(graus);

		if (Jogo.controleTecla[Jogo.Tecla.BC.ordinal()]) {
			adicionarTiro(nave.getAngulo(), TipoTiro.TRIPLO);
			Jogo.liberaTecla(Jogo.Tecla.BC);
		}

		for (Asteroide ast : aerolitos) {

			if (!ast.isAtivo())
				continue;

			// Asteroides comecam fora da tela
			if (ast.getPy() < ast.getAltura() * -2 || ast.getPy() > ast.getAltura() + altura) {
				ast.setAtivo(false);
				continue;
			}

			if (ast.getPx() + ast.getLargura() < 0 || ast.getPx() > largura) {
				ast.setAtivo(false);
				continue;
			}

			for (Tiro tiro : tiros) {
				if (Util.colide(ast, tiro)) {
					ast.setAtivo(false);
					tiro.setAtivo(false);
					pontos += ast.getVel();
					break;
				}
			}

			if (Util.colide(ast, nave)) {
				ast.setAtivo(false);
				nave.setLargura(nave.getLargura() - 2);
				nave.setAltura(nave.getAltura() - 2);
				nave.inverteCor();

				Util.centraliza(nave, largura, altura);
				continue;
			}

			ast.atualiza();
		}

		for (Tiro tiro : tiros) {
			if (!tiro.isAtivo())
				continue;

			if (Util.saiu(tiro, largura, altura))
				tiro.setAtivo(false);
			else
				tiro.atualiza();
		}

	}

	enum TipoTiro {
		COMUM, DUPLO, TRIPLO
	}

	private void proximoTiro() {
		if (contadorTiro > 1)
			contadorTiro--;
		else
			contadorTiro = tiros.length - 1;
	}

	private void adicionarTiro(float angulo, TipoTiro tipo) {

		if (TipoTiro.COMUM == tipo || TipoTiro.TRIPLO == tipo) {

			proximoTiro();

			Tiro t = tiros[contadorTiro];

			t.setAngulo(angulo);
			t.setPx(nave.getPx() + nave.getLargura() / 2 - t.getLargura() / 2);
			t.setPy(nave.getPy() + nave.getAltura() / 2 - t.getAltura() / 2);

			t.setAtivo(true);

		}

		if (TipoTiro.DUPLO == tipo || TipoTiro.TRIPLO == tipo) {
			proximoTiro();
			Tiro tiroA = tiros[contadorTiro];

			proximoTiro();
			Tiro tiroB = tiros[contadorTiro];

			tiroA.setAngulo(angulo - 10);
			tiroA.setPx(nave.getPx() + nave.getLargura() / 2 - tiroA.getLargura() / 2);
			tiroA.setPy(nave.getPy() + nave.getAltura() / 2 - tiroA.getAltura() / 2);

			tiroB.setAngulo(angulo + 10);
			tiroB.setPx(nave.getPx() + nave.getLargura() / 2 - tiroB.getLargura() / 2);
			tiroB.setPy(nave.getPy() + nave.getAltura() / 2 - tiroB.getAltura() / 2);

			tiroA.setAtivo(true);
			tiroB.setAtivo(true);

		}
	}

	private void maisAerolitos() {
		int contador = 0;
		for (int i = 0; i < aerolitos.length; i++) {
			if (contador == adiciona)
				break;

			Asteroide ast = aerolitos[i];

			if (ast.isAtivo())
				continue;

			contador++;
			ast.setAtivo(true);

			ast.setAltura((rand.nextInt(4) + 1) * 10);
			ast.setLargura(ast.getAltura());

			ast.setPx(rand.nextInt(largura));

			int py = rand.nextInt(2) * altura;
			if (py == 0)
				py = py - ast.getAltura();

			ast.setPy(py);
			ast.setVel(rand.nextInt(3) + 1);

			switch (Jogo.nivel) {
			case 0:
				// Modo Facil: Vai para qualquer lado
				ast.setAngulo(rand.nextInt(360));
				break;

			case 1:
				// Modo Normal: Vai em angulos proximos a nave do jogador
				if (ast.getPy() <= 0)
					ast.setAngulo(90);
				else
					ast.setAngulo(270);
				break;

			default:
				// Modo Dificil: Todos vao em diracao a nave do jogador
				float arco = (float) Math.atan2(nave.getPy() - ast.getPy(), nave.getPx() - ast.getPx());
				float angulo = (float) Math.toDegrees(arco);
				ast.setAngulo(angulo);
				break;
			}
		}
	}

	@Override
	public void desenhar(Graphics2D g) {
		texto.desenha(g, "Pontos: " + pontos, 10, 20);

		for (int i = 0; i < tiros.length; i++) {
			if (tiros[i].isAtivo())
				tiros[i].desenha(g);
		}

		for (int i = 0; i < aerolitos.length; i++) {
			if (aerolitos[i].isAtivo())
				aerolitos[i].desenha(g);
		}

		nave.desenha(g);
	}

}
