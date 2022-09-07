package pl.estrix.app.frontend.web.dashboard.event;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;
import pl.estrix.app.backend.event.service.EventService;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.common.dto.model.EventDto;
import pl.estrix.app.common.dto.model.SettingDto;
import pl.estrix.app.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@Getter
@Setter
@Scope(FacesViewScope.NAME)
@Component
@ManagedBean(name = "eventController")
public class EventController extends MainController  {


    @Autowired
    private EventService eventService;

    private LazyDataModel<EventDto> lazyModel;

    private EventDto selected;

    private String searchText;

    @PostConstruct
    public void init() {
        super.updateOwnUrl();
        tablePageIndex = 1;
        lazyModel = new EventLazyDataModel(eventService, searchText);
        selected = new EventDto();
    }

    public void select(Long id) {
        if (id == null || id == 0) {
        } else {
            selected = eventService.get(id);
        }
    }

    public void search() {
        lazyModel = new EventLazyDataModel(eventService, searchText);
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
