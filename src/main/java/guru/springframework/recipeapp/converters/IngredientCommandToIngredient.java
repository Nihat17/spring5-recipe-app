package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if(ingredientCommand == null){
            return null;
        }

        final Ingredient ingredient = new Ingredient();

        ingredient.setId(ingredientCommand.getId());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setAmount(ingredientCommand.getAmount());

        return ingredient;
    }
}
