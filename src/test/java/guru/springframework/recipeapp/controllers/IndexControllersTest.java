package guru.springframework.recipeapp.controllers;

import guru.springframework.recipeapp.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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
        String viewName = controllers.getIndexPage(model);

        assertEquals("index", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }
}
