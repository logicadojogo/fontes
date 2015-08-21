package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.ImageIcon;

import br.com.mvbos.lgj.Legume.Modo;
import br.com.mvbos.lgj.base.CenarioPadrao;
import br.com.mvbos.lgj.base.Elemento;
import br.com.mvbos.lgj.base.Texto;
import br.com.mvbos.lgj.base.Util;

public class JogoCenario extends CenarioPadrao {

	public enum Estado {
		JOGANDO, GANHOU, PERDEU
	}

	public enum Direcao {
		NORTE, SUL, OESTE, LESTE;
	}

	private static final boolean depurar = false;

	private Direcao prxDirecao = Direcao.OESTE;

	private int temporizadorPizza;
	private int temporizadorFantasma;

	private Pizza pizza;

	private Legume[] inimigos;

	private int[][] grade;

	private Texto texto = new Texto();

	private Random rand = new Random();

	private Estado estado = Estado.JOGANDO;

	private final ImageIcon azeitona = new ImageIcon("imagens/azeitona.png");

	private final ImageIcon pepperoni = new ImageIcon("imagens/pepperoni.png");

	private final ImageIcon cenario = new ImageIcon("imagens/tiles_cenario.png");

	private int largEl;

	public static final int ESPACO_TOPO = 25; // Espacamento topo

	private int totalPastilha;
	private int pontos;

	private int pontoFugaCol;
	private int pontoFugaLin;

	private int pontoVoltaCol;
	private int pontoVoltaLin;

	private boolean superPizza;

	private int pausaColisao;
	private boolean trocarCenario;

	public JogoCenario(int largura, int altura) {
		super(largura, altura);
	}

	@Override
	public void carregar() {
		grade = copiaNivel(Nivel.cenario);
		largEl = largura / grade[0].length; // 16

		texto.setCor(Color.WHITE);

		pizza = new Pizza();
		pizza.setVel(4);
		pizza.setAtivo(true);
		pizza.setDirecao(Direcao.OESTE);

		// Inimigos
		inimigos = new Legume[4];

		inimigos[0] = new Legume(Legume.Tipo.VERMELHO);
		inimigos[0].setVel(3 + Jogo.nivel);
		inimigos[0].setAtivo(true);
		inimigos[0].setDirecao(Direcao.OESTE);
		inimigos[0].setModo(Legume.Modo.CACANDO);

		inimigos[1] = new Legume(Legume.Tipo.ROXO);
		inimigos[1].setVel(3 + Jogo.nivel);
		inimigos[1].setAtivo(false);
		inimigos[1].setDirecao(Direcao.NORTE);
		inimigos[1].setModo(Legume.Modo.PRESO);

		inimigos[2] = new Legume(Legume.Tipo.VERDE);
		inimigos[2].setVel(3 + Jogo.nivel);
		inimigos[2].setAtivo(false);
		inimigos[2].setDirecao(Direcao.NORTE);
		inimigos[2].setModo(Legume.Modo.PRESO);

		inimigos[3] = new Legume(Legume.Tipo.AMARELO);
		inimigos[3].setVel(3 + Jogo.nivel);
		inimigos[3].setAtivo(false);
		inimigos[3].setDirecao(Direcao.NORTE);
		inimigos[3].setModo(Legume.Modo.PRESO);

		for (int lin = 0; lin < grade.length; lin++) {
			for (int col = 0; col < grade[0].length; col++) {
				if (grade[lin][col] == Nivel.CN || grade[lin][col] == Nivel.SC) {
					totalPastilha++;

				} else if (grade[lin][col] == Nivel.PI) {
					pizza.setPx(converteInidicePosicao(col));
					pizza.setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P1) {
					inimigos[0].setPx(converteInidicePosicao(col));
					inimigos[0].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P2) {
					inimigos[1].setPx(converteInidicePosicao(col));
					inimigos[1].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P3) {
					inimigos[2].setPx(converteInidicePosicao(col));
					inimigos[2].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P4) {
					inimigos[3].setPx(converteInidicePosicao(col));
					inimigos[3].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.PF) {
					pontoFugaCol = col;
					pontoFugaLin = lin;

				} else if (grade[lin][col] == Nivel.PV) {
					pontoVoltaCol = col;
					pontoVoltaLin = lin;
				}
			}
		}
	}

	private int[][] copiaNivel(int[][] cenario) {
		int[][] temp = new int[cenario.length][cenario[0].length];
		for (int lin = 0; lin < cenario.length; lin++) {
			for (int col = 0; col < cenario[0].length; col++) {
				temp[lin][col] = cenario[lin][col];
			}
		}

		return temp;
	}

