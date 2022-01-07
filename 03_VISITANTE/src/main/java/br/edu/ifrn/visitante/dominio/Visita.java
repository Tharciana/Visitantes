package br.edu.ifrn.visitante.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Visita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String data;
	
	@Column(nullable = false)
	private String hora;
	
	@ManyToOne(optional = false)
	private Usuario visitante;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Usuario getVisitante() {
		return visitante;
	}

	public void setVisitante(Usuario visitante) {
		this.visitante = visitante;
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
		Visita other = (Visita) obj;
		if (id != other.id)
			return false;
		return true;

}
}