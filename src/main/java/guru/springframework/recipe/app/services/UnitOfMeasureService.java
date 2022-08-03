package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.domain.UnitOfMeasure;

import java.util.List;
import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand>listAllUoms();
}
