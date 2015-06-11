package br.com.mvbos.lgj;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AnimaTelaDesenho extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel tela;

	public AnimaTelaDesenho() {
		tela = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				g.setColor(Color.BLUE);
				g.drawLine(0, 240, 640, 240);
				g.drawRect(10, 25, 20, 20);
				g.drawOval(30, 20, 40, 30);

				g.setColor(Color.YELLOW);
				g.drawLine(320, 0, 320, 480);
				g.fillRect(110, 125, 120, 120);
				g.fillOval(230, 220, 240, 230);

				g.setColor(Color.RED);
				g.drawString("Eu seria um ótimo Score!", 5, 10);
			}
		};

		getContentPane().add(tela);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);

		tela.repaint();
	}

	public static void main(String[] args) {
		new AnimaTelaDesenho();
	}
}
