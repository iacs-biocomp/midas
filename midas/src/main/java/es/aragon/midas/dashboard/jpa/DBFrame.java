package es.aragon.midas.dashboard.jpa;

import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the bigan_frames database table.
 * 
 */
@Entity
@Table(name="db_frames")
@NamedQuery(name="DBFrame.findAll", query="SELECT b FROM DBFrame b")
public class DBFrame implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String comment;

	private String description;

	private String grant;

	private Integer order;
	
	private Integer suborder;

	private String path;

	private String title;
	
	private String js;

	//bi-directional many-to-one association to BiganDashboard
	@ManyToOne
	@JoinColumn(name="dashboard_id")
	private DBDashboard dbDashboard;

	//bi-directional many-to-one association to BiganFrameType
	@ManyToOne
	@JoinColumn(name="frame_type")
	private DBFrameType dbFrameType;

	//bi-directional many-to-one association to BiganQuery
	@ManyToOne
	@JoinColumn(name="query_id")
	private DBQuery dbQuery;

	public DBFrame() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DBDashboard getDBDashboard() {
		return this.dbDashboard;
	}

	public void setDBDashboard(DBDashboard dbDashboard) {
		this.dbDashboard = dbDashboard;
	}

	public DBFrameType getDBFrameType() {
		return this.dbFrameType;
	}

	public void setDBFrameType(DBFrameType dbFrameType) {
		this.dbFrameType = dbFrameType;
	}

	public DBQuery getDBQuery() {
		return this.dbQuery;
	}

	public void setDBQuery(DBQuery dbQuery) {
		this.dbQuery = dbQuery;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public Integer getSuborder() {
		return suborder;
	}

	public void setSuborder(Integer suborder) {
		this.suborder = suborder;
	}

}