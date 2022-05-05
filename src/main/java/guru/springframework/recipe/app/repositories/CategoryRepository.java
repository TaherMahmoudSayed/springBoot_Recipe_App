package guru.springframework.recipe.app.repositories;

import guru.springframework.recipe.app.domain.Category;
import guru.springframework.recipe.app.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository  extends CrudRepository<Category,Long> {

    Optional<Category> findByDescription(String description);
}
