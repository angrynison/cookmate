package com.cookmate.cookmate.ingredient;

import com.cookmate.global.security.JwtProvider;
import com.cookmate.global.type.IngredientCategory;
import com.cookmate.ingredient.domain.Ingredient;
import com.cookmate.ingredient.dto.IngredientRequestDto;
import com.cookmate.ingredient.repository.IngredientRepository;
import com.cookmate.ingredient.service.IngredientService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class IngredientIntegrationTest {

    @Autowired
    private IngredientService ingredientService;
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
    @DisplayName("등록테스트")
    void CreateTest() throws Exception{
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

    @Test
    @DisplayName("수정 및 삭제 테스트")
    void PatchNDeleteTest() throws Exception {

        Long adminId = 1L;
        String role = "ADMIN";

        String token = jwtProvider.createToken(adminId,role);


        Ingredient ingredient = Ingredient.builder()
                .name("대파")
                .defaultExpiry(10)
                .frozenExpiry(15)
                .ambientExpiry(5)
                .refrigeratedExpiry(20)
                .ingredientCategory(IngredientCategory.채소류)
                .build();

        Long id = ingredientRepository.save(ingredient).getId();

        // 업데이트
        IngredientRequestDto.UpdateRequest updateRequest = updateRequest();
        String content = objectMapper.writeValueAsString(updateRequest);

        mvc.perform(MockMvcRequestBuilders.patch("/api/admin/ingredient/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                        .header("Authorization", "Bearer"+" "+token))
                .andExpect(status().isOk());

        assertThat(ingredient.getDefaultExpiry()).isEqualTo(100);


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

    IngredientRequestDto.UpdateRequest updateRequest() {

        IngredientRequestDto.UpdateRequest updateRequest = IngredientRequestDto.UpdateRequest.builder()
                .defaultExpiry(100)
                .frozenExpiry(1000)
                .build();

        return updateRequest;
    }
}