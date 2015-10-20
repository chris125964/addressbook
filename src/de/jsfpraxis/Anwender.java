package de.jsfpraxis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Anwender implements Serializable {

	private static final long serialVersionUID = -516421438352337764L;
	private Integer id;
	private String nummer;
	private String passwort;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getNummer() {
		return this.nummer;
	}

	public void setNummer(final String nummer) {
		this.nummer = nummer;
	}

	public String getPasswort() {
		return this.passwort;
	}

	public void setPasswort(final String passwort) {
		this.passwort = passwort;
	}

}
