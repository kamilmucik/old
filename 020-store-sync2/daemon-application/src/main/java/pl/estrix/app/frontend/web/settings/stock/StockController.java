package pl.estrix.app.frontend.web.settings.stock;

import lombok.Getter;
import lombok.Setter;
import org.firebirdsql.logging.Logger;
import org.firebirdsql.logging.LoggerFactory;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.send.service.SendProductService;
import pl.estrix.app.backend.shop.service.ShopService;
import pl.estrix.app.backend.stock.service.StockService;
//import pl.estrix.app.common.dto.StockSearchCriteriaDto;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.ShopCategorySearchCriteriaDto;
import pl.estrix.app.common.dto.StockProductSearchCriteriaDto;
import pl.estrix.app.common.dto.model.*;
//import pl.estrix.app.common.dto.model.StockProductDto;
import pl.estrix.app.frontend.web.MainController;
import pl.estrix.app.frontend.web.product.product.ProductProductController;
import pl.estrix.app.frontend.web.product.product.model.TreeNodeDocument;
import pl.estrix.app.lib.download.DownloadUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;


@Getter
@Setter
@Scope(FacesViewScope.NAME)
@Component
@ManagedBean(name = "stockController")
public class StockController extends MainController {


    private static Logger LOG = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;


    private TreeNode root;
    private TreeNode selectedNode;
    private CheckboxTreeNode[] selectedNodes2;

    @Autowired
    private DownloadUtil downloadUtil;

    @Autowired
    private SendProductService sendProductService;

    @Autowired
    private ShopService shopService;

    private LazyDataModel<StockDto> lazyModel;

    private StockDto selected;

    private String searchText;
    private String selectedShop;
    private Map<String,String> shops = new HashMap<String, String>();
    private int number;

    private volatile boolean jobDone = false;

    @PostConstruct
    public void init() {
        super.updateOwnUrl();
        tablePageIndex = 1;
//        lazyModel = new StockLazyDataModel(stockService, searchText);
        lazyModel = new StockLazyDataModel(stockService, searchText);
        selected = new StockDto();
        ListResponseDto<ShopDto> responseDto = shopService.getItems(null,null);
        for (ShopDto shop: responseDto.getData()){
            shops.put(shop.getName(),shop.getId().toString());
        }

        root = new CheckboxTreeNode ("", null);
    }

    public void select(Long id) {
        if (id == null || id == 0) {
            selected = new StockDto();
            selected.setName("");
            selected.setApiUrl("");
            selected.setUrl("");
            selected.setUpdateHour("");
            selected.setSiteScratch(false);
        } else {
            selected = stockService.get(id);
        }
    }
    public void getProducts(Long id) {

        selected = stockService.get(id);

        stockService.downloadStockProduct(selected);
    }

    public void loadProgress(Long id){
        selected = stockService.get(id);
        SimpleAsyncTaskExecutor tasks = new SimpleAsyncTaskExecutor();
        tasks.submitListenable(new Callable<String>() {

            public String call() throws Exception {
                downloadUtil.downloadProduct(selected);
                jobDone = true;
                return "";
            }
        });
    }

    public void onDateSelectRefresh(SelectEvent event) {

    }

    public void save() {
        if (selected.getId() != null){
            stockService.update(selected);
        } else {
            stockService.create(selected);
        }
    }

