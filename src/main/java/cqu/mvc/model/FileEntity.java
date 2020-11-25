package cqu.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FileEntity {
	@Id
	@Column(length=50,nullable=false)
	private String id;

	@Column(length=50,nullable=false)
	private String name;

	@ManyToOne(optional=false)
	public UserEntity user;
	
	public FileEntity() {
	}
	public FileEntity(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public FileEntity(String id, String name,UserEntity user) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	
}
