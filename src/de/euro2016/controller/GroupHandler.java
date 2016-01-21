package de.euro2016.controller;

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

import de.euro2016.model.Group;
import de.euro2016.model.Team;

@ManagedBean
@SessionScoped
public class GroupHandler {

	private static final long serialVersionUID = -6544110274546711076L;

	private static Logger logger = Logger.getLogger(GroupHandler.class.getCanonicalName());

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	private DataModel<Group> groups;
	private Group aktuelleGroup = new Group();
	private List<Team> listLaender;
	private Integer teamId1;
	private Integer teamId2;
	private Integer teamId3;
	private Integer teamId4;

	public GroupHandler() {
		logger.log(Level.INFO, GroupHandler.class.getName() + "-Instanz erzeugt");
	}

	public String speichern() {
		Logger.getAnonymousLogger().log(Level.INFO, "speichern() [1] mit " + this.aktuelleGroup + "' aufgerufen");
		try {
			this.utx.begin();
			Team gefundenesLand1 = this.em.find(Team.class, this.getTeamId1());
			Team gefundenesLand2 = this.em.find(Team.class, this.getTeamId2());
			Team gefundenesLand3 = this.em.find(Team.class, this.getTeamId3());
			Team gefundenesLand4 = this.em.find(Team.class, this.getTeamId4());
			this.speichereGroup(this.aktuelleGroup, gefundenesLand1, gefundenesLand2, gefundenesLand3, gefundenesLand4);
			// this.speichereLand(gefundenesLand1);
			// this.speichereLand(gefundenesLand2);
			// this.speichereLand(gefundenesLand3);
			// this.speichereLand(gefundenesLand4);
			Logger.getAnonymousLogger().log(Level.INFO, "speichern() [2] mit " + this.aktuelleGroup + "' aufgerufen");
			this.groups.setWrappedData(this.em.createNamedQuery(Group.select).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'speichern()' nicht geklappt: " + e.getMessage());
		}
		return "/anzeige-gruppen.xhtml";
	}

	private void speichereGroup(final Group pGroup, final Team pLand1, final Team pLand2, final Team pLand3,
			final Team pLand4) {
		Group group = this.em.merge(pGroup);
		group.setTeam1(pLand1);
		group.setTeam2(pLand2);
		group.setTeam3(pLand3);
		group.setTeam4(pLand4);
		this.em.persist(group);
	}

	private void speichereLand(final Team pLand) {
		Team land = this.em.merge(pLand);
		this.em.persist(land);
	}

	public String aendern() {
		this.aktuelleGroup = this.groups.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "aendern() mit " + this.aktuelleGroup);
		return "/gruppe.xhtml";
	}

	public String neuanlage() {
		this.aktuelleGroup = new Group();
		Logger.getAnonymousLogger().log(Level.INFO, "neuanlage()");
		return "/gruppe.xhtml";
	}

	public String loeschen() {
		this.aktuelleGroup = this.groups.getRowData();
		Logger.getAnonymousLogger().log(Level.INFO, "loeschen() mit " + this.aktuelleGroup);
		try {
			this.utx.begin();
			this.aktuelleGroup = this.em.merge(this.aktuelleGroup);
			this.em.remove(this.aktuelleGroup);
			this.groups.setWrappedData(this.em.createNamedQuery("" + Group.select).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'loeschen()' nicht geklappt", e.getMessage());
		}
		return null;
	}

	@PostConstruct
	public void init() {
		Logger.getAnonymousLogger().log(Level.INFO, "'GroupHandler.init()' aufgerufen");
		try {
			this.utx.begin();
			this.em.persist(new Group('A'));
			// this.em.persist(new Group('B'));
			// this.em.persist(new Group('C'));
			// this.em.persist(new Group('D'));
			// this.em.persist(new Group('E'));
			// this.em.persist(new Group('F'));
			// this.em.persist(new Group('G'));
			// this.em.persist(new Group('H'));
			this.groups = new ListDataModel<Group>();
			this.groups.setWrappedData(this.em.createNamedQuery(Group.select).getResultList());
			this.utx.commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "'init()' nicht geklappt: " + e.getMessage());
		}
	}

	public DataModel<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(final DataModel<Group> gruppen) {
		this.groups = gruppen;
	}

	public Group getCurrentGroup() {
		return this.aktuelleGroup;
	}

	public void setCurrentGroup(final Group aktuelleGruppe) {
		this.aktuelleGroup = aktuelleGruppe;
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
