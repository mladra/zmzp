package pl.lodz.p.it.wks.wksrecruiter.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableMongoRepositories(basePackages="pl.lodz.p.it.wks.wksrecruiter.repositories")
public class MongoConfig extends AbstractMongoConfiguration {

    private final Environment env;

    @Autowired
    public MongoConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public MongoClient mongoClient() { return new MongoClient("localhost", 27017); }
    @Override
    protected String getDatabaseName() { return "wks"; }
}
