package pl.estrix.app.backend.shop.dao.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.app.backend.shop.dao.ShopRepositoryCustom;
import pl.estrix.app.backend.shop.model.Shop;
import pl.estrix.app.backend.shop.model.QShop;
import pl.estrix.app.common.dto.ShopSearchCriteriaDto;
import pl.estrix.app.common.dto.model.ShopDto;

import java.util.List;

public class ShopRepositoryImpl
        extends QueryDslRepositorySupportBase
        implements ShopRepositoryCustom {

    private static final QShop qShop = new QShop("shop");

    public ShopRepositoryImpl() {
        super(Shop.class);
    }

    @Override
    public List<ShopDto> find(ShopSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qShop.id.asc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                ShopDto.class,
                qShop.id,
                qShop.name,
                qShop.url,
                qShop.apiUrl,
                qShop.consumerKey,
                qShop.consumerSecret,
                qShop.status,
                qShop.lastUpdate.as("lastUpdated")
        ));
    }

    @Override
    public long findCount(ShopSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ShopSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qShop);

        query.where(builder);
        return query;
    }
}
