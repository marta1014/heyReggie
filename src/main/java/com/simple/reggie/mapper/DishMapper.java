package com.simple.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
