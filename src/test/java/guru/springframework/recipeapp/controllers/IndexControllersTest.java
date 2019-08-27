package guru.springframework.recipeapp.controllers;

import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IndexControllersTest {

    @Mock
    RecipeServiceImpl recipeService;

    @Mock
    Model model;

    IndexControllers controllers;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        controllers = new IndexControllers(recipeService);
    }

    @Test
    public void getIndexPage() {

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> captor = ArgumentCaptor.forClass(Set.class);

        String viewName = controllers.getIndexPage(model);

        assertEquals("index", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), captor);

        Set<Recipe> setInController = captor.getValue();

        assertEquals(2, setInController.size());
    }
}
