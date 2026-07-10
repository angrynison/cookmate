package com.cookmate.pantry.dto;

import com.cookmate.global.type.StorageType;
import com.cookmate.global.type.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

// record 는 Getter/생성자가 자동 생성되어 DTO 만들 때 편리함
@Getter
@Builder
public final class PantryRequestDto {
    
    // 요청,응답에만 쓰는 껍데기 형태라서 생성자를 외부에서 만들 필요가 없음
    private PantryRequestDto() {}

    // web에서 정보를 받아와서 개인 식재료 등록
    public record CreateRequest(
            Long ingredientId,

            @NotBlank(message = "식재료 이름은 필수입니다")
            String name,

            @NotNull(message =  "구매일은 필수입니다")
            LocalDate purchaseDate,

            LocalDate expiryDate,

            @NotNull(message = "보관 방법은 필수입니다")
            StorageType storageType,

            @Positive(message = "수량은 0보다 커야 합니다")
            Integer quantity,

            @NotNull(message = "단위가 있어야 합니다")
            Unit unit
    ) {}

    // web에서 정보를 받아와서 개인 식재료 업데이트
    public record UpdateRequest(

            String name,
            LocalDate purchaseDate,
            LocalDate expiryDate,
            StorageType storageType,
            Integer quantity
    ){}

}
