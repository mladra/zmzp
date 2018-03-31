package pl.lodz.p.it.wks.wksrecruiter.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.lodz.p.it.wks.wksrecruiter.collections.Position;

public interface PositionsRepository extends MongoRepository<Position, String> {
    Position findPositionByName(String name);
}
