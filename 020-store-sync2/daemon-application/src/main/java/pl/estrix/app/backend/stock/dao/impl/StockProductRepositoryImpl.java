package pl.estrix.app.backend.stock.dao.impl;


import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.app.backend.stock.dao.StockProductRepositoryCustom;
import pl.estrix.app.backend.stock.model.*;
import pl.estrix.app.common.dto.StockProductSearchCriteriaDto;
import pl.estrix.app.common.dto.model.StockProductDto;

import java.util.List;

@Slf4j
public class StockProductRepositoryImpl
        extends QueryDslRepositorySupportBase
        implements StockProductRepositoryCustom {

    private static final QStockProduct qStockProduct = QStockProduct.stockProduct;
    private static final QStockProductImage qStockProductImage = QStockProductImage.stockProductImage;

    public StockProductRepositoryImpl() {
        super(StockProduct.class);
    }

    @Override
    public List<StockProductDto> find(StockProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qStockProduct.id.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);


//        log.debug("sql/. : {}", query.toString() );
        return query.list(Projections.bean(
                StockProductDto.class,
                qStockProduct.id,
                qStockProduct.name,
                qStockProduct.description,
                qStockProduct.shopId,
                qStockProduct.stockId,
                qStockProduct.code,
//                qStockProduct.category,
                qStockProduct.stock,
                qStockProduct.stockExt,
                qStockProduct.price,
                qStockProduct.priceBrutto,
                qStockProduct.priceRetail,
                qStockProduct.active.as("avail"),
//                qStockProduct.d14,
//                qStockProduct.updateHash,
                qStockProduct.lastUpdate
//                qStockProduct.shopExternalId
//                qStockProduct.images
        ));
    }

    @Override
    public long findCount(StockProductSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(StockProductSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qStockProduct);

        if (StringUtils.isNotEmpty(searchParams.getTableSearch())){
            query.where(
                    qStockProduct.name.like("%"+searchParams.getTableSearch()+"%")
                            .or(qStockProduct.description.like("%"+searchParams.getTableSearch()+"%"))
                            .or(qStockProduct.code.like("%"+searchParams.getTableSearch()+"%"))
            );
        }
        if (searchParams.getPriceFrom() != null && searchParams.getPriceFrom().intValue() > 0) {
            builder.and(qStockProduct.priceRetail.goe(searchParams.getPriceFrom()));
        }
        if (searchParams.getPriceTo() != null && searchParams.getPriceFrom().intValue() > 0) {
            builder.and(qStockProduct.priceRetail.loe(searchParams.getPriceTo()));
        }
        if (searchParams.getStockFrom() != null && searchParams.getStockFrom().intValue() > 0) {
            builder
                    .and(qStockProduct.stock.goe(searchParams.getStockFrom()).or(qStockProduct.stockExt.goe(searchParams.getStockFrom())
                    ))
            ;
        }
        if (searchParams.getStockTo() != null && searchParams.getStockTo().intValue() > 0) {
            builder
                    .and(qStockProduct.stock.loe(searchParams.getStockTo()).or(qStockProduct.stockExt.loe(searchParams.getStockTo())
                    ))
            ;
        }
        if (searchParams.getActive() !=null) {
            builder.and(qStockProduct.active.eq(searchParams.getActive()));
        }
        if (searchParams.getSelectedStock() !=null) {
            builder.and(qStockProduct.stockId.eq(searchParams.getSelectedStock().longValue()));
        }


        query.where(builder);
//        log.debug("8. : {}", query.toString() );
        return query;
    }
}
