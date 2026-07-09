package ingredient.service;

import global.type.IngredientCategory;
import ingredient.domain.Ingredient;
import ingredient.dto.IngredientRequestDto;

import java.util.List;

public interface IngredientService {
    Long saveIngredient(IngredientRequestDto.CreateRequest request);
    Long updateIngredient(Long ingredientId,IngredientRequestDto.UpdateRequest request);
    void deleteIngredient(Long pantryId);
    List<Ingredient> getIngredientList();
    List<Ingredient> getIngredientListByCategory(IngredientCategory category);
}
