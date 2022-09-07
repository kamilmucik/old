package pl.estrix.app.backend.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.shop.model.Shop;

@Repository
public interface ShopRepository extends ShopRepositoryCustom,
        JpaRepository<Shop, Long>,
        QueryDslPredicateExecutor<Shop> {

    Shop findById(Long id);
}
