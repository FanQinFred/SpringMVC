package cqu.mvc.model;

import javax.persistence.*;
import java.util.Set;

/**
 * 对象关系映射
 */
@Entity
public class UserEntity {
	@Id
	@Column(length=32,nullable=false)
	private String name;

	@Column(length=32,nullable=false)
	private String pwd;

	@OneToMany(mappedBy="user",cascade= CascadeType.REMOVE)
	private Set<FileEntity> fileSet;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Set<FileEntity> getFileSet() {
		return fileSet;
	}

	public void setFileSet(Set<FileEntity> fileSets) {
		this.fileSet = fileSets;
	}
}
