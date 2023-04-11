/*
package SpringBoot.Countries.businness;

package SpringBoot.Countries.businness;

import SpringBoot.Countries.entities.Country;
import SpringBoot.Countries.repository.CountryRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class yedek {

    private CountryRepository countryRepository;
    private final static String COUNTRY_JSON = "src/main/resources/countries.json";
    List<Country> countries = new ArrayList<>();
    @PersistenceContext
    private EntityManager entityManager; // JPA ile çalışan uygulamalarda veritabanı işlemlerini yönetir
    public yedek(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
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
    public List<Country> getCountryByPhoneName(String name) {
        return countryRepository.findByName(name);
    }

    @Override
    public List<Country> orderCountriesByPhoneCode(boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        return countryRepository.findAll(Sort.by(direction, "phone"));
    }



    @Override
    public List<Country> getCountriesByProperties(String currency, String phone, String continent) {
        return null;
    }

    @Override
    public Country insertCountry() {
        return null;
    }

    */
/*   @Override
       public Country insertCountry(){
           ObjectMapper objectMapper = new ObjectMapper();

           try {
               // JSON verilerini bir Map nesnesine okuyun.
               Map<String, Map<String, Object>> countryMaps = objectMapper.readValue(new File(COUNTRY_JSON), new TypeReference<Map<String, Map<String, Object>>>() {});

               // SessionFactory oluşturun
               Configuration configuration = new Configuration().configure();
               StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
               SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

               // Session oluşturun
               Session session = sessionFactory.openSession();
               Transaction transaction = session.beginTransaction();

               // Map nesnesindeki her giriş üzerinde döngü yapın ve verileri veritabanına ekleyin.
               for (Map.Entry<String, Map<String, Object>> entry : countryMaps.entrySet()) {
                   String countryCode = entry.getKey();
                   Map<String, Object> countryMap = entry.getValue();

                   // Map nesnesini country nesnesine dönüştürün.
                   Country country = objectMapper.convertValue(countryMap, Country.class);
                   country.setId(countryCode);
                   country.setName((String) countryMap.get("name"));
                   country.setNativeName((String) countryMap.get("native"));
                   country.setPhone((String) countryMap.get("phone"));
                   country.setContinent((String) countryMap.get("continent"));
                   country.setCapital((String) countryMap.get("capital"));
                   country.setCurrency((String) countryMap.get("currency"));
                   country.setFlag("http://aedemirsen.bilgimeclisi.com/country_flags/" + countryCode + ".svg");

                   // Map nesnesinin "languages" alanını bir String Listesine dönüştürün ve onu Country nesnesinin languages alanı olarak ayarlayın.
                   TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {};
                   List<String> languages = objectMapper.readValue(objectMapper.writeValueAsString(countryMap.get("languages")), typeRef);
                   country.setLanguages(languages);

                   // session.save() kullanarak veritabanına kaydedin
                   session.save(country);
               }

               // Transaction'ı commit edin ve session'ı kapatın
               transaction.commit();
               session.close();

               // SessionFactory'yi kapatın
               sessionFactory.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }*//*

    public void oneTimeInsert(List<Country> countries) {
        countryRepository.saveAll(countries);
    }

*/
/*    @Override
    public List<Country> getCountriesByProperties(String currency, String phone, String continent) throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Country> criteria = builder.createQuery(Country.class);
            Root<Country> root = criteria.from(Country.class);
            criteria.select(root);

            if (currency != null) {
                criteria.where(builder.equal(root.get("currency"), currency));
            }
            if (phone != null) {
                criteria.where(builder.equal(root.get("phone"), phone));
            }
            if (continent != null) {
                criteria.where(builder.equal(root.get("continent"), continent));
            }

            List<Country> countries = HibernateUtil.getSessionFactory().getCurrentSession().createQuery(query)
            return countries;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*//*

}
*/