    Integer pageIndex = 0;
    Integer pageLimit = 0;
    private ListResponseDto<StockProductDto> products;
    public void sendAllProductToShop(Integer selectedStock) {

        Long selectedShopId = Long.parseLong(selectedShop);
        System.out.println("sendAllProductToShop: " + selectedStock);
        System.out.println("selectedShop: " + selectedShopId);




        PagingCriteria pagingCriteria = PagingCriteria
                .builder()
                .start(0)
                .page(pageIndex)
                .limit(25)
                .build();
        products =  stockService.getProductItems(
                StockProductSearchCriteriaDto
                        .builder()
                        .selectedStock(selectedStock)
                        .build(),
                pagingCriteria);

        pageLimit = products.getTotalCount() / 25;

        System.out.println("products: " + products.isEmpty());
//        System.out.println("products: " + products.isEmpty();
        StringBuilder categories = new StringBuilder();
        List<Long> exeternalCatIds = new ArrayList<>();
        Long exeternalCatId = 0L;
        for(CheckboxTreeNode node : selectedNodes2) {
            TreeNodeDocument checkboxTreeNode = (TreeNodeDocument)node.getData();
            exeternalCatId = Long.parseLong(checkboxTreeNode.getSize());
            exeternalCatIds.add(exeternalCatId);
        }

        HashSet<String> idTree = new HashSet<>();
        for (Long ids : exeternalCatIds){
            for (Integer i : shopService.getCategories(selectedShopId,ids)){
                idTree.add(i.toString());
            }
        }
        String citiesCommaSeparated = idTree.stream().collect(Collectors.joining(","));
        categories.append(citiesCommaSeparated);

        SimpleAsyncTaskExecutor tasks = new SimpleAsyncTaskExecutor();
        tasks.submitListenable(new Callable<String>() {
            public String call() throws Exception {
                for (int i = 0; i< pageLimit;i++){
                    System.out.println("pages["+i+"]: " + pageLimit);
//                    pagingCriteria = ;

                    products =  stockService.getProductItems(
                            StockProductSearchCriteriaDto
                                    .builder()
                                    .selectedStock(selectedStock)
                                    .build(),
                            PagingCriteria
                                    .builder()
                                    .start(i*25)
//                                    .page(pageIndex)
                                    .limit(25)
                                    .build());
                    if(!products.isEmpty()){

                        products.getData().stream().forEach(product ->{

                System.out.println("product: " + product.getId());
                            SendProductDto sendProductDto = SendProductDto
                                    .builder()
                                    .productId(product.getId())
                                    .shopId(selectedShopId)
                                    .shopCategories(categories.toString())
                                    .price(product.getPrice())
                                    .margin(new BigDecimal(0))
                                    .stock(product.getStock())
                                    .processed(false)
                                    .code(product.getCode())
                                    .message("")
                                    .permalink("")
                                    .image(product.getImages().get(0).getUrl())
                                    .build();

                            sendProductService.create(sendProductDto);

                        });
                    }
                }
                return "";
            }
        });




    }

    public void handleChange(){
        ShopDto shopDto = shopService.get(Long.parseLong(selectedShop));
        LOG.debug("handleChange: " + shopDto);

        ShopCategorySearchCriteriaDto shopSearchCriteria = new ShopCategorySearchCriteriaDto();
        PagingCriteria shopPagingCriteria= new PagingCriteria();

        shopSearchCriteria.setType("woo");
        shopSearchCriteria.setParentId(0);
        shopSearchCriteria.setShopId(shopDto.getId());

        root.getChildren().clear();
        ListResponseDto<ShopCategoryDto> categories = shopService.getCategoryItems(shopSearchCriteria,shopPagingCriteria);
        if (!categories.isEmpty()) {
            for ( ShopCategoryDto shopCategoryDto : categories.getData()){
                CheckboxTreeNode treeNode = new CheckboxTreeNode(new TreeNodeDocument(shopCategoryDto.getName(), "" + shopCategoryDto.getExternalId(), "Folder"), root);
                createNode(treeNode, shopCategoryDto.getExternalId().intValue(), shopCategoryDto.getShopId().longValue());
                root.getChildren().add(treeNode);
            }
        }
    }

    private void createNode(CheckboxTreeNode rootItem, Integer parentId, Long shopId){
        ListResponseDto<ShopCategoryDto> subCat = shopService.getCategoryItems(ShopCategorySearchCriteriaDto.builder().parentId(parentId).shopId(shopId).build(),null);
        if (!subCat.isEmpty()) {
            for ( ShopCategoryDto sub1 : subCat.getData()){
                CheckboxTreeNode item = new CheckboxTreeNode(new TreeNodeDocument(sub1.getName(), "" + sub1.getExternalId(), "Folder"), root);
                createNode(item,sub1.getExternalId().intValue(), shopId);
                rootItem.getChildren().add(item);
            }
        }
    }

    public void delete() {
        stockService.delete(selected.getId());
    }

    public void search() {
        lazyModel = new StockLazyDataModel(stockService, searchText);
    }

    public void fakeListener(){
        System.out.println("fakeListener");
    }

    public void increment() {
        number++;
    }

    public Integer getTablePageIndex() {
        return tablePageIndex;
    }

    public void setTablePageIndex(Integer tablePageIndex) {
        this.tablePageIndex = tablePageIndex;
    }

//    public boolean isJobDone() {
//        return jobDone;
//    }
}
