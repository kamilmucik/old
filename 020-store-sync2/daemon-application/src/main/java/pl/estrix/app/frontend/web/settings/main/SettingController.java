package pl.estrix.app.frontend.web.settings.main;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.common.dto.model.SettingDto;
import pl.estrix.app.common.dto.model.ShopDto;
import pl.estrix.app.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@Getter
@Setter
@Scope(FacesViewScope.NAME)
@Component
@ManagedBean(name = "settingController")
public class SettingController extends MainController  {


    @Autowired
    private SettingService settingService;

    private LazyDataModel<SettingDto> lazyModel;

    private SettingDto selected;

    private String searchText;

    @PostConstruct
    public void init() {
        super.updateOwnUrl();
        tablePageIndex = 1;
        lazyModel = new SettingLazyDataModel(settingService, searchText);
        selected = new SettingDto();
    }

    public void select(Long id) {
        if (id == null || id == 0) {
            selected = new SettingDto();
            selected.setName("");
            selected.setValue("");
//            selected.setUpdateHour("");
        } else {
            selected = settingService.get(id).get();
        }
    }

    public void save() {
        if (selected.getId() != null){
            settingService.update(selected);
        } else {
            settingService.create(selected);
        }
    }

    public void search() {
        lazyModel = new SettingLazyDataModel(settingService, searchText);
    }

    public void refreshPriceToSend(){
        System.out.println("refreshPriceToSend");
    }


    public void onRowSelect() {
        System.out.println("onRowSelect");
    }

    public Integer getTablePageIndex() {
        return tablePageIndex;
    }

    public void setTablePageIndex(Integer tablePageIndex) {
        this.tablePageIndex = tablePageIndex;
    }
}
