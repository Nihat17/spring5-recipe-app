package guru.springframework.recipeapp.services;

import guru.springframework.recipeapp.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface RecipeService {
     Recipe getRecipe(String desc);
}
