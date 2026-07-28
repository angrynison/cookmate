package com.cookmate.cookmate.ingredient;

import com.cookmate.global.security.JwtProvider;
import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.domain.Ingredient;
import com.cookmate.ingredient.dto.IngredientRequestDto;
import com.cookmate.ingredient.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class IngredientIntegrationTest {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtProvider jwtProvider;



    @BeforeEach
    void Setup() {
        Ingredient ingredient = Ingredient.builder()
                .name("마늘")
                .defaultExpiry(10)
                .frozenExpiry(15)
                .ambientExpiry(5)
                .refrigeratedExpiry(20)
                .ingredientCategory(IngredientCategory.채소류)
                .build();

        ingredientRepository.save(ingredient);
    }

    @Test
    @DisplayName("IngredientService 통합테스트")
    void IngredientServiceIntegrationTest() throws Exception{
        Long memberId = 1L;
        String role = "ADMIN";

        String token = jwtProvider.createToken(memberId,role);

        List<IngredientRequestDto.CreateRequest> requestList = createRequests();

        for (IngredientRequestDto.CreateRequest request : requestList) {
            String content = objectMapper.writeValueAsString(request);
            mvc.perform(MockMvcRequestBuilders.post("/api/admin/ingredient")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
                            .header("Authorization", "Bearer"+" "+token))
                    .andExpect(status().isCreated());
        }

        long count = ingredientRepository.count();
        assertThat(count).isEqualTo(requestList.size()+1);
    }


    List<IngredientRequestDto.CreateRequest> createRequests() {
        IngredientRequestDto.CreateRequest createRequest1 = IngredientRequestDto.CreateRequest.builder()
                .name("대파")
                .defaultExpiry(10)
                .frozenExpiry(15)
                .ambientExpiry(5)
                .refrigeratedExpiry(20)
                .ingredientCategory(IngredientCategory.채소류)
                .build();

        IngredientRequestDto.CreateRequest createRequest2 = IngredientRequestDto.CreateRequest.builder()
                .name("양파")
                .defaultExpiry(20)
                .frozenExpiry(40)
                .ambientExpiry(20)
                .refrigeratedExpiry(30)
                .ingredientCategory(IngredientCategory.채소류)
                .build();

        IngredientRequestDto.CreateRequest createRequest3 = IngredientRequestDto.CreateRequest.builder()
                .name("소고기")
                .defaultExpiry(5)
                .frozenExpiry(15)
                .ambientExpiry(5)
                .refrigeratedExpiry(20)
                .ingredientCategory(IngredientCategory.육류)
                .build();

        return List.of(createRequest1, createRequest2, createRequest3);
    }
}
