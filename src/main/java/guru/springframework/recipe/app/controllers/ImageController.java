package guru.springframework.recipe.app.controllers;

import guru.springframework.recipe.app.commands.RecipeCommand;
import guru.springframework.recipe.app.services.ImageService;
import guru.springframework.recipe.app.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String GetImageUploadForm(@PathVariable long recipeId, Model model){
        model.addAttribute("recipe",recipeService.findRecipeCommandById(recipeId));
        return "recipe/imageUploadForm";
    }
    @PostMapping("/recipe/{recipeId}/image")
    public String PostImage(@PathVariable long recipeId, @RequestParam("imagefile")MultipartFile file){
        imageService.saveImageFile(recipeId,file);
        return "redirect:/recipe/"+recipeId+"/show";
    }

    @GetMapping("/recipe/{recipeId}/recipeimage")
    public void RenderImgFromDB(@PathVariable long recipeId, HttpServletResponse response)throws IOException{
        RecipeCommand recipeCommand=recipeService.findRecipeCommandById(recipeId);

        byte[] imgArr=new byte[recipeCommand.getImg().length];
        int i=0;
        for (Byte wrappeedImg:recipeCommand.getImg()){
            imgArr[i++]=wrappeedImg;

        }
        response.setContentType("image/jpeg");
        InputStream inputStream=new ByteArrayInputStream(imgArr);
        IOUtils.copy(inputStream,response.getOutputStream());
    }
}
