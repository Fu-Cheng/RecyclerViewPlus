package com.chengfu.recyclerviewplus.visibility.scroll;

import com.chengfu.recyclerviewplus.visibility.items.ListItem;

/**
 * This interface is used by {@link com.chengfu.recyclerviewplus.visibility.calculator.SingleListViewItemActiveCalculator}.
 * Using this class to get {@link com.chengfu.recyclerviewplus.visibility.items.ListItem}
 *
 * @author Wayne
 */
public interface ItemsProvider {

    ListItem getListItem(int position);

    int listItemSize();

}
