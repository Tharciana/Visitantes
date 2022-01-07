package br.edu.ifrn.visitante.dominio;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Funcionario {
	
	public static final String ADMIN ="ADMIN";
	public static final String USUARIO_COMUM ="FUNC";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	@NotBlank(message = "O campo usuario é obrigatório")
	@Size(min = 4, message = "Um usuario deve ter pelo menos quatro caracteres.")
	private String usuario;
	
	@Column(nullable = false)
	@NotBlank(message = "O campo email é obrigatório")
	private String email;

	@Column(nullable = false)
	@NotBlank(message = "O campo senha é obrigatório")
	@Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
	private String senha;
	
	@Column(nullable = false)
	@NotBlank(message = "O campo turno é obrigatório")
	private String turno;
	
	@Column(nullable = false)
	private String perfil = USUARIO_COMUM;
	
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	
}
