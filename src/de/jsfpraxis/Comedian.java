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
	private String land;
	private String nachname;
	private Date geburtstag;

	public Comedian() {
	}

	public Comedian(final String land, final String nachname, final Date geburtstag) {
		this.land = land;
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
		return this.land;
	}

	public void setVorname(final String land) {
		this.land = land;
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
		return "Kunde(" + this.id + "): " + this.land + " " + this.nachname;
	}
}
