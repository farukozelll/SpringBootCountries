package SpringBoot.Countries.repository;

import SpringBoot.Countries.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country,String> {

    Country save(Country country);

    List<Country> findAllByOrderByPhoneAsc();
    List<Country> findAllByOrderByPhoneDesc();

    List<Country> findByPhone(String phoneCode);

    List<Country> findByNameContaining(String name);

    List<Country> findByCurrencyAndPhoneAndContinent(String currency, String phone, String continent);



}

