package com.pal.taxi.persistence.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	private UUID uuid;

	private String name;

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public void setName(String name) {
		this.name = name;
	}

}
