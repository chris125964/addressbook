package de.euro2016.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = Team.select, query = "Select c From Team  c")
@Entity
public class Team implements Serializable {

	private static final long serialVersionUID = -7569584658487581798L;

	public static final String select = "SelectTeams";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;

	public Team() {
	}

	public Team(final String land) {
		this.name = land;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String land) {
		this.name = land;
	}

	@Override
	public String toString() {
		return "Land(" + this.id + "): " + this.name;
	}
}
