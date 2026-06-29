package pantry.policy;

import global.type.StorageType;
import ingredient.Ingredient;

import java.time.LocalDate;

public interface ExpiryDatePolicy {
    LocalDate calculateExpiryDate(Ingredient ingredient, LocalDate purchaseDate, StorageType storageType);
}
