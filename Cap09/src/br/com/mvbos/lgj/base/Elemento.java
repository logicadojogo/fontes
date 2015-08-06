package br.com.mvbos.lgj.base;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Elemento {

	public static AffineTransform afAnterior;
	protected static final AffineTransform af = new AffineTransform();

	private float px;
	private float py;
	private int largura;
	private int altura;

	private int vel;
	private boolean ativo;
	private Color cor;

	private ImageIcon imagem;

	public Elemento() {
	}

	public Elemento(int px, int py, int largura, int altura) {
		this.px = px;
		this.py = py;
		this.largura = largura;
		this.altura = altura;
	}

	public void atualiza() {
	}

	public void desenha(Graphics2D g) {
		if (imagem == null) {
			g.setColor(cor);
			g.fillRect((int) px, (int) py, largura, altura);
		} else {
			g.drawImage(imagem.getImage(), (int) px, (int) py, null);
		}
	}

	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getPx() {
		return (int) px;
	}

	public void setPx(float px) {
		this.px = px;
	}

	public int getPy() {
		return (int) py;
	}

	public void setPy(float py) {
		this.py = py;
	}

	public float getMovPx() {
		return px;
	}

	public float getMovPy() {
		return py;
	}

	public int getVel() {
		return vel;
	}

	public void setVel(int vel) {
		this.vel = vel;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public ImageIcon getImagem() {
		return imagem;
	}

	public void setImagem(ImageIcon imagem) {
		this.imagem = imagem;
	}

	public void incPx(int x) {
		px = px + x;
	}

	public void incPy(int y) {
		py = py + y;
	}

	@Override
	public String toString() {
		return "Elemento [px=" + px + ", py=" + py + "]";
	}

}
