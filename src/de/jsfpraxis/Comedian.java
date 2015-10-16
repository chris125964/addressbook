/*
 *  (c) Bernd Müller, www.jsfpraxis.de
 */

package de.jsfpraxis;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQuery(name = "SelectComedians", query = "Select c From Comedian c")
@Entity
public class Comedian implements Serializable {

	private static final long serialVersionUID = -7569584658487581798L;
	private Integer id;
	private String vorname;
	private String nachname;
	private Date geburtstag;

	public Comedian() {
	}

	public Comedian(final String vorname, final String nachname, final Date geburtstag) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtstag = geburtstag;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(final String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return this.nachname;
	}

	public void setNachname(final String nachname) {
		this.nachname = nachname;
	}

	@Temporal(TemporalType.DATE)
	public Date getGeburtstag() {
		return this.geburtstag;
	}

	public void setGeburtstag(final Date geburtstag) {
		this.geburtstag = geburtstag;
	}

	@Override
	public String toString() {
		return "Kunde(" + this.id + "): " + this.vorname + " " + this.nachname;
	}
}
