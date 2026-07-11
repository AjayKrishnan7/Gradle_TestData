//package com.gradleproject.data;
//
//import io.qameta.allure.Description;
//import io.qameta.allure.Severity;
//import io.qameta.allure.SeverityLevel;
//import io.qameta.allure.Story;
//import org.flywaydb.core.Flyway;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.mysql.MySQLContainer;
//import org.testcontainers.utility.DockerImageName;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@Testcontainers(disabledWithoutDocker = true)
// class OrderTestIT {
//     @Container
//     static MySQLContainer mySQL = new MySQLContainer(DockerImageName.parse("mysql:8.0"))
//             .withDatabaseName("retail_test")
//             .withUsername("root")
//             .withPassword("root");
//
//     static OrderRepository repository;
//     static OrderFactory factory;
//
//    //  @BeforeAll
//    //  static void migrateSchema(){
//    //      Flyway.configure()
//    //              .dataSource(mySQL.getJdbcUrl(), mySQL.getUsername(), mySQL.getPassword())
//    //              .locations("classpath:db/migration")
//    //              .load()
//    //              .migrate();
//
//    //      repository = new OrderRepository(mySQL.getJdbcUrl(), mySQL.getUsername(), mySQL.getPassword());
//    //      factory = new OrderFactory(repository);
//    //  }
//
//    @BeforeAll
//    static void migrateSchema() throws Exception {
//
//        System.out.println("===== TESTCONTAINERS DEBUG =====");
//        System.out.println("JDBC URL  : " + mySQL.getJdbcUrl());
//        System.out.println("Username  : " + mySQL.getUsername());
//        System.out.println("Password  : " + mySQL.getPassword());
//        System.out.println("Container : " + mySQL.getContainerName());
//        System.out.println("================================");
//
//        Flyway flyway = Flyway.configure()
//                .dataSource(
//                        mySQL.getJdbcUrl(),
//                        mySQL.getUsername(),
//                        mySQL.getPassword())
//                .locations("classpath:db/migration")
//                .load();
//
//        var result = flyway.migrate();
//
//        System.out.println("Flyway migrations executed : "
//                + result.migrationsExecuted);
//
//        try (Connection con = DriverManager.getConnection(
//                mySQL.getJdbcUrl(),
//                mySQL.getUsername(),
//                mySQL.getPassword());
//            Statement stmt = con.createStatement()) {
//
//            System.out.println("\n===== TABLES =====");
//
//            ResultSet rs = stmt.executeQuery("SHOW TABLES");
//
//            while (rs.next()) {
//                System.out.println(rs.getString(1));
//            }
//
//            System.out.println("==================");
//
//        }
//
//        repository = new OrderRepository(
//                mySQL.getJdbcUrl(),
//                mySQL.getUsername(),
//                mySQL.getPassword());
//
//        factory = new OrderFactory(repository);
//    }
//
//     @BeforeEach
//     void reset(){
//         repository.resetMutableTables();
//     }
//
//     @Test
//     void flywaySeedingReferenceDataButNoPerTestOrders(){
//         assertEquals(4,repository.referenceStatusCount());
//         assertEquals(0,repository.count());
//     }
//
//     @Test
//    void persistedBuilderDataAgainstIsolatedMysql(){
//         long id = factory.persisted(OrderBuilder.anOrder().qty(3));
//
//         assertTrue(id>0);
//         assertEquals(1,repository.count());
//     }
//
//    @Test
//    void countsOnlyPersistedTestOrders(){
//         factory.persisted(OrderBuilder.anOrder());
//         factory.persisted(OrderBuilder.anOrder().sku("SKU-2").qty(2));
//
//         assertEquals(2,repository.count());
//    }
//
//    @Test
//    void resetMakesTestOrderIndependent(){
//         assertEquals(0,repository.count());
//         factory.persisted(OrderBuilder.anOrder().refunded());
//
//         assertEquals(1,repository.count());
//         assertEquals(1,repository.countByStatus("REFUNDED"));
//    }
//
//
//    // FAILED TESTS//
//
//
//    @Test
//    void resetMakesTestOrderFailed(){
//        assertEquals(0,repository.count());
//        factory.persisted(OrderBuilder.anOrder().refunded());
//
//        assertEquals(1,repository.count());
//        assertEquals(1,repository.countByStatus("FAILED"));
//    }
//
//
//    @Test
//    void persistedBuilderDataAgainstIsolatedMysqlFailed(){
//        long id = factory.persisted(OrderBuilder.anOrder().qty(3));
//
//        assertFalse(id>0);
//        assertEquals(1,repository.count());
//    }
//
//
//    @Test
//    void countsOnlyPersistedTestOrdersFailed(){
//        factory.persisted(OrderBuilder.anOrder());
//        factory.persisted(OrderBuilder.anOrder().sku("SKU-10"));
//
//        assertEquals(2,repository.count());
//    }
//
//    @Test
//    @Story("Categories")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Test will brok")
//    void brokenTest(){
//        String Test = null;
//        Test.length();
//    }
//
//    @Test
//    @Story("Categories")
//    @Severity(SeverityLevel.CRITICAL)
//    @Disabled
//    @Description("Test will brok")
//    void skipped(){
//    }
//
//}



