package guru.springframework.recipe.app.repositories;

import guru.springframework.recipe.app.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UnitOfMeasureRepositoryIntegrationTest {
    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;
    @BeforeEach
    void setUp() {
    }

    @Test
    void findByUom() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUom("Teaspoon");
        assertEquals("Teaspoon",unitOfMeasure.get().getUom());
    }
    @Test
    @DirtiesContext
    void AddUom() {
        UnitOfMeasure u1=new UnitOfMeasure();
        u1.setId(1L);
        u1.setUom("test");
        u1.setIngredient(null);
       unitOfMeasureRepository.save(u1);
        assertEquals("test", unitOfMeasureRepository.findByUom("test").get().getUom());
    }

}