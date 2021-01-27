package org.sacc.smis.controller;


import com.sun.org.apache.xpath.internal.operations.Bool;
import org.sacc.smis.entity.ApplicationItem;
import org.sacc.smis.entity.Item;
import org.sacc.smis.entity.ItemType;
import org.sacc.smis.entity.ItemValue;
import org.sacc.smis.model.RestResult;
import org.sacc.smis.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

//学生提交表单API
@RestController
@Secured("ROLE_STUDENT")
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
    @PostMapping("/item")
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

    @GetMapping("/item/{item_id}")
    public RestResult<Item> getItemById(@PathVariable("item_id") Integer itemId){
        return null;
    }

    @PutMapping("/item")
    public RestResult<Boolean> updateItem(@RequestBody Item item){
        return null;
    }



}
