package pantry.service.Impl;

import global.type.IngredientCategory;
import ingredient.domain.Ingredient;
import ingredient.repository.IngredientRepository;
import member.repository.MemberRepository;
import member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pantry.Pantry;
import pantry.policy.ExpiryDatePolicy;
import pantry.repository.PantryRepository;
import pantry.dto.PantryRequestDto;
import pantry.dto.PantryResponseDto;
import pantry.service.PantryService;

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
    public Long savePantry(Long memberId, PantryRequestDto.CreateRequest request) {

        LocalDate finalExpiryDate;

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Ingredient ingredient = ingredientRepository.findById(request.ingredientId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식재료입니다."));

        if (request.expiryDate() != null) {
            finalExpiryDate = request.expiryDate();
        } else {
            finalExpiryDate = expiryDatePolicy.calculateExpiryDate(ingredient, request.purchaseDate(), request.storageType());
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
        Pantry pantry = pantryRepository.findById(pantryId)
                .orElseThrow(() -> new IllegalArgumentException("이미 삭제되었거나 존재하지 않는 식재료입니다."));

        pantryRepository.deleteById(pantryId);
    }

    @Override
    public List<Pantry> getPantryList(IngredientCategory category) {
        return pantryRepository.findByCategory(category);
    }
}
