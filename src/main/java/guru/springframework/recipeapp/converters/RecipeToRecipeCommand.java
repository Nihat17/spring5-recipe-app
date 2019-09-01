package guru.springframework.recipeapp.converters;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

   private CategoryToCategoryCommand toCategoryCommand;
   private NotesToNotesCommand toNotesCommand;
   private IngredientToIngredientCommand toIngredientCommand;

   @Autowired
   public RecipeToRecipeCommand(CategoryToCategoryCommand toCategoryCommand, NotesToNotesCommand toNotesCommand,
                                IngredientToIngredientCommand toIngredientCommand) {
        this.toCategoryCommand = toCategoryCommand;
        this.toNotesCommand = toNotesCommand;
        this.toIngredientCommand = toIngredientCommand;
   }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {

        if(recipe == null){
            return null;
        }

        RecipeCommand command = new RecipeCommand();

        command.setId(recipe.getId());
        command.setDescription(recipe.getDescription());
        command.setPrepTime(recipe.getPrepTime());
        command.setCookTime(recipe.getCookTime());
        command.setDirections(recipe.getDirections());
        command.setDifficulty(recipe.getDifficulty());
        command.setSource(recipe.getSource());
        command.setUrl(recipe.getUrl());
        command.setServings(recipe.getServings());

        if(recipe.getCategories() != null && recipe.getCategories().size() > 0){
            recipe.getCategories()
                    .forEach(category -> command.getCategories().add(toCategoryCommand.convert(category)));
        }

        if(recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
            recipe.getIngredients()
                    .forEach(ingredient -> command.getIngredients().add(toIngredientCommand.convert(ingredient)));
        }

        command.setNotes(toNotesCommand.convert(recipe.getNotes()));

        return command;
    }
}
