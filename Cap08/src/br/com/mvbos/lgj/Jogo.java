package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.mvbos.lgj.base.CenarioPadrao;

public class Jogo extends JFrame {

	/**
	 * Marcus Vinicius Becker
	 */

	private static final long serialVersionUID = 1L;

	private static final int FRAMES = 20;

	private static final int FPS = Math.round(1000 / (FRAMES + 1.5f));

	private static final int JANELA_ALTURA = 600;

	private static final int JANELA_LARGURA = 800;

	private final JPanel tela;

	private final Graphics2D g2d;

	private final BufferedImage buffer;

	private CenarioPadrao cenario;

	private ImageIcon fundo;

	public enum Tecla {
		CIMA, BAIXO, ESQUERDA, DIREITA, BA, BB, BC
	}

	public static boolean[] controleTecla = new boolean[Tecla.values().length];

	public static int nivel;

	public static int velocidade;

	public static boolean pausado;

	public Jogo() {

		buffer = new BufferedImage(JANELA_LARGURA, JANELA_ALTURA, BufferedImage.TYPE_INT_RGB);
		g2d = buffer.createGraphics();

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				setaTecla(e.getKeyCode(), false);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				setaTecla(e.getKeyCode(), true);
			}
		});

		tela = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(buffer, 0, 0, null);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(JANELA_LARGURA, JANELA_ALTURA);
			}

			@Override
			public Dimension getMinimumSize() {
				return getPreferredSize();
			}
		};

		getContentPane().add(tela);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();

		setVisible(true);
		tela.repaint();
	}

	private void carregarJogo() {
		fundo = new ImageIcon("imagens/bg.png");

		cenario = new InicioCenario(tela.getWidth(), tela.getHeight());
		// cenario = new JogoCenario(tela.getWidth(), tela.getHeight());
		cenario.carregar();
	}

	public static int atraso = 0;
	public static int somaFPS = 0;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("ss:SSSS");

	private static final boolean depurar = true;

	public void iniciarJogo() {
		long agora;
		int contadorFPS = 0;
		long prxAtualizacao = 0;

		long prxSegundo = System.currentTimeMillis() + 1000;

		while (true) {
			agora = System.currentTimeMillis();

			if (agora >= prxAtualizacao) {

				verificaTeclas();

				if (fundo == null) {
					g2d.setColor(Color.BLACK);
					g2d.fillRect(0, 0, JANELA_LARGURA, JANELA_ALTURA);
				} else
					g2d.drawImage(fundo.getImage(), 0, 0, null);

				if (cenario == null) {
					g2d.setColor(Color.WHITE);
					g2d.drawString("Carregando...", 20, 20);

				} else {
					if (!Jogo.pausado)
						cenario.atualizar();

					cenario.desenhar(g2d);

					if (Jogo.pausado) {
						g2d.setColor(Color.WHITE);
						g2d.drawString("Pausado", tela.getWidth() / 2 - 30, 30);
					}

					g2d.setColor(Color.WHITE);
					g2d.drawString("FPS " + somaFPS, 10, JANELA_ALTURA - 10);
				}

				tela.repaint();

				contadorFPS++;

				atraso = (int) (System.currentTimeMillis() - agora);
				prxAtualizacao = System.currentTimeMillis() + FPS;
			}

			if (depurar && agora >= prxSegundo) {
				System.out.println("Atraso = " + atraso);
				System.out.println("FPS = " + contadorFPS);
				System.out.println("Segundos: " + sdf.format(agora));

				somaFPS = contadorFPS;
				contadorFPS = 0;
				prxSegundo = System.currentTimeMillis() + 1000;
			}
		}
	}

	public void verificaTeclas() {
		if (controleTecla[Tecla.BA.ordinal()]) {
			// Pressionou espaço ou enter
			if (cenario instanceof InicioCenario) {
				cenario.descarregar();

				cenario = null;

				cenario = new JogoCenario(tela.getWidth(), tela.getHeight());

				cenario.carregar();

			} else
				Jogo.pausado = !Jogo.pausado;

			liberaTeclas();

		} else if (controleTecla[Tecla.BB.ordinal()]) {
			// Pressionou ESQ
			if (!(cenario instanceof InicioCenario)) {
				cenario.descarregar();

				cenario = null;
				cenario = new InicioCenario(tela.getWidth(), tela.getHeight());
				cenario.carregar();
			}

			liberaTeclas();
		}
	}

	public static void liberaTecla(Tecla tecla) {
		controleTecla[tecla.ordinal()] = false;
	}

	public static void liberaTeclas() {
		for (int i = 0; i < controleTecla.length; i++) {
			controleTecla[i] = false;
		}
	}

	private void setaTecla(int tecla, boolean pressionada) {
		switch (tecla) {
		case KeyEvent.VK_UP:
			controleTecla[Tecla.CIMA.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_DOWN:
			controleTecla[Tecla.BAIXO.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_LEFT:
			controleTecla[Tecla.ESQUERDA.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_RIGHT:
			controleTecla[Tecla.DIREITA.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_ESCAPE:
			controleTecla[Tecla.BB.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_ENTER:
			controleTecla[Tecla.BA.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_SPACE:
			controleTecla[Tecla.BC.ordinal()] = pressionada;
		}
	}

	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.carregarJogo();
		jogo.iniciarJogo();
	}
}
