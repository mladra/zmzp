package pl.lodz.p.it.wks.wksrecruiter.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;

import java.util.Optional;

public interface AccountsRepository extends MongoRepository<Account, String> {

    Optional<Account> findByLogin(String login);
}
