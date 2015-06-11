package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InterativoMouse extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel tela;

	private int px;
	private int py;
	private Point mouseClick = new Point();
	private boolean jogando = true;

	private final int FPS = 1000 / 20; // 50

	public InterativoMouse() {

		tela = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, tela.getWidth(), tela.getHeight());

				int x = px - 20;
				int y = py - 20;

				g.setColor(Color.BLUE);
				g.fillRect(x, y, 40, 40);
				g.drawString("Agora eu estou em " + x + "x" + y, 5, 10);
			}
		};

		tela.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Botao mouse liberado
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Botao mouse pressionado
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Mouse saiu da tela
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Mouse entrou na tela
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Clique do mouse
				mouseClick = e.getPoint();
			}
		});

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
		px = mouseClick.x;
		py = mouseClick.y;
	}

	public static void main(String[] args) {
		InterativoMouse jogo = new InterativoMouse();
		jogo.inicia();
	}

}
