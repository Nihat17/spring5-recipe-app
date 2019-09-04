package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientToIngredientCommand toIngredientCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository, toIngredientCommand);
    }

    @Test
    public void findByRecipeIdAndIngredientIdTest() throws Exception{

        //GIVEN
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient;
        ingredient = new Ingredient();
        ingredient.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient.setId(3L);

        recipe.addIngredient(ingredient);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //WHEN
        IngredientCommand returnedValue = ingredientService.findByRecipeIdAndIngredientId(1L,2L);

        //THEN
        assertNotNull(returnedValue);
        assertEquals(Long.valueOf(2), returnedValue.getId());
        assertEquals(Long.valueOf(1), returnedValue.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }
}
