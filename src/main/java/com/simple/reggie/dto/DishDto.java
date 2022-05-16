package com.simple.reggie.dto;

import com.simple.reggie.entity.Dish;
import com.simple.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO Date Transfer Object
 *
 * 数据传输对象 用于展示层和服务层的数据传输
 */

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
