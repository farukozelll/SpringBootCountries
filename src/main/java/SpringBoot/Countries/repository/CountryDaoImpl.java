package SpringBoot.Countries.repository;

import SpringBoot.Countries.dataAccess.Query;
import SpringBoot.Countries.entities.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import SpringBoot.Countries.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class CountryDaoImpl implements CountryDao{
    @Autowired
    private final DataSource dataSource;

   public CountryDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}
