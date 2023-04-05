package SpringBoot.Countries.businness;

import SpringBoot.Countries.repository.CountryDao;
import SpringBoot.Countries.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceIml implements CountryService  {

    @Autowired
    private CountryDao countryDao;

    @Override
    public List<Country> getAllCountries() {
        return countryDao.getAllCountries();
    }

}
