package guru.springframework.recipe.app.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import guru.springframework.recipe.app.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.app.domain.UnitOfMeasure;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if(unitOfMeasure==null)
            return null;
        final UnitOfMeasureCommand command=new UnitOfMeasureCommand();
        command.setId(unitOfMeasure.getId());
        command.setUom(unitOfMeasure.getUom());
        return command;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
