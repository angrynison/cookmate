package com.cookmate.ingredient.dto;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.domain.Ingredient;
import lombok.Builder;

public class IngredientResponseDto {

    @Builder
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


    public record ApiResponse<T>(
            boolean success,
            String message,
            T data
    ) {
        public static <T> ApiResponse<T> success(T data) {
            return new ApiResponse<>(true, "요청에 성공하였습니다.", data);
        }

        public static <T> ApiResponse<T> success(String message, T data) {
            return new ApiResponse<>(true, message, data);
        }
    }
}
