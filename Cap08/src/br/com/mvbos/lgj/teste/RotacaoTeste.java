package br.com.mvbos.lgj.teste;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RotacaoTeste extends JFrame {

	private static final long serialVersionUID = 1L;

	public enum Modo {
		EIXO, EIXO_CENTRAL, EIXO_ELEMENTO, EIXO_ELEMENTO2, ELEMENTO_CENTRO, ELEMENTO_CENTRO_ESCALA, SEM_EFEITO
	}

	private static final int FPS = 1000 / 20;
	private static final int TAMANHO_TELA = 250;

	private int graus;
	private int inc = 1;
	private int largEl = 40, altEl = 40;

	private float escala;
	private float incEscala = 0.1f;

	private Modo modo = Modo.ELEMENTO_CENTRO_ESCALA;

	private JPanel tela;

	public RotacaoTeste() {
		this.setBackground(Color.LIGHT_GRAY);
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
					int prx = modo.ordinal() + 1;
					if (prx == Modo.values().length) {
						prx = 0;
					}

					modo = Modo.values()[prx];

				} else {
					inc = -inc;
				}

			}
		});

		tela = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;

				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fillRect(0, 0, TAMANHO_TELA, TAMANHO_TELA);

				g2d.setColor(Color.WHITE);
				g2d.drawString("Modo " + modo, 10, 10);

				float anguloEmRadiano = (float) Math.toRadians(graus);

				if (modo == Modo.EIXO) {
					g2d.rotate(anguloEmRadiano);

					g2d.setColor(Color.YELLOW);
					g2d.fillRect(0, 0, largEl, altEl); // 40x40

					g2d.setColor(Color.RED);
					g2d.fillRect(TAMANHO_TELA - largEl, TAMANHO_TELA - altEl, largEl, altEl);

				} else if (modo == Modo.EIXO_CENTRAL) {
					int meio = TAMANHO_TELA / 2;
					g2d.rotate(anguloEmRadiano, meio, meio);

					g2d.setColor(Color.YELLOW);
					g2d.fillRect(0, 0, largEl, altEl);

					g2d.setColor(Color.GREEN);
					g2d.fillRect(TAMANHO_TELA / 2 - largEl / 2, TAMANHO_TELA / 2 - altEl / 2, largEl, altEl);

					g2d.setColor(Color.RED);
					g2d.fillRect(TAMANHO_TELA - largEl, TAMANHO_TELA - altEl, largEl, altEl);

				} else if (modo == Modo.EIXO_ELEMENTO) {
					int px = 0;
					int py = 0;

					int metadeLargEl = largEl / 2;
					int metadeAltEl = altEl / 2;

					AffineTransform af = g2d.getTransform();
					// Quadrado Amarelo
					g2d.rotate(anguloEmRadiano, metadeLargEl + px, metadeAltEl + py);
					g2d.setColor(Color.YELLOW);
					g2d.fillRect(px, px, largEl, altEl);

					// Quadrado Verde
					g2d.setTransform(af);
					px = TAMANHO_TELA / 2 - metadeLargEl;
					py = TAMANHO_TELA / 2 - metadeAltEl;

					g2d.rotate(anguloEmRadiano, metadeLargEl + px, metadeAltEl + py);
					g2d.setColor(Color.GREEN);
					g2d.fillRect(px, py, largEl, altEl);

					// Quadrado Vermelho
					g2d.setTransform(af);
					px = TAMANHO_TELA - largEl;
					py = TAMANHO_TELA - altEl;

					g2d.translate(metadeLargEl + px, metadeAltEl + py);
					g2d.rotate(anguloEmRadiano);
					g2d.translate(-metadeLargEl - px, -metadeAltEl - py);

					g2d.setColor(Color.RED);
					g2d.fillRect(px, py, largEl, altEl);

				} else if (modo == Modo.EIXO_ELEMENTO2) {
					int px = 0;
					int py = 0;

					int metadeLargEl = largEl / 2;
					int metadeAltEl = altEl / 2;

					AffineTransform af = new AffineTransform();
					// Quadrado Amarelo
					af.rotate(anguloEmRadiano, metadeLargEl + px, metadeAltEl + py);
					g2d.setTransform(af);
					g2d.setColor(Color.YELLOW);
					g2d.fillRect(px, px, largEl, altEl);

					// Quadrado Verde
					px = TAMANHO_TELA / 2 - metadeLargEl;
					py = TAMANHO_TELA / 2 - metadeAltEl;

					af.setToIdentity();
					af.rotate(anguloEmRadiano, metadeLargEl + px, metadeAltEl + py);
					g2d.setTransform(af);
					g2d.setColor(Color.GREEN);
					g2d.fillRect(px, py, largEl, altEl);

					// Quadrado Vermelho
					px = TAMANHO_TELA - largEl;
					py = TAMANHO_TELA - altEl;

					af.setToIdentity();
					af.translate(metadeLargEl + px, metadeAltEl + py);
					af.rotate(anguloEmRadiano);
					af.translate(-metadeLargEl - px, -metadeAltEl - py);
					g2d.setTransform(af);

					g2d.setColor(Color.RED);
					g2d.fillRect(px, py, largEl, altEl);

				} else if (modo == Modo.ELEMENTO_CENTRO) {
					int metadeLargEl = largEl / 2;
					int metadeAltEl = altEl / 2;

					g2d.translate(TAMANHO_TELA / 2, TAMANHO_TELA / 2);
					g2d.rotate(anguloEmRadiano);
					g2d.translate(-metadeLargEl, -metadeAltEl);

					g2d.setColor(Color.YELLOW);
					g2d.fillRect(0, 0, largEl, altEl);

				} else if (modo == Modo.ELEMENTO_CENTRO_ESCALA) {
					int metadeLargEl = largEl / 2;
					int metadeAltEl = altEl / 2;

					// g2d.scale(escala, escala); // Chamada A

					g2d.translate(TAMANHO_TELA / 2, TAMANHO_TELA / 2);
					g2d.rotate(anguloEmRadiano);

					g2d.scale(escala, escala); // Chamada B

					g2d.translate(-metadeLargEl, -metadeAltEl);

					// g2d.scale(escala, escala); // Chamada C

					g2d.setColor(Color.YELLOW);
					g2d.fillRect(0, 0, largEl, altEl);

				} else {
					int px = 0;
					int py = 0;

					int metadeLargEl = largEl / 2;
					int metadeAltEl = altEl / 2;

					// Quadrado Amarelo
					g2d.setColor(Color.YELLOW);
					g2d.fillRect(px, px, largEl, altEl);

					// Quadrado Verde
					px = TAMANHO_TELA / 2 - metadeLargEl;
					py = TAMANHO_TELA / 2 - metadeAltEl;
					g2d.setColor(Color.GREEN);
					g2d.fillRect(px, py, largEl, altEl);

					// Quadrado Vermelho
					px = TAMANHO_TELA - largEl;
					py = TAMANHO_TELA - altEl;
					g2d.setColor(Color.RED);
					g2d.fillRect(px, py, largEl, altEl);
				}

			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(TAMANHO_TELA, TAMANHO_TELA);
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

	public void iniciar() {
		long prxAtualizacao = 0;

		while (true) {
			if (System.currentTimeMillis() >= prxAtualizacao) {

				graus += inc;

				if (graus > 360)
					graus = 0;
				else if (graus < 0)
					graus = 360;

				escala += incEscala;

				if (escala > 9f || escala < 0.1f)
					incEscala = -incEscala;

				tela.repaint();
				prxAtualizacao = System.currentTimeMillis() + FPS;
			}
		}
	}

	public static void main(String[] args) {
		RotacaoTeste jogo = new RotacaoTeste();
		jogo.iniciar();
	}

}
