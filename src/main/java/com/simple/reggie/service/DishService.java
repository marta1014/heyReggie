package com.simple.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.reggie.dto.DishDto;
import com.simple.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    // 新增菜品 同时插入对应的口味数据 需要操作两张表
    public void saveWithFlavor(DishDto dishDto);
}
