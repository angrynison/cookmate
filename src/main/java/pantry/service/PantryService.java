package pantry.service;

import global.type.IngredientCategory;
import pantry.Pantry;
import pantry.dto.PantryRequestDto;
import pantry.dto.PantryResponseDto;

import java.util.List;

public interface PantryService {

    PantryResponseDto.SummaryResponse getPantrySummary(Long memberId);
    Long savePantry(Long memebrId, PantryRequestDto.CreateRequest request);
    Long updatePantry(Long pantryId, PantryRequestDto.UpdateRequest request);
    void deletePantry(Long pantryId);
    List<Pantry> getPantryList(IngredientCategory category);
}
