package pl.estrix.app.frontend.web.product.product;

import lombok.Getter;
import lombok.Setter;
import org.firebirdsql.logging.Logger;
import org.firebirdsql.logging.LoggerFactory;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.send.service.SendProductService;
import pl.estrix.app.backend.shop.service.ShopService;
import pl.estrix.app.backend.stock.service.StockService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.ShopCategorySearchCriteriaDto;
import pl.estrix.app.common.dto.StockProductSearchCriteriaDto;
import pl.estrix.app.common.dto.StockSearchCriteriaDto;
import pl.estrix.app.common.dto.model.*;
import pl.estrix.app.frontend.web.MainController;
import pl.estrix.app.frontend.web.product.product.model.TreeNodeDocument;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Scope(FacesViewScope.NAME)
@Component
@ManagedBean(name = "productProductController")
public class ProductProductController extends MainController {


    private static Logger LOG = LoggerFactory.getLogger(ProductProductController.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private SendProductService sendProductService;

    private LazyDataModel<StockProductDto> lazyModel;

    private StockProductDto selected;
    private StockProductDto selectedProduct;

    private StockProductSearchCriteriaDto stockProductSearchCriteriaDto = new StockProductSearchCriteriaDto();

    private BigDecimal marginPrice = new BigDecimal(0);
    private BigDecimal priceToSend = new BigDecimal(0);
    private Integer stockToSend = 0;

    private String searchText;
    private Integer setPage = 0;

    private TreeNode root;
    private TreeNode selectedNode;
    private CheckboxTreeNode[] selectedNodes2;

    private DataTable dataTable;

    private String console;

//    private SelectItem theme;
//    private List<SelectItem> themes = new ArrayList<>();

    private String country;
    private Map<String,String> countries;

    private String selectedShop;
    private Map<String,String> shops = new HashMap<String, String>();

    private Integer selectedStock;
    private Map<String,String> stocks = new HashMap<String, String>();

    @PostConstruct
    public void init() {
        super.init();
        super.updateOwnUrl();
        lazyModel = new ProductProductLazyDataModel(stockService, searchText);
        selected = new StockProductDto();

        ListResponseDto<ShopDto> responseDto = shopService.getItems(null,null);
        for (ShopDto shop: responseDto.getData()){
            shops.put(shop.getName(),shop.getId().toString());
        }

        ListResponseDto<StockDto> responseStockDto = stockService.getItems(StockSearchCriteriaDto.builder().active(true).build(),null);
        for (StockDto stock: responseStockDto.getData()){
            stocks.put(stock.getName(),stock.getId().toString());
        }
//        root = new DefaultTreeNode("", null);
        root = new CheckboxTreeNode ("", null);
//        root = createCheckboxDocuments();
    }

    public void refreshPriceToSend(){
        priceToSend = selected.getPriceRetail().add(marginPrice);
    }

    public void handleChange2(){
        LOG.debug("selectedStock: " + selectedStock);
    }

    public void handleChange(){
        ShopDto shopDto = shopService.get(Long.parseLong(selectedShop));

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

    public void onRowSelect() {
        priceToSend = selected.getPriceRetail().add(marginPrice);
        stockToSend = selected.getStock() + selected.getStockExt();
    }

    public void select(Long id) {
        if (id == null || id == 0) {
            selected = new StockProductDto();
            selected.setName("");
        } else {
            selected = stockService.getProduct(id);
            priceToSend = selected.getPriceRetail();
            stockToSend = selected.getStock() + selected.getStockExt();
        }
    }

    public void onDateSelectRefresh(SelectEvent event) {

    }

    public void shopSendProduct(){
        Long selectedShopId = Long.parseLong(selectedShop);

        StringBuilder categories = new StringBuilder();
        if(selectedNodes2 != null && selectedNodes2.length > 0) {
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

            SendProductDto sendProductDto = SendProductDto
                    .builder()
                    .productId(selected.getId())
                    .shopId(selectedShopId)
                    .shopCategories(categories.toString())
                    .price(priceToSend)
                    .margin(marginPrice)
                    .stock(stockToSend)
                    .processed(false)
                    .code("")
                    .message("")
                    .permalink("")
                    .image(selected.getImages().get(0).getThumb())
                    .build();

            sendProductService.create(sendProductDto);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Produkt", "Dodano do wysyłki");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }


    }

    public void olxSendProduct(){

    }
    public void allegroSendProduct(){

    }

    public void save() {
        if (selected.getId() != null){
//            stockService.update(selected);
        } else {
//            stockService.create(selected);
        }
    }
    public void delete() {
        stockService.delete(selected.getId());
    }

    public void switchPage() {
        tablePageIndex = setPage;
        LOG.debug("setPage: " + setPage);
        setTablePageIndex(setPage);
    }
    public void search() {
        LOG.debug("searchText: " + searchText);
        LOG.debug("selectedStock: " + selectedStock);

        stockProductSearchCriteriaDto.setSelectedStock(selectedStock);
        stockProductSearchCriteriaDto.setTableSearch(searchText);

        lazyModel = new ProductProductLazyDataModel(stockService, stockProductSearchCriteriaDto);
    }

    public Integer getTablePageIndex() {
        return tablePageIndex;
    }

    public void setTablePageIndex(Integer tablePageIndex) {
        this.tablePageIndex = tablePageIndex;
    }
}
