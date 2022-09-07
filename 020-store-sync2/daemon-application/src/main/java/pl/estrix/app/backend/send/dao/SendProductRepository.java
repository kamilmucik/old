package pl.estrix.app.backend.send.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.send.model.SendProduct;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;


@Repository
public interface SendProductRepository extends SendProductRepositoryCustom,
        JpaRepository<SendProduct, Long>,
        QueryDslPredicateExecutor<SendProduct> {

    SendProduct findById(Long id);

//    @Query(name = "FROM SendProduct sp WHERE sp.processed = false")
//    @Query(name = "FROM SendProduct sp WHERE sp.processed = false ORDER BY sp.id DESC LIMIT 1", nativeQuery=true)
//    SendProduct findFirstByProcessed();

    @Query("FROM SendProduct sp WHERE sp.processed = :processed")
//    @QueryHints(@QueryHint(name = JDBC_MAX_ROWS, value = "1"))
//    @Query(name = "FROM SendProduct sp WHERE sp.processed = :processed ORDER BY sp.id DESC LIMIT 1", nativeQuery=true)
    List<SendProduct> findFirstByProcessed(@Param("processed") Boolean processed);

//    @Transactional
//    @Modifying
//    @Query("UPDATE StockProduct sp SET sp.active = :stat WHERE sp.stockId = :stockId")
//    int disableProductForStock(@Param("stat") Boolean stat,@Param("stockId") Long stockId);

    @Transactional
    @Modifying
    @Query("UPDATE SendProduct sp SET sp.processed = :processed, sp.price = :price, sp.price = :price, sp.stock = :stock WHERE sp.code = :code")
    int upgradeProductInShop(@Param("processed") Boolean processed, @Param("price")BigDecimal price, @Param("stock") Integer stock, @Param("code") String code);

}
