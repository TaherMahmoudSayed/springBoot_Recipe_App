package guru.springframework.recipe.app.repositories;

import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure,Long> {
    Optional<UnitOfMeasure> findByUom(String uom );
}
