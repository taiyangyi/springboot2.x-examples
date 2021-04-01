package com.daobili.dao.example.impl;

import com.daobili.dao.example.EmployeeDAO;
import com.daobili.dao.mapper.EmployeeMapper;
import com.daobili.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 员工管理模块的DAO组件接口实现类
 *
 * @author bamaw
 * @date 2021-04-01  21:32
 */
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {


    /**
     * 员工管理模块的mapper组件
     */
    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public List<Employee> getAllEmployee() {
        return employeeMapper.selectAllEmployee();
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return employeeMapper.selectEmployeeById(id);
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeMapper.insertEmployee(employee);

    }

    @Override
    public void updateEmployeeById(Employee employee) {
        employeeMapper.updateEmployeeById(employee);

    }

    @Override
    public void removeEmployeeById(Long id) {
        employeeMapper.deleteEmployeeById(id);

    }
}
