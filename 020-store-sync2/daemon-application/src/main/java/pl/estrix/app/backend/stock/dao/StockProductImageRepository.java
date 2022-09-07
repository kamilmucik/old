package pl.estrix.app.backend.stock.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.stock.model.StockProductAttribute;
import pl.estrix.app.backend.stock.model.StockProductImage;

import java.util.List;

@Repository
public interface StockProductImageRepository extends
        JpaRepository<StockProductImage, Long>,
        QueryDslPredicateExecutor<StockProductImage> {

    List<StockProductImage> findByProductId(Long productId);

//    @Query("FROM StockProductImage spi WHERE spi.storedFile = :storedFile LIMIT 1")
//    @QueryHints(@QueryHint(name = JDBC_MAX_ROWS, value = "1"))
//    @Query(name = "SELECT * FROM StockProductImage spi", nativeQuery=true)
//    List<StockProductImage> findStockProductImageNative();

    StockProductImage findTopByStoredFileOrderByProductIdDesc(String storedFile);


    List<StockProductImage>  findTop1ByStoredFileOrderByProductIdDesc(String storedFile);

    //getLastStudentDetails(new PageRequest(0,1));
    @Query("SELECT s FROM StockProductImage s ORDER BY s.id DESC")
    List<StockProductImage> getLastStudentDetails(Pageable pageable);
}