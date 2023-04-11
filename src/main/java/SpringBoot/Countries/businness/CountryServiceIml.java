package SpringBoot.Countries.businness;

import SpringBoot.Countries.entities.Country;
import SpringBoot.Countries.repository.CountryRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CountryServiceIml implements CountryService {
    @Autowired
    private CountryRepository countryRepository;
    private final static String COUNTRY_JSON = "src/main/resources/countries.json";
    List<Country> countries = new ArrayList<>();
    @PersistenceContext
    private EntityManager entityManager; // JPA ile çalışan uygulamalarda veritabanı işlemlerini yönetir
   public CountryServiceIml(EntityManager entityManager) {
       this.entityManager = entityManager;
   }

     /*** Verilen id değerine göre bir ülke döndürür.
     *
     * @param id ülke id'si
     * @return id değerine göre bulunan ülke*/
    @Override
    public Optional<Country> getCountry(String id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> cq = cb.createQuery(Country.class);
        Root<Country> root = cq.from(Country.class);
        cq.select(root).where(cb.equal(root.get("id"), id));
        TypedQuery<Country> query = entityManager.createQuery(cq);
        List<Country> results = query.getResultList();
        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

     /** * Tüm ülkeleri getirir.
     *
     * @return tüm ülkelerin listesi*/
    @Override
    public List<Country> getAllCountries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> cq = cb.createQuery(Country.class);
        Root<Country> root = cq.from(Country.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }
    @Override
    public List<Country> getCountryByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> cq = cb.createQuery(Country.class);
        Root<Country> root = cq.from(Country.class);

        // Ülke adının aranması için kullanılan LIKE operatörü ile arama yapılır
        cq.where(cb.like(root.get("name"), "%" + name + "%"));

        return entityManager.createQuery(cq).getResultList();
    }
    @Override
    public Optional<Country> getCountryById(String id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
        Root<Country> root = criteriaQuery.from(Country.class);

        // Verilen ID'ye göre tek bir ülke döndürmek için kullanılır.
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<Country> query = entityManager.createQuery(criteriaQuery);
        List<Country> countries = query.getResultList();
        return countries.isEmpty() ? Optional.empty() : Optional.of(countries.get(0));
    }
    @Override
    public List<Country> getCountryByPhoneCode(String phoneCode) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> cq = cb.createQuery(Country.class);
        Root<Country> root = cq.from(Country.class);

        // Ülke telefon koduna göre ülkelerin listesi döndürülür.
        cq.where(cb.equal(root.get("phone"), phoneCode));
        TypedQuery<Country> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
     /*** Belirtilen sıralama parametresine göre ülkeleri telefon koduna göre sıralar.
     *
     * @param order Sıralama parametresi: "asc" (artan) veya "desc" (azalan)
     * @return Telefon koduna göre sıralanmış ülke listesi
     * @throws IllegalArgumentException Geçersiz sıralama parametresi verildiğinde fırlatılır. */
    @Override
    public List<Country> orderCountriesByPhoneCode(String order) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> query = builder.createQuery(Country.class);
        Root<Country> root = query.from(Country.class);
        if ("asc".equalsIgnoreCase(order)) {
            query.orderBy(builder.asc(root.get("phone")));
        }  else if ("desc".equalsIgnoreCase(order)) {
            query.orderBy(builder.desc(root.get("phone")));
        }else {
            throw new IllegalArgumentException("ascending parametresi 'asc' veya 'desc' olmalidir.");
        }
        TypedQuery<Country> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
    @Override
    public List<Country> getCountriesByProperties(String currency, String phone, String continent) {
        // CriteriaBuilder ve CriteriaQuery oluşturuluyor
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> query = criteriaBuilder.createQuery(Country.class);

        // Root nesnesi oluşturuluyor ve sorgu başlangıç noktası olarak belirtiliyor
        Root<Country> root = query.from(Country.class);

        // Filtrelemek için Predicate nesneleri oluşturuluyor
        List<Predicate> predicates = new ArrayList<>();

        // currency filtresi eklendi
        if (currency != null) {
            predicates.add(criteriaBuilder.equal(root.get("currency"), currency));
        }
        // phone filtresi eklendi
        if (phone != null) {
            predicates.add(criteriaBuilder.equal(root.get("phone"), phone));
        }
        // continent filtresi eklendi
        if (continent != null) {
            predicates.add(criteriaBuilder.equal(root.get("continent"), continent));
        }
        // Tüm filtreler ve 'and' bağlacı ile birlikte sorguya ekleniyor
        if (!predicates.isEmpty()) {
            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }
        // Sorgu sonucu döndürülüyor
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Country insertCountry(Country country) throws IOException {
        country.setId(UUID.randomUUID().toString()); // rastgele bir kimlik
        return countryRepository.save(country);
    }
    @Override
    public void populateCountries() {
        if (countryRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, Map<String, Object>> countryMaps = objectMapper.readValue(new File(COUNTRY_JSON), new TypeReference<Map<String, Map<String, Object>>>() {});
                for (Map.Entry<String, Map<String, Object>> entry : countryMaps.entrySet()) {
                    String countryCode = entry.getKey();
                    Map<String, Object> countryMap = entry.getValue();
                    Country country = objectMapper.convertValue(countryMap, Country.class);
                    country.setId(countryCode);
                    country.setName((String) countryMap.get("name"));
                    country.setNativeName((String) countryMap.get("native"));
                    country.setPhone((String) countryMap.get("phone"));
                    country.setContinent((String) countryMap.get("continent"));
                    country.setCapital((String) countryMap.get("capital"));
                    country.setCurrency((String) countryMap.get("currency"));
                    country.setLanguages(Collections.singletonList(countryMap.get("languages")).toString());
                    country.setFlag("http://aedemirsen.bilgimeclisi.com/country_flags/" + countryCode + ".svg");
                    insertCountry(country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*   @Override
    public List<Country> getCountriesByProperties(String currency, String phoneCode, String continent) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> query = criteriaBuilder.createQuery(Country.class);
        Root<Country> root = query.from(Country.class);
        List<Predicate> predicates = new ArrayList<>();
        if (currency != null) {
            predicates.add(criteriaBuilder.equal(root.get("currency"), currency));
        }
        if (phoneCode != null) {
            predicates.add(criteriaBuilder.equal(root.get("phone"), phoneCode));
        }
        if (continent != null) {
            predicates.add(criteriaBuilder.equal(root.get("continent"), continent));
        }
        if (!predicates.isEmpty()) {
            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }
        return entityManager.createQuery(query).getResultList();
    }*/
 /*   @Override
    public Country insertCountry(Country country) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON verilerini bir Map nesnesine okuyun.
            Map<String, Map<String, Object>> countryMaps = objectMapper.readValue(new File(COUNTRY_JSON), new TypeReference<Map<String, Map<String, Object>>>() {});

            // EntityManagerFactory oluşturun
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("country");

            // EntityManager oluşturun
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                // Map nesnesindeki her giriş üzerinde döngü yapın ve verileri veritabanına ekleyin.
                for (Map.Entry<String, Map<String, Object>> entry : countryMaps.entrySet()) {
                    String countryCode = entry.getKey();
                    Map<String, Object> countryMap = entry.getValue();

                    // Map nesnesini country nesnesine dönüştürün.
                    country = objectMapper.convertValue(countryMap, Country.class);
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
                    country.setLanguages(languages.toString());

                    // entityManager.persist() kullanarak veritabanına kaydedin
                    transaction.begin();
                    entityManager.persist(country);
                    transaction.commit();
                }
            } catch (Exception e) {
                // Transaction'ı rollback edin
                transaction.rollback();
                throw e;
            } finally {
                // EntityManager'ı ve EntityManagerFactory'yi kapatın
                entityManager.close();
                entityManagerFactory.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return country;
    }
*/


/*    @Override
    public Country insertCountry(Country country) {
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

            try {
                // Map nesnesindeki her giriş üzerinde döngü yapın ve verileri veritabanına ekleyin.
                for (Map.Entry<String, Map<String, Object>> entry : countryMaps.entrySet()) {
                    String countryCode = entry.getKey();
                    Map<String, Object> countryMap = entry.getValue();

                    // Map nesnesini country nesnesine dönüştürün.
                    country = objectMapper.convertValue(countryMap, Country.class);
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
                    country.setLanguages(languages.toString());

                    // session.save() kullanarak veritabanına kaydedin
                    session.save(country);
                }

                // Transaction'ı commit edin ve session'ı kapatın
                transaction.commit();
            } catch (Exception e) {
                // Transaction'ı rollback edin ve session'ı kapatın
                transaction.rollback();
                throw e;
            } finally {
                session.close();
            }

            // SessionFactory'yi kapatın
            sessionFactory.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return country;
    }*/


}
