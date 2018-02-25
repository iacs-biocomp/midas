package es.aragon.midas.dashboard.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the db_queries database table.
 * 
 */
@Entity
@Table(name="db_queries")
@NamedQuery(name="DBQuery.findAll", query="SELECT b FROM DBQuery b")
public class DBQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(updatable=false)
	private Integer id;

	private String description;

	private String query;
	
	private String columns;

	//bi-directional many-to-one association to DBFrame
	@OneToMany(mappedBy="dbQuery", fetch = FetchType.EAGER)
	private List<DBFrame> dbFrames;

	public DBQuery() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<DBFrame> getDBFrames() {
		return this.dbFrames;
	}

	public void setDBFrames(List<DBFrame> dbFrames) {
		this.dbFrames = dbFrames;
	}

	public DBFrame addDBFrame(DBFrame dbFrame) {
		getDBFrames().add(dbFrame);
		dbFrame.setDBQuery(this);

		return dbFrame;
	}

	public DBFrame removeDBFrame(DBFrame dbFrame) {
		getDBFrames().remove(dbFrame);
		dbFrame.setDBQuery(null);

		return dbFrame;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

}