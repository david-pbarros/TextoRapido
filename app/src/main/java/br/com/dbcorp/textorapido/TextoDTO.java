package br.com.dbcorp.textorapido;

import java.io.Serializable;

public class TextoDTO implements Serializable {
	private static final long serialVersionUID = -4684583493072486572L;
	
	private int id;
	private LivrosBiblicos livro;
	private long capitulo;
	private String versiculos;
	private String texto;
	private String abreviacao;
	
	public int getId() {
		return id;
	}
	public TextoDTO setId(int id) {
		this.id = id;
		return this;
	}
	
	public LivrosBiblicos getLivro() {
		return livro;
	}
	public TextoDTO setLivro(LivrosBiblicos livro) {
		this.livro = livro;
		return this;
	}
	
	public long getCapitulo() {
		return capitulo;
	}
	public TextoDTO setCapitulo(long capitulo) {
		this.capitulo = capitulo;
		return this;
	}
	
	public String getVersiculos() {
		return versiculos;
	}
	public TextoDTO setVersiculos(String versiculos) {
		this.versiculos = versiculos;
		return this;
	}
	
	public String getTexto() {
		return texto;
	}
	public TextoDTO setTexto(String texto) {
		this.texto = texto;
		return this;
	}
	
	public String getAbreviacao() {
		return abreviacao;
	}
	public TextoDTO setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
		return this;
	}
}
