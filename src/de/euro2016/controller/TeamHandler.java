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
import de.jsfpraxis.Gruppe;

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

	private DataModel<Team> laender;
	private Team aktuellesLand = new Team();
	private Integer gruppenID;

	public TeamHandler() {
		Logger.getAnonymousLogger().log(Level.INFO, "Konstruktor LandHandler() aufgerufen");
	}

	public String speichern() {
		Logger.getAnonymousLogger().log(Level.INFO, "speichern() [1] mit " + this.aktuellesLand + "' aufgerufen");
		try {
			this.utx.begin();
			this.aktuellesLand = this.em.merge(this.aktuellesLand);
			Gruppe gefundeneGruppe = this.em.find(Gruppe.class, this.getGruppenID());
			// this.aktuellesLand.setGruppe();
			Logger.getAnonymousLogger().log(Level.INFO, "speichern() [2] mit " + this.aktuellesLand + "' aufgerufen");
			this.em.persist(this.aktuellesLand);
			this.laender.setWrappedData(this.em.createNamedQuery(Team.findAll).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'speichern()' nicht geklappt", e.getMessage());
		}
		return "/anzeige-laender.xhtml";
	}

	public String aendern() {
		this.aktuellesLand = this.laender.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "aendern() mit " + this.aktuellesLand);
		return "/land.xhtml";
	}

	public String neuanlage() {
		this.aktuellesLand = new Team();
		Logger.getAnonymousLogger().log(Level.INFO, "neuanlage()");
		return "/land.xhtml";
	}

	public String loeschen() {
		this.aktuellesLand = this.laender.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "loeschen() mit " + this.aktuellesLand);
		try {
			this.utx.begin();
			this.aktuellesLand = this.em.merge(this.aktuellesLand);
			this.em.remove(this.aktuellesLand);
			this.laender.setWrappedData(this.em.createNamedQuery(Team.findAll).getResultList());
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
		Logger.getAnonymousLogger().log(Level.INFO, "'init()' aufgerufen");
		try {
			this.utx.begin();
			this.em.persist(new Team("England"));
			this.laender = new ListDataModel<Team>();
			this.laender.setWrappedData(this.em.createNamedQuery(Team.findAll).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'init()' nicht geklappt: " + e.getMessage());
		}
	}

	public DataModel<Team> getLaender() {
		return this.laender;
	}

	public Team getAktuellesLand() {
		return this.aktuellesLand;
	}

	public void setAktuellesLand(final Team aktuellesLand) {
		this.aktuellesLand = aktuellesLand;
	}

	public Integer getGruppenID() {
		return this.gruppenID;
	}

	public void setGruppenID(final Integer gruppenID) {
		this.gruppenID = gruppenID;
	}

}
