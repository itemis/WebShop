package de.itemis.webshop.repositories;

import org.springframework.data.repository.CrudRepository;

import de.itemis.webshop.domain.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
