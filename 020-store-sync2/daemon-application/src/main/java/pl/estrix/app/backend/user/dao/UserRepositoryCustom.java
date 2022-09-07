package pl.estrix.app.backend.user.dao;

import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.user.model.User;
import pl.estrix.app.common.dto.UserSearchCriteriaDto;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> find(UserSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(UserSearchCriteriaDto searchCriteria);
}
