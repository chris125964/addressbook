package de.jsfpraxis;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

@ManagedBean
@SessionScoped
public class GruppenHandler implements Serializable {

	private static final long serialVersionUID = -6544110274546711076L;

	private static Logger logger = Logger.getLogger(GruppenHandler.class.getCanonicalName());

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<Gruppe> gruppen;
	private Gruppe aktuelleGruppe = new Gruppe();

	public GruppenHandler() {
		logger.log(Level.INFO, GruppenHandler.class.getName() + "-Instanz erzeugt");
	}

	public String speichern() {
		Logger.getAnonymousLogger().log(Level.INFO, "speichern() [1] mit " + this.aktuelleGruppe + "' aufgerufen");
		try {
			this.utx.begin();
			this.aktuelleGruppe = this.em.merge(this.aktuelleGruppe);
			Logger.getAnonymousLogger().log(Level.INFO, "speichern() [2] mit " + this.aktuelleGruppe + "' aufgerufen");
			this.em.persist(this.aktuelleGruppe);
			this.gruppen.setWrappedData(this.em.createNamedQuery("SelectGruppen").getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'speichern()' nicht geklappt: " + e.getMessage());
		}
		return "/anzeige-gruppen.xhtml";
	}

	public String aendern() {
		this.aktuelleGruppe = this.gruppen.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "aendern() mit " + this.aktuelleGruppe);
		return "/gruppe.xhtml";
	}

	public String neuanlage() {
		this.aktuelleGruppe = new Gruppe();
		Logger.getAnonymousLogger().log(Level.INFO, "neuanlage()");
		return "/gruppe.xhtml";
	}

	public String loeschen() {
		this.aktuelleGruppe = this.gruppen.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "loeschen() mit " + this.aktuelleGruppe);
		try {
			this.utx.begin();
			this.aktuelleGruppe = this.em.merge(this.aktuelleGruppe);
			this.em.remove(this.aktuelleGruppe);
			this.gruppen.setWrappedData(this.em.createNamedQuery("SelectGruppen").getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'loeschen()' nicht geklappt", e.getMessage());
		}
		return null;
	}

	public DataModel<Gruppe> getGruppen() {
		return this.gruppen;
	}

	public void setGruppen(final DataModel<Gruppe> gruppen) {
		this.gruppen = gruppen;
	}

	public Gruppe getAktuelleGruppe() {
		return this.aktuelleGruppe;
	}

	public void setAktuelleGruppe(final Gruppe aktuelleGruppe) {
		this.aktuelleGruppe = aktuelleGruppe;
	}
}
