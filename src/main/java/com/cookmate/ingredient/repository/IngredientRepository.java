package com.cookmate.ingredient.repository;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient findByName(String name);
    Boolean existsByName(String name);

    // category 별 식재료 리스트반환
    @Query("SELECT i FROM Ingredient i WHERE i.ingredientCategory = :category")
    List<Ingredient> findByCategory(@Param("category") IngredientCategory category);
}
