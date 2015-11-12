package de.jsfpraxis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = "SelectGruppen", query = "Select c From Gruppe c order by c.kennzeichen")
@Entity
public class Gruppe implements Serializable {

	private static final long serialVersionUID = -5529994540448820088L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;
	private Character kennzeichen;
	private Land team1;
	private Land team2;
	private Land team3;
	private Land team4;

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

	@Override
	public String toString() {
		return "Gruppe [kennzeichen=" + this.kennzeichen + "]";
	}

	public Land getTeam1() {
		return this.team1;
	}

	public void setTeam1(final Land team1) {
		this.team1 = team1;
	}

	public Land getTeam2() {
		return this.team2;
	}

	public void setTeam2(final Land team2) {
		this.team2 = team2;
	}

	public Land getTeam3() {
		return this.team3;
	}

	public void setTeam3(final Land team3) {
		this.team3 = team3;
	}

	public Land getTeam4() {
		return this.team4;
	}

	public void setTeam4(final Land team4) {
		this.team4 = team4;
	}

}
