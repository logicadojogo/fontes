package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Interativo2 extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel tela;

	private int px;
	private int py;
	private boolean jogando = true;

	private final int FPS = 1000 / 20; // 50

	private boolean[] controleTecla = new boolean[4];

	public Interativo2() {

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
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, tela.getWidth(), tela.getHeight());

				int x = tela.getWidth() / 2 - 20 + px;
				int y = tela.getHeight() / 2 - 20 + py;

				g.setColor(Color.BLUE);
				g.fillRect(x, y, 40, 40);
				g.drawString("Agora eu estou em " + x + "x" + y, 5, 10);
			}
		};

		getContentPane().add(tela);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);
	}

	public void inicia() {
		long prxAtualizacao = 0;

		while (jogando) {
			if (System.currentTimeMillis() >= prxAtualizacao) {
				atualizaJogo();
				tela.repaint();

				prxAtualizacao = System.currentTimeMillis() + FPS;
			}
		}
	}

	private void atualizaJogo() {
		if (controleTecla[0])
			py--;
		else if (controleTecla[1])
			py++;

		if (controleTecla[2])
			px--;
		else if (controleTecla[3])
			px++;
	}

	private void setaTecla(int tecla, boolean pressionada) {
		switch (tecla) {
		case KeyEvent.VK_ESCAPE:
			// Tecla ESC
			jogando = false;
			dispose();
			break;
		case KeyEvent.VK_UP:
			// Seta para cima
			controleTecla[0] = pressionada;
			break;
		case KeyEvent.VK_DOWN:
			// Seta para baixo
			controleTecla[1] = pressionada;
			break;
		case KeyEvent.VK_LEFT:
			// Seta para esquerda
			controleTecla[2] = pressionada;
			break;
		case KeyEvent.VK_RIGHT:
			// Seta para direita
			controleTecla[3] = pressionada;
			break;
		}
	}

	public static void main(String[] args) {
		Interativo2 jogo = new Interativo2();
		jogo.inicia();
	}

}
