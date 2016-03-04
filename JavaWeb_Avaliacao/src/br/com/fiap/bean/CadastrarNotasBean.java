package br.com.fiap.bean;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Aluno;
import br.com.fiap.entity.Disciplina;
import br.com.fiap.entity.Nota;
import br.com.fiap.util.SessionManager;

@ManagedBean
@ViewScoped
public class CadastrarNotasBean {

	private Disciplina disciplina;
	private Nota nota;
	private Aluno aluno;
	private String[] peso;
	private String mensagem;
	private String buttonText;
	private String buttonLink;
	
	public String[] getPeso() {
		return peso;
	}

	public void setPeso(String[] peso) {
		this.peso = peso;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}	
	
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Nota getNota() {
		return nota;
	}
	
	public void setNota(Nota nota) {
		this.nota = nota;
	}
	
	public CadastrarNotasBean(){
		
		GenericDao<Aluno> dao = new GenericDao<Aluno>(Aluno.class);
		
		int idAluno = Integer.parseInt(SessionManager.get("idAluno"));
		
		this.aluno = dao.buscar(idAluno);
		
		SessionManager.setAttribute("aluno", aluno);
		
		this.disciplina = (Disciplina) SessionManager.getObject("disciplina");
		
		GenericDao<Nota> daoNota = new GenericDao<Nota>(Nota.class);
		
		List<Nota> notas = daoNota.buscarNotasPorAlunoDisciplina(disciplina.getId(), idAluno);
		
		System.out.println(notas.size());
		
		if(notas != null){
			if(notas.size() >= 3){				
				FacesContext contex = FacesContext.getCurrentInstance();
				try {
					contex.getExternalContext().redirect("/JavaWeb_Avaliacao/professor/notasExcedidas.faces");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		boolean tem = false;
		for(Nota n : notas){
			if(n.getPeso() == 40){
				tem = true;
			}
		}
		if(tem == true){
			peso = new String[1];
			peso[0] = "20";
		}else{
			peso = new String[2];
			peso[0] = "20";
			peso[1] = "40";
		}
		
		this.nota = new Nota();
	}
	
	
	public String cadastrarNota(){
		
		System.out.println(this.aluno.getNome());
		System.out.println(this.disciplina.getNome());
		
		this.nota.setAluno(this.aluno);
		this.nota.setDisciplina(this.disciplina);
		
		GenericDao<Nota> dao = new GenericDao<Nota>(Nota.class);
		
		try {
			dao.cadastrar(this.nota);
			this.mensagem = "Nota cadastrada com sucesso.";
			this.buttonText = "Cadastrar outra nota";
			this.buttonLink = "ListarDiciplinasAluno.faces";
			
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "sucesso";
		} catch (Exception e) {
			this.mensagem = "Erro ao tentar cadastrar a nota.";
			this.buttonText = "Tentar cadastrar outra nota";
			this.buttonLink = "ListarDiciplinasAluno.faces";
			
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			e.printStackTrace();
			return "erro";
		}
		
	}
	
}
