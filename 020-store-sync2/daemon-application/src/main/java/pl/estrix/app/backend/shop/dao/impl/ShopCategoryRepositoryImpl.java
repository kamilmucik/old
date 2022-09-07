package pl.estrix.app.backend.shop.dao.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.app.backend.shop.dao.ShopCategoryRepositoryCustom;
import pl.estrix.app.backend.shop.model.ShopCategory;
import pl.estrix.app.backend.shop.model.QShopCategory;
import pl.estrix.app.common.dto.ShopCategorySearchCriteriaDto;
import pl.estrix.app.common.dto.model.ShopCategoryDto;

import java.util.List;

public class ShopCategoryRepositoryImpl extends QueryDslRepositorySupportBase implements ShopCategoryRepositoryCustom {


    public ShopCategoryRepositoryImpl() {
        super(ShopCategory.class);
    }

    private static final QShopCategory qShopCategory = new QShopCategory("shopCategory");

    @Override
    public List<ShopCategoryDto> find(ShopCategorySearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {


//        System.out.println("categories: " + searchCriteria);

        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qShopCategory.id.asc());
        addPagingCriteriaToQuery(query, pagingCriteria);

//        System.out.println("categories 2: " + searchCriteria);
        return query.list(Projections.bean(
                ShopCategoryDto.class,
                qShopCategory.id,
                qShopCategory.name,
                qShopCategory.parentId,
                qShopCategory.externalId,
                qShopCategory.shopId,
                qShopCategory.type,
                qShopCategory.allegroId,
                qShopCategory.allegroChildren,
                qShopCategory.allegroLeaf,
                qShopCategory.allegroParentId
        ));
    }

    @Override
    public long findCount(ShopCategorySearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ShopCategorySearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qShopCategory);

        if (searchParams.getType() != null){
            builder.and(qShopCategory.type.eq(searchParams.getType()));

            if (searchParams.getType().equals("allegro")){
//                if (searchParams.getParentId() != null){
//                    builder.and(qShopCategory.parentId.eq(searchParams.getParentId()));
//                }
            }

        }
        if (searchParams.getShopId() != null){
            builder.and(qShopCategory.shopId.eq(searchParams.getShopId().intValue()));
        }
        if (searchParams.getParentId() != null){
            builder.and(qShopCategory.parentId.eq(searchParams.getParentId()));
        }
        if (searchParams.getLeaf() != null){
            builder.and(qShopCategory.allegroLeaf.eq(searchParams.getLeaf()));
        }
        if (searchParams.getAllegroChildren() != null){
            builder.and(qShopCategory.allegroChildren.eq(searchParams.getAllegroChildren()));
        }
//        if (StringUtils.isNotEmpty(searchParams.getEan())){
//            builder.and(shipmentProduct.ean.eq(searchParams.getEan()));
//        }

        query.where(builder);
//        System.out.println("query: " + query.toString());

        return query;
    }
}
