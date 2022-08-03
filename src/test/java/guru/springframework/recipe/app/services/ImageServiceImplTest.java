package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceImplTest {
        @Mock
        RecipeRepository recipeRepository;
        ImageService imageService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageService=new ImageServiceImpl(recipeRepository);
    }
    @Test
    public void saveImageFile() throws IOException {
        long recipeId=1L;
        MultipartFile multipartFile=new MockMultipartFile("imagefile","testing.txt",
                "text/plain","this is a image upload test".getBytes(StandardCharsets.UTF_8));
        Recipe recipe=new Recipe().builder().id(recipeId).build();

        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipe));
        ArgumentCaptor<Recipe>argumentCaptor=ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(recipeId,multipartFile);

        Mockito.verify(recipeRepository,Mockito.times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe=argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length,savedRecipe.getImage().length);

    }
}