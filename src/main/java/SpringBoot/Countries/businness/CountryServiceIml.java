package SpringBoot.Countries.businness;

import SpringBoot.Countries.dataAccess.Query;
import SpringBoot.Countries.entities.Country;
import SpringBoot.Countries.repository.CountryRepository;
import SpringBoot.Countries.repository.HibernateUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Selection;
import org.hibernate.HibernateException;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@Service
public class CountryServiceIml implements CountryService  {


    @Autowired
    private CountryRepository countryRepository;
    List<Country> countries = new ArrayList<>();
    private final static String COUNTRY_JSON = "src/main/resources/countries.json";

   /* @Override
    public List<Country> getAllCountries() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/countries_db", "root", "12345");
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(Query.GET_ALL_COUNTRIES)) {
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getString("id"));
                country.setName(rs.getString("name"));
                country.setNativeName(rs.getString("native_name"));
                country.setPhone(rs.getString("phone"));
                country.setContinent(rs.getString("continent"));
                country.setCapital(rs.getString("capital"));
                country.setCurrency(rs.getString("currency"));
                String languages = rs.getString("languages");
                country.setLanguages(Arrays.asList(languages.split(",")));
                country.setFlag(rs.getString("flag"));
                countries.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countries;
    }
*/
   private SessionFactory sessionFactory;

    public CountryServiceIml() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    @Override
    public void insertCountry() {
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
    }

    @Override
    public List<Country> getAllCountries() {
           try (Session session = HibernateUtil.getSessionFactory().openSession()) {
               String hql = Query.GET_ALL_COUNTRIES;
               Query query = (Query) session.createQuery(hql, Country.class);
               countries = query.getResultList();
           } catch (Exception e) {
               e.printStackTrace();
           }
           return countries;
       }

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    public List<Country> getCountriesByName(String name) {
        return countryRepository.findByName(name);
    }
    @Override
    public List<Country> getCountry() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
            Root<Country> root = (Root<Country>) criteriaQuery.from(Country.class);
            criteriaQuery.select((Selection<? extends Country>) root);
            Query query = (Query<Country>) session.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Country> getCountryByCode(String kod) {
        Session session = null;
        try {
            // Session oluşturuluyor
            session = sessionFactory.openSession();
            // Sorgu oluşturuluyor
            Query query = (Query<Country>) session.createQuery(Query.GET_COUNTRY_BY_CODE, Country.class);
            // Parametre atanıyor
            query.setParameter("kod", kod);
            // Sorgu sonucu veritabanından çekiliyor
            countries = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            // Session kapatılıyor
            if (session != null) {
                session.close();
            }
        }
        return countries;
    }

    @Override
    public List<Country> getCountryByName(String name) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Hibernate sorgusu hazırlanıyor
            String hql = "FROM Country WHERE name = :name";
            Query query = (Query<Country>) session.createQuery(hql, Country.class);
            query.setParameter("name", name);

            // Sorgu sonucunda dönen Country nesneleri, countries listesine atanıyor
            countries = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return countries;
    }


    @Override
    public List<Country> getCountryById(String kod) throws SQLException {
        return null;
    }

    @Override
    public List<Country> getCountryByPhoneCode(String kod) throws SQLException {
        return null;
    }

    @Override
    public List<Country> orderCountriesByPhoneCode(boolean ascending) {
        // Create Hibernate session factory and session
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        // Build HQL query to retrieve countries sorted by phone code
        String hql = Query.GET_COUNTRIES_SORTED_BY_PHONE + (ascending ? "ASC" : "DESC");

        try {
            // Begin transaction
            session.beginTransaction();

            // Execute query and get list of Country objects
            Query query = (Query<Country>) session.createQuery(hql, Country.class);
            List<Country> countries = query.getResultList();

            // Commit transaction
            session.getTransaction().commit();

            return countries;
        } catch (Exception e) {
            // Roll back transaction if there is an error
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Close session and session factory
            session.close();
            sessionFactory.close();
        }
        return null;
    }


    @Override
    public List<Country> getCountriesByProperties(String currency, String phone, String continent) {

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
