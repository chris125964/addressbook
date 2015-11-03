package de.jsfpraxis;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQuery(name = "SelectGruppen", query = "Select c From Gruppe c order by c.kennzeichen")
@Entity
public class Gruppe implements Serializable {

	private static final long serialVersionUID = -5529994540448820088L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;
	private Character kennzeichen;
	@OneToMany(fetch = FetchType.EAGER)
	private List<Land> teams;

	public Gruppe() {
		super();
	}

	public Gruppe(final Character c) {
		this.kennzeichen = c;
	}

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

	public void setTeams(final List<Land> teams) {
		this.teams = teams;
	}

	public List<Land> getTeams() {
		return this.teams;
	}

	@Override
	public String toString() {
		return "Gruppe [kennzeichen=" + this.kennzeichen + "]";
	}

}
