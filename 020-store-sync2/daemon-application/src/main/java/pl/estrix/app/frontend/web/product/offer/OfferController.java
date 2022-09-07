package pl.estrix.app.frontend.web.product.offer;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.send.service.SendProductService;
import pl.estrix.app.backend.shop.service.ShopService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.ShopCategorySearchCriteriaDto;
import pl.estrix.app.common.dto.model.SendProductDto;
import pl.estrix.app.common.dto.model.ShopCategoryDto;
import pl.estrix.app.common.dto.model.ShopDto;
import pl.estrix.app.frontend.web.MainController;
import pl.estrix.app.frontend.web.product.product.model.TreeNodeDocument;
import pl.estrix.app.lib.woocommerce.WooCommerceUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Scope(FacesViewScope.NAME)
@Component
@ManagedBean(name = "offerController")
public class OfferController extends MainController {

    private int number;

    @Autowired
    private SendProductService sendProductService;
    @Autowired
    private ShopService shopService;

    private LazyDataModel<SendProductDto> lazyModel;

    private SendProductDto selected;

    private String searchText;


    private TreeNode root;
    private CheckboxTreeNode[] selectedNodes2;

    @PostConstruct
    public void init() {
        super.updateOwnUrl();
        tablePageIndex = 1;
        lazyModel = new OfferLazyDataModel(sendProductService, searchText);
        selected = new SendProductDto();

        root = new CheckboxTreeNode ("", null);
    }


    public void onRowSelect() {
        ShopCategorySearchCriteriaDto shopSearchCriteria = new ShopCategorySearchCriteriaDto();
        PagingCriteria shopPagingCriteria= new PagingCriteria();


        shopSearchCriteria.setType("woo");
        shopSearchCriteria.setParentId(0);
        shopSearchCriteria.setShopId(selected.getShopId());
//        shopSearchCriteria.setShopId(shopDto.getId());


        root.getChildren().clear();
        ListResponseDto<ShopCategoryDto> categories = shopService.getCategoryItems(shopSearchCriteria,shopPagingCriteria);
        if (!categories.isEmpty()) {
            for ( ShopCategoryDto shopCategoryDto : categories.getData()){
                CheckboxTreeNode treeNode = new CheckboxTreeNode(new TreeNodeDocument(shopCategoryDto.getName(), "-" + shopCategoryDto.getExternalId(), "Folder"), root);
                treeNode.setExpanded(true);
                createNode(treeNode, shopCategoryDto.getExternalId().intValue(), shopCategoryDto.getShopId().longValue());


                root.getChildren().add(treeNode);
            }
        }
//        priceToSend = selected.getPriceRetail().add(marginPrice);
//        stockToSend = selected.getStock() + selected.getStockExt();
    }

    private void createNode(CheckboxTreeNode rootItem, Integer parentId, Long shopId){
        ListResponseDto<ShopCategoryDto> subCat = shopService.getCategoryItems(ShopCategorySearchCriteriaDto.builder().parentId(parentId).shopId(shopId).build(),null);
        if (!subCat.isEmpty()) {
            for ( ShopCategoryDto sub1 : subCat.getData()){
                CheckboxTreeNode item = new CheckboxTreeNode(new TreeNodeDocument(sub1.getName(), "" + sub1.getExternalId(), "Folder"), root);
                createNode(item,sub1.getExternalId().intValue(), shopId);
                if (selected.getShopCategories().contains(sub1.getExternalId().toString())){
//                    rootItem.isSelected()
                    CheckboxTreeNode parent  = (CheckboxTreeNode)item.getParent();
//                    item.setExpanded(true);
//                    if(rootItem.isSelected()){
//                        rootItem.setSelected(false);
//                        rootItem.setExpanded(true);
//                    }

                    if (item.getChildCount() == 0) {
//                        item.getParent().setExpanded(true);
                        item.setExpanded(true);
                        item.setSelected(true);
                    }
                }
                rootItem.getChildren().add(item);
            }
        }
    }

    public void select(Long id) {
        if (id == null || id == 0) {
            selected = new SendProductDto();
//            selected.setName("");
        } else {
            selected = sendProductService.get(id);
        }
    }


    public void refreshPriceToSend(){
        System.out.println("refreshPriceToSend");
    }

    public void onDateSelectRefresh(SelectEvent event) {

    }

    public void save() {
        if (selected.getId() != null){
            if(selectedNodes2 != null && selectedNodes2.length > 0) {
                StringBuilder categories = new StringBuilder();
                List<Long> exeternalCatIds = new ArrayList<>();
                Long exeternalCatId = 0L;
                for(CheckboxTreeNode node : selectedNodes2) {
                    TreeNodeDocument checkboxTreeNode = (TreeNodeDocument)node.getData();
                    exeternalCatId = Long.parseLong(checkboxTreeNode.getSize());
                    exeternalCatIds.add(exeternalCatId);
                }
                Long selectedShopId = selected.getShopId();
                HashSet<String> idTree = new HashSet<>();
                for (Long ids : exeternalCatIds){
                    for (Integer i : shopService.getCategories(selectedShopId,ids)){
                        idTree.add(i.toString());
                    }
                }
                String citiesCommaSeparated = idTree.stream().collect(Collectors.joining(","));
                categories.append(citiesCommaSeparated);

                selected.setShopCategories(categories.toString());
            }


            selected.setProcessed(false);
            sendProductService.update(selected);
//        } else {
//            shopService.create(selected);
        }
    }
    public void delete() {
        sendProductService.delete(selected.getId());
    }

    public void search() {
        lazyModel = new OfferLazyDataModel(sendProductService, searchText);
    }


    public Integer getTablePageIndex() {
        return tablePageIndex;
    }

    public void setTablePageIndex(Integer tablePageIndex) {
        this.tablePageIndex = tablePageIndex;
    }
}
