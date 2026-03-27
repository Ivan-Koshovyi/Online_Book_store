package store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInCartDto {
    Long bookId;
    int quantity;
}
