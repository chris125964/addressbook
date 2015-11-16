package de.euro2016.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = Gruppe.findAll, query = "Select c From Gruppe c order by c.kennzeichen")
@Entity
public class Gruppe implements Serializable {

	private static final long serialVersionUID = -5529994540448820088L;

	public static final String findAll = "Group.findAll";

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;
	private Character kennzeichen;
	private Team team1;
	private Team team2;
	private Team team3;
	private Team team4;

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
		return "Group [kennzeichen=" + this.kennzeichen + "]";
	}

	public Team getTeam1() {
		return this.team1;
	}

	public void setTeam1(final Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return this.team2;
	}

	public void setTeam2(final Team team2) {
		this.team2 = team2;
	}

	public Team getTeam3() {
		return this.team3;
	}

	public void setTeam3(final Team team3) {
		this.team3 = team3;
	}

	public Team getTeam4() {
		return this.team4;
	}

	public void setTeam4(final Team team4) {
		this.team4 = team4;
	}

}
