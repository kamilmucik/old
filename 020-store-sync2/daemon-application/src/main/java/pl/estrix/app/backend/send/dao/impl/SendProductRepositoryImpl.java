package pl.estrix.app.backend.send.dao.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.app.backend.send.dao.SendProductRepositoryCustom;
import pl.estrix.app.backend.send.model.QSendProduct;
import pl.estrix.app.backend.send.model.SendProduct;
import pl.estrix.app.common.dto.SendProductSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SendProductDto;

import java.util.List;

public class SendProductRepositoryImpl
        extends QueryDslRepositorySupportBase
        implements SendProductRepositoryCustom {

    private static final QSendProduct qSendProduct = new QSendProduct("sendProduct");

    public SendProductRepositoryImpl() {
        super(SendProduct.class);
    }

    @Override
    public List<SendProductDto> find(SendProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qSendProduct.id.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                SendProductDto.class,
                qSendProduct.id,
                qSendProduct.productId,
                qSendProduct.shopId,
                qSendProduct.shopProductId,
                qSendProduct.shopCategories,
                qSendProduct.price,
                qSendProduct.margin,
                qSendProduct.stock,
                qSendProduct.processed,
                qSendProduct.code,
                qSendProduct.message,
                qSendProduct.permalink,
                qSendProduct.image
        ));
    }

    @Override
    public long findCount(SendProductSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(SendProductSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qSendProduct);

        query.where(builder);
        return query;
    }
}
