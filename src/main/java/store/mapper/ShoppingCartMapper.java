package store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import store.config.MapperConfig;
import store.dto.ShoppingCartDto;
import store.model.ShoppingCart;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItems")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
