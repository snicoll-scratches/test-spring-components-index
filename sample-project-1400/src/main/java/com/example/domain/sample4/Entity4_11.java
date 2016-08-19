package com.example.domain.sample4;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Entity4_11 {

	@Id
	@GeneratedValue
	private long id;

	private String description;

	public long getId() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
