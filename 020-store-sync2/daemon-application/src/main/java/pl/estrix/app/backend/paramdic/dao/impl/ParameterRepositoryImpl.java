package pl.estrix.app.backend.paramdic.dao.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.app.backend.paramdic.dao.ParameterRepositoryCustom;
import pl.estrix.app.backend.paramdic.model.Parameter;
import pl.estrix.app.backend.paramdic.model.QParameter;
import pl.estrix.app.common.dto.ParameterSearchCriteriaDto;
import pl.estrix.app.common.dto.model.ParameterDto;

import java.util.List;

public class ParameterRepositoryImpl extends QueryDslRepositorySupportBase
        implements ParameterRepositoryCustom {

    private static final QParameter qParameter = new QParameter("parameter");

    public ParameterRepositoryImpl() {
        super(Parameter.class);
    }

    @Override
    public List<ParameterDto> find(ParameterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qParameter.id.asc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                ParameterDto.class,
                qParameter.id,
                qParameter.allegroID,
                qParameter.name,
                qParameter.type,
                qParameter.required,
                qParameter.unit,
                qParameter.categoryId
        ));
    }

    @Override
    public long findCount(ParameterSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ParameterSearchCriteriaDto searchCriteria) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qParameter);

//        System.out.println("getCategoryId: " + searchCriteria.getCategoryId());
        if (searchCriteria.getCategoryId() != null){
            builder.and(qParameter.categoryId.eq(searchCriteria.getCategoryId()));
        }
//        System.out.println("getAllegroId: " + searchCriteria.getAllegroId());
        if (searchCriteria.getAllegroId() != null){
            builder.and(qParameter.allegroCatId.eq(searchCriteria.getAllegroId()));
        }
        if (searchCriteria.getRequired() != null){
            builder.and(qParameter.required.eq(searchCriteria.getRequired()));
        }
        query.where(builder);
        return query;
    }
}
