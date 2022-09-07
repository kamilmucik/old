package pl.estrix.app.frontend.web.settings.shop;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;
import pl.estrix.app.backend.shop.service.ShopService;
import pl.estrix.app.common.dto.model.ShopDto;
import pl.estrix.app.frontend.web.MainController;
import pl.estrix.app.lib.woocommerce.WooCommerceUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.concurrent.Callable;


@Getter
@Setter
@Scope(FacesViewScope.NAME)
@Component
@ManagedBean(name = "shopController")
public class ShopController extends MainController {

    private int number;

    @Autowired
    private ShopService shopService;

    @Autowired
    private WooCommerceUtil wooCommerceUtil;

    private LazyDataModel<ShopDto> lazyModel;

    private ShopDto selected;

    private String searchText;

    @PostConstruct
    public void init() {
        super.updateOwnUrl();
        tablePageIndex = 1;
        lazyModel = new ShopLazyDataModel(shopService, searchText);
        selected = new ShopDto();
    }

    public void select(Long id) {
        if (id == null || id == 0) {
            selected = new ShopDto();
            selected.setName("");
            selected.setApiUrl("");
            selected.setUrl("");
            selected.setConsumerKey("");
            selected.setConsumerSecret("");
            selected.setStatus("0");
//            selected.setUpdateHour("");
        } else {
            selected = shopService.get(id);
        }
    }
    public void getProducts(Long id) {

        selected = shopService.get(id);

//        stockService.downloadStockProduct(selected);
    }

    public void loadProgress(Long id){
        selected = shopService.get(id);
        SimpleAsyncTaskExecutor tasks = new SimpleAsyncTaskExecutor();
        tasks.submitListenable(new Callable<String>() {
            public String call() throws Exception {
                wooCommerceUtil.runTask(selected);
                return "";
            }
        });
    }

    public void increment() {
        number++;
    }

    public void onDateSelectRefresh(SelectEvent event) {

    }

    public void save() {
        if (selected.getId() != null){
            shopService.update(selected);
        } else {
            shopService.create(selected);
        }
    }
    public void delete() {
        shopService.delete(selected.getId());
    }

    public void search() {
        lazyModel = new ShopLazyDataModel(shopService, searchText);
    }


    public Integer getTablePageIndex() {
        return tablePageIndex;
    }

    public void setTablePageIndex(Integer tablePageIndex) {
        this.tablePageIndex = tablePageIndex;
    }
}
