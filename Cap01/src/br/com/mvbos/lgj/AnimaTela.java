package br.com.mvbos.lgj;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AnimaTela extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel tela;

	public AnimaTela() {
		tela = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {

			}
		};

		getContentPane().add(tela);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);
	}

	public static void main(String[] args) {
		new AnimaTela();
	}
}
