/*
 *  (c) Bernd Müller, www.jsfpraxis.de
 */

package de.jsfpraxis;

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

/**
 * Einfache Managed-Bean zur Verwaltung von Comedians
 */
@ManagedBean
@SessionScoped
public class ComedianHandler implements Serializable {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<Land> laender;
	private Land aktuellerComedian = new Land();

	public ComedianHandler() {
		Logger.getAnonymousLogger().log(Level.INFO, "Konstruktor ComedianHandler() aufgerufen");
	}

	public String speichern() {
		Logger.getAnonymousLogger().log(Level.INFO, "speichern() [1] mit " + this.aktuellerComedian + "' aufgerufen");
		try {
			this.utx.begin();
			this.aktuellerComedian = this.em.merge(this.aktuellerComedian);
			Logger.getAnonymousLogger().log(Level.INFO,
					"speichern() [2] mit " + this.aktuellerComedian + "' aufgerufen");
			this.em.persist(this.aktuellerComedian);
			this.laender.setWrappedData(this.em.createNamedQuery("SelectComedians").getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'speichern()' nicht geklappt", e.getMessage());
		}
		return "/anzeige-laender.xhtml";
	}

	public String aendern() {
		this.aktuellerComedian = this.laender.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "aendern() mit " + this.aktuellerComedian);
		return "/land.xhtml";
	}

	public String neuanlage() {
		this.aktuellerComedian = new Land();
		Logger.getAnonymousLogger().log(Level.INFO, "neuanlage()");
		return "/land.xhtml";
	}

	public String loeschen() {
		this.aktuellerComedian = this.laender.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "loeschen() mit " + this.aktuellerComedian);
		try {
			this.utx.begin();
			this.aktuellerComedian = this.em.merge(this.aktuellerComedian);
			this.em.remove(this.aktuellerComedian);
			this.laender.setWrappedData(this.em.createNamedQuery("SelectComedians").getResultList());
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
			this.em.persist(new Land("Mario"));
			this.em.persist(new Land("Atze"));
			this.em.persist(new Land("Dieter"));
			this.em.persist(new Land("Anke"));
			this.em.persist(new Land("Kaya"));
			this.laender = new ListDataModel<Land>();
			this.laender.setWrappedData(this.em.createNamedQuery("SelectComedians").getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'init()' nicht geklappt: " + e.getMessage());
		}
	}

	public DataModel<Land> getComedians() {
		return this.laender;
	}

	public Land getAktuellerComedian() {
		return this.aktuellerComedian;
	}

	public void setAktuellerComedian(final Land aktuellerComedian) {
		this.aktuellerComedian = aktuellerComedian;
	}

}
