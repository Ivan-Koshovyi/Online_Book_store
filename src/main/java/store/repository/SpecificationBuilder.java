package store.repository;

import org.springframework.data.jpa.domain.Specification;
import store.dto.BookSearchParametersDto;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParametersDto params);
}
