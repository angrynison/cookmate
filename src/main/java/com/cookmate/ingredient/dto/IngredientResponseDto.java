package com.cookmate.ingredient.dto;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.domain.Ingredient;

public class IngredientResponseDto {

    public record IngredientResponse(
            Long id,
            String name,
            Integer defaultExpiry,
            Integer frozenExpiry,
            Integer ambientExpiry,
            Integer refrigeratedExpiry,
            IngredientCategory ingredientCategory
    ){
        public static IngredientResponse from(Ingredient ingredient) {
            return new IngredientResponse(
                    ingredient.getId(),
                    ingredient.getName(),
                    ingredient.getDefaultExpiry(),
                    ingredient.getFrozenExpiry(),
                    ingredient.getAmbientExpiry(),
                    ingredient.getRefrigeratedExpiry(),
                    ingredient.getIngredientCategory()
            );
        }

    };
}
