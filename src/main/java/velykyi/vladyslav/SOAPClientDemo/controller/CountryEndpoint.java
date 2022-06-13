package velykyi.vladyslav.SOAPClientDemo.controller;

import io.spring.guides.gs_producing_web_service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import velykyi.vladyslav.SOAPClientDemo.repository.CountryRepository;

import static java.lang.String.format;

@Endpoint
public class CountryEndpoint {

    private static final Logger log = LoggerFactory.getLogger(CountryEndpoint.class);

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private final CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        log.info(format("Getting country for given request %s", request));

        GetCountryResponse response = new GetCountryResponse();
        Country country = countryRepository.findCountry(request.getName());
        response.setCountry(country);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCountryRequest")
    @ResponsePayload
    public CreateCountryResponse createCountry(@RequestPayload CreateCountryRequest request) {
        log.info(format("Creating country for given request %s", request));

        CreateCountryResponse response = new CreateCountryResponse();
        response.setCountry(countryRepository.addCountry(request.getName(),
                                                         request.getCapital(),
                                                         request.getPopulation(),
                                                         request.getCurrency()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCountryRequest")
    @ResponsePayload
    public DeleteCountryResponse deleteCountry(@RequestPayload DeleteCountryRequest request) {
        log.info(format("Deleting country for given request %s", request));

        DeleteCountryResponse response = new DeleteCountryResponse();
        response.setCountry(countryRepository.removeCountry(request.getName()));

        return response;
    }
}