	public void reiniciar() {
		superPizza = false;
		temporizadorFantasma = 0;
		prxDirecao = Direcao.OESTE;

		pizza.setDirecao(Direcao.OESTE);

		inimigos[0].setDirecao(Direcao.OESTE);
		inimigos[0].setModo(Legume.Modo.CACANDO);
		inimigos[0].setAtivo(true);

		inimigos[1].setDirecao(Direcao.NORTE);
		inimigos[1].setModo(Legume.Modo.PRESO);
		inimigos[1].setAtivo(false);

		inimigos[2].setDirecao(Direcao.NORTE);
		inimigos[2].setModo(Legume.Modo.PRESO);
		inimigos[2].setAtivo(false);

		inimigos[3].setDirecao(Direcao.NORTE);
		inimigos[3].setModo(Legume.Modo.PRESO);
		inimigos[3].setAtivo(false);

		// Voce pode guardar as posicoes iniciais em cada elemento ou refazer o
		// loop
		for (int lin = 0; lin < grade.length; lin++) {
			for (int col = 0; col < grade[0].length; col++) {
				if (grade[lin][col] == Nivel.PI) {
					pizza.setPx(converteInidicePosicao(col));
					pizza.setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P1) {
					inimigos[0].setPx(converteInidicePosicao(col));
					inimigos[0].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P2) {
					inimigos[1].setPx(converteInidicePosicao(col));
					inimigos[1].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P3) {
					inimigos[2].setPx(converteInidicePosicao(col));
					inimigos[2].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P4) {
					inimigos[3].setPx(converteInidicePosicao(col));
					inimigos[3].setPy(converteInidicePosicao(lin));
				}
			}
		}
	}

	@Override
	public void descarregar() {
		pizza = null;
		grade = null;
		inimigos = null;
	}

	@Override
	public void atualizar() {

		if (estado == Estado.PERDEU) {
			return;
		}

		if (pausaColisao > 0) {
			pausaColisao--;
			return;
		}

		if (trocarCenario) {
			carregar();
			reiniciar();
			trocarCenario = false;
			return;
		}

		if (Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()]) {
			prxDirecao = Direcao.OESTE;

		} else if (Jogo.controleTecla[Jogo.Tecla.DIREITA.ordinal()]) {
			prxDirecao = Direcao.LESTE;

		} else if (Jogo.controleTecla[Jogo.Tecla.CIMA.ordinal()]) {
			prxDirecao = Direcao.NORTE;

		} else if (Jogo.controleTecla[Jogo.Tecla.BAIXO.ordinal()]) {
			prxDirecao = Direcao.SUL;
		}

		pizza.setDirecao(prxDirecao);
		atualizaDirecao(pizza);
		corrigePosicao(pizza);
		comePastilha(pizza);
		pizza.atualiza();

		if (superPizza && temporizadorPizza > 200) {
			temporizadorPizza = 0;
			superPizza(false);

		} else
			temporizadorPizza += 1;

