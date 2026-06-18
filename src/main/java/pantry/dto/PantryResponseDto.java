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
@AllArgsConstructor
@Builder
public class PantryResponseDto {

    // 백엔드 데이터 응답 반환
    public record Response(
            Long id,
            String name,
            LocalDate expiryDate,
            StorageType storageType,
            Integer quantity
    ) {
        // pantry 엔티티를 dto 객체로 조립해서 리턴, static 메서드만 가능
        public static Response from(Pantry pantry) {
            return new Response(
                    pantry.getId(),
                    pantry.getName(),
                    pantry.getExpiryDate(),
                    pantry.getStorageType(),
                    pantry.getQuantity()
            );
        }
    }
}
