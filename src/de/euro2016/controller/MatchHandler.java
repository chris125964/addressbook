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

import de.euro2016.model.Gruppe;
import de.euro2016.model.Match;

@ManagedBean
@SessionScoped
public class MatchHandler implements Serializable {

	private static final long serialVersionUID = 8483673816098447616L;

	private static Logger logger = Logger.getLogger(MatchHandler.class.getCanonicalName());

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<Match> matches;
	private Match currentMatch = new Match();

	public MatchHandler() {
		logger.log(Level.INFO, MatchHandler.class.getName() + "-Instanz erzeugt");
	}

	public void createMatches(final Gruppe group) {
		Logger.getAnonymousLogger().log(Level.INFO, "createMatches() aufgerufen");
		try {
			this.utx.begin();
			this.em.persist(new Match(group, group.getTeam1(), group.getTeam2()));
			this.em.persist(new Match(group, group.getTeam3(), group.getTeam4()));
			this.em.persist(new Match(group, group.getTeam1(), group.getTeam3()));
			this.em.persist(new Match(group, group.getTeam2(), group.getTeam4()));
			this.em.persist(new Match(group, group.getTeam4(), group.getTeam1()));
			this.em.persist(new Match(group, group.getTeam3(), group.getTeam2()));
			this.matches = new ListDataModel<Match>();
			this.matches.setWrappedData(this.em.createNamedQuery(Gruppe.findAll).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'init()' nicht geklappt: " + e.getMessage());
		}
	}

	@PostConstruct
	public void init() {
		Logger.getAnonymousLogger().log(Level.INFO, "'MatchHandler.init()' aufgerufen");
	}

	public DataModel<Match> getMatches() {
		return this.matches;
	}

}
