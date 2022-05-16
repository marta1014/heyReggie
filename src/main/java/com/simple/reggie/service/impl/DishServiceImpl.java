package com.simple.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.reggie.dto.DishDto;
import com.simple.reggie.entity.Dish;
import com.simple.reggie.entity.DishFlavor;
import com.simple.reggie.mapper.DishMapper;
import com.simple.reggie.service.DishFlavorService;
import com.simple.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    // 涉及多表操作 开始事务控制
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        Long id = dishDto.getId(); //菜品id

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }
}
