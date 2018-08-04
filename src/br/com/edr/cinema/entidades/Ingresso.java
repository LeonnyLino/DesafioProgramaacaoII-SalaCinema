package br.com.edr.cinema.entidades;

public class Ingresso {

	private long id;
	private String fileira;
	private int coluna;
	private boolean estudante;
	private long numeroCarteira;
	private boolean flagVendida;

	public Ingresso() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileira() {
		return fileira;
	}

	public void setFileira(String fileira) {
		this.fileira = fileira;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public boolean isEstudante() {
		return estudante;
	}

	public void setEstudante(boolean estudante) {
		this.estudante = estudante;
	}

	public long getNumeroCarteira() {
		return numeroCarteira;
	}

	public void setNumeroCarteira(long numeroCarteira) {
		this.numeroCarteira = numeroCarteira;
	}

	public boolean isFlagVendida() {
		return flagVendida;
	}

	public void setFlagVendida(boolean flagVendida) {
		this.flagVendida = flagVendida;
	}
	
	@Override
	public String toString() {
		return this.fileira + " - " + this.coluna;
	}

}
