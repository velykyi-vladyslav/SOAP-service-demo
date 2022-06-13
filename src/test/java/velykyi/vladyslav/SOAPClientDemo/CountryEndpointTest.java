package velykyi.vladyslav.SOAPClientDemo;

import io.spring.guides.gs_producing_web_service.*;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import velykyi.vladyslav.SOAPClientDemo.controller.CountryEndpoint;
import velykyi.vladyslav.SOAPClientDemo.repository.CountryRepository;

import static java.lang.String.format;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

@SpringBootTest
public class CountryEndpointTest {

    private static final Logger log = LoggerFactory.getLogger(CountryEndpointTest.class);

    public static final String UKRAINE = "Ukraine";

    public static final String GB = "Great Britain%2312_0;:";

    public CountryRepository repository;

    public CountryEndpoint endpoint;

    @BeforeClass
    public void setUp(){
        repository = Mockito.mock(CountryRepository.class);
        endpoint = new CountryEndpoint(repository);
    }

    @Test(dataProvider = "get_countries")
    public void shouldGetCountryByName(String name, Country expected) {
        when(repository.findCountry(name)).thenReturn(expected);

        GetCountryRequest req = new GetCountryRequest();
        req.setName(name);

        GetCountryResponse response = endpoint.getCountry(req);

        assertNotNull(response);

        Country actual = response.getCountry();

        assertCountries(expected, actual);
    }

    @Test(dataProvider = "get_countries")
    public void shouldDeleteCountryByName(String name, Country expected) {
        when(repository.removeCountry(name)).thenReturn(expected);

        DeleteCountryRequest req = new DeleteCountryRequest();
        req.setName(name);

        DeleteCountryResponse response = endpoint.deleteCountry(req);

        assertNotNull(response);

        Country actual = response.getCountry();

        assertCountries(expected, actual);
    }

    @Test(dataProvider = "get_countries")
    public void shouldCreateCountry(String name, Country expected) {
       when(repository.addCountry(name, expected.getCapital(), expected.getPopulation(), expected.getCurrency()))
               .thenReturn(expected);

        CreateCountryRequest req = new CreateCountryRequest();
        req.setName(name);
        req.setCapital(expected.getCapital());
        req.setPopulation(expected.getPopulation());
        req.setCurrency(expected.getCurrency());

        CreateCountryResponse response = endpoint.createCountry(req);

        assertNotNull(response);

        Country actual = response.getCountry();

        assertCountries(expected, actual);
    }

    @DataProvider(name = "get_countries")
    private Object[][] getDifferentCountries() {
        return new Object[][] {{UKRAINE, buildCountry(UKRAINE, "Kyiv", 50, Currency.UAN)},
                {UKRAINE, buildCountry(UKRAINE, "Kyiv", 0, Currency.EUR)},
                {GB, buildCountry(GB, "London", -60, Currency.GBP)}};
    }

    private void assertCountries(Country expected, Country actual) {
        log.info(format("Asserting actual %s, with expected %s", actual, expected));

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCapital(), actual.getCapital());
        assertEquals(expected.getCurrency(), actual.getCurrency());
        assertEquals(expected.getPopulation(), actual.getPopulation());
    }

    private Country buildCountry(String name, String capital, int population, Currency currency) {
        Country country = new Country();
        country.setName(name);
        country.setCapital(capital);
        country.setPopulation(population);
        country.setCurrency(currency);

        return country;
    }
}
