package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.app.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }


    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        Set<UnitOfMeasureCommand> unitOfMeasureCommandSet= StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(),false)
                .map(t -> unitOfMeasureToUnitOfMeasureCommand.convert(t)).collect(Collectors.toSet());

        return unitOfMeasureCommandSet;
    }
}
