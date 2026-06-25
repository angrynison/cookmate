package pantry;

import global.type.StorageType;
import ingredient.Ingredient;
import ingredient.IngredientRepository;
import member.MemberRepository;
import member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pantry.dto.PantryRequestDto;
import pantry.service.Impl.PantryServiceImpl;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DisplayName("Pantry 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class PantryServiceTest {

    @InjectMocks
    private PantryServiceImpl pantryService;

    @Mock
    private PantryRepository pantryRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private IngredientRepository ingredientRepository;


    @Test
    @DisplayName("재료 통계 요약 테스트")
    void getPantrySummary() {
        Long memberId = 1L;


    }

    @Test
    @DisplayName("재료추가 테스트")
    void addPantry() {

        Long member1 = 1L;
        Long member2 = 2L;
        Long ingredient1 = 1L;
        Long ingredient2 = 2L;

        Member mockMember1 = Member.builder().id(member1).build();
        Member mockMember2 = Member.builder().id(member2).build();
        Ingredient mockIngredient1 = Ingredient.builder()
                .id(ingredient1)
                .defaultExpiry(5)
                .build();
        Ingredient mockIngredient2 = Ingredient.builder()
                .id(ingredient2)
                .defaultExpiry(3)
                .build();

        PantryRequestDto.CreateRequest request1 = new PantryRequestDto.CreateRequest(
                ingredient1,
                "감자",
                LocalDate.now(),
                StorageType.냉장,
                2
        );

        PantryRequestDto.CreateRequest request2 = new PantryRequestDto.CreateRequest(
                ingredient2,
                "양파",
                LocalDate.now(),
                StorageType.냉장,
                3
        );

        given(memberRepository.findById(member1)).willReturn(Optional.of(mockMember1));
        given(memberRepository.findById(member2)).willReturn(Optional.of(mockMember2));

        given(ingredientRepository.findById(ingredient1)).willReturn(Optional.of(mockIngredient1));
        given(ingredientRepository.findById(ingredient2)).willReturn(Optional.of(mockIngredient2));

        pantryService.savePantry(member1, request1);
        pantryService.savePantry(member2, request2);

        verify(pantryRepository, times(2)).save(any(Pantry.class));
    }



}
