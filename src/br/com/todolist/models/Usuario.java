package br.com.todolist.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.auth0.jwt.internal.com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 35)
	private String login;
	
	@JsonIgnore
	private String senha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@XmlTransient
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		Md5PasswordEncoder senhaCriptografada = new Md5PasswordEncoder();
		String md5 = senhaCriptografada.encodePassword(senha, null);
		this.senha = md5;
	}
	
}
