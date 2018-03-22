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
    public MongoClient mongoClient() { return new MongoClient("localhost", 27017); }
    @Override
    protected String getDatabaseName() { return "wks"; }
}
