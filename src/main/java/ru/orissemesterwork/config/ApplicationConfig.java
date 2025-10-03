package ru.orissemesterwork.config;

public class ApplicationConfig {
    private static DatabaseConnectionProvider mainDatabaseConnectionProvider;

    public static DatabaseConnectionProvider getMainDatabaseConnectionProvider() {
        if (mainDatabaseConnectionProvider == null) {
            mainDatabaseConnectionProvider = new DatabaseConnectionProvider(DbConfig.URL, DbConfig.USER, DbConfig.PASSWORD);
        }
        return mainDatabaseConnectionProvider;
    }

    public static DatabaseConnectionProvider generateTestDatabaseConnectionProvider() {
        return new DatabaseConnectionProvider("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
    }
}
