package de.euro2016.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = "SelectAnwender", query = "Select c From Anwender c")
@Entity
public class Anwender implements Serializable {

	private static final long serialVersionUID = -516421438352337764L;
	private Integer id;
	private String account;
	private String passwort;

	public Anwender() {
	}

	public Anwender(final String account) {
		this.account = account;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(final String account) {
		this.account = account;
	}

	public String getPasswort() {
		return this.passwort;
	}

	public void setPasswort(final String passwort) {
		this.passwort = passwort;
	}

	@Override
	public String toString() {
		return "Anwender(" + this.id + "): " + this.account;
	}

}
