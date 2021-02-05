package org.sacc.smis.service.impl;

import org.sacc.smis.entity.*;
import org.sacc.smis.mapper.*;
import org.sacc.smis.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ApplicationItemRepository applicationItemRepository;
    @Autowired
    private ItemValueRepository itemValueRepository;
    @Autowired
    private ItemTypeRepository itemTypeRepository;


    /**
     * 提交表单
     *
     * @param item
     * @return
     */
    @Override
    public boolean addItem(Item item) {
        ApplicationItem applicationItem = new ApplicationItem();
        applicationItem.setApplicationId(item.getId());
        applicationItem.setItemId(item.getId());
        itemRepository.save(item);
        applicationItemRepository.save(applicationItem);
        return true;
    }

    @Override
    public boolean addItemValue(ItemValue itemValue) {
        itemValueRepository.save(itemValue);
        return true;
    }

    @Override
    public boolean addItemType(ItemType itemType) {
        itemTypeRepository.save(itemType);
        return true;
    }

    @Override
    public boolean addApplicationItem(ApplicationItem applicationItem) {
        applicationItemRepository.save(applicationItem);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)//添加回滚支持
    public boolean submitItem(Item item, ItemValue itemValue, ItemType itemType, ApplicationItem applicationItem) {
        addItemType(itemType);
        item.setItemTypeId(itemType.getId());
        addItem(item);
        itemValue.setItemId(item.getId());
        addItemValue(itemValue);
        addApplicationItem(applicationItem);
        return true;
    }

}
