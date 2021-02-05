package org.sacc.smis.service;

import org.sacc.smis.entity.ApplicationItem;
import org.sacc.smis.entity.Item;
import org.sacc.smis.entity.ItemType;
import org.sacc.smis.entity.ItemValue;
import org.springframework.stereotype.Service;

public interface ItemService {
    //    Item findItemByApplicationID(Integer applicationId);
    boolean addItem(Item item);

    boolean addItemValue(ItemValue itemValue);

    boolean addItemType(ItemType itemType);

    boolean addApplicationItem(ApplicationItem applicationItem);

    boolean submitItem(Item item, ItemValue itemValue, ItemType itemType, ApplicationItem applicationItem);
}
