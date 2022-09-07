package pl.estrix.app.frontend.web.settings.user;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;
import pl.estrix.app.backend.user.service.UserService;
import pl.estrix.app.common.dto.model.UserDto;
import pl.estrix.app.frontend.web.MainController;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Component("userLazyView")
@Scope(FacesViewScope.NAME)
@Getter
@Setter
public class UserListBean extends MainController implements Serializable {

    private LazyDataModel<UserDto> lazyModel;

    private UserDto selectedUser;

    private String searchText;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        super.init();
        searchText = (String) getContext().getExternalContext().getSessionMap().get("_user_list_search");

        lazyModel = new LazyUserDataModel(userService, searchText);
    }

    public void delete() {
        userService.delete(selectedUser);
    }

    public void edit(Long id) {
        if (id == null || id == 0) {

        } else {
            selectedUser = userService.getItem(id);
        }
    }

    public void search() {
        lazyModel = new LazyUserDataModel(userService, searchText);
    }

    public void cancel() {
        selectedUser = null;
    }

    public LazyDataModel<UserDto> getLazyModel() {
        return lazyModel;
    }

    public UserDto getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }

}
