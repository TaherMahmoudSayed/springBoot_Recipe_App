package guru.springframework.recipe.app.services;

import guru.springframework.recipe.app.domain.Recipe;
import guru.springframework.recipe.app.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
       try {
           Recipe recipe=recipeRepository.findById(recipeId).orElseGet(null);
            Byte[] imgByteArr=new Byte[file.getBytes().length];
            int i=0;
            for (byte b : file.getBytes()){
                imgByteArr[i++]=b;
            }
            recipe.setImage(imgByteArr);
            recipeRepository.save(recipe);
       }catch (Exception e){
        log.error("something went wrong");
       }

    }
}
