package com.pal.taxi.persistence.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.pal.taxi.common.Location;
import com.pal.taxi.persistence.entities.LocationEntity;
import com.pal.taxi.persistence.mapper.internal.LocationMapper;

/**
 * The location repository to fetch details from DB.
 * 
 * @author Palraj
 */
public class LocationRepository extends AbstractRepository<LocationEntity> {

	private LocationRepository() {
		// single instance of repository.
	}

	private static final LocationRepository SINGLE_INSTANCE = new LocationRepository();

	public static LocationRepository getInstance() {
		return SINGLE_INSTANCE;
	}

	@Override
	protected Class<LocationEntity> getEntityClass() {
		return LocationEntity.class;
	}

	/**
	 * as of now, the locations wont change and hence, there is no need to go again
	 * and again to fetch the data from DB server.
	 */
	private final Collection<Location> locations = new TreeSet<>(getAllLocationsFromRepo());

	private Collection<Location> getAllLocationsFromRepo() {
		return getAll().stream().map(LocationMapper.INSTANCE::toLocation).collect(Collectors.toSet());
	}

	/**
	 * @return All the predefined locations stored in the DB.
	 */
	public Collection<Location> getAllLocations() {
		return Collections.unmodifiableCollection(locations);
	}

}
