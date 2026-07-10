package com.cookmate.pantry.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PantryResponseDto {

    private PantryResponseDto() {}

    /*
    // 백엔드 데이터(dashboard) 응답 반환
    public record PantriesResponse(
            Long id,
            String name,
            LocalDate expiryDate,
            StorageType storageType,
            Integer quantity
    ) {}
     */

    // 화면 상단의 재료 요약 데이터 응답 반환
    public record SummaryResponse(
            Long totalItems,
            Long oldItems,
            Long freshItems
    ) {}

    public record returnId(
            Long id
    ) {}
}
