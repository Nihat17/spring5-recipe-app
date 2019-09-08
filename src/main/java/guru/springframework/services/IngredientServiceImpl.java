package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private RecipeRepository recipeRepository;
    private IngredientToIngredientCommand toIngredientCommand;
    private IngredientCommandToIngredient toIngredient;
    private UnitOfMeasureRepository uomRepository;
    private UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    @Autowired
    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand toIngredientCommand,
                                 IngredientCommandToIngredient toIngredient, UnitOfMeasureRepository uomRepository,
                                 UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand) {
        this.recipeRepository = recipeRepository;
        this.toIngredientCommand = toIngredientCommand;
        this.toIngredient = toIngredient;
        this.uomRepository = uomRepository;
        this.toUnitOfMeasureCommand = toUnitOfMeasureCommand;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            log.debug("Recipe isn't present, recipe id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> toIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            log.debug("Ingredient isn't present, ingredient id: " + ingredientId);
        }
        return ingredientCommandOptional.get();
    }

    @Transactional
    @Override
    public IngredientCommand saveOrUpdateIngredient(IngredientCommand ingredientCommand) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        Recipe recipe = new Recipe();

        if(recipeOptional.isPresent()){
            recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ing -> ing.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setUom(uomRepository
                        .findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM not found")));
            }
            else{
                Ingredient ingredient = toIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }
        }
        else{
            log.debug("Recipe with id " + ingredientCommand.getRecipeId() + " isn't present.");
            return new IngredientCommand();
        }

        Recipe savedRecipe = recipeRepository.save(recipe);
        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
                .stream().filter(ing -> ing.getId().equals(ingredientCommand.getId())).findFirst();

        if(!savedIngredientOptional.isPresent()){
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ing -> ing.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(ing -> ing.getUom().getId().equals(ingredientCommand.getUom().getId()))
                    .filter(ing -> ing.getAmount().equals(ingredientCommand.getAmount())).findFirst();
        }

        return toIngredientCommand.convert(savedIngredientOptional.get());
    }

    @Override
    public Set<UnitOfMeasureCommand> getListUom() {

        return StreamSupport.stream(uomRepository.findAll()
                .spliterator(), false)
                .map(toUnitOfMeasureCommand :: convert)
                .collect(Collectors.toSet());
    }
}
