package br.com.fiap.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Login;
import br.com.fiap.util.SessionManager;

@ManagedBean
@RequestScoped
public class CadastrarAdminBean {

	private Login login;
	private String mensagem;
	private String buttonText;
	private String buttonLink;
	
	
	
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public CadastrarAdminBean(){
		login = new Login();
	}
	
	public String cadastrarAdmin(){
		
		GenericDao<Login> dao = new GenericDao<Login>(Login.class);
		
		try {
			
			this.mensagem = "Admnistrador cadastrado com sucesso";
			this.buttonText = "Cadastra outro";
			this.buttonLink = "cadAdmin.faces";
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			
			dao.cadastrar(login);
			return "sucesso";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.mensagem = "Erro ao cadastrar o usuario administrador";
			this.buttonText = "Tentar novamente";
			this.buttonLink = "cadAdmin.faces";
			SessionManager.addSucessoErro(this.mensagem, this.buttonText, this.buttonLink);
			return "erro";
		}
		
	}
}
