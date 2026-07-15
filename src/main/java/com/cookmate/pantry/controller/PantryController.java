package com.cookmate.pantry.controller;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.pantry.Pantry;
import com.cookmate.pantry.dto.PantryRequestDto;
import com.cookmate.pantry.dto.PantryResponseDto;
import com.cookmate.pantry.service.PantryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pantry")
@RequiredArgsConstructor
public class PantryController {

    private final PantryService pantryService;

    // 카테고리별 보유 식재료 반환
    @GetMapping
    public ResponseEntity<List<PantryResponseDto.PantryResponse>> getPantriesByCategory(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "category")IngredientCategory ingredientCategory
            ) {

        Long id = Long.parseLong(userDetails.getUsername());

        List<PantryResponseDto.PantryResponse> pantryList = pantryService.getPantryList(id, ingredientCategory);
        return ResponseEntity.ok(pantryList);
    }

    // 보유 식재료 등록
    @PostMapping
    public ResponseEntity<Long> createPantry(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PantryRequestDto.CreateRequest createRequest) {

        Long id = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(pantryService.createPantry(id, createRequest));
    }

    // 보유 식재료 수정
    @PatchMapping
    public ResponseEntity<Long> updatePantry(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PantryRequestDto.UpdateRequest updateRequest
    ) {
        Long id = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(pantryService.updatePantry(id, updateRequest));
    }

    // 보유 식재료 삭제
    @DeleteMapping
    public void deletePantry(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long id = Long.parseLong(userDetails.getUsername());
        pantryService.deletePantry(id);
    }

}
