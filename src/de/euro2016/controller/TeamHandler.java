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

	private static final long serialVersionUID = -3907213515737033925L;

	@PersistenceContext
	private EntityManager em;

	// @EJB
	// private ImageService service;

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
			this.em.persist(new Team("Albanien", "Albanien.gif")); // 1
			this.em.persist(new Team("Belgien", "Belgien.gif")); // 2
			this.em.persist(new Team("Deutschland", "Deutschland.gif")); // 3
			this.em.persist(new Team("England", "England.gif")); // 4
			this.em.persist(new Team("Frankreich", "Frankreich.gif")); // 5
			this.em.persist(new Team("Irland", "Irland.gif")); // 6
			this.em.persist(new Team("Island", "Island.gif")); // 7
			this.em.persist(new Team("Italien", "Italien.gif")); // 8
			this.em.persist(new Team("Kroatien", "Kroatien.gif")); // 9
			this.em.persist(new Team("Nordirland", "Nordirland.gif")); // 10
			this.em.persist(new Team("Österreich", "Oesterreich.gif")); // 11
			this.em.persist(new Team("Polen", "Polen.gif")); // 12
			this.em.persist(new Team("Portugal", "Portugal.gif")); // 13
			this.em.persist(new Team("Rumänien", "Rumaenien.gif")); // 14
			this.em.persist(new Team("Russland", "Russland.gif")); // 15
			this.em.persist(new Team("Schweden", "Schweden.gif")); // 16
			this.em.persist(new Team("Schweiz", "Schweiz.gif")); // 17
			this.em.persist(new Team("Slowakei", "Slowakei.gif")); // 18
			this.em.persist(new Team("Spanien", "Spanien.gif")); // 19
			this.em.persist(new Team("Tschechische Republik", "Tschechien.gif")); // 20
			this.em.persist(new Team("Türkei", "Tuerkei.gif")); // 21
			this.em.persist(new Team("Ukraine", "Ukraine.gif")); // 22
			this.em.persist(new Team("Ungarn", "Ungarn.gif")); // 23
			this.em.persist(new Team("Wales", "Wales.gif")); // 24
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
