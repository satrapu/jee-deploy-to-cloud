package ro.satrapu.jeedeploytocloud;

import io.codearte.jfairy.Fairy;

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
        generatePersons(Fairy.create(), MAX_AMOUNT_OF_PERSONS_TO_GENERATE);
    }

    private void generatePersons(Fairy fairy, int maxAmount) {
        IntStream.rangeClosed(1, MAX_AMOUNT_OF_PERSONS_TO_GENERATE).forEach(index -> {
            io.codearte.jfairy.producer.person.Person generatedPerson = fairy.person();

            Person person = new Person();
            person.setFirstName(generatedPerson.firstName());
            person.setMiddleName(generatedPerson.middleName());
            person.setLastName(generatedPerson.lastName());

            entityManager.persist(person);
        });
    }
}
