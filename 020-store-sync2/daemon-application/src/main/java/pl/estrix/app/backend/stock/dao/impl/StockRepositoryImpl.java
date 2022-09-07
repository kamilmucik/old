package pl.estrix.app.backend.stock.dao.impl;


import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.app.backend.stock.dao.StockRepositoryCustom;
import pl.estrix.app.backend.stock.model.QStock;
import pl.estrix.app.backend.stock.model.Stock;
import pl.estrix.app.common.dto.StockSearchCriteriaDto;
import pl.estrix.app.common.dto.model.StockDto;

import java.util.List;

public class StockRepositoryImpl
        extends QueryDslRepositorySupportBase
        implements StockRepositoryCustom {

    private static final QStock qStock = new QStock("stock");

    public StockRepositoryImpl() {
        super(Stock.class);
    }

    @Override
    public List<StockDto> find(StockSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qStock.id.asc());
        addPagingCriteriaToQuery(query, pagingCriteria);

        return query.list(Projections.bean(
                StockDto.class,
                qStock.id,
                qStock.name,
                qStock.updateHour,
                qStock.url,
                qStock.apiUrl,
                qStock.productCounter,
                qStock.lastUpdate,
                qStock.status,
                qStock.siteScratch

        ));
    }

    @Override
    public long findCount(StockSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(StockSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qStock);

        if (searchParams.getActive() != null) {
//            query.where(qStock.active.eq(searchParams.getActive()));
        }

//        System.out.println("searchParams: " + searchParams.getTableSearch());
        if (StringUtils.isNotEmpty(searchParams.getTableSearch())){
            query.where(
                    qStock.name.like("%"+searchParams.getTableSearch()+"%")
            );
        }

        query.where(builder);



//        System.out.println("query: " + query.toString());
        return query;
    }
}
