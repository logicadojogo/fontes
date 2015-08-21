package br.com.mvbos.lgj.base;

import java.awt.Graphics2D;

public class Menu extends Texto {

	private short idx;
	private String rotulo;
	private String[] opcoes;
	private boolean selecionado;

	public Menu(String rotulo) {
		super();

		this.rotulo = rotulo;
		setLargura(120);
		setAltura(20);
	}

	public void addOpcoes(String... opcao) {
		opcoes = opcao;
	}

	@Override
	public void desenha(Graphics2D g) {
		if (opcoes == null)
			return;

		g.setColor(getCor());
		super.desenha(g, getRotulo() + ": <" + opcoes[idx] + ">", getPx(), getPy() + getAltura());
		//super.desenha(g, String.format("%s: <%s>", getRotulo(), opcoes[idx]), getPx(), getPy() + getAltura());

		if (selecionado)
			g.drawLine(getPx(), getPy() + getAltura() + 5, getPx() + getLargura(), getPy() + getAltura() + 5);

	}

	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;

	}

	public int getOpcaoId() {
		return idx;
	}

	public String getOpcaoTexto() {
		return opcoes[idx];
	}

	public void trocaOpcao(boolean esquerda) {
		if (!isSelecionado() || !isAtivo())
			return;

		idx += esquerda ? -1 : 1;

		if (idx < 0)
			idx = (short) (opcoes.length - 1);
		else if (idx == opcoes.length)
			idx = 0;

	}

}
