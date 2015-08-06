package br.com.mvbos.lgj.teste;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
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

	private float escala;
	private float incEscala = 0.1f;

	private Modo modo = Modo.EIXO;

	private JPanel tela;
	private final ImageIcon imgA = new ImageIcon("imagens/teste_a.png");
	private final ImageIcon imgB = new ImageIcon("imagens/teste_b.png");

	private AffineTransform af = new AffineTransform();

	public RotacaoTeste() {

		this.setBackground(Color.LIGHT_GRAY);

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
					int prx = modo.ordinal() + 1;
					if (prx == Modo.values().length)
						prx = 0;

					modo = Modo.values()[prx];

				} else
					inc = -inc;

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
					af.setToIdentity();
					af.rotate(anguloEmRadiano);
					g2d.drawImage(imgA.getImage(), af, null);

					af.translate(TAMANHO_TELA - imgB.getIconWidth(), TAMANHO_TELA - imgB.getIconHeight());
					g2d.drawImage(imgB.getImage(), af, null);

				} else if (modo == Modo.EIXO_CENTRAL) {
					int meio = TAMANHO_TELA / 2;
					af.setToIdentity();
					af.rotate(anguloEmRadiano, meio, meio);

					g2d.drawImage(imgA.getImage(), af, null);

					af.translate(TAMANHO_TELA / 2 - imgB.getIconWidth() / 2, TAMANHO_TELA / 2 - imgB.getIconHeight() / 2);
					g2d.drawImage(imgB.getImage(), af, null);

					// af.translate(-af.getTranslateX(), -af.getTranslateY());
					af.setToIdentity();
					af.rotate(anguloEmRadiano, meio, meio);

					af.translate(TAMANHO_TELA - imgA.getIconWidth(), TAMANHO_TELA - imgA.getIconHeight());
					g2d.drawImage(imgA.getImage(), af, null);

				} else if (modo == Modo.EIXO_ELEMENTO) {
					int px = 0;
					int py = 0;

					// Quadrado Amarelo
					g2d.rotate(anguloEmRadiano, imgA.getIconWidth() / 2 + px, imgA.getIconHeight() / 2 + py);
					g2d.drawImage(imgA.getImage(), px, px, null);

					// Quadrado Verde
					px = TAMANHO_TELA / 2 - imgB.getIconWidth() / 2;
					py = TAMANHO_TELA / 2 - imgB.getIconHeight() / 2;

					af.setToIdentity();
					af.rotate(anguloEmRadiano, imgB.getIconWidth() / 2 + px, imgB.getIconWidth() / 2 + py);
					g2d.setTransform(af);

					g2d.drawRect(px, py, imgB.getIconWidth(), imgB.getIconWidth());
					g2d.drawImage(imgB.getImage(), px, py, null);

					// Quadrado Vermelho
					af.setToIdentity();
					g2d.setTransform(af);
					px = TAMANHO_TELA - imgA.getIconWidth();
					py = TAMANHO_TELA - imgA.getIconHeight();

					g2d.translate(imgA.getIconWidth() / 2 + px, imgA.getIconHeight() / 2 + py);
					g2d.rotate(anguloEmRadiano);
					g2d.translate(-(imgA.getIconWidth() / 2) - px, -(imgA.getIconHeight() / 2) - py);

					g2d.drawImage(imgA.getImage(), px, py, null);

				} else if (modo == Modo.EIXO_ELEMENTO2) {
					int metadeLargEl = imgB.getIconWidth() / 2;
					int metadeAltEl = imgB.getIconHeight() / 2;
					int px = 0;
					int py = 0;

					af.setToIdentity();

					// Quadrado Amarelo
					af.rotate(anguloEmRadiano, metadeLargEl + px, metadeAltEl + py);
					g2d.drawImage(imgB.getImage(), af, null);

					// Quadrado Verde
					metadeLargEl = imgA.getIconWidth() / 2;
					metadeAltEl = imgA.getIconHeight() / 2;
					px = TAMANHO_TELA / 2 - metadeLargEl;
					py = TAMANHO_TELA / 2 - metadeAltEl;

					af.setToIdentity();
					af.rotate(anguloEmRadiano, metadeLargEl + px, metadeAltEl + py);
					af.translate(px, py);
					g2d.drawImage(imgA.getImage(), af, null);

					// Quadrado Vermelho
					metadeLargEl = imgB.getIconWidth() / 2;
					metadeAltEl = imgB.getIconHeight() / 2;
					px = TAMANHO_TELA - imgB.getIconWidth();
					py = TAMANHO_TELA - imgB.getIconHeight();

					af.setToIdentity();
					af.rotate(anguloEmRadiano, metadeLargEl + px, metadeAltEl + py);
					af.translate(px, py);
					g2d.drawImage(imgB.getImage(), af, null);

				} else if (modo == Modo.ELEMENTO_CENTRO) {
					int metadeLargEl = imgA.getIconWidth() / 2;
					int metadeAltEl = imgB.getIconHeight() / 2;

					g2d.translate(TAMANHO_TELA / 2, TAMANHO_TELA / 2);
					g2d.rotate(anguloEmRadiano);
					g2d.translate(-metadeLargEl, -metadeAltEl);
					g2d.drawImage(imgA.getImage(), 0, 0, null);

				} else if (modo == Modo.ELEMENTO_CENTRO_ESCALA) {
					int metadeLargEl = imgA.getIconWidth() / 2;
					int metadeAltEl = imgB.getIconHeight() / 2;

					// g2d.scale(escala, escala); // Chamada A

					g2d.translate(TAMANHO_TELA / 2, TAMANHO_TELA / 2);
					g2d.rotate(anguloEmRadiano);

					g2d.scale(escala, escala); // Chamada B

					g2d.translate(-metadeLargEl, -metadeAltEl);

					// g2d.scale(escala, escala); // Chamada C

					g2d.drawImage(imgA.getImage(), 0, 0, null);

				} else {
					int px = 0;
					int py = 0;

					// Quadrado Amarelo
					g2d.drawImage(imgB.getImage(), px, py, null);

					// Quadrado Verde
					px = TAMANHO_TELA / 2 - imgA.getIconWidth() / 2;
					py = TAMANHO_TELA / 2 - imgA.getIconHeight() / 2;
					
					g2d.drawImage(imgA.getImage(), px, py, null);

					// Quadrado Vermelho
					px = TAMANHO_TELA - imgB.getIconWidth();
					py = TAMANHO_TELA - imgB.getIconHeight();

					g2d.drawImage(imgB.getImage(), px, py, null);
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
		long tempoInicio = System.currentTimeMillis();

		while (true) {
			if (System.currentTimeMillis() - tempoInicio >= FPS) {

				graus += inc;

				if (graus > 360)
					graus = 0;
				else if (graus < 0)
					graus = 360;

				escala += incEscala;

				if (escala > 9f || escala < 0.1f)
					incEscala = -incEscala;

				tela.repaint();
				tempoInicio = System.currentTimeMillis();
			}
		}
	}

	public static void main(String[] args) {
		RotacaoTeste jogo = new RotacaoTeste();
		jogo.iniciar();
	}

}
