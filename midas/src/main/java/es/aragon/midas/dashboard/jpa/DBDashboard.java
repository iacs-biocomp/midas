package es.aragon.midas.dashboard.jpa;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the db_dashboards database table.
 * 
 */
@Entity
@Table(name="db_dashboards")
@NamedQuery(name="DBDashboard.findAll", query="SELECT b FROM DBDashboard b")
public class DBDashboard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String description;

	private String grant;

	private String template;
	
	private String script;

	//bi-directional many-to-one association to DBFrame
	@OneToMany(mappedBy="dbDashboard", fetch = FetchType.EAGER)
	@OrderBy("suborder ASC")
	private List<DBFrame> dbFrames;

	public DBDashboard() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGrant() {
		return this.grant;
	}

	public void setGrant(String grant) {
		this.grant = grant;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<DBFrame> getDBFrames() {
		return this.dbFrames;
	}

	public void setDBFrames(List<DBFrame> dbFrames) {
		this.dbFrames = dbFrames;
	}

	public DBFrame addDBFrame(DBFrame dbFrame) {
		getDBFrames().add(dbFrame);
		dbFrame.setDBDashboard(this);

		return dbFrame;
	}

	public DBFrame removeDBFrame(DBFrame dbFrame) {
		getDBFrames().remove(dbFrame);
		dbFrame.setDBDashboard(null);

		return dbFrame;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

}