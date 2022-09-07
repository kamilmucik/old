package pl.estrix.app.backend.paramdic.dao.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.app.backend.paramdic.dao.DictionaryRepositoryCustom;
import pl.estrix.app.backend.paramdic.model.Dictionary;
import pl.estrix.app.backend.paramdic.model.QDictionary;
import pl.estrix.app.common.dto.DictionarySearchCriteriaDto;
import pl.estrix.app.common.dto.model.DictionaryDto;

import java.util.List;

public class DictionaryRepositoryImpl extends QueryDslRepositorySupportBase
        implements DictionaryRepositoryCustom {

    private static final QDictionary qDictionary = new QDictionary("dictionary");

    public DictionaryRepositoryImpl() {
        super(Dictionary.class);
    }

    @Override
    public List<DictionaryDto> find(DictionarySearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qDictionary.id.asc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                DictionaryDto.class,
                qDictionary.id,
                qDictionary.allegroID,
                qDictionary.allegroName,
                qDictionary.userId,
                qDictionary.dictionaryName,
                qDictionary.parameterId

        ));
    }

    @Override
    public long findCount(DictionarySearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(DictionarySearchCriteriaDto searchCriteria) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qDictionary);

        if (StringUtils.isNotEmpty(searchCriteria.getUserId())){
            builder.and(qDictionary.userId.eq(searchCriteria.getUserId()));
        }

        if (StringUtils.isNotEmpty(searchCriteria.getDictionaryName())){
            builder.and(qDictionary.dictionaryName.eq(searchCriteria.getDictionaryName()));
        }
        if (searchCriteria.getParameterId() != null){
            builder.and(qDictionary.parameterId.eq(searchCriteria.getParameterId()));
        }



        query.where(builder);

//        System.out.println("query: " + query);

        return query;
    }
}
