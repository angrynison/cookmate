package pantry.policy;

import global.error.InvalidExpiryDateException;
import global.type.StorageType;
import ingredient.domain.Ingredient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SeasonalExpiryDatePolicy implements ExpiryDatePolicy{

    @Override
    public LocalDate calculateExpiryDate(Ingredient ingredient, LocalDate purchaseDate, StorageType storageType) {

        Integer days;

        switch (storageType) {
            case 냉동 -> days = ingredient.getFrozenExpiry();
            case 냉장 -> days = ingredient.getRefrigeratedExpiry();
            case 상온 -> days = ingredient.getAmbientExpiry();
            default -> days = ingredient.getDefaultExpiry();
        };

        // 설정한 보관타입의 유통기한 정보가 없을때 default 값 사용
        if (days == null) {
            days = ingredient.getDefaultExpiry();
            if (days == null) {
                throw new InvalidExpiryDateException("식재료의 유통기한 정보가 존재하지 않습니다");
            }
            return purchaseDate.plusDays(days);
        }

        if (storageType == StorageType.상온) {
            return calculateAmbientExpiryDate(days, purchaseDate);
        }

        return purchaseDate.plusDays(days);
    }

    // 계절별 상온 유통기한 계산 로직
    public LocalDate calculateAmbientExpiryDate(int defaultDays, LocalDate purchaseDate) {
        int month = purchaseDate.getMonthValue();
        double ratio = 1.0;

        if (month == 5) { // 늦봄
            ratio = 0.9;
        } else if (month == 6) { // 초여름
            ratio = 0.7;
        } else if (month == 7 || month == 8) { // 한여름
            ratio = 0.5;
        } else if (month == 9) { // 늦여름
            ratio = 0.8;
        } else if  (month == 12 || month == 1) { // 한겨울
            ratio = 0.9;
        }

        int finalDays = Math.max(1, (int) Math.round(defaultDays * ratio));
        return purchaseDate.plusDays(finalDays);
    }








}
