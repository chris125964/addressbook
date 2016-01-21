package de.euro2016.controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import de.euro2016.model.Team;

/**
 * Einfache Managed-Bean zur Verwaltung von Laendern
 */
@ManagedBean
@SessionScoped
public class TeamHandler implements Serializable {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<Team> teams;
	private Team currentTeam = new Team();
	private Integer gruppenID;

	public TeamHandler() {
		Logger.getAnonymousLogger().log(Level.INFO, "Konstruktor TeamHandler() aufgerufen");
	}

	public String speichern() {
		Logger.getAnonymousLogger().log(Level.INFO, "speichern() [1] mit " + this.currentTeam + "' aufgerufen");
		try {
			this.utx.begin();
			this.currentTeam = this.em.merge(this.currentTeam);
			// Group gefundeneGruppe = this.em.find(Group.class,
			// this.getGruppenID());
			// this.currentTeam.setGroup(gefundeneGruppe);
			Logger.getAnonymousLogger().log(Level.INFO, "speichern() [2] mit " + this.currentTeam + "' aufgerufen");
			this.em.persist(this.currentTeam);
			this.teams.setWrappedData(this.em.createNamedQuery(Team.select).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'speichern()' nicht geklappt: " + e.getMessage());
		}
		return "/anzeige-laender.xhtml";
	}

	public String aendern() {
		this.currentTeam = this.teams.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "aendern() mit " + this.currentTeam);
		return "/land.xhtml";
	}

	public String neuanlage() {
		this.currentTeam = new Team();
		Logger.getAnonymousLogger().log(Level.INFO, "neuanlage()");
		return "/land.xhtml";
	}

	public String loeschen() {
		this.currentTeam = this.teams.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "loeschen() mit " + this.currentTeam);
		try {
			this.utx.begin();
			this.currentTeam = this.em.merge(this.currentTeam);
			this.em.remove(this.currentTeam);
			this.teams.setWrappedData(this.em.createNamedQuery(Team.select).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'loeschen()' nicht geklappt", e.getMessage());
		}
		return null;
	}

	/*
	 * Bekannter Fehler: Methode wird pro Session aufgerufen, d.h. Daten werden
	 * für JEDE Session in die DB geschrieben. Mögliche Abhilfe: In MB mit
	 * Application-Scope auslagern.
	 */
	@PostConstruct
	public void init() {
		Logger.getAnonymousLogger().log(Level.INFO, "'TeamHandler.init()' aufgerufen");
		try {
			this.utx.begin();
			this.em.persist(new Team("England"));
			this.teams = new ListDataModel<Team>();
			this.teams.setWrappedData(this.em.createNamedQuery(Team.select).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'init()' nicht geklappt: " + e.getMessage());
		}
	}

	public DataModel<Team> getLaender() {
		return this.teams;
	}

	public Team getAktuellesLand() {
		return this.currentTeam;
	}

	public void setAktuellesLand(final Team currentTeam) {
		this.currentTeam = currentTeam;
	}

	public Integer getGruppenID() {
		return this.gruppenID;
	}

	public void setGruppenID(final Integer gruppenID) {
		this.gruppenID = gruppenID;
	}

}
