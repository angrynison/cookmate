package com.cookmate.ingredient.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.cookmate.global.type.IngredientCategory;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class IngredientRequestDto {

    private IngredientRequestDto() {}

    public record CreateRequest(

            @NotBlank(message = "기본재료 이름은 필수입니다")
            String name,

            Integer defaultExpiry,
            Integer frozenExpiry,
            Integer ambientExpiry,
            Integer refrigeratedExpiry,
            
            @NotNull(message = "카테고리 선택은 필수입니다")
            IngredientCategory ingredientCategory
    ) {}

    public record UpdateRequest(

            String name,

            Integer defaultExpiry,
            Integer frozenExpiry,
            Integer ambientExpiry,
            Integer refrigeratedExpiry,

            IngredientCategory ingredientCategory
    ) {}


}
