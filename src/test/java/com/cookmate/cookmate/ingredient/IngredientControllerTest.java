package com.cookmate.cookmate.ingredient;


import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.IngredientController;
import com.cookmate.ingredient.domain.Ingredient;
import com.cookmate.ingredient.dto.IngredientResponseDto;
import com.cookmate.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IngredientController.class)
@RequiredArgsConstructor
public class IngredientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private IngredientService ingredientService;


    @Test
    void getIngredients() throws Exception {

        Ingredient ingredient1 = Ingredient.builder()
                .id(15L)
                .name("대파")
                .ingredientCategory(IngredientCategory.채소류)
                .build();

        Ingredient ingredient2 = Ingredient.builder()
                .id(16L)
                .name("양파")
                .ingredientCategory(IngredientCategory.채소류)
                .build();

        Ingredient ingredient3 = Ingredient.builder()
                .id(16L)
                .name("소고기")
                .ingredientCategory(IngredientCategory.육류)
                .build();

        IngredientResponseDto.IngredientResponse ingredientResponse1 = IngredientResponseDto.IngredientResponse.from(ingredient1);
        IngredientResponseDto.IngredientResponse ingredientResponse2 = IngredientResponseDto.IngredientResponse.from(ingredient2);
        IngredientResponseDto.IngredientResponse ingredientResponse3 = IngredientResponseDto.IngredientResponse.from(ingredient3);

        List<IngredientResponseDto.IngredientResponse> ingredientResponseList = List.of(ingredientResponse1, ingredientResponse2, ingredientResponse3);

        given(ingredientService.getIngredientList()).willReturn(ingredientResponseList);

        mvc.perform(MockMvcRequestBuilders.get("/api/admin/ingredient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].name").value("대파"))
                .andExpect(jsonPath("$[1].name").value("양파"))
                // Enum.String으로 해서 value를 string으로 작성
                .andExpect(jsonPath("$[2].ingredientCategory").value("육류"));

    }





}
