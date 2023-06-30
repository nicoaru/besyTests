package com.asj.besyTest.besyRefereneces;

import com.asj.besyTest.model.entities.ChuckNorrisJoke;
import com.besysoft.besyreferences.BesyReferences;
import com.besysoft.besyreferences.entities.PostgreSQLDatabase;
import com.besysoft.besyreferences.entities.WebServiceRest;
import com.besysoft.besyreferences.exception.BesyReferencesException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReferencesServices implements CommandLineRunner {
    private final RestTemplate restTemplate = new RestTemplate();

    public ChuckNorrisJoke webServiceChuckNorrisCall() {
        System.out.println("Entro al webServiceCall");
        BesyReferences besyReferences = new BesyReferences();
        ChuckNorrisJoke chuckNorrisJoke = null;

        try {
            WebServiceRest wsr = (WebServiceRest) besyReferences.getExternalResource("chuckNorrisWebService");
            String webServiceChuckNorrisUrl = wsr.getUrl();
            System.out.println("chuckNorris webServiceUrl: "+webServiceChuckNorrisUrl);

            ResponseEntity<ChuckNorrisJoke> chuckNorrisJokeResponse = restTemplate.getForEntity(webServiceChuckNorrisUrl, ChuckNorrisJoke.class);

            System.out.println("chucknorris respones: "+chuckNorrisJokeResponse);
            chuckNorrisJoke = chuckNorrisJokeResponse.getBody();

            System.out.println(chuckNorrisJoke);
            return chuckNorrisJoke;
        } catch (Exception e) {
            System.out.println("Se produjo un error consultando BesyReferences: " + e);
            throw new RuntimeException("Error procesando besy webService");
        }
    }


    public DataSource dataSource() {
        BesyReferences besyReferences = new BesyReferences();
        DataSource dataSource = null;

        try {
            PostgreSQLDatabase postgreDB = (PostgreSQLDatabase) besyReferences.getExternalResource("besyDB");
            return (DataSource) postgreDB.getDataSource();

            /*PoolProperties poolProperties = new PoolProperties();
            poolProperties.setUrl(postgreDB.getUrl());
            poolProperties.setUsername(postgreDB.getUsername());
            poolProperties.setPassword(postgreDB.getPassword());
            poolProperties.setDriverClassName(postgreDB.getDriver());

            // Inicializa el driver
            Class.forName(postgreDB.getDriver());

            poolProperties.setTestOnBorrow(true);
            poolProperties.setValidationQuery("SELECT * FROM state");

            dataSource = new DataSource(poolProperties);*/

            //System.out.println("Connecting to database: "+ postgreDB.getUsername());

        }
        catch (BesyReferencesException e) {
            System.out.println("Se produjo un error consultando BesyReferences"+ e);
        }
        /*catch (ClassNotFoundException e) {
            System.out.println("Error en la inicialización de la base de datos"+ e);
        }*/
        catch (Exception e) {
            System.out.println("Error en la inicialización de la base de datos"+ e);
        }

        return dataSource;

    };


    @Override
    public void run(String... args) throws Exception {
        DataSource dataSource = dataSource();
        System.out.println("data source: " + dataSource);
    }

}
