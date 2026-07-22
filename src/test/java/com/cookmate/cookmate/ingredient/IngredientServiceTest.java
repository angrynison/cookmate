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
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//Junit 5, Mockito
@ExtendWith(MockitoExtension.class)
@SpringBootTest
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
    
    
}
