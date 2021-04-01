package com.daobili.service.impl;

import com.daobili.dao.example.EmployeeDAO;
import com.daobili.domain.Employee;
import com.daobili.service.EmployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 员工管理模块Service组件接口实现类
 *
 * @author bamaw
 * @date 2021-04-01  22:00
 */
@Service
public class EmployServiceImpl implements EmployService {


    /**
     * 员工管理模块DAO组件
     */
    @Autowired
    private EmployeeDAO employeeDAO;


    /**
     * 查询所有员工的信息
     *
     * @return 员工list集合
     */
    @Override
    public List<Employee> getAllEmployee() {
        return employeeDAO.getAllEmployee();
    }

    /**
     * 通过员工id 查询对应的员工信息
     * @param id 员工id
     * @return 员工实体类
     */
    @Override
    public Employee findEmployeeById(Long id) {
        return employeeDAO.findEmployeeById(id);
    }

    /**
     * 保存员工信息
     *
     * @param employee 员工实体类
     */
    @Override
    public void saveEmployee(Employee employee) {
        employeeDAO.saveEmployee(employee);
    }

    /**
     * 根据员工id 更新员工信息
     *
     * @param employee 员工实体类
     */
    @Override
    public void updateEmployeeById(Employee employee) {
        employeeDAO.updateEmployeeById(employee);
    }

    /**
     * 删除对应的员工信息
     *
     * @param id 员工id
     */
    @Override
    public void removeEmployeeById(Long id) {
        employeeDAO.removeEmployeeById(id);
    }
}
