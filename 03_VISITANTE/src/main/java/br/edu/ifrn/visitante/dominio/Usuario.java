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
public class Usuario {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Arquivo foto;
	
	@Column(nullable = false)
	@NotBlank(message = "O campo nome é obrigatório")
	@Size(min = 2, message = "Um nome deve ter pelo menos dois caracteres.")
	private String nome;
	
	@Column(nullable = false)
	@NotBlank(message = "O campo email é obrigatório")
	private String email;
	
	@Column(nullable = false)
	@NotBlank(message = "O campo sexo é obrigatório")
	private String sexo;
	
	public Arquivo getFoto() {
		return foto;
	}
	public void setFoto(Arquivo foto) {
		this.foto = foto;
	}
	@Column(nullable = false)
	@NotBlank(message = "O campo cpf é obrigatório")
	@Size(max = 11, min = 11, message = "Um cpf deve ter 11 caracteres.")
	private String cpf;
	
	
	
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
		Usuario other = (Usuario) obj;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
