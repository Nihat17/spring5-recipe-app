package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    private final IngredientToIngredientCommand toIngredientCommand;
    private final IngredientCommandToIngredient toIngredient;
    private final UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImplTest() {
        this.toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.toIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        this.toUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository, toIngredientCommand,
                toIngredient, unitOfMeasureRepository, toUnitOfMeasureCommand);
    }

    @Test
    public void findByRecipeIdAndIngredientIdTest() throws Exception{

        //GIVEN
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

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
    }

    @Test
    public void testUpdateIngredient(){

        //GIVEN
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(1L);

        recipe.addIngredient(ingredient);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        when(recipeRepository.save(anyObject())).thenReturn(recipe);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();

        Optional<UnitOfMeasure> uomOptional = Optional.of(unitOfMeasure);

        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(uomOptional);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setRecipeId(1L);
        ingredientCommand.setDescription("Some String");

        UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
        uom.setId(1L);
        uom.setDescription("Tablespoon");

        ingredientCommand.setUom(uom);
        //WHEN
        IngredientCommand returnedValue = ingredientService.updateIngredient(ingredientCommand);

        //THEN
        assertNotNull(returnedValue);
        assertEquals(ingredientCommand.getDescription(), returnedValue.getDescription());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(anyObject());
        verify(unitOfMeasureRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetListUom(){

        //GIVEN
        HashSet uomsData = new HashSet();
        uomsData.add(new UnitOfMeasure());

        when(unitOfMeasureRepository.findAll()).thenReturn(uomsData);

        //WHEN
        HashSet returnedValue = (HashSet) ingredientService.getListUom();

        //THEN
        assertNotNull(returnedValue);
        assertEquals(1, returnedValue.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}
