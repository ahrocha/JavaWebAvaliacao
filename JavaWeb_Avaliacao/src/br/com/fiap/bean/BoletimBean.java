package br.com.fiap.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Aluno;
import br.com.fiap.entity.Curso;
import br.com.fiap.entity.Disciplina;
import br.com.fiap.entity.Nota;
import br.com.fiap.util.SessionManager;

@ManagedBean
@RequestScoped
public class BoletimBean {

	private Curso curso;
	private String media;
	private Aluno aluno;
	private List<Disciplina> disciplinas;
	
	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public BoletimBean(){
		
		aluno = (Aluno) SessionManager.getObject("session_usuario");
		
		if(aluno != null){
			this.curso = ((Aluno) SessionManager.getObject("session_usuario")).getCurso();
			
			disciplinas = new ArrayList<Disciplina>();
			
			System.out.println("quantidade de disciplinas no curso: "+curso.getDisciplinas().size());
			
			for(Disciplina d : curso.getDisciplinas()){
				getNotas(d);
				disciplinas.add(d);
			}
		}
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public List<Disciplina> getDisciplinas(){
		
		
		return disciplinas;
	}
	
	private void getNotas(Disciplina dis){
		GenericDao<Nota> dao = new GenericDao<Nota>(Nota.class);
		
		List<Nota> notas = dao.buscarNotasPorAlunoDisciplina(dis.getId(), this.aluno.getId());
		
		dis.setNota20("-");
		dis.setNota20_2("-");
		dis.setNota40("-");
		dis.setMedia("-");
		
		System.out.println("quantidade de notas retornadas na pesquisa do banco: "+notas.size());
		
		if(notas != null){
			
			int media = 0, nota20 = 0;
			
			for(Nota n : notas){
				if(n.getPeso() == 40){
					
					dis.setNota40(String.valueOf(n.getNota()));
					
				}else if(nota20 == 0){
					
					dis.setNota20(String.valueOf(n.getNota()));
					nota20++;
					
				}else{
					dis.setNota20_2(String.valueOf(n.getNota()));
				}
				
				media += n.getNota();
			}
			
			if(notas.size() ==3 ){
				media = media / 3;
				dis.setMedia(String.valueOf(media));
			}
		}
	}
}
