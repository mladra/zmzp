package pl.lodz.p.it.wks.wksrecruiter.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages="pl.lodz.p.it.wks.wksrecruiter.repositories")
public class MongoConfig extends AbstractMongoConfiguration {
    @Autowired
    private Environment env;
    @Override
    public MongoClient mongoClient() { return new MongoClient(env.getProperty("spring.data.mongodb.host"), Integer.parseInt(env.getProperty("spring.data.mongodb.port"))); }
    @Override
    protected String getDatabaseName() { return env.getProperty("spring.data.mongodb.database"); }
}
