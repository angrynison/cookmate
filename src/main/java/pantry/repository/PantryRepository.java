package pantry.repository;

import global.type.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pantry.Pantry;

import java.time.LocalDate;
import java.util.List;

public interface PantryRepository extends JpaRepository<Pantry, Long> {

    Pantry findByName(String name);

    // SELECT COUNT(*) FROM pantry WHERE member_id = ?
    long countByMemberId(Long memberId);

    /*
     유통기한 3일남은 재료들을 count해서 반환
     JPQL
     */
    @Query("SELECT COUNT(p) FROM Pantry p WHERE p.member = :memebrId AND p.expiryDate <= :soonDate")
    long countBysoonDate(@Param("memberId") Long memberId, @Param("soonDate") LocalDate soonDate);

    // category 별 식재료 리스트반환
    @Query("SELECT i FROM Pantry i WHERE i.ingredientCategory = :category")
    List<Pantry> findByCategory(@Param("category") IngredientCategory category);
}
