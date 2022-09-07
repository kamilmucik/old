package pl.estrix.app.backend.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.stock.model.StockProduct;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StockProductRepository extends StockProductRepositoryCustom,
        JpaRepository<StockProduct, Long>,
        QueryDslPredicateExecutor<StockProduct> {

    StockProduct findById(Long id);

    @Query("FROM StockProduct sp WHERE sp.shopId=:shopId AND sp.code like :productCode")
    List<StockProduct> findByShopAndCode(@Param("shopId") Long shopId, @Param("productCode")  String productCode);

    @Query("FROM StockProduct sp WHERE sp.stockId=:stockId AND sp.code like :productCode")
    List<StockProduct> findByStockAndCode(@Param("stockId") Long stockId, @Param("productCode")  String productCode);

    @Query("FROM StockProduct sp WHERE sp.stockId=:stockId AND sp.extId like :externalId")
    List<StockProduct> findByStockIdAndExternalId(@Param("stockId") Long stockId, @Param("externalId")  String externalId);

    @Transactional
    @Modifying
    @Query("UPDATE StockProduct sp SET sp.active = :stat WHERE sp.stockId = :stockId")
    int disableProductForStock(@Param("stat") Boolean stat,@Param("stockId") Long stockId);
}
