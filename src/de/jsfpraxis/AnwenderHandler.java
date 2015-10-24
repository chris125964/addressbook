package de.jsfpraxis;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@ManagedBean
@SessionScoped
public class AnwenderHandler implements Serializable {

	private static final long serialVersionUID = 9086280220018876553L;

	private static Logger logger = Logger.getLogger(AnwenderHandler.class.getCanonicalName());

	private String account;
	private String passwort;
	private Anwender anwender;

	@PersistenceContext
	private EntityManager em;

	public AnwenderHandler() {
		logger.log(Level.INFO, AnwenderHandler.class.getName() + "-Instanz erzeugt");
	}

	public String login() {
		logger.log(Level.INFO, AnwenderHandler.class.getName() + "-login");
		if (this.account.equals("admin")) {
			this.anwender = new Anwender();
			this.anwender.setNummer("Administrator");
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
					this.anwender.setNummer("Administrator");
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
		logger.log(Level.INFO, AnwenderHandler.class.getName() + "-getLogin");
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

}
