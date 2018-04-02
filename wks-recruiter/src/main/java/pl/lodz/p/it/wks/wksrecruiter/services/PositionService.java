package pl.lodz.p.it.wks.wksrecruiter.services;

import pl.lodz.p.it.wks.wksrecruiter.collections.Position;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

public interface PositionService {
    Position addPosition(Position position) throws WKSRecruiterException;

    boolean modifyPosition(String name, boolean value) throws WKSRecruiterException;

    Iterable<Position> getPositions();
}
