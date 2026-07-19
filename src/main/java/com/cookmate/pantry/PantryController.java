package com.cookmate.pantry;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.pantry.dto.PantryRequestDto;
import com.cookmate.pantry.dto.PantryResponseDto;
import com.cookmate.pantry.service.PantryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user/pantry")
@RequiredArgsConstructor
public class PantryController {

    private final PantryService pantryService;

    // DashBoard 화면 요약 데이터 반환
    @GetMapping("/summary")
    public ResponseEntity<PantryResponseDto.SummaryResponse> getPantrySummary(@RequestAttribute("memberId") Long memberId) {
        return ResponseEntity.ok(pantryService.getPantrySummary(memberId));
    }
    
    // 카테고리별 보유 식재료 반환
    @GetMapping
    public ResponseEntity<List<PantryResponseDto.PantryResponse>> getPantriesByCategory(
            @RequestAttribute("memberId") Long memberId,
            @RequestParam(name = "category")IngredientCategory ingredientCategory
            ) {

        List<PantryResponseDto.PantryResponse> pantryList = pantryService.getPantryList(memberId, ingredientCategory);
        return ResponseEntity.ok(pantryList);
    }

    // 보유 식재료 등록
    @PostMapping
    public ResponseEntity<Long> createPantry(
            @RequestAttribute("memberId") Long memberId,
            @RequestBody PantryRequestDto.CreateRequest createRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(pantryService.createPantry(memberId, createRequest));
    }

    // 보유 식재료 수정
    @PatchMapping("/{ingredientId}")
    public ResponseEntity<Long> updatePantry(
            @RequestAttribute("memberId") Long memberId,
            @PathVariable("ingredientId") Long ingredientId,
            @RequestBody PantryRequestDto.UpdateRequest updateRequest
    ) {
        return ResponseEntity.ok(pantryService.updatePantry(memberId, ingredientId, updateRequest));
    }

    // 보유 식재료 삭제
    @DeleteMapping("/{ingredientId}")
    public void deletePantry(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("ingredientId") Long ingredientId
    ) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        pantryService.deletePantry(memberId, ingredientId);
    }

}
