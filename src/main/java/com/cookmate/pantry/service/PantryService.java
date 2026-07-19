package com.cookmate.pantry.service;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.pantry.dto.PantryRequestDto;
import com.cookmate.pantry.dto.PantryResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PantryService {

    PantryResponseDto.SummaryResponse getPantrySummary(Long memberId);
    Long createPantry(Long memberId, PantryRequestDto.CreateRequest request);
    Long updatePantry(Long memberId, Long pantryId, PantryRequestDto.UpdateRequest request);
    void deletePantry(Long memberId, Long pantryId);
    List<PantryResponseDto.PantryResponse> getPantryList(Long memberId, IngredientCategory category);
}
