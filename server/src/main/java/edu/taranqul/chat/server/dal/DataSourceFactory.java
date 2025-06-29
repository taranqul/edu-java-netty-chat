package edu.taranqul.chat.server.dal;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    public static DataSource create(String url, String user, String password) {
        try {
            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setUrl(url);
            ds.setUser(user);
            ds.setPassword(password);
            return ds;
        } catch (Exception e) {
            throw new RuntimeException("PostgreSQL cant init", e);
        }
    }
        
}