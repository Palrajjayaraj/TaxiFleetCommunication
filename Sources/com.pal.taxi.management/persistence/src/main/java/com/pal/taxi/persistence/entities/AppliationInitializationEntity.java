package com.pal.taxi.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "application_initialization")
public class AppliationInitializationEntity {

	public static final String IS_INITIALIZED = "IS_INITIALIZED";

	@Id
	private String value;

	protected AppliationInitializationEntity() {
		// For Hibernate
	}

	public AppliationInitializationEntity(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
