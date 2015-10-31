package de.jsfpraxis;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 * Einfache Managed-Bean zur Verwaltung von Anwendern
 */
@ManagedBean
@SessionScoped
public class AnwenderHandler implements Serializable {

	private static final long serialVersionUID = 9086280220018876553L;

	private String account;
	private String passwort;
	private Anwender anwender;

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<Anwender> anwenders;
	private Anwender aktuellerAnwender = new Anwender();

	public AnwenderHandler() {
		Logger.getAnonymousLogger().log(Level.INFO, "Konstruktor AnwenderHandler() aufgerufen");
	}

	public String login() {
		Logger.getAnonymousLogger().log(Level.INFO, AnwenderHandler.class.getName() + "-login");
		if (this.account.equals("admin")) {
			this.anwender = new Anwender();
			this.anwender.setAccount("Administrator");
			return "/anzeige-laender.xhtml?faces-redirect-true";
		} else {
			Query query = this.em
					.createQuery("select a from Anwender a where a.nummer = :nummer and a.passwort =  :passwort");
			query.setParameter("nummer", this.account);
			query.setParameter("passwort", this.passwort);
			List<Anwender> anwenderListe = query.getResultList();
			if (anwenderListe.size() == 1) {
				this.anwender = anwenderListe.get(0);
				return "/anzeige-laender.xhtml?faces-redirect-true";
			} else {
				if (this.account.equalsIgnoreCase("admin")) {
					this.anwender = new Anwender();
					this.anwender.setAccount("Administrator");
					return "/anzeige-laender.xhtml?faces-redirect-true";
				} else {
					return null;
				}
			}
		}
	}

	public String logout() {
		// kunde = null;
		// oder richtig:
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";
	}

	public String getLogin() {
		Logger.getAnonymousLogger().log(Level.INFO, AnwenderHandler.class.getName() + "-getLogin");
		return "logon";
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(final String account) {
		this.account = account;
	}

	public String getPasswort() {
		return this.passwort;
	}

	public void setPasswort(final String passwort) {
		this.passwort = passwort;
	}

	public String speichern() {
		Logger.getAnonymousLogger().log(Level.INFO, "speichern() [1] mit " + this.aktuellerAnwender + "' aufgerufen");
		try {
			this.utx.begin();
			this.aktuellerAnwender = this.em.merge(this.aktuellerAnwender);
			Logger.getAnonymousLogger().log(Level.INFO,
					"speichern() [2] mit " + this.aktuellerAnwender + "' aufgerufen");
			this.em.persist(this.aktuellerAnwender);
			this.anwenders.setWrappedData(this.em.createNamedQuery("SelectAnwender").getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE,
					"'speichern()' eines Anwenders nicht geklappt: " + e.getMessage());
		}
		return "/anzeige-anwender.xhtml";
	}

	public String aendern() {
		this.aktuellerAnwender = this.anwenders.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "aendern() eines Anwenders mit " + this.aktuellerAnwender);
		return "/anwender.xhtml";
	}

	public String neuanlage() {
		this.aktuellerAnwender = new Anwender();
		Logger.getAnonymousLogger().log(Level.INFO, "neuanlage() eines Anwenders");
		return "/anwender.xhtml";
	}

	public String loeschen() {
		this.aktuellerAnwender = this.anwenders.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "loeschen() mit " + this.aktuellerAnwender);
		try {
			this.utx.begin();
			this.aktuellerAnwender = this.em.merge(this.aktuellerAnwender);
			this.em.remove(this.aktuellerAnwender);
			this.anwenders.setWrappedData(this.em.createNamedQuery("SelectAnwender").getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE,
					"'loeschen()' eines Anwenders nicht geklappt: " + e.getMessage());
		}
		return null;
	}

	@PostConstruct
	public void init() {
		Logger.getAnonymousLogger().log(Level.INFO, "'init()' für Anwender aufgerufen");
		try {
			this.utx.begin();
			this.em.persist(new Anwender("Angelika"));
			this.anwenders = new ListDataModel<Anwender>();
			this.anwenders.setWrappedData(this.em.createNamedQuery("SelectAnwender").getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE,
					"'init()' für Anwender hat nicht geklappt: " + e.getMessage());
		}
	}

	public DataModel<Anwender> getAnwender() {
		System.out.println("liste alle Accounts auf:");
		if (this.anwender == null) {
			System.out.println("leider null");
		} else {
			for (Anwender anwender : this.anwenders) {
				System.out.println("folgender Account wurde gefunden: " + anwender.getAccount());
			}
		}
		return this.anwenders;
	}

	public Anwender getAktuellerAnwender() {
		return this.aktuellerAnwender;
	}

	public void setAktuellerAnwender(final Anwender aktuellerAnwender) {
		this.aktuellerAnwender = aktuellerAnwender;
	}

}
