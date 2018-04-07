package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.wks.wksrecruiter.collections.Position;
import pl.lodz.p.it.wks.wksrecruiter.collections.Test;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.repositories.PositionsRepository;
import pl.lodz.p.it.wks.wksrecruiter.repositories.TestsRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    private final TestsRepository testsRepository;

    private final PositionsRepository positionsRepository;

    @Autowired
    public TestServiceImpl(TestsRepository testsRepository, PositionsRepository positionsRepository) {
        this.testsRepository = testsRepository;
        this.positionsRepository = positionsRepository;
    }

    @Override
    public Test addPositionsToTest(Collection<String> positionNames, String testId) throws WKSRecruiterException {
        Optional<Test> test = testsRepository.findById(testId);
        Collection<Position> positions = positionsRepository.findAllByNameIn(positionNames);
        if (positions.isEmpty()) {
            throw WKSRecruiterException.createPositionNotFoundException();
        }
        if (test.isPresent() && test.get().isActive()) {
            test.get().getPositions().addAll(positions);
            return testsRepository.save(test.get());
        } else {
            throw WKSRecruiterException.createTestNotFoundException();
        }
    }

    @Override
    public Test removePositionsFromTest(Collection<String> positionNames, String testId) throws WKSRecruiterException {
        Optional<Test> test = testsRepository.findById(testId);
        Collection<Position> positions = positionsRepository.findAllByNameIn(positionNames);
        if (positions.isEmpty() || positionNames.size() != positions.size()) {
            throw WKSRecruiterException.createPositionNotFoundException();
        }
        if (test.isPresent() && test.get().isActive()) {
            positions.forEach(position -> test.get().getPositions().removeIf(positionToRemove -> positionToRemove.getName().equals(position.getName())));
            return testsRepository.save(test.get());
        } else {
            throw WKSRecruiterException.createTestNotFoundException();
        }
    }

    @Override
    public Test deleteTest(String testId) throws WKSRecruiterException {
        Optional<Test> test = testsRepository.findById(testId);
        if (test.isPresent() && test.get().isActive()) {
            test.get().setActive(false);
            this.testsRepository.save(test.get());
            return test.get();
        } else {
            throw WKSRecruiterException.createTestNotFoundException();
        }
    }

    @Override
    public Iterable<Test> getTests() {
        return testsRepository.findAll();
    }
}
