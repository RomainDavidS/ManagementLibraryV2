package musers.service.address;



import musers.model.address.Address;

import java.util.List;

public interface IAddressService {
    List<Address> findAll();
    void save(Address address);
    Address findAddress(Long id);
}
