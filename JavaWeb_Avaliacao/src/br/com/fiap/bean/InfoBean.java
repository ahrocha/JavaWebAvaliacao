package br.com.fiap.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class InfoBean {

	private String mensagem;
	private String buttonText;
	private String buttonLink;
	
	public InfoBean(){
		super();
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
}
