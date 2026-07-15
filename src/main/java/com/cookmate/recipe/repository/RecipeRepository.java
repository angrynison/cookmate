package com.cookmate.recipe.repository;

import com.cookmate.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
