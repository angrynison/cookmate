package pantry;

import global.type.StorageType;
import global.type.Unit;
import ingredient.Ingredient;
import ingredient.IngredientRepository;
import member.MemberRepository;
import member.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.spel.ast.NullLiteral;
import pantry.dto.PantryRequestDto;
import pantry.repository.PantryRepository;
import pantry.service.Impl.PantryServiceImpl;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DisplayName("Pantry 서비스 테스트")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
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

        //given
        Long member1 = 1L;
        Long member2 = 2L;
        Long ingredient1 = 1L;
        Long ingredient2 = 2L;

//        Member mockMember1 = Member.builder().id(member1).build();
//        Member mockMember2 = Member.builder().id(member2).build();

//        Ingredient mockIngredient1 = Ingredient.builder()
//                .id(ingredient1)
//                .defaultExpiry(5)
//                .build();
//        Ingredient mockIngredient2 = Ingredient.builder()
//                .id(ingredient2)
//                .defaultExpiry(3)
//                .build();

        
        // h2 db 구현
        // memberRepository.save() ~~ 구현
        // ingredientRepository.save() ~~ 구현


        PantryRequestDto.CreateRequest request1 = new PantryRequestDto.CreateRequest(
                ingredient1,
                "감자",
                LocalDate.now(),
                null,
                StorageType.냉장,
                2,
                Unit.EA
        );

        PantryRequestDto.CreateRequest request2 = new PantryRequestDto.CreateRequest(
                ingredient2,
                "감자",
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                StorageType.상온,
                3,
                Unit.EA
        );

//        given(memberRepository.findById(member1)).willReturn(Optional.of(mockMember1));
//        given(memberRepository.findById(member2)).willReturn(Optional.of(mockMember2));
//
//        given(ingredientRepository.findById(ingredient1)).willReturn(Optional.of(mockIngredient1));
//        given(ingredientRepository.findById(ingredient2)).willReturn(Optional.of(mockIngredient2));

        //when
        pantryService.savePantry(member1, request1);
        pantryService.savePantry(member2, request2);

        //then
        verify(pantryRepository, times(2)).save(any(Pantry.class));
    }
    
    @Test
    @DisplayName("재료정보 업데이트 테스트")
    void updatePantry() {

        //given
        Long memberId = 1L;
        Long ingredientId = 1L;

//        Member mockMember1 = Member.builder().id(memberId).build();
//        Ingredient mockIngredient1 = Ingredient.builder().id(ingredientId).build();

        // h2 db 구현후 테스트 코드 작성 진행


        PantryRequestDto.CreateRequest request = new PantryRequestDto.CreateRequest(
                ingredientId,
                "감자",
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                StorageType.상온,
                3,
                Unit.EA
                );

//        given(memberRepository.findById(memberId)).willReturn(Optional.of(mockMember1));
//        given(ingredientRepository.findById(ingredientId)).willReturn(Optional.of(mockIngredient1));

        Long pantryId = pantryService.savePantry(memberId, request);

        Pantry pantry1 = pantryRepository.findById(pantryId)
                .orElseThrow(() -> new IllegalArgumentException("보유하지 않은 식재료입니다."));

        LocalDate changeExpiry = LocalDate.now().plusDays(5);
        Integer changeQuantity = 5;

        PantryRequestDto.UpdateRequest updateRequest = new PantryRequestDto.UpdateRequest(
                null,
                null,
                changeExpiry,
                null,
                changeQuantity
        );

        //when
        pantryService.updatePantry(pantryId, updateRequest);

        LocalDate afterExpiry = pantry1.getExpiryDate();
        Integer afterQuantity = pantry1.getQuantity();

        //then
        Assertions.assertEquals(changeQuantity, afterQuantity);
        Assertions.assertEquals(changeExpiry, afterExpiry);
    }



}
