package pl.estrix.app.lib.woocommerce.model;

import java.util.Comparator;

public class WooCategoryDtoSort implements Comparator<WooCategoryDto>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(WooCategoryDto a, WooCategoryDto b)
    {
        return a.getParent() - b.getParent();
    }
}