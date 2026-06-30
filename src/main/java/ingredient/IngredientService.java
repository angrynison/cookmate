package ingredient;

import global.type.IngredientCategory;

import java.util.List;

public interface IngredientService {
    Long saveIngredient(IngredientRequestDto.CreateRequest request);
    Long updateIngredient(Long ingredientId,IngredientRequestDto.UpdateRequest request);
    void deleteIngredient(Long pantryId);
    List<Ingredient> getIngredientList(IngredientCategory category);
}
