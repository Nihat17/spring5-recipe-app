package guru.springframework.services;

import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureServiceImpl uomService;

    UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

    @Mock
    UnitOfMeasureRepository uomRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uomService = new UnitOfMeasureServiceImpl(uomRepository, toUnitOfMeasureCommand);
    }

    @Test
    public void testGetListUom(){

        //GIVEN
        HashSet uomsData = new HashSet();
        uomsData.add(new UnitOfMeasure());

        when(uomRepository.findAll()).thenReturn(uomsData);

        //WHEN
        HashSet returnedValue = (HashSet) uomService.getListUom();

        //THEN
        assertNotNull(returnedValue);
        assertEquals(1, returnedValue.size());
        verify(uomRepository, times(1)).findAll();
    }
}