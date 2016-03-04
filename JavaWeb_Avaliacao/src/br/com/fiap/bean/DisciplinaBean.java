package br.com.fiap.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Curso;
import br.com.fiap.entity.Disciplina;
import br.com.fiap.entity.Escola;
import br.com.fiap.entity.Professor;
import br.com.fiap.util.SessionManager;

@ManagedBean
@ViewScoped
public class DisciplinaBean{

	private Disciplina disciplina;
	private int idCurso;
	private int idProfessor;
	private int idEscola;
	private String mensagem;
	private String buttonText;
	private String buttonLink;
	private Map<Integer, Professor> mapProferssor;
	private Map<Integer, Curso> mapCurso;
	private Curso curso;
	private Professor professor;
	
	public DisciplinaBean(){
		this.disciplina = new Disciplina();
	}
		
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public String getButtonLink() {
		return buttonLink;
	}

	public void setButtonLink(String buttonLink) {
		this.buttonLink = buttonLink;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public int getIdEscola() {
		return idEscola;
	}

	public void setIdEscola(int idEscola) {
		this.idEscola = idEscola;
	}

	public void setIdEscola(String idEscola) {
		this.idEscola = Integer.parseInt(idEscola);
	}
	
	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
	
	public int getIdProfessor() {
		return idProfessor;
	}

	public void setIdProfessor(int idProfessor) {
		this.idProfessor = idProfessor;
	}

	public String cadastrarDisciplina(){
		try {
			this.curso = mapCurso.get(idCurso);
			
			this.professor = mapProferssor.get(idProfessor);
			
			if(this.curso == null){
				this.mensagem = "Erro ao cadastrar a disciplina, nenhum curso foi selecionado.";
				this.buttonText = "Tentar novamente";
				this.buttonLink = "listarEscolas.faces";
				SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
				return "erro";
			}
			
			if(this.professor == null){
				this.mensagem = "Erro ao cadastrar a disciplina, nenhum professor foi selecionado.";
				this.buttonText = "Tentar novamente";
				this.buttonLink = "listarEscolas.faces";
				SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
				return "erro";
			}
						
			this.disciplina.setCurso(curso);
			
			this.disciplina.setProfessor(professor);
			
			GenericDao<Disciplina> dao = new GenericDao<Disciplina>(Disciplina.class);
			
			dao.adicionar(disciplina);
			
			this.mensagem = "Disciplina cadastrada no curso "+this.curso.getNome()+" com sucesso.";
			this.buttonText = "Cadastrar outra disciplina";
			this.buttonLink = "listarEscolas.faces";
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "sucesso";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.mensagem = "Erro ao cadastrar a disciplina.";
			this.buttonText = "Tentar novamente";
			this.buttonLink = "listarEscolas.faces";
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "erro";
		}
	}
	
	public List<Curso> getlistCursos(){
		GenericDao<Escola> dao = new GenericDao<Escola>(Escola.class);
		
		this.idEscola = Integer.parseInt(SessionManager.get("idEsc"));
		
		Escola escola = dao.listarCursosPorEscola(this.idEscola);
		
		List<Curso> cursos = new ArrayList<Curso>();
		
		this.mapCurso = new HashMap<Integer, Curso>();
		
		for(Curso c : escola.getCursos()){
			this.mapCurso.put(c.getId(), c);
			cursos.add(c);
		}
		
		return cursos;
	}
	
	
	public List<Professor> listProfessores(){	
	
		GenericDao<Escola> dao = new GenericDao<Escola>(Escola.class);
		
		this.idEscola = Integer.parseInt(SessionManager.get("idEsc"));
		
		Escola escola = dao.buscar(this.idEscola);
		
		List<Professor> professores =  new ArrayList<Professor>();
		
		this.mapProferssor = new HashMap<Integer, Professor>();
		
		for(Professor p : escola.getProfessores()){
			this.mapProferssor.put(p.getId(), p);
			professores.add(p);
		}
	
		return professores;
	}
	
	public Professor listProfessorDisciplinas(){
		
		Professor professor =  (Professor) SessionManager.getObject("session_usuario");
		
		return professor;
	}
}
