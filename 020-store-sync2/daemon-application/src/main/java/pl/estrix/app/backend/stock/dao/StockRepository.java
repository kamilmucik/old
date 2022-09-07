package pl.estrix.app.backend.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.stock.model.Stock;

@Repository
public interface StockRepository extends StockRepositoryCustom,
        JpaRepository<Stock, Long>,
        QueryDslPredicateExecutor<Stock> {

    Stock findById(Long id);
}
