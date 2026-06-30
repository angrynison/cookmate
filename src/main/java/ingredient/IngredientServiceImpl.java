package ingredient;

import global.type.IngredientCategory;

import java.util.List;

public class IngredientServiceImpl implements IngredientService {

    IngredientRepository ingredientRepository;


    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public Long saveIngredient(IngredientRequestDto.CreateRequest request) {
        Ingredient ingredient = Ingredient.create(
                request.name(),
                request.defaultExpiry(),
                request.frozenExpiry(),
                request.ambientExpiry(),
                request.refrigeratedExpiry(),
                request.ingredientCategory()
                );
        ingredientRepository.save(ingredient);
        return ingredient.getId();
    }

    @Override
    public Long updateIngredient(Long ingredientId, IngredientRequestDto.UpdateRequest request) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기본 재료입니다"));

        ingredient.update(
                request.name(),
                request.defaultExpiry(),
                request.frozenExpiry(),
                request.ambientExpiry(),
                request.refrigeratedExpiry(),
                request.ingredientCategory()
        );
        return ingredient.getId();
    }

    @Override
    public void deleteIngredient(Long pantryId) {
        ingredientRepository.deleteById(pantryId);
    }

    @Override
    public List<Ingredient> getIngredientList(IngredientCategory category) {
        return ingredientRepository.findByCategory(category);
    }
}
