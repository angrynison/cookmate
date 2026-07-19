package com.cookmate.ingredient;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.domain.Ingredient;
import com.cookmate.ingredient.dto.IngredientRequestDto;
import com.cookmate.ingredient.dto.IngredientResponseDto;
import com.cookmate.ingredient.service.IngredientService;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    // 기본 재료정보 리스트 반환
    @RequestMapping
    public ResponseEntity<List<IngredientResponseDto.IngredientResponse>> getIngredients() {
        List<IngredientResponseDto.IngredientResponse> ingredientList = ingredientService.getIngredientList();
        return ResponseEntity.ok(ingredientList);
    }

    // 기본 재료정보 카테고리별 반환
    @RequestMapping
    public ResponseEntity<List<IngredientResponseDto.IngredientResponse>> getIngredientsByCategory(@RequestParam IngredientCategory category) {
        List<IngredientResponseDto.IngredientResponse> ingredientList = ingredientService.getIngredientListByCategory(category);
        return ResponseEntity.ok(ingredientList);
    }

    // 기본 재료정보 등록
    @PostMapping
    public ResponseEntity<Long> createIngredient(@RequestBody IngredientRequestDto.CreateRequest request) {
        Long id = ingredientService.saveIngredient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    // 기본 재료 검증
    
    // 기본 재료정보 수정
    @PatchMapping("/{ingredientId}")
    public ResponseEntity<Long> updateIngredient(@PathVariable("ingredientId") Long ingredientId, @RequestBody IngredientRequestDto.UpdateRequest request) {
        return ResponseEntity.ok(ingredientService.updateIngredient(ingredientId, request));
    }

    // 기본 재료정보 삭제
    @DeleteMapping("/{ingredientId}")
    public void deleteIngredient(@PathVariable("ingredientId") Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
    }

}
