package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.UnitOfMeasure;

import java.util.Set;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientCommand updateIngredient(IngredientCommand ingredientCommand);
    Set<UnitOfMeasure> getListUom();
}
