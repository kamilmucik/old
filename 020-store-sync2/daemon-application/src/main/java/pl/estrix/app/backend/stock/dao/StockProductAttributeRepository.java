package pl.estrix.app.backend.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.stock.model.StockProductAttribute;

import java.util.List;

@Repository
public interface StockProductAttributeRepository extends
        JpaRepository<StockProductAttribute, Long>,
        QueryDslPredicateExecutor<StockProductAttribute> {

    List<StockProductAttribute> findByProductId(Long productId);
}
