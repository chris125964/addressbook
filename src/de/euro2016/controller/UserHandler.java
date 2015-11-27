package de.euro2016.controller;

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

import de.euro2016.model.User;

/**
 * Einfache Managed-Bean zur Verwaltung von Usern
 */
@ManagedBean
@SessionScoped
public class UserHandler implements Serializable {

	private static final long serialVersionUID = 9086280220018876553L;

	private String account;
	private String passwort;
	private User User;

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<User> users;
	private User currentUser = new User();

	public UserHandler() {
		Logger.getAnonymousLogger().log(Level.INFO, "Konstruktor UserHandler() aufgerufen");
	}

	public String login() {
		Logger.getAnonymousLogger().log(Level.INFO, UserHandler.class.getName() + "-login");
		if (this.account.equals("admin")) {
			this.User = new User();
			this.User.setAccount("Administrator");
			return "/anzeige-laender.xhtml?faces-redirect-true";
		} else {
			Query query = this.em
					.createQuery("select a from User a where a.nummer = :nummer and a.passwort =  :passwort");
			query.setParameter("nummer", this.account);
			query.setParameter("passwort", this.passwort);
			List<User> UserListe = query.getResultList();
			if (UserListe.size() == 1) {
				this.User = UserListe.get(0);
				return "/anzeige-laender.xhtml?faces-redirect-true";
			} else {
				if (this.account.equalsIgnoreCase("admin")) {
					this.User = new User();
					this.User.setAccount("Administrator");
					return "/anzeige-laender.xhtml?faces-redirect-true";
				} else {
					return null;
				}
			}
		}
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";
	}

	public String getLogin() {
		Logger.getAnonymousLogger().log(Level.INFO, UserHandler.class.getName() + "-getLogin");
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
		Logger.getAnonymousLogger().log(Level.INFO, "speichern() [1] mit " + this.currentUser + "' aufgerufen");
		try {
			this.utx.begin();
			this.currentUser = this.em.merge(this.currentUser);
			Logger.getAnonymousLogger().log(Level.INFO, "speichern() [2] mit " + this.currentUser + "' aufgerufen");
			this.em.persist(this.currentUser);
			this.users.setWrappedData(this.em.createNamedQuery("SelectUser").getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE,
					"'speichern()' eines Users nicht geklappt: " + e.getMessage());
		}
		return "/anzeige-User.xhtml";
	}

	public String aendern() {
		this.currentUser = this.users.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "aendern() eines Users mit " + this.currentUser);
		return "/User.xhtml";
	}

	public String neuanlage() {
		this.currentUser = new User();
		Logger.getAnonymousLogger().log(Level.INFO, "neuanlage() eines Users");
		return "/User.xhtml";
	}

	public String loeschen() {
		this.currentUser = this.users.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "loeschen() mit " + this.currentUser);
		try {
			this.utx.begin();
			this.currentUser = this.em.merge(this.currentUser);
			this.em.remove(this.currentUser);
			this.users.setWrappedData(this.em.createNamedQuery("SelectUser").getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'loeschen()' eines Users nicht geklappt: " + e.getMessage());
		}
		return null;
	}

	@PostConstruct
	public void init() {
		Logger.getAnonymousLogger().log(Level.INFO, "'UserHandler.init()' für User aufgerufen");
		try {
			this.utx.begin();
			this.em.persist(new User("Angelika", "Rott"));
			this.em.persist(new User("Christian", "Todd"));
			this.users = new ListDataModel<User>();
			this.users.setWrappedData(this.em.createNamedQuery("SelectUser").getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'init()' für User hat nicht geklappt: " + e.getMessage());
		}
	}

	public DataModel<User> getUser() {
		System.out.println("liste alle Accounts auf:");
		if (this.User == null) {
			System.out.println("leider null");
		} else {
			for (User User : this.users) {
				System.out.println("folgender Account wurde gefunden: " + User.getAccount());
			}
		}
		return this.users;
	}

	public User getAktuellerUser() {
		return this.currentUser;
	}

	public void setAktuellerUser(final User aktuellerUser) {
		this.currentUser = aktuellerUser;
	}

}
