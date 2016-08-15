package ro.satrapu.jeedeploytocloud;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourceArchive;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Bootstraps WildFly Swarm.
 */
public class WildFlySwarmBoostrapper {
    private static final String ENVIRONMENT_VARIABLE_DATABASE_URL = "DATABASE_URL";
    private static final String POSTGRESQL_JDBC_PREFIX = "jdbc:postgresql://";
    private static final String POSTGRESQL_JDBC_DRIVER_NAME = "postgresql";
    private static final String POSTGRESQL_DATASOURCE_NAME = "HerokuDS";

    /**
     * Creates a new instance of the {@link WildFlySwarmBoostrapper} class.
     */
    private WildFlySwarmBoostrapper() {
    }

    /**
     * Starts a WildFly Swarm container.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) throws Exception {
        Container container = new Container(args);
        container.start();

        //ensure the Heroku datasource is deployed before the JEE application, so that the latter's persistence unit
        // will be able to find a suitable datasource
        container.deploy(buildDatasourceArchive(System.getenv(ENVIRONMENT_VARIABLE_DATABASE_URL)));

        //deploy the JEE 7 application, containing configured Swarm fractions like CDI, EJB, etc.
        container.deploy(container.createDefaultDeployment());
    }

    /**
     * Creates a {@link DatasourceArchive} instance pointing to the Heroku managed PostgreSQL database.
     *
     * @param herokuDatabaseUrl The URL pointing to the Heroku managed PostgreSQL database - see more
     *                          <a href="https://devcenter.heroku.com/articles/connecting-to-relational-databases-on-heroku-with-java#using-the-database_url-in-plain-jdbc">here</a>
     */
    private static DatasourceArchive buildDatasourceArchive(String herokuDatabaseUrl) throws URISyntaxException {
        URI dbUri = new URI(herokuDatabaseUrl);
        String userInfo = dbUri.getUserInfo();
        String userName = null;
        String password = null;

        if (userInfo != null) {
            userName = userInfo.split(":")[0];
            password = userInfo.split(":")[1];
        }

        String dbUrl =
                POSTGRESQL_JDBC_PREFIX
                        + dbUri.getHost()
                        + ":" + dbUri.getPort()
                        + dbUri.getPath()
                        + (dbUri.getQuery() != null ? "?" + dbUri.getQuery() : "");

        String finalUserName = userName;
        String finalPassword = password;

        DatasourceArchive datasourceArchive = ShrinkWrap.create(DatasourceArchive.class);
        datasourceArchive.dataSource(POSTGRESQL_DATASOURCE_NAME, dataSource -> {
            dataSource.connectionUrl(dbUrl);
            dataSource.driverName(POSTGRESQL_JDBC_DRIVER_NAME);

            if (finalUserName != null) {
                dataSource.userName(finalUserName);
            }

            if (finalPassword != null) {
                dataSource.password(finalPassword);
            }
        });

        return datasourceArchive;
    }
}
