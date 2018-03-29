package pl.lodz.p.it.wks.wksrecruiter.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.lodz.p.it.wks.wksrecruiter.collections.InvalidToken;

public interface InvalidTokensRepository extends MongoRepository<InvalidToken, String> {

    boolean existsByValue(String value);

}
