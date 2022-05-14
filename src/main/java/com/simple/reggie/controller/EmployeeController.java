package com.simple.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.reggie.common.R;
import com.simple.reggie.entity.Employee;
import com.simple.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {

        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if(emp == null) {
            return R.error("登陆失败,用户不存在");
        }
        if(!emp.getPassword().equals(password)) {
            return R.error("登陆失败,密码错误");
        }
        if(emp.getStatus() == 0) {
            return R.error("登陆失败,用户已禁用");
        }

        httpServletRequest.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工信息：{}",employee.toString());

        // 设置初始密码 进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name) {
        log.info("page = {}, pageSize = {} , name = {}",page,pageSize,name);
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        // 构造条件构造器 添加条件
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper();
        // StringUtils.isNotEmpty不能用？
        qw.like(StringUtils.hasLength(name),Employee::getName,name);
        // 添加排序条件
        qw.orderByDesc(Employee::getUpdateTime); // Employee里面的UpdateTime => Employee::getUpdateTime
        // 执行查询
        employeeService.page(pageInfo,qw);
        return R.success(pageInfo);
    }
}
