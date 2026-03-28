package store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInCartDto {
    private Long bookId;
    private int quantity;
}
