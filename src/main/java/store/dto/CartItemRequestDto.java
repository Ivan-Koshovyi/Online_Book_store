package store.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDto {
    private Long bookId;
    @Positive(message = "Quantity must be greater than 0")
    private int quantity;
}
