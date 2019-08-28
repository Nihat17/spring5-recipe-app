package guru.springframework.recipeapp.bootstrap;

import guru.springframework.recipeapp.domain.Ingredient;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import guru.springframework.recipeapp.repositories.CategoryRepository;
import guru.springframework.recipeapp.repositories.RecipeRepository;
import guru.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class DevBootStrap implements ApplicationListener<ContextRefreshedEvent> {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository uomRepository;

    @Autowired
    public DevBootStrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository uomRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.uomRepository = uomRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {

        Recipe guacamole = new Recipe();

        guacamole.setDescription("Guacamole");
        guacamole.setIngredients(get_ingredients(guacamole));

        recipeRepository.save(guacamole);

        Recipe tacos = new Recipe();

        tacos.setDescription("Tacos");
        tacos.setIngredients(get_ingredients(tacos));

        recipeRepository.save(tacos);

    }

    private Set<Ingredient> get_ingredients(Recipe recipe) {

        Set<Ingredient> ingredients = new HashSet<>();

        if(recipe.getDescription().equals("Guacamole")) {

            Ingredient twoRipeAvocados = new Ingredient("2 ripe avocados", BigDecimal.valueOf(2),
                    get_uom("Ripe"), recipe);

            Ingredient teaSpoonSalt = new Ingredient("1/2 teaspoon Kosher salt", BigDecimal.valueOf(0.5),
                    get_uom("Teaspoon"), recipe);

            Ingredient limeJuice = new Ingredient("1 teaspoon of fresh lime or lemon juice", BigDecimal.valueOf(1),
                    get_uom("Teaspoon"), recipe);

            Ingredient mincedOnion = new Ingredient("2 Tbsp to 1/4 cup of minced red onion", BigDecimal.valueOf(.25),
                    get_uom("Tablespoon"), recipe);

            Ingredient serranoChiles = new Ingredient("1-2 serrano chiles, stems and seeds removed", BigDecimal
                    .valueOf(0.5), get_uom("Teaspoon"), recipe);

            Ingredient cilantro = new Ingredient("2 tablespoons cilantro", BigDecimal.valueOf(2),
                    get_uom("Tablespoon"), recipe);

            Ingredient blackPepper = new Ingredient("A dash grated black pepper", BigDecimal.valueOf(1),
                    get_uom("Dash"), recipe);

            Ingredient ripeTomato = new Ingredient("1/2 ripe tomato, seeds and pulp removed, chopped", BigDecimal
                    .valueOf(.5), get_uom("Ripe"), recipe);

            ingredients.add(twoRipeAvocados);
            ingredients.add(teaSpoonSalt);
            ingredients.add(limeJuice);
            ingredients.add(mincedOnion);
            ingredients.add(serranoChiles);
            ingredients.add(cilantro);
            ingredients.add(blackPepper);
            ingredients.add(ripeTomato);
        }

        if(recipe.getDescription().equals("Tacos")) {

            Ingredient chiliPowder = new Ingredient("2 tablespoons ancho chili powder", BigDecimal.valueOf(2),
                    get_uom("Tablespoon"), recipe);

            Ingredient driedOregano = new Ingredient("1 teaspoon dried oregano", BigDecimal.valueOf(1),
                    get_uom("Teaspoon"), recipe);

            Ingredient driedCumin = new Ingredient("1 teaspoon dried cumin", BigDecimal.valueOf(1),
                    get_uom("Teaspoon"), recipe);

            Ingredient sugar = new Ingredient("1 teaspoon sugar", BigDecimal.valueOf(1),
                    get_uom("Teaspoon"), recipe);

            Ingredient salt = new Ingredient("1/2 teaspoon salt", BigDecimal.valueOf(.5),
                    get_uom("Teaspoon"), recipe);

            Ingredient cloveGarlic = new Ingredient("1 clove garlic, finely chopped", BigDecimal.valueOf(1),
                    get_uom("None"), recipe);

            Ingredient orangeZest = new Ingredient("1 tablespoon finely grated orange zest", BigDecimal.valueOf(1),
                    get_uom("Tablespoon"), recipe);

            Ingredient orangeJuice = new Ingredient("3 tablespoons fresh-squeezed orange juice", BigDecimal
                    .valueOf(3), get_uom("Tablespoon"), recipe);

            Ingredient oliveOil = new Ingredient("2 tablespoons olive oil", BigDecimal
                    .valueOf(2), get_uom("Tablespoon"), recipe);

            Ingredient chickenThigh = new Ingredient("4 to 6 skinless, boneless chicken thighs (1 1/4 pounds)",
                    BigDecimal.valueOf(5), get_uom("None"), recipe);

            ingredients.add(chiliPowder);
            ingredients.add(driedOregano);
            ingredients.add(driedCumin);
            ingredients.add(sugar);
            ingredients.add(salt);
            ingredients.add(cloveGarlic);
            ingredients.add(orangeZest);
            ingredients.add(oliveOil);
            ingredients.add(orangeJuice);
            ingredients.add(chickenThigh);
        }
            return ingredients;
    }

    private UnitOfMeasure get_uom(String descr) {

        if(uomRepository.existsUnitOfMeasureByDescription(descr)){
            return uomRepository.findByDescription(descr).get();
        }

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(descr);
        uomRepository.save(uom);

        return uom;
    }

}
