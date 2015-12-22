package de.euro2016.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = Match.findAll, query = "Select c From Match c")
@Entity
public class Match implements Serializable {

	private static final long serialVersionUID = 3409565940093541529L;
	public static final String findAll = "Match.findAll";

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;

	private Gruppe group;
	private int nrMatch;
	private Team team1;
	private Team team2;
	private int tore1;
	private int tore2;

	public Match(final Gruppe group, final Team team1, final Team team2) {
		super();
		this.group = group;
		this.team1 = team1;
		this.team2 = team2;
		this.tore1 = 0;
		this.tore2 = 0;
	}

	public Match() {
		super();
	}

	public Gruppe getGroup() {
		return this.group;
	}

	public void setGroup(final Gruppe group) {
		this.group = group;
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

	public int getTore1() {
		return this.tore1;
	}

	public void setTore1(final int tore1) {
		this.tore1 = tore1;
	}

	public int getTore2() {
		return this.tore2;
	}

	public void setTore2(final int tore2) {
		this.tore2 = tore2;
	}
}
