package ingredient;

import global.type.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient findByName(String name);

    // category 별 식재료 리스트반환
    @Query("SELECT i FROM Ingredient i WHERE i.ingredientCategory = :category")
    List<Ingredient> findByCategory(@Param("category") IngredientCategory category);
}
