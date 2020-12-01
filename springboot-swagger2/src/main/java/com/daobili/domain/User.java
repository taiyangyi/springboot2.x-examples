package com.daobili.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author bamaw
 * @Date 2020-11-23  16:21
 * @Description
 */
@ApiModel(description = "用户类")
public class User implements Serializable {

    @ApiModelProperty("用户id")
    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户地址")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
