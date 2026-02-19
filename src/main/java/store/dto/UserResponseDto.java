package store.dto;

import lombok.Getter;
import lombok.Setter;
import store.model.Role;

import java.util.Set;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
}
