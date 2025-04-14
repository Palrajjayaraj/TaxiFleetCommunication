package com.pal.taxi.user;

import java.util.UUID;

import com.pal.taxi.common.IUniqueIdentifiable;

import lombok.NonNull;

public class User implements IUniqueIdentifiable {

	private final UUID uuid;

	public User(@NonNull UUID uuid, @NonNull String name) {
		this.uuid = uuid;
		this.name = name;
	}

	@Override
	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	private final String name;

}
