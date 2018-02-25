package es.aragon.midas.dashboard.jpa;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the db_frame_type database table.
 * 
 */
@Entity
@Table(name="db_frame_type")
@NamedQuery(name="DBFrameType.findAll", query="SELECT b FROM DBFrameType b")
public class DBFrameType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	@Column(name="data_format")
	private String dataFormat;

	@Column(name="data_snippet")
	private String dataSnippet;

	private String description;

	private String js;

	private String snippet;
	
	private String renderer;

	//bi-directional many-to-one association to DBFrame
	@OneToMany(mappedBy="dbFrameType", fetch = FetchType.EAGER)
	private List<DBFrame> dbFrames;

	public DBFrameType() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataFormat() {
		return this.dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getDataSnippet() {
		return this.dataSnippet;
	}

	public void setDataSnippet(String dataSnippet) {
		this.dataSnippet = dataSnippet;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJs() {
		return this.js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public String getSnippet() {
		return this.snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public List<DBFrame> getDBFrames() {
		return this.dbFrames;
	}

	public void setDBFrames(List<DBFrame> dbFrames) {
		this.dbFrames = dbFrames;
	}

	public DBFrame addDBFrame(DBFrame dbFrame) {
		getDBFrames().add(dbFrame);
		dbFrame.setDBFrameType(this);

		return dbFrame;
	}

	public DBFrame removeDBFrame(DBFrame dbFrame) {
		getDBFrames().remove(dbFrame);
		dbFrame.setDBFrameType(null);

		return dbFrame;
	}

	public String getRenderer() {
		return renderer;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

}