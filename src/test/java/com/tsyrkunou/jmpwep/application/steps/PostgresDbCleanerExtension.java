//package com.tsyrkunou.jmpwep.application.steps;
//
///*
// * Copyright © 2021 EPAM Systems, Inc. All Rights Reserved. All information contained herein is, and remains the
// * property of EPAM Systems, Inc. and/or its suppliers and is protected by international intellectual
// * property law. Dissemination of this information or reproduction of this material is strictly forbidden,
// * unless prior written permission is obtained from EPAM Systems, Inc
// */
//
//import static com.tsyrkunou.jmpwep.application.AbstractTest.POSTGRES;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.junit.jupiter.api.extension.BeforeEachCallback;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.postgresql.ds.PGSimpleDataSource;
//
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class PostgresDbCleanerExtension implements BeforeEachCallback {
//
//    @Override
//    public void beforeEach(ExtensionContext context) {
//        var dataSource = getDataSourceFromTestContainer();
//        cleanDatabase(dataSource);
//    }
//
//    private DataSource getDataSourceFromTestContainer() {
//        var pgSimpleDataSource = new PGSimpleDataSource();
//        pgSimpleDataSource.setUrl(POSTGRES.getJdbcUrl());
//        pgSimpleDataSource.setUser(POSTGRES.getUsername());
//        pgSimpleDataSource.setPassword(POSTGRES.getPassword());
//        return pgSimpleDataSource;
//    }
//
//    private void cleanDatabase(DataSource dataSource) {
//        try (Connection connection = dataSource.getConnection()) {
//            connection.setAutoCommit(false);
//            var tablesToClean = loadTablesToClean(connection);
//            cleanTablesData(tablesToClean, connection);
//            connection.commit();
//        } catch (SQLException e) {
//            log.error(String.format("Failed to clean database due to error: %s", e.getMessage()));
//            e.printStackTrace();
//        }
//    }
//
//    @SneakyThrows
//    private List<TableData> loadTablesToClean(Connection connection) {
//        var databaseMetaData = connection.getMetaData();
//        var resultSet = databaseMetaData.getTables(
//                connection.getCatalog(), null, null, new String[] {"TABLE"});
//
//        var tablesToClean = new ArrayList<TableData>();
//        while (resultSet.next()) {
//            var table = new TableData(resultSet.getString("TABLE_SCHEM"), resultSet.getString("TABLE_NAME"));
//            tablesToClean.add(table);
//        }
//        return tablesToClean;
//    }
//
//    @SneakyThrows
//    private void cleanTablesData(List<TableData> tablesNames, Connection connection) {
//        if (tablesNames.isEmpty()) {
//            return;
//        }
//        var stringBuilder = new StringBuilder("TRUNCATE ");
//        for (int i = 0; i < tablesNames.size(); i++) {
//            if (i == 0) {
//                stringBuilder.append(tablesNames.get(i).fullyQualifiedTableName);
//            } else {
//                stringBuilder.append(", ").append(tablesNames.get(i).fullyQualifiedTableName);
//            }
//        }
//        connection.prepareStatement(stringBuilder.toString()).execute();
//    }
//
//    @Getter
//    @Setter
//    @EqualsAndHashCode
//    static class TableData {
//        private String schema;
//        private String name;
//        private String fullyQualifiedTableName;
//
//        public TableData(String schema, String name) {
//            this.schema = schema;
//            this.name = name;
//            this.fullyQualifiedTableName = schema != null ? schema + "." + name : name;
//        }
//
//        public TableData(String name) {
//            this("public", name);
//        }
//    }
//}
