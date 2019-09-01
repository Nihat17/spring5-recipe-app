package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if(ingredient == null){
            return null;
        }

        final IngredientCommand command = new IngredientCommand();
        command.setId(ingredient.getId());
        command.setDescription(ingredient.getDescription());

        return command;
    }
}
