package com.cookmate.pantry.policy;

import com.cookmate.global.type.StorageType;
import com.cookmate.ingredient.domain.Ingredient;

import java.time.LocalDate;

public interface ExpiryDatePolicy {
    LocalDate calculateExpiryDate(Ingredient ingredient, LocalDate purchaseDate, StorageType storageType);
}
