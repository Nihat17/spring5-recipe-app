package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl {

    private UnitOfMeasureRepository uomRepository;

    private UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    @Autowired
    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand) {
        this.uomRepository = uomRepository;
        this.toUnitOfMeasureCommand = toUnitOfMeasureCommand;
    }

    public Set<UnitOfMeasureCommand> getListUom() {

        return StreamSupport.stream(uomRepository.findAll()
                .spliterator(), false)
                .map(toUnitOfMeasureCommand :: convert)
                .collect(Collectors.toSet());
    }
}
