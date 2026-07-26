package com.cookmate.cookmate.ingredient;


import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.IngredientController;
import com.cookmate.ingredient.domain.Ingredient;
import com.cookmate.ingredient.dto.IngredientRequestDto;
import com.cookmate.ingredient.dto.IngredientResponseDto;
import com.cookmate.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

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

    //java 객체를 json으로 변경
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("모든 기본 재료 정보 가져오기")
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

    @Test
    @DisplayName("카테고리별 재료 가져오기")
    void getIngredientsByCategory() throws Exception{

        IngredientResponseDto.IngredientResponse ingredientResponse1 = IngredientResponseDto.IngredientResponse.builder()
                .id(15L)
                .name("대파")
                .ingredientCategory(IngredientCategory.채소류)
                .build();

        IngredientResponseDto.IngredientResponse ingredientResponse2 = IngredientResponseDto.IngredientResponse.builder()
                .id(16L)
                .name("소고기")
                .ingredientCategory(IngredientCategory.육류)
                .build();

        List<IngredientResponseDto.IngredientResponse> ingredientList = List.of(ingredientResponse1);
        given(ingredientService.getIngredientListByCategory(IngredientCategory.채소류)).willReturn(ingredientList);
        mvc.perform(MockMvcRequestBuilders.get("/api/admin/ingredient/category")
                        .param("category","채소류"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("대파"));
    }

    @Test
    @DisplayName("기본 재료 정보 등록 테스트")
    void createIngredient() throws Exception{

        IngredientRequestDto.CreateRequest createdIngredient = IngredientRequestDto.CreateRequest.builder()
                .name("대파")
                .defaultExpiry(25)
                .ingredientCategory(IngredientCategory.채소류)
                .build();

        given(ingredientService.saveIngredient(any())).willReturn(1L);
        Long savedId = ingredientService.saveIngredient(createdIngredient);

        String content = objectMapper.writeValueAsString(createdIngredient);

        mvc.perform(MockMvcRequestBuilders.post("/api/admin/ingredient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value(2L));
    }




}
