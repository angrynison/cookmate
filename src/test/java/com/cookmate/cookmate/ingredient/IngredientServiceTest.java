package com.cookmate.cookmate.ingredient;

import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.domain.Ingredient;
import com.cookmate.ingredient.dto.IngredientRequestDto;
import com.cookmate.ingredient.repository.IngredientRepository;
import com.cookmate.ingredient.service.Impl.IngredientServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//단위 테스트 라서 SpringBootTest 어노테이션 삭제
//Junit 5, Mockito
@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {

    // 테스트 객체
    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    @DisplayName("기본 재료 정보 등록 테스트")
    void addIngredient() {

        IngredientRequestDto.CreateRequest request = new IngredientRequestDto.CreateRequest(
                "대파",
                10,
                20,
                10,
                15,
                IngredientCategory.채소류
        );

        IngredientRequestDto.CreateRequest request2 = new IngredientRequestDto.CreateRequest(
                "대파",
                5,
                3,
                2,
                5,
                IngredientCategory.채소류
        );

        Ingredient mockIngredient1 = Ingredient.builder().id(15L).build();
        given(ingredientRepository.save(any(Ingredient.class))).willReturn(mockIngredient1);
        Long ingredient1 = ingredientService.saveIngredient(request);
        assertThat(ingredient1).isEqualTo(15L);

        Ingredient mockIngredient2 = Ingredient.builder().id(16L).build();
        given(ingredientRepository.save(any(Ingredient.class))).willReturn(mockIngredient2);
        Long ingredient2 = ingredientService.saveIngredient(request);

        verify(ingredientRepository, times(2)).save(any(Ingredient.class));
    }
    
    @Test
    @DisplayName("기본 재료 정보 수정 테스트")
    void updateIngredient() {

        Long targetId = 15L;
        Ingredient ingredient = Ingredient.builder()
                .id(targetId)
                .name("대파")
                .ambientExpiry(2)
                .defaultExpiry(10)
                .refrigeratedExpiry(15)
                .ingredientCategory(IngredientCategory.채소류)
                .build();

        IngredientRequestDto.UpdateRequest updateRequest = IngredientRequestDto.UpdateRequest.builder()
                .ambientExpiry(5)
                .refrigeratedExpiry(10)
                .build();

        given(ingredientRepository.findById(targetId)).willReturn(Optional.ofNullable(ingredient));
        Long updatedIngredient = ingredientService.updateIngredient(targetId, updateRequest);


        assertThat(ingredient.getAmbientExpiry()).isEqualTo(5);
        assertThat(ingredient.getRefrigeratedExpiry()).isEqualTo(10);
    }


    
}
