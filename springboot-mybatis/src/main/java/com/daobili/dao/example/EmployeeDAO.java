package com.daobili.dao.example;

import com.daobili.domain.Employee;

import java.util.List;

/**
 * 员工管理模块的DAO组件接口
 *
 * @author bamaw
 * @date 2021-04-01  21:31
 */
public interface EmployeeDAO {

    /**
     * 查询所有员工的信息
     *
     * @return 员工list集合
     */
    List<Employee> getAllEmployee();

    /**
     * 通过员工id 查询对应的员工信息
     * @param id 员工id
     * @return 员工实体类
     */
    Employee findEmployeeById(Long id);

    /**
     * 保存员工信息
     *
     * @param employee 员工实体类
     */
    void saveEmployee(Employee employee);

    /**
     * 根据员工id 更新员工信息
     *
     * @param employee 员工实体类
     */
    void updateEmployeeById(Employee employee);

    /**
     * 删除对应的员工信息
     *
     * @param id 员工id
     */
    void removeEmployeeById(Long id);
}
