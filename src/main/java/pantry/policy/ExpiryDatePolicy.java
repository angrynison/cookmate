package pantry.policy;

import global.type.StorageType;
import ingredient.domain.Ingredient;

import java.time.LocalDate;

public interface ExpiryDatePolicy {
    LocalDate calculateExpiryDate(Ingredient ingredient, LocalDate purchaseDate, StorageType storageType);
}
