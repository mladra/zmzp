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
            throw WKSRecruiterException.createException("NAME_NOT_UNIQUE", "Position with such name already exists. Try another one.");
        }
    }

    @Override
    public boolean modifyPosition(String name, boolean value) throws WKSRecruiterException {
        Position position = positionsRepository.findPositionByName(name);
        if (position == null) {
            throw WKSRecruiterException.createPositionNotFoundException();
        }
        position.setActive(value);
        return positionsRepository.save(position).isActive();
    }

    @Override
    public Iterable<Position> getPositions() {
        return positionsRepository.findAll();
    }
}
