package guru.springframework.recipeapp.services;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;
    private RecipeCommandToRecipe toRecipe;
    private RecipeToRecipeCommand toRecipeCommand;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe toRecipe,
                             RecipeToRecipeCommand toRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.toRecipe = toRecipe;
        this.toRecipeCommand = toRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");

        Set<Recipe> recipeSet = new HashSet<>();

        recipeRepository.findAll().iterator().forEachRemaining(recipeSet ::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if(!optionalRecipe.isPresent()) {
            throw new RuntimeException("Recipe isn't present");
        }

        return optionalRecipe.get();
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = toRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);

        if(detachedRecipe.equals(savedRecipe)){
            log.info("They r equal");
        }
        return toRecipeCommand.convert(savedRecipe);
    }
}
