package ingredient;

import global.type.IngredientCategory;
import global.type.StorageType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingredient")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Integer defaultExpiry;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IngredientCategory ingredientCategory;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;
}
