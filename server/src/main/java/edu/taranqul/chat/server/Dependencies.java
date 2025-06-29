package edu.taranqul.chat.server;

import javax.sql.DataSource;

import edu.taranqul.chat.server.services.UserService;
import edu.taranqul.chat.server.repository.UserRepository;
import edu.taranqul.chat.server.dal.DataSourceFactory;

public class Dependencies {
    private final UserService UserService;

    public Dependencies() {
        DataSource ds = DataSourceFactory.create(Properties.POSTGRES_URl, Properties.POSTGRES_USER, Properties.POSTGRES_PASSWORD);
        UserRepository userRepository = new UserRepository();
       this.UserService = new UserService(ds, userRepository);
    }

    public UserService getUserService() {
        return UserService;
    }

}
