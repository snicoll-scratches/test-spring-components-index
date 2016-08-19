package com.example.domain.sample8;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Entity8_7 {

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
