package SpringBoot.Countries.businness;

import SpringBoot.Countries.dataAccess.Query;
import SpringBoot.Countries.entities.Country;
import SpringBoot.Countries.repository.CountryRepository;
import SpringBoot.Countries.repository.HibernateUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceJPA implements CountriesJPA {
    List<Country> countries = new ArrayList<>();
    private final static String COUNTRY_JSON = "src/main/resources/countries.json";
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    public CountryServiceJPA(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    @Override
    public Country insertCountry(Country country) throws SQLException {
        return countryRepository.save(country);
    }
    public void oneTimeInsert(List<Country> countries) {
        countryRepository.saveAll(countries);
    }
    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
    @Override
    public Optional<Country> getCountry(String id) {
        return countryRepository.findById(id);
    }

    @Override
    public List<Country> getCountryByName(String name) {
        return countryRepository.findByNameContaining(name);
    }

    @Override
    public Optional<Country> getCountryById(String id) {
        return countryRepository.findById(id);
    }

    @Override
    public List<Country> getCountryByPhoneCode(String phoneCode) {
        return countryRepository.findByPhone(phoneCode);
    }

    @Override
    public List<Country> orderCountriesByPhoneCode(boolean ascending) {
        if (ascending) {
            return countryRepository.findAll(Sort.by(Sort.Direction.ASC, "phone"));
        } else {
            return countryRepository.findAll(Sort.by(Sort.Direction.DESC, "phone"));
        }
    }

    @Override
    public List<Country> getCountriesByProperties(String currency, String phone, String continent) throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String queryString = Query.GET_COUNTRIES_BY_PROPERTIES;
            if (currency != null) {
                queryString += " AND currency = :currency";
            }
            if (phone != null) {
                queryString += " AND phone = :phone";
            }
            if (continent != null) {
                queryString += " AND continent = :continent";
            }

            Query query = (Query<Country>) session.createQuery(queryString, Country.class);
            if (currency != null) {
                ((org.hibernate.query.Query<?>) query).setParameter("currency", currency);
            }
            if (phone != null) {
                ((org.hibernate.query.Query<?>) query).setParameter("phone", phone);
            }
            if (continent != null) {
                ((org.hibernate.query.Query<?>) query).setParameter("continent", continent);
            }
            countries = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;
    }

}
