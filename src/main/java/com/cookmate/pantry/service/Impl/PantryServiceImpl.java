package com.cookmate.pantry.service.Impl;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.domain.Ingredient;
import com.cookmate.ingredient.repository.IngredientRepository;
import com.cookmate.member.repository.MemberRepository;
import com.cookmate.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cookmate.pantry.domain.Pantry;
import com.cookmate.pantry.policy.ExpiryDatePolicy;
import com.cookmate.pantry.repository.PantryRepository;
import com.cookmate.pantry.dto.PantryRequestDto;
import com.cookmate.pantry.dto.PantryResponseDto;
import com.cookmate.pantry.service.PantryService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PantryServiceImpl implements PantryService {

    private final PantryRepository pantryRepository;
    private final MemberRepository memberRepository;
    private final IngredientRepository ingredientRepository;
    private final ExpiryDatePolicy expiryDatePolicy;

    public PantryServiceImpl(PantryRepository pantryRepository, MemberRepository memberRepository, IngredientRepository ingredientRepository, ExpiryDatePolicy expiryDatePolicy) {
        this.pantryRepository = pantryRepository;
        this.memberRepository = memberRepository;
        this.ingredientRepository = ingredientRepository;
        this.expiryDatePolicy = expiryDatePolicy;
    }

    // DashBoard 화면 구성 데이터 반환 메소드
    @Override
    public PantryResponseDto.SummaryResponse getPantrySummary(Long memberId) {

        LocalDate soonDate = LocalDate.now().plusDays(3);

        // 전체 count
        Long totalItems = pantryRepository.countByMemberId(memberId);

        // 유통기한 임박 count
        Long oldItems = pantryRepository.countBysoonDate(memberId, soonDate);

        // 신선한 재료 count
        Long freshItems = totalItems - oldItems;

        return new PantryResponseDto.SummaryResponse(totalItems,oldItems,freshItems);
    }

    // 보유 식재료 추가 메소드
    @Override
    @Transactional
    public Long createPantry(Long memberId, PantryRequestDto.CreateRequest request) {

        LocalDate finalExpiryDate;

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Ingredient ingredient = null;
        if (request.ingredientId() != null) {
            ingredient = ingredientRepository.findById(request.ingredientId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식재료입니다."));
        }

        if (request.expiryDate() != null) {
            finalExpiryDate = request.expiryDate();
        } else if (ingredient != null){
            finalExpiryDate = expiryDatePolicy.calculateExpiryDate(ingredient, request.purchaseDate(), request.storageType());
        } else {
            throw new IllegalArgumentException("기본 식재료가 아닐 경우, 유통기한을 반드시 직접 입력해야 합니다.");
        }
        Pantry pantry = Pantry.create(
                member,
                ingredient,
                request.name(),
                request.purchaseDate(),
                finalExpiryDate,
                request.storageType(),
                request.quantity(),
                request.unit()
        );
        pantryRepository.save(pantry);

        return pantry.getId();
    }

    @Override
    @Transactional
    public Long updatePantry(Long pantryId, PantryRequestDto.UpdateRequest request) {
        Pantry pantry = pantryRepository.findById(pantryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식재료입니다."));

        pantry.update(
                request.name(),
                request.purchaseDate(),
                request.expiryDate(),
                request.storageType(),
                request.quantity()
        );

        return pantry.getId();
    }

    @Override
    @Transactional
    public void deletePantry(Long pantryId) {
        pantryRepository.findById(pantryId)
                .orElseThrow(() -> new IllegalArgumentException("이미 삭제되었거나 존재하지 않는 식재료입니다."));

        pantryRepository.deleteById(pantryId);
    }

    @Override
    public List<PantryResponseDto.PantryResponse> getPantryList(Long memberId, IngredientCategory category) {
        List<Pantry> pantries =  pantryRepository.findByCategory(memberId, category);

        return pantries.stream()
                .map(PantryResponseDto.PantryResponse::from)
                .toList();
    }
}
