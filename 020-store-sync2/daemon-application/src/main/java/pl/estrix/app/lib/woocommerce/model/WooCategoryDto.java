package pl.estrix.app.lib.woocommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WooCategoryDto implements Serializable {

    private Integer id;
    private Integer parent;
    private String name;
//    private String slug;
//    private String description;
//    private String display;
//    private String image;
//    private Integer menu_order;
//    private Integer count;
    private WooCategoryDto parentcategory;
    private List<WooCategoryDto> subcategory = new ArrayList<>();

    public WooCategoryDto findInChild(Integer parentId, Integer index, Boolean debug) {
        WooCategoryDto result = null;
        if (debug) System.out.println("1.["+index+"]: " + this.getId() + " : " + parentId);
        if (this.getId() == parentId) {
            result = this;
        }

        for (WooCategoryDto category : this.getSubcategory()) {
            if (debug) System.out.println("2.["+index+"]: " + category.getId() + " : " + parentId);
            if (category.getId() == parentId) {
                result = category;
                break;
            }

            if (category.getSubcategory().size() > 0){
                result = category.findInChild(parentId, index+1, debug);
            }
        }
        return result;
    }



    public List<Integer> getParentIds(int i, Integer index, Boolean debug) {
        List<Integer> resultIds = new ArrayList<>();
        if (this.getId() == i) {
            resultIds.add(this.getId());
        }

        for (WooCategoryDto category : this.getSubcategory()) {
            if (category.getId() == i) {

                if (category.getParentcategory() != null){
                    resultIds.add(category.getId());
                    if (category.getParentcategory().getParentcategory() != null){
                        resultIds.add(category.getParentcategory().getId());
                        if (category.getParentcategory().getParentcategory().getParentcategory() != null){
                            resultIds.add(category.getParentcategory().getParentcategory().getId());
                        }
                    }
                }

                break;
            }

            if (category.getSubcategory().size() > 0){
                resultIds.addAll(category.getParentIds(i, index+1, debug));
            }
        }
        return resultIds;
    }

    public WooCategoryDto printTree(Integer index){
        for (WooCategoryDto category : this.subcategory) {

            for (Integer i=0; i < index; i++){
                System.out.print("\t");
            }
            System.out.println("" + category.getName() + "["+category.getId()+"/"+category.getParent()+"]");

            if (category.getSubcategory().size() > 0){
                category.printTree(index+1);

            }
        }
        return null;
    }


    private List<WooCategoryDto> flatList = new ArrayList<>();

    public List<WooCategoryDto> getFlatList() {
        return flatList;
    }

    private static Map<Integer, String> map = new HashMap<>();

    public Map<Integer, String> printFlatTree(Integer index){
        for (WooCategoryDto category : this.subcategory) {
            StringBuilder name = new StringBuilder();
            for (Integer i=0; i < index; i++){
                System.out.print("\t");
                name.append("-");
            }
            name.append(category.getName());
            map.put(category.getId(), name.toString());

//            category.setName(name.toString());
            System.out.println("*" + category.getName() + "["+category.getId()+"/"+category.getParent()+"]");
//            flatList.add(category);
            if (category.getSubcategory().size() > 0){
                category.printFlatTree(index+1);
            }
        }
        return map;
    }


    private static List<WooCategoryDto> categoryDtoList = new ArrayList<>();
    public List<WooCategoryDto> printFlatList(Integer index){
//
        for (WooCategoryDto category : this.subcategory) {
            StringBuilder name = new StringBuilder();
            for (Integer i=0; i < index; i++){
                System.out.print("\t");
                name.append("-");
            }
            name.append(category.getName());
//            map.put(category.getId(), name.toString());
            categoryDtoList.add(category);

//            System.out.println("*" + category.getName() + "["+category.getId()+"/"+category.getParent()+"]");

            if (category.getSubcategory().size() > 0){
                category.printFlatList(index+1);
            }
        }
        return categoryDtoList;
    }

    public static List<WooCategoryDto> getCategoryDtoList() {
        return categoryDtoList;
    }

    @Override
    public String toString() {
        return "WooCategoryDto{" +
                "id=" + id +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", subcategory='" + subcategory.size() + '\'' +
                '}';
    }


}


