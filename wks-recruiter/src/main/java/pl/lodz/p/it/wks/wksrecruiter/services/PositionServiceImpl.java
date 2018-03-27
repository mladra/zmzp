package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.wks.wksrecruiter.collections.Position;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.repositories.PositionsRepository;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionsRepository positionsRepository;

    @Autowired
    public PositionServiceImpl(PositionsRepository positionsRepository) {
        this.positionsRepository = positionsRepository;
    }

    @Override
    public Position addPosition(Position position) throws WKSRecruiterException {
        try {
            return positionsRepository.save(position);
        } catch (DuplicateKeyException e) {
            throw new WKSRecruiterException(new WKSRecruiterException.Error("NAME_NOT_UNIQUE", "Position with such name already exists. Try another one."));
        }
    }
}
