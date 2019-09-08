package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService, ingredientService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        //GIVEN
        RecipeCommand command = new RecipeCommand();
        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(command);

        //WHEN
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //THEN
        verify(recipeService, times(1)).findRecipeCommandById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception{
        //GIVEN
        IngredientCommand command = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);

        //WHEN
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        //THEN
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    public void testUpdateIngredient() throws Exception{
        //GIVEN
        IngredientCommand command = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);

        HashSet uomList = new HashSet<>();

        UnitOfMeasureCommand command1 = new UnitOfMeasureCommand();
        command1.setId(1L);

        UnitOfMeasureCommand command2 = new UnitOfMeasureCommand();
        command2.setId(2L);

        uomList.add(command1);
        uomList.add(command2);

        when(ingredientService.getListUom()).thenReturn(uomList);

        //WHEN
        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        //THEN
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
        verify(ingredientService, times(1)).getListUom();
    }

    @Test
    public void testSaveOrUpdate() throws Exception{

        IngredientCommand command = new IngredientCommand();
        command.setId(1L);
        command.setRecipeId(1L);

        when(ingredientService.saveOrUpdateIngredient(anyObject())).thenReturn(command);

        mockMvc.perform(post("/recipe/1/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/1/show"));

        //THEN
        verify(ingredientService, times(1)).saveOrUpdateIngredient(anyObject());
    }

    @Test
    public void testCreateIngredient() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        UnitOfMeasureCommand uom1 = new UnitOfMeasureCommand();
        UnitOfMeasureCommand uom2 = new UnitOfMeasureCommand();
        HashSet uomList = new HashSet();
        uomList.add(uom1);
        uomList.add(uom2);

        when(ingredientService.getListUom()).thenReturn(uomList);

        mockMvc.perform(get("/recipe/1/ingredient/new")
                .param("recipeId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipeService, times(1)).findRecipeCommandById(anyLong());
        verify(ingredientService, times(1)).getListUom();
    }

}