package com.gradleproject.data;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers(disabledWithoutDocker = true)
class OrderTestIT {

    private static final Logger log = LoggerFactory.getLogger(OrderTestIT.class);

    @Container
    static MySQLContainer mySQL = new MySQLContainer(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("retail_test")
            .withUsername("root")
            .withPassword("root");

    static OrderRepository repository;
    static OrderFactory factory;

    @BeforeAll
    static void migrateSchema() throws Exception {

        // Log TestContainers connection details for troubleshooting
        log.info("===== TESTCONTAINERS DEBUG =====");
        log.info("JDBC URL  : {}", mySQL.getJdbcUrl());
        log.info("Username  : {}", mySQL.getUsername());
        log.info("Password  : {}", mySQL.getPassword());
        log.info("Container : {}", mySQL.getContainerName());
        log.info("================================");

        // Execute Flyway database migrations before tests start
        log.trace("Starting Flyway migration");

        Flyway flyway = Flyway.configure()
                .dataSource(
                        mySQL.getJdbcUrl(),
                        mySQL.getUsername(),
                        mySQL.getPassword())
                .locations("classpath:db/migration")
                .load();

        var result = flyway.migrate();

        log.info("Flyway migrations executed : {}", result.migrationsExecuted);

        // Verify tables created by Flyway
        try (Connection con = DriverManager.getConnection(
                mySQL.getJdbcUrl(),
                mySQL.getUsername(),
                mySQL.getPassword());
             Statement stmt = con.createStatement()) {

            log.info("===== TABLES =====");

            ResultSet rs = stmt.executeQuery("SHOW TABLES");

            while (rs.next()) {
                log.info("Table : {}", rs.getString(1));
            }

            log.info("==================");

        }

        // Initialize repository and test data factory
        repository = new OrderRepository(
                mySQL.getJdbcUrl(),
                mySQL.getUsername(),
                mySQL.getPassword());

        factory = new OrderFactory(repository);

        log.info("Repository and Factory initialized successfully");
    }

    @BeforeEach
    void reset() {

        // Ensure each test starts with a clean state
        log.debug("Resetting mutable tables before test execution");

        repository.resetMutableTables();
    }

    @Test
    void flywaySeedingReferenceDataButNoPerTestOrders() {

        log.info("Validating Flyway seeded reference data");

        assertEquals(4, repository.referenceStatusCount());
        assertEquals(0, repository.count());

        log.info("Reference data validation completed");
    }

    @Test
    void persistedBuilderDataAgainstIsolatedMysql() {

        log.info("Persisting order with quantity 3");

        long id = factory.persisted(OrderBuilder.anOrder().qty(3));

        log.debug("Generated order id : {}", id);

        assertTrue(id > 0);
        assertEquals(1, repository.count());

        log.info("Order persistence validation completed");
    }

    @Test
    void countsOnlyPersistedTestOrders() {

        log.info("Creating multiple test orders");

        factory.persisted(OrderBuilder.anOrder());
        factory.persisted(OrderBuilder.anOrder().sku("SKU-2").qty(2));

        assertEquals(2, repository.count());

        log.info("Order count validation completed");
    }

    @Test
    void resetMakesTestOrderIndependent() {

        log.info("Validating test isolation and reset behavior");

        assertEquals(0, repository.count());

        factory.persisted(OrderBuilder.anOrder().refunded());

        assertEquals(1, repository.count());
        assertEquals(1, repository.countByStatus("REFUNDED"));

        log.info("Reset behavior validation completed");
    }


    // FAILED TESTS//

    @Test
    void resetMakesTestOrderFailed() {

        log.info("Executing intentional failing test: resetMakesTestOrderFailed");

        assertEquals(0, repository.count());
        factory.persisted(OrderBuilder.anOrder().refunded());

        assertEquals(1, repository.count());
        assertEquals(1, repository.countByStatus("FAILED"));
    }


    @Test
    void persistedBuilderDataAgainstIsolatedMysqlFailed() {

        log.info("Executing intentional failing test: persistedBuilderDataAgainstIsolatedMysqlFailed");

        long id = factory.persisted(OrderBuilder.anOrder().qty(3));

        log.debug("Generated order id : {}", id);

        assertFalse(id > 0);
        assertEquals(1, repository.count());
    }


    @Test
    void countsOnlyPersistedTestOrdersFailed() {

        log.info("Executing intentional failing test: countsOnlyPersistedTestOrdersFailed");

        factory.persisted(OrderBuilder.anOrder());
        factory.persisted(OrderBuilder.anOrder().sku("SKU-10"));

        assertEquals(2, repository.count());
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test will brok")
    void brokenTest() {

        log.error("Executing intentionally broken test");

        String Test = null;
        Test.length();
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Disabled
    @Description("Test will brok")
    void skipped() {

        log.info("Skipped test invoked (JUnit @Disabled)");
    }

}