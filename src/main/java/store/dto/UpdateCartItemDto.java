package store.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemDto {
    @Min(value = 0, message = "Quantity must be at least 0")
    private int quantity;
}
