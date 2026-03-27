package store.dto;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import store.model.CartItem;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
