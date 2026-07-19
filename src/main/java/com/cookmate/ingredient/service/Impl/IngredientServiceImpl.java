package com.cookmate.ingredient.service.Impl;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.domain.Ingredient;
import com.cookmate.ingredient.repository.IngredientRepository;
import com.cookmate.ingredient.dto.IngredientRequestDto;
import com.cookmate.ingredient.dto.IngredientResponseDto;
import com.cookmate.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    IngredientRepository ingredientRepository;

    // 재료 기본 정보 등록
    @Override
    public Long saveIngredient(IngredientRequestDto.CreateRequest request) {

        if (ingredientRepository.existsByName(request.name()))
            throw new IllegalArgumentException("해당 재료의 정보가 이미 존재합니다.");

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

    // 기본 재료 정보 수정
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

    // 기본 재료 정보 삭제
    @Override
    public void deleteIngredient(Long pantryId) {
        ingredientRepository.deleteById(pantryId);
    }

    // 기본 재료 정보 리스트 반환
    @Override
    public List<IngredientResponseDto.IngredientResponse> getIngredientList() {
        // 추후 Pagnination 도입 예정
        List<Ingredient> ingredientList = ingredientRepository.findAll();

        return ingredientList.stream()
                .map(IngredientResponseDto.IngredientResponse::from)
                .toList();
    }

    // 기본 재료 정보 카테고리별 (리스트) 반환
    public List<IngredientResponseDto.IngredientResponse> getIngredientListByCategory(IngredientCategory category) {
        List<Ingredient> ingredientList = ingredientRepository.findByCategory(category);

        return ingredientList.stream()
                .map(IngredientResponseDto.IngredientResponse::from)
                .toList();
    }

    // 기본 재료 정보 검증 서비스 (AI)



}
