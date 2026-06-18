package pantry.service;

import pantry.dto.PantryRequestDto;
import pantry.dto.PantryResponseDto;

public interface PantryService {

    PantryResponseDto.SummaryResponse getPantrySummary(Long memberId);
    void addPantry(PantryRequestDto pantryRequestDto);
}
