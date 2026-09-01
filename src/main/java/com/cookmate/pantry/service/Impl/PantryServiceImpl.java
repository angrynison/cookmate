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


import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

        Ingredient ingredient = ingredientRepository.findByName(request.name())
                .orElse(null);

        // 우선순위는 1. 요청에 들어온 만료일 2. 기본 재료정보에 적힌 만료일 3. 오류
        if (request.expiryDate() != null) {
            finalExpiryDate = request.expiryDate();
        } else if (ingredient != null){
            finalExpiryDate = expiryDatePolicy.calculateExpiryDate(ingredient, request.purchaseDate(), request.storageType());
        } else {
            throw new IllegalArgumentException("기본 식재료가 아닐 경우, 유통기한을 반드시 직접 입력해야 합니다.");
        }

        // Quota 용량 제한 적용, 한 냉장고에 200개의 재료까지만 허용, Dos 방지
        long currentCount = pantryRepository.countByMemberId(memberId);
        if (currentCount >= 200) {
            throw new IllegalStateException("냉장고가 꽉 찼습니다. 더 이상 식재료를 추가할 수 없습니다.");
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

    // 보유 식재료 수정 메소드
    @Override
    @Transactional
    public Long updatePantry(Long memberId, Long pantryId, PantryRequestDto.UpdateRequest request) {


        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(("존재하지 않는 회원입니다.")));

        Pantry pantry = pantryRepository.findById(pantryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식재료입니다."));

        if (!pantry.getMember().getId().equals(memberId)) {
            throw new AccessDeniedException("해당 식재료에 대한 권한이 없습니다.");
        }

        pantry.update(
                request.name(),
                request.purchaseDate(),
                request.expiryDate(),
                request.storageType(),
                request.quantity()
        );

        return pantry.getId();
    }

    // 보유 식재료 삭제 메소드
    @Override
    @Transactional
    public void deletePantry(Long memberId, Long pantryId) {
        Pantry pantry = pantryRepository.findById(pantryId)
                .orElseThrow(() -> new IllegalArgumentException("이미 삭제되었거나 존재하지 않는 식재료입니다."));

        if (!pantry.getMember().getId().equals(memberId)) {
            throw new AccessDeniedException("해당 식재료에 대한 권한이 없습니다.");
        }

        pantryRepository.deleteById(pantryId);
    }

    // 카테고리별 보유 식재료 반환 메소드
    @Override
    public List<PantryResponseDto.PantryResponse> getPantryList(Long memberId, IngredientCategory category) {
        List<Pantry> pantryList =  pantryRepository.findByCategory(memberId, category);

        return pantryList.stream()
                .map(PantryResponseDto.PantryResponse::from)
                .toList();
    }
}
