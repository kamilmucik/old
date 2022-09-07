package pl.estrix.app.backend.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.shop.model.ShopCategory;

import java.util.List;

@Repository
public interface ShopCategoryRepository extends ShopCategoryRepositoryCustom,
        JpaRepository<ShopCategory, Long>,
        QueryDslPredicateExecutor<ShopCategory> {

    ShopCategory findById(Long id);

    @Query("FROM ShopCategory sp WHERE sp.externalId = :externalId AND sp.shopId = :shopId")
    ShopCategory findByExternalIdAndShopId(@Param("externalId") Integer id,@Param("shopId")  Integer shopId);

    @Query("FROM ShopCategory sp WHERE sp.shopId = :shopId")
    List<ShopCategory> findByShopId(@Param("shopId") Long shopId);


}
