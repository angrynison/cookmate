package pantry;

import global.type.StorageType;

import java.time.LocalDate;
// record 는 Getter/생성자가 자동 생서되어 DTO 만들 때 편리함
public final class PantryDto {
    
    // 요청,응답에만 쓰는 껍데기 형태라서 생성자를 외부에서 만들 필요가 없음
    private PantryDto() {}

    // web에서 정보를 받아와서 개인 식재료 등록
    public record CreateRequest(
            String name,
            LocalDate purchaseDate,
            LocalDate expiryDate,
            StorageType storageType,
            String quantityUnit
    ) {}

    // web에서 정보를 받아와서 개인 식재료 업데이트
    public record UpdateRequest(
            String name,
            LocalDate purchaseDate,
            LocalDate expiryDate,
            StorageType storageType,
            String quantityUnit
    ){}

    
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
