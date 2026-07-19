package com.cookmate.ingredient.service;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.domain.Ingredient;
import com.cookmate.ingredient.dto.IngredientRequestDto;
import com.cookmate.ingredient.dto.IngredientResponseDto;

import java.util.List;

public interface IngredientService {
    Long saveIngredient(IngredientRequestDto.CreateRequest request);
    Long updateIngredient(Long ingredientId,IngredientRequestDto.UpdateRequest request);
    void deleteIngredient(Long pantryId);
    List<IngredientResponseDto.IngredientResponse> getIngredientList();
    List<IngredientResponseDto.IngredientResponse> getIngredientListByCategory(IngredientCategory category);
}
