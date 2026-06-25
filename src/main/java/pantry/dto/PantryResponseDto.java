package pantry.dto;

import global.type.StorageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pantry.Pantry;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Builder
public class PantryResponseDto {

    // 백엔드 데이터(dashboard) 응답 반환
    public record DashboardResponse(
            Long id,
            String name,
            LocalDate expiryDate,
            StorageType storageType,
            Integer quantity
    ) {}

    // 화면 상단의 재료 요약 데이터 응답 반환
    public record SummaryResponse(
            Long totalItems,
            Long oldItems,
            Long freshItems
    ) {}
}