		for (Legume el : inimigos) {
			if (el == null)
				continue;

			atualizaDirecaoInimigos(el);
			corrigePosicao(el);
			el.atualiza();

			if (Util.colide(pizza, el)) {

				if (el.getModo() == Legume.Modo.CACANDO) {
					reiniciar();
				} else if (el.getModo() == Legume.Modo.FUGINDO) {
					el.setAtivo(false);
					el.setModo(Legume.Modo.FANTASMA);
					pontos += 50;
					pausaColisao = 5;
				}
			}
		}
	}

	private boolean validaDirecao(Direcao dir, Elemento el) {

		if (dir == Direcao.OESTE && validaMovimento(el, -1, 0))
			return true;

		else if (dir == Direcao.LESTE && validaMovimento(el, 1, 0))
			return true;

		else if (dir == Direcao.NORTE && validaMovimento(el, 0, -1))
			return true;

		else if (dir == Direcao.SUL && validaMovimento(el, 0, 1))
			return true;

		return false;
	}

	private boolean validaMovimento(Elemento el, int dx, int dy) {
		// Proxima posicao x e y
		int prxPosX = el.getPx() + el.getVel() * dx;
		int prxPosY = el.getPy() + el.getVel() * dy;

		int col = convertePosicaoIndice(prxPosX);
		int lin = convertePosicaoIndice(prxPosY);

		int colLarg = convertePosicaoIndice(prxPosX + el.getLargura() - el.getVel());
		int linAlt = convertePosicaoIndice(prxPosY + el.getAltura() - el.getVel());

		if (foraDaGrade(col, lin) || foraDaGrade(colLarg, linAlt))
			return true;

		// Validar linha branca
		if (el instanceof Legume) {
			if (grade[lin][col] == Nivel.LN || grade[lin][colLarg] == Nivel.LN || grade[linAlt][col] == Nivel.LN || grade[linAlt][colLarg] == Nivel.LN) {

				if (el.isAtivo() || ((Legume) el).getModo() == Modo.PRESO)
					return false;

				return true;
			}
		}

		if (grade[lin][col] >= Nivel.BL || grade[lin][colLarg] >= Nivel.BL || grade[linAlt][col] >= Nivel.BL || grade[linAlt][colLarg] >= Nivel.BL) {

			return false;
		}

		return true;
	}

	private void atualizaDirecao(Pizza el) {

		if (foraDaTela(el))
			return;

		// Temporario Direcao X e Y
		int tempDx = el.getDx();
		int tempDy = el.getDy();

		Direcao direcao = el.getDirecao();

		if (validaDirecao(direcao, el)) {
			if (direcao == Direcao.OESTE)
				tempDx = -1;
			else if (direcao == Direcao.LESTE)
				tempDx = 1;

			if (direcao == Direcao.NORTE)
				tempDy = -1;
			else if (direcao == Direcao.SUL)
				tempDy = 1;

		}

		if (!validaMovimento(el, tempDx, tempDy))
			tempDx = tempDy = 0;

		el.setDx(tempDx);
		el.setDy(tempDy);
	}

	private void atualizaDirecaoInimigos(Legume el) {

		if (foraDaTela(el))
			return;

		int col = convertePosicaoIndice(el.getPx());
		int lin = convertePosicaoIndice(el.getPy());

		Direcao direcao = el.getDirecao();

		// Variaveis auxiliares
		Direcao tempDir = null;
		int tempDx = 0, tempDy = 0;
		int xCol = 0, yLin = 0;

		if (el.getModo() == Legume.Modo.PRESO) {
			if (el.getDirecao() == Direcao.SUL && !validaDirecao(Direcao.SUL, el))
				el.setDirecao(Direcao.NORTE);

			else if (el.getDirecao() == Direcao.NORTE && !validaDirecao(Direcao.NORTE, el))
				el.setDirecao(Direcao.SUL);

			else if (el.getDirecao() != Direcao.NORTE && el.getDirecao() != Direcao.SUL)
				el.setDirecao(Direcao.NORTE);

			if (temporizadorFantasma > 50) {
				temporizadorFantasma = 0; // Faz os inimigos sairem em tempos
											// diferentes
				el.setModo(Legume.Modo.ATIVO);

			} else
				temporizadorFantasma++;

		} else if (el.getModo() == Legume.Modo.ATIVO) {
			xCol = pontoFugaCol;
			yLin = pontoFugaLin;

			int colLarg = convertePosicaoIndice(el.getPx() + el.getLargura() - el.getVel());
			int linAlt = convertePosicaoIndice(el.getPy() + el.getAltura() - el.getVel());

			if (lin > yLin && validaDirecao(Direcao.NORTE, el))
				el.setDirecao(Direcao.NORTE);

			else if (lin < yLin && validaDirecao(Direcao.SUL, el))
				el.setDirecao(Direcao.SUL);

			else if (col < xCol && validaDirecao(Direcao.LESTE, el))
				el.setDirecao(Direcao.LESTE);

			else if (col > xCol && validaDirecao(Direcao.OESTE, el))
				el.setDirecao(Direcao.OESTE);

			else if (col == xCol && lin == yLin && colLarg == xCol && linAlt == yLin) {
				el.setAtivo(true);
				el.setModo(Legume.Modo.CACANDO);
			}

		} else if (el.getModo() == Legume.Modo.CACANDO || el.getModo() == Legume.Modo.FUGINDO) {

			xCol = convertePosicaoIndice(pizza.getPx());
			yLin = convertePosicaoIndice(pizza.getPy());

			// Inverte posicao para fugir
			if (el.getModo() == Legume.Modo.FUGINDO) {
				xCol = xCol * -1;
				yLin = yLin * -1;
			}

			// TODO melhorar, problema de leg
			boolean perdido = rand.nextInt(100) == 35;

			if (el.isAtivo() && perdido) {
				tempDir = sorteiaDirecao();

			} else if (direcao == null) {
				direcao = sorteiaDirecao();

			} else if (direcao == Direcao.NORTE || direcao == Direcao.SUL) {
				if (xCol < col && validaDirecao(Direcao.OESTE, el))
					tempDir = Direcao.OESTE;
				else if (xCol > col && validaDirecao(Direcao.LESTE, el))
					tempDir = Direcao.LESTE;

			} else {
				/* direcao = OESTE ou LESTE */
				if (yLin < lin && validaDirecao(Direcao.NORTE, el))
					tempDir = Direcao.NORTE;
				else if (yLin > lin && validaDirecao(Direcao.SUL, el))
					tempDir = Direcao.SUL;
			}

			if (tempDir != null && validaDirecao(tempDir, el))
				el.setDirecao(tempDir);
			else if (!validaDirecao(el.getDirecao(), el))
				el.setDirecao(sorteiaDirecao());

		} else if (el.getModo() == Legume.Modo.FANTASMA) {
			xCol = pontoFugaCol;
			yLin = pontoFugaLin;

			if (direcao == Direcao.NORTE || direcao == Direcao.SUL) {
				if (xCol < col && validaDirecao(Direcao.OESTE, el))
					tempDir = Direcao.OESTE;
				else if (xCol > col && validaDirecao(Direcao.LESTE, el))
					tempDir = Direcao.LESTE;

			} else {
				if (yLin < lin && validaDirecao(Direcao.NORTE, el))
					tempDir = Direcao.NORTE;
				else if (yLin > lin && validaDirecao(Direcao.SUL, el))
					tempDir = Direcao.SUL;
			}

			if (tempDir != null && validaDirecao(tempDir, el))
				el.setDirecao(tempDir);
			else if (!validaDirecao(el.getDirecao(), el))
				el.setDirecao(trocaDirecao(el.getDirecao()));

			if (col == xCol && lin == yLin)
				el.setModo(Legume.Modo.INATIVO);

		} else if (el.getModo() == Legume.Modo.INATIVO) {
			xCol = pontoVoltaCol;
			yLin = pontoVoltaLin;

			if (lin > yLin && validaDirecao(Direcao.NORTE, el))
				el.setDirecao(Direcao.NORTE);

			else if (lin < yLin && validaDirecao(Direcao.SUL, el))
				el.setDirecao(Direcao.SUL);

			else if (col < xCol && validaDirecao(Direcao.LESTE, el))
				el.setDirecao(Direcao.LESTE);

			else if (col > xCol && validaDirecao(Direcao.OESTE, el))
				el.setDirecao(Direcao.OESTE);

			else if (col == xCol && lin == yLin)
				el.setModo(Legume.Modo.PRESO);
		}

		if (validaDirecao(el.getDirecao(), el)) {
			if (el.getDirecao() == Direcao.NORTE)
				tempDy = -1;
			else if (el.getDirecao() == Direcao.SUL)
				tempDy = 1;
			else if (el.getDirecao() == Direcao.OESTE)
				tempDx = -1;
			else if (el.getDirecao() == Direcao.LESTE)
				tempDx = 1;
		}

		el.setDx(tempDx);
		el.setDy(tempDy);
	}

	private Direcao trocaDirecao(Direcao direcao) {
		if (direcao == Direcao.NORTE)
			return Direcao.OESTE;
		else if (direcao == Direcao.OESTE)
			return Direcao.SUL;
		else if (direcao == Direcao.SUL)
			return Direcao.LESTE;
		else
			return Direcao.NORTE;
	}

	private Direcao sorteiaDirecao() {
		return Direcao.values()[rand.nextInt(Direcao.values().length)];
	}

	private boolean foraDaGrade(int coluna, int linha) {
		if (linha < 0 || linha >= grade.length)
			return true;

		if (coluna < 0 || coluna >= grade[0].length)
			return true;

		return false;
	}

	private boolean foraDaTela(Elemento el) {
		if (el.getPx() < 0 || el.getPx() + el.getLargura() > largura)
			return true;

		if (el.getPy() < 0 || el.getPy() + el.getAltura() > altura)
			return true;

		return false;
	}

	/**
	 * Teleporte
	 */
	private void corrigePosicao(Elemento el) {
		int novaPx = el.getPx(); // Nova posição x
		int novaPy = el.getPy(); // Nova posição y

		int col = convertePosicaoIndice(el.getPx()) * largEl;
		int lin = convertePosicaoIndice(el.getPy()) * largEl;

		if (el.getDx() == 0 && novaPx != col)
			novaPx = col;
		else if (el.getPx() + largEl < 0)
			novaPx = largura;
		else if (el.getPx() > largura)
			novaPx = -largEl;

		if (el.getDy() == 0 && novaPy != lin)
			novaPy = lin;
		else if (el.getPy() + largEl < 0)
			novaPy = altura;
		else if (el.getPy() > altura)
			novaPy = -largEl;

		el.setPx(novaPx);
		el.setPy(novaPy);
	}

	private void comePastilha(Elemento el) {
		int col = convertePosicaoIndice(el.getPx());
		int lin = convertePosicaoIndice(el.getPy());

		if (foraDaGrade(col, lin)) {
			return;
		}

		if (grade[lin][col] == Nivel.CN || grade[lin][col] == Nivel.SC) {
			pontos += grade[lin][col] == Nivel.CN ? 5 : 25;
			totalPastilha--;

			if (totalPastilha == 0) {
				pausaColisao = 15;
				trocarCenario = true;
				estado = JogoCenario.Estado.GANHOU;

			} else if (grade[lin][col] == Nivel.SC)
				superPizza(true);

			grade[lin][col] = Nivel.EV;
		}
	}

	private void superPizza(boolean modoSuper) {
		superPizza = modoSuper;
		temporizadorPizza = 0;

		for (Legume el : inimigos) {
			if (el == null)
				continue;

			if (modoSuper && el.getModo() == Legume.Modo.CACANDO)
				el.setModo(Legume.Modo.FUGINDO);
			else if (!modoSuper && el.getModo() == Legume.Modo.FUGINDO)
				el.setModo(Legume.Modo.CACANDO);
		}
	}

	private int converteInidicePosicao(int linhaColuna) {
		return linhaColuna * largEl;
	}

	private int convertePosicaoIndice(int eixoXY) {
		return eixoXY / largEl;
	}

	protected void imprimeMovimento(int x, int y, int ix, int iy, int dx, int dy) {

		int px, py;

		px = x + ix * dx;
		py = y + iy * dy;

		int col = convertePosicaoIndice(px);
		int lin = convertePosicaoIndice(py);

		int col2 = convertePosicaoIndice(px + 14);
		int lin2 = convertePosicaoIndice(py + 14);

		System.out.print("[x=" + x + ", y=" + y + ", ix=" + ix + ", iy=" + iy + ", dx=" + dx + ", dy=" + dy + "]");
		System.out.print("[lin=" + lin + ", col=" + col + "]");
		System.out.println("[lin2=" + lin2 + ", col2=" + col2 + "]");

	}

	@Override
	public void desenhar(Graphics2D g) {

		for (int lin = 0; lin < grade.length; lin++) {
			for (int col = 0; col < grade[0].length; col++) {
				int valor = grade[lin][col];

				if (valor >= 0) {

					int linha = 0;
					int coluna = valor;

					if (valor > 9) {
						linha = valor / 10;
						coluna = valor % 10;
					}

					int pX = col * largEl;
					int pY = lin * largEl + ESPACO_TOPO;

					int largMoldura = cenario.getIconWidth() / 9;
					int altMoldura = cenario.getIconHeight() / 5;

					int largImg = largMoldura * coluna;
					int altImg = altMoldura * linha;

					g.drawImage(cenario.getImage(), pX, pY, pX + largMoldura, pY + altMoldura, largImg, altImg, largImg + largMoldura, altImg + altMoldura, null);

				} else {
					if (valor == Nivel.CN)
						g.drawImage(azeitona.getImage(), col * largEl, lin * largEl + ESPACO_TOPO, null);
					else if (valor == Nivel.SC)
						g.drawImage(pepperoni.getImage(), col * largEl, lin * largEl + ESPACO_TOPO, null);
				}

			}
		}

		texto.desenha(g, "Pontos: " + pontos, 10, 20);
		pizza.desenha(g);

		for (Elemento el : inimigos) {
			if (el == null)
				continue;

			el.desenha(g);
		}

		if (depurar) {
			g.setColor(Color.WHITE);
			for (int i = 0; i < grade.length; i++) {
				for (int j = 0; j < grade[0].length; j++)
					g.drawRect(j * largEl, i * largEl + ESPACO_TOPO, largEl, largEl);

			}

			int col = convertePosicaoIndice(pizza.getPx());
			int lin = convertePosicaoIndice(pizza.getPy());

			g.setColor(Color.ORANGE);
			g.fillRect(col * 16, lin * 16 + ESPACO_TOPO, 16, 16);
		}

	}

}
