package ro.satrapu.jeedeploytocloud;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Stateless
public class PersistenceService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Person> getPersons() {
        List<Person> result = new ArrayList<>();
        result.addAll(entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList());
        return result;
    }
}
