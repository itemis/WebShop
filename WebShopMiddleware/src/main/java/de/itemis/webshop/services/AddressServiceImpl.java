package de.itemis.webshop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import de.itemis.webshop.domain.Address;
import de.itemis.webshop.repositories.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<Address> getAddressList() {
		List<Address> addressList = Lists.newArrayList(addressRepository.findAll()); 

		return addressList;
	}
}
