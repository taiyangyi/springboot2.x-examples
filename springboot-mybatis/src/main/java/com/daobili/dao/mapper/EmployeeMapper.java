package com.daobili.dao.mapper;

import com.daobili.domain.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *
 * 员工管理模块的mapper组件
 * @author bamaw
 * @date 2021-03-31  23:09
 */
@Mapper
public interface EmployeeMapper {


    /**
     * 获取所有的员工信息
     * @return 所有员工清单
     */
    @Select("SELECT * FROM employee")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    List<Employee> selectAllEmployee();


    /**
     * 通过员工id查询对应的员工信息
     * @param id 员工id
     * @return 单个员工信息
     */
    @Select("SELECT * FROM employee WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    Employee selectEmployeeById(@Param("id") Long id);


    /**
     * 新增员工信息
     * @param employee 员工信息
     */
    @Insert("INSERT INTO employee(name,age) VALUES(#{name},#{age})")
    void insertEmployee(Employee employee);


    /**
     * 修改员工信息
     * @param employee 员工信息
     */
    @Update("UPDATE employee SET `name` = #{name}, age = #{age} WHERE id = #{id}")
    void updateEmployeeById(Employee employee);

    /**
     * 通过员工id 删除信息
     * @param id 员工id
     */
    @Delete("DELETE FROM employee WHERE id = #{id}")
    void deleteEmployeeById(@Param("id") Long id);

}
