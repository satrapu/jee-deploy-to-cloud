package ro.satrapu.jeedeploytocloud;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.IntStream;

/**
 * Initializes the underlying database with a number of entities.
 */
@Startup
@Singleton
@SuppressWarnings("unused")
public class DatabaseInitializer {
    private static final int MAX_AMOUNT_OF_PERSONS_TO_GENERATE = 5;

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void initializeDatabase() {
        generatePersons(MAX_AMOUNT_OF_PERSONS_TO_GENERATE);
    }

    private void generatePersons(int maxAmount) {
        IntStream.rangeClosed(1, MAX_AMOUNT_OF_PERSONS_TO_GENERATE).forEach(index -> {
            Person person = new Person();
            person.setFirstName("FirstName_" + index);
            person.setMiddleName("MiddleName_" + index);
            person.setLastName("LastName_" + index);

            entityManager.persist(person);
        });
    }
}
