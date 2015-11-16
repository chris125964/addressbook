package de.euro2016.controller;

import java.io.Serializable;
import java.util.List;
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
import de.euro2016.model.Team;

@ManagedBean
@SessionScoped
public class GroupHandler implements Serializable {

	private static final long serialVersionUID = -6544110274546711076L;

	private static Logger logger = Logger.getLogger(GroupHandler.class.getCanonicalName());

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<Gruppe> groups;
	private Gruppe currentGroup = new Gruppe();
	private List<Team> listLaender;
	private Integer teamId1;
	private Integer teamId2;
	private Integer teamId3;
	private Integer teamId4;

	public GroupHandler() {
		logger.log(Level.INFO, GroupHandler.class.getName() + "-Instanz erzeugt");
	}

	public String speichern() {
		Logger.getAnonymousLogger().log(Level.INFO, "speichern() [1] mit " + this.currentGroup + "' aufgerufen");
		try {
			this.utx.begin();
			Team gefundenesLand1 = this.em.find(Team.class, this.getTeamId1());
			Team gefundenesLand2 = this.em.find(Team.class, this.getTeamId2());
			Team gefundenesLand3 = this.em.find(Team.class, this.getTeamId3());
			Team gefundenesLand4 = this.em.find(Team.class, this.getTeamId4());
			this.speichereGruppe(this.currentGroup, gefundenesLand1, gefundenesLand2, gefundenesLand3,
					gefundenesLand4);
			this.speichereLand(gefundenesLand1);
			this.speichereLand(gefundenesLand2);
			this.speichereLand(gefundenesLand3);
			this.speichereLand(gefundenesLand4);
			Logger.getAnonymousLogger().log(Level.INFO, "speichern() [2] mit " + this.currentGroup + "' aufgerufen");
			this.groups.setWrappedData(this.em.createNamedQuery(Gruppe.findAll).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'speichern()' nicht geklappt: " + e.getMessage());
		}
		return "/anzeige-gruppen.xhtml";
	}

	private void speichereGruppe(final Gruppe pGruppe, final Team pLand1, final Team pLand2, final Team pLand3,
			final Team pLand4) {
		Gruppe gruppe = this.em.merge(pGruppe);
		gruppe.setTeam1(pLand1);
		gruppe.setTeam2(pLand2);
		gruppe.setTeam3(pLand3);
		gruppe.setTeam4(pLand4);
		this.em.persist(gruppe);
	}

	private void speichereLand(final Team pLand) {
		Team land = this.em.merge(pLand);
		this.em.persist(land);
	}

	public String aendern() {
		this.currentGroup = this.groups.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "aendern() mit " + this.currentGroup);
		return "/gruppe.xhtml";
	}

	public String neuanlage() {
		this.currentGroup = new Gruppe();
		Logger.getAnonymousLogger().log(Level.INFO, "neuanlage()");
		return "/gruppe.xhtml";
	}

	public String loeschen() {
		this.currentGroup = this.groups.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "loeschen() mit " + this.currentGroup);
		try {
			this.utx.begin();
			this.currentGroup = this.em.merge(this.currentGroup);
			this.em.remove(this.currentGroup);
			this.groups.setWrappedData(this.em.createNamedQuery(Gruppe.findAll).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'loeschen()' nicht geklappt", e.getMessage());
		}
		return null;
	}

	@PostConstruct
	public void init() {
		Logger.getAnonymousLogger().log(Level.INFO, "'init()' aufgerufen");
		try {
			this.utx.begin();
			this.em.persist(new Gruppe('A'));
			this.em.persist(new Gruppe('B'));
			this.em.persist(new Gruppe('C'));
			this.em.persist(new Gruppe('D'));
			this.em.persist(new Gruppe('E'));
			this.em.persist(new Gruppe('F'));
			this.em.persist(new Gruppe('G'));
			this.em.persist(new Gruppe('H'));
			this.groups = new ListDataModel<Gruppe>();
			this.groups.setWrappedData(this.em.createNamedQuery(Gruppe.findAll).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'init()' nicht geklappt: " + e.getMessage());
		}
	}

	public DataModel<Gruppe> getGroups() {
		return this.groups;
	}

	public void setGroups(final DataModel<Gruppe> gruppen) {
		this.groups = gruppen;
	}

	public Gruppe getCurrentGroup() {
		return this.currentGroup;
	}

	public void setCurrentGroup(final Gruppe currentGroup) {
		this.currentGroup = currentGroup;
	}

	public List<Team> getListLaender() {
		return this.listLaender;
	}

	public void setListLaender(final List<Team> listLaender) {
		this.listLaender = listLaender;
	}

	public Integer getTeamId1() {
		return this.teamId1;
	}

	public void setTeamId1(final Integer teamId) {
		this.teamId1 = teamId;
	}

	public Integer getTeamId2() {
		return this.teamId2;
	}

	public void setTeamId2(final Integer teamId2) {
		this.teamId2 = teamId2;
	}

	public Integer getTeamId3() {
		return this.teamId3;
	}

	public void setTeamId3(final Integer teamId3) {
		this.teamId3 = teamId3;
	}

	public Integer getTeamId4() {
		return this.teamId4;
	}

	public void setTeamId4(final Integer teamId4) {
		this.teamId4 = teamId4;
	}
}
