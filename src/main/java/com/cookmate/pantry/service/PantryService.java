package com.cookmate.pantry.service;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.pantry.dto.PantryRequestDto;
import com.cookmate.pantry.dto.PantryResponseDto;

import java.util.List;

public interface PantryService {

    PantryResponseDto.SummaryResponse getPantrySummary(Long memberId);
    Long createPantry(Long memebrId, PantryRequestDto.CreateRequest request);
    Long updatePantry(Long pantryId, PantryRequestDto.UpdateRequest request);
    void deletePantry(Long pantryId);
    List<PantryResponseDto.PantryResponse> getPantryList(Long memberId, IngredientCategory category);
}
