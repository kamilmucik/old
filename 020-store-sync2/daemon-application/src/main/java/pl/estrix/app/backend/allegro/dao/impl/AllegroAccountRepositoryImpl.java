package pl.estrix.app.backend.allegro.dao.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.app.backend.allegro.dao.AllegroAccountRepositoryCustom;
import pl.estrix.app.backend.allegro.model.AllegroAccount;
import pl.estrix.app.backend.allegro.model.QAllegroAccount;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.app.common.dto.AllegroAccountSearchCriteriaDto;
import pl.estrix.app.common.dto.model.AllegroAccountDto;

import java.util.List;

public class AllegroAccountRepositoryImpl
        extends QueryDslRepositorySupportBase
        implements AllegroAccountRepositoryCustom {

    private static final QAllegroAccount qAllegroAccount = new QAllegroAccount("allegroAccount");

    public AllegroAccountRepositoryImpl() {
        super(AllegroAccount.class);
    }

    @Override
    public List<AllegroAccountDto> find(AllegroAccountSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qAllegroAccount.id.asc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                AllegroAccountDto.class,
                qAllegroAccount.id,
                qAllegroAccount.name,
                qAllegroAccount.accessToken,
                qAllegroAccount.tokenType,
                qAllegroAccount.refreshToken,
                qAllegroAccount.expiresIn,
                qAllegroAccount.scope,
                qAllegroAccount.userCode,
                qAllegroAccount.deviceCode,
                qAllegroAccount.verificationUri,
                qAllegroAccount.verificationUriComplete,
                qAllegroAccount.interval,
                qAllegroAccount.city,
                qAllegroAccount.province,
                qAllegroAccount.postCode,
                qAllegroAccount.countryCode,
                qAllegroAccount.sellerId,
                qAllegroAccount.lastUpdate.as("lastUpdated")
        ));
    }

    @Override
    public long findCount(AllegroAccountSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(AllegroAccountSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qAllegroAccount);

        query.where(builder);
        return query;
    }
}
