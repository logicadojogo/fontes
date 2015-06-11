package br.com.mvbos.lgj;

import javax.swing.JFrame;

public class Anima extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Anima() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Anima();
	}

}
