package SpringBoot.Countries.repository;

import SpringBoot.Countries.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface CountryRepository extends JpaRepository<Country, String> { //Spring Data JPA'nın sorgu oluşturma mekanizmasını kullanabilirsin

    // Yeni bir Country nesnesi ekler
    @Transactional
    Country save(Country country);

    // Birden fazla Country nesnesi ekler
    @Transactional
    List<Country> saveAll(List<Country> countries);

    // Verilen id'ye sahip bir Country nesnesi döndürür
    Optional<Country> findById(Long id);

    // Verilen ülke adına sahip olan tüm Country nesnelerini döndürür
    List<Country> findByName(String name);

    // Verilen telefon koduna sahip olan tüm Country nesnelerini döndürür
    List<Country> findByPhone(String phone);

    // Verilen telefon koduna sahip olan tüm Country nesnelerini, telefon koduna göre sıralayarak döndürür
    List<Country> findByOrderByPhoneAsc(String phone);

    // Tüm Country nesnelerini döndürür
    List<Country> findAll();

    // Verilen id'ye sahip olan Country nesnesini siler

    List<Country> findByNameContaining(String name);
}