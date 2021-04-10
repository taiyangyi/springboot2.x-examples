package com.daobili.controller;

import com.daobili.domain.Employee;
import com.daobili.service.EmployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 员工管理模块的控制器组件
 * @author bamaw
 * @date 2021-04-01  22:04
 */
@RestController
@RequestMapping(value = "/api/v1.0/employee")
public class EmployeeController {


    /**
     * 员工管理模块Service组件
     */
    @Autowired
    private EmployService employService;


    /**
     * 查询所有员工的信息
     *
     * @return 员工list集合
     */
    @GetMapping
    public List<Employee> getAllEmployee() {
        return employService.getAllEmployee();
    }

    /**
     * 通过员工id 查询对应的员工信息
     * @param id 员工id
     * @return 员工实体类
     */
    @GetMapping(value = "/find/{employeeId}")
    public Employee findEmployeeById(@PathVariable(name = "employeeId") Long id) {
        return employService.findEmployeeById(id);
    }

    /**
     * 保存员工信息
     *
     * @param employee 员工实体类
     */
    @PostMapping("/")
    public String saveEmployee(@RequestBody Employee employee) {
        employService.saveEmployee(employee);
        return "success";

    }

    /**
     * 根据员工id 更新员工信息
     *
     * @param employee 员工实体类
     */
    @PutMapping(value = "update/{employeeId}")
    public String updateEmployeeById(@PathVariable(value = "employeeId") Long employeeId,Employee employee) {
        employee.setId(employeeId);
        employService.updateEmployeeById(employee);
        return "success";
    }

    /**
     * 删除对应的员工信息
     *
     * @param id 员工id
     */
    @DeleteMapping(value = "remove/{employeeId}")
    public String removeEmployeeById(@PathVariable(value = "employeeId") Long id) {
        employService.removeEmployeeById(id);
        return "success";
    }

}
