package de.jsfpraxis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = "SelectGruppen", query = "Select c From Gruppe c")
@Entity
public class Gruppe implements Serializable {

	private static final long serialVersionUID = -5529994540448820088L;
	private Integer id;
	private Character kennzeichen;

	public Gruppe() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Character getKennzeichen() {
		return this.kennzeichen;
	}

	public void setKennzeichen(final Character kennzeichen) {
		this.kennzeichen = kennzeichen;
	}

	@Override
	public String toString() {
		return "Gruppe [kennzeichen=" + this.kennzeichen + "]";
	}

}
