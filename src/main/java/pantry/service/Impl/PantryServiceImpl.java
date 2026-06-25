package pantry.service.Impl;

import ingredient.Ingredient;
import ingredient.IngredientRepository;
import member.MemberRepository;
import member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pantry.Pantry;
import pantry.PantryRepository;
import pantry.dto.PantryRequestDto;
import pantry.dto.PantryResponseDto;
import pantry.service.PantryService;
import recipe.RecipeRepository;

import java.time.LocalDate;

@Service
@Transactional
public class PantryServiceImpl implements PantryService {

    private final PantryRepository pantryRepository;
    private final MemberRepository memberRepository;
    private final IngredientRepository ingredientRepository;

    public PantryServiceImpl(PantryRepository pantryRepository, MemberRepository memberRepository, IngredientRepository ingredientRepository) {
        this.pantryRepository = pantryRepository;
        this.memberRepository = memberRepository;
        this.ingredientRepository = ingredientRepository;
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
    public void savePantry(Long memberId, PantryRequestDto.CreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Ingredient ingredient = ingredientRepository.findById(request.ingredientId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식재료입니다."));

        Pantry pantry = Pantry.create(member, ingredient, request);
        pantryRepository.save(pantry);
    }
}
