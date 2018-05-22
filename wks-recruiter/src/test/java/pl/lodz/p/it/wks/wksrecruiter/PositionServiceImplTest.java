package pl.lodz.p.it.wks.wksrecruiter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.lodz.p.it.wks.wksrecruiter.collections.Position;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.repositories.PositionsRepository;
import pl.lodz.p.it.wks.wksrecruiter.services.PositionService;
import pl.lodz.p.it.wks.wksrecruiter.services.PositionServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PositionServiceImplTest {

    private PositionService positionService;
    private PositionsRepository positionsRepository;

    @Before
    public void setUp() {
        positionsRepository = Mockito.mock(PositionsRepository.class);
        positionService = new PositionServiceImpl(positionsRepository);
    }

    @Test
    public void createPositionSuccessfully() throws WKSRecruiterException {
        when(positionsRepository.findPositionByName(eq("Foo"))).thenReturn(null);
        doAnswer(returnsFirstArg()).when(positionsRepository).save(any(Position.class));

        Position position = positionService.addPosition(new Position("Foo", true));
        assertNotNull(position);
        assertEquals("Foo", position.getName());
    }
}
