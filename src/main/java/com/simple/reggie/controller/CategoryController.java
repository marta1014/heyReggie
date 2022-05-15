package com.simple.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.reggie.common.R;
import com.simple.reggie.entity.Category;
import com.simple.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("新增分类成功～");
    }

    @GetMapping("/page")
    public R<Page> pageFn(int page,int pageSize) {
        Page<Category> p = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
        qw.orderByAsc(Category::getSort); // 升序排序

        categoryService.page(p,qw);
        return R.success(p);
    }

    @DeleteMapping
    public R<String> deleteFn(Long id) {
        log.warn("delete by id :{}",id);
        categoryService.removeById(id);
        return R.success("很low 拿不到id");
    }
}
