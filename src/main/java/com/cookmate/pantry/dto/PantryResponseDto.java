package com.cookmate.pantry.dto;

import com.cookmate.global.type.StorageType;
import com.cookmate.global.type.Unit;
import com.cookmate.pantry.domain.Pantry;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PantryResponseDto {

    private PantryResponseDto() {}


    // 백엔드 데이터(dashboard) 응답 반환
    public record PantryResponse(
            Long pantryId,
            String name,
            LocalDate purchaseDate,
            LocalDate expiryDate,
            StorageType storageType,
            Integer quantity,
            Unit unit
    ) {

        public static PantryResponse from(Pantry pantry) {
            return new PantryResponse(
                    pantry.getId(),
                    pantry.getName(),
                    pantry.getPurchaseDate(),
                    pantry.getExpiryDate(),
                    pantry.getStorageType(),
                    pantry.getQuantity(),
                    pantry.getUnit()
            );
        }
    }



    // 화면 상단의 재료 요약 데이터 응답 반환
    public record SummaryResponse(
            Long totalItems,
            Long oldItems,
            Long freshItems
    ) {}

}
