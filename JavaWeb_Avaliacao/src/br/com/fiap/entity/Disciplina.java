package br.com.fiap.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="disciplinas", catalog="dbescolas")
public class Disciplina implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "nome", length = 45)
	private String nome;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "disciplina")
	private Set<Nota> notas = new LinkedHashSet<Nota>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CURSO")
	private Curso curso;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PROFESSOR")
	private Professor professor;
	
	private String media;
	private String nota20;
	private String nota20_2;
	private String nota40;
	

	public String getNota20() {
		return nota20;
	}

	public void setNota20(String nota20) {
		this.nota20 = nota20;
	}

	public String getNota20_2() {
		return nota20_2;
	}

	public void setNota20_2(String nota20_2) {
		this.nota20_2 = nota20_2;
	}

	public String getNota40() {
		return nota40;
	}

	public void setNota40(String nota40) {
		this.nota40 = nota40;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Nota> getNotas() {
		return notas;
	}

	public void setNotas(Set<Nota> notas) {
		this.notas = notas;
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
