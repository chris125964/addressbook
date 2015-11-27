package de.euro2016.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = User.findAll, query = "Select c From User c")
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = -516421438352337764L;

	public static final String findAll = "User.findAll";

	private Integer id;
	private String account;
	private String passwort;
	private int punkte;

	public User() {
	}

	public User(final String pAccount, final String pPasswort) {
		this.account = pAccount;
		this.passwort = pPasswort;
		this.punkte = 0;
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

	public int getPunkte() {
		return this.punkte;
	}

	public void setPunkte(final int punkte) {
		this.punkte = punkte;
	}

	@Override
	public String toString() {
		return "Anwender(" + this.id + "): " + this.account;
	}

}
