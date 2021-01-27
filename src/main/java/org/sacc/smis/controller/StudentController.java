package org.sacc.smis.controller;


import org.sacc.smis.entity.ApplicationItem;
import org.sacc.smis.entity.Item;
import org.sacc.smis.entity.ItemType;
import org.sacc.smis.entity.ItemValue;
import org.sacc.smis.mapper.ItemTypeMapper;
import org.sacc.smis.model.RestResult;
import org.sacc.smis.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

//学生提交表单API
@RestController

public class StudentController {

    @Autowired
    private ItemService itemService;


    /**
     *
     * @param application_id
     * @param name
     * @param textContentValue
     * @param typeContentValue
     * @param user_id
     * @return
     */
    //提交表单
    @RequestMapping(value = "/submit_item",method = RequestMethod.POST)
    public RestResult<Boolean> submit(@RequestParam("application_id")  Integer application_id,
                                      @RequestParam("name") String name,
                                      @RequestParam("textContentValue") String textContentValue,
                                      @RequestParam("content_type") String content_type,
                                      @RequestParam("typeContentValue") String typeContentValue,
                                      @RequestParam("user_id")  Integer user_id){
        Item item = new Item();
        ItemValue itemValue = new ItemValue();
        ItemType itemType = new ItemType();
        ApplicationItem applicationItem = new ApplicationItem();
       //封装ItemType
        itemType.setName(content_type);
        itemType.setDataSource(typeContentValue);
        //封装ItemValue
        itemValue.setValue(textContentValue);
        itemValue.setUserId(user_id);
        //封装Item
        item.setName(name);
        //封装ApplicationItem
        applicationItem.setApplicationId(application_id);
        return RestResult.success(200, itemService.submitItem(item,itemValue,itemType,applicationItem));//提交Item

    }
}
