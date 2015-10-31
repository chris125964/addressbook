package de.jsfpraxis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = "SelectLaender", query = "Select c From Land  c")
@Entity
public class Land implements Serializable {

	private static final long serialVersionUID = -7569584658487581798L;
	private Integer id;
	private String land;

	public Land() {
	}

	public Land(final String land) {
		this.land = land;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getLand() {
		return this.land;
	}

	public void setLand(final String land) {
		this.land = land;
	}

	@Override
	public String toString() {
		return "Land(" + this.id + "): " + this.land;
	}
}
