package com.fall.securitydemo02.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author FAll
 * @date 2023/4/4 18:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @TableId
    private Long id;

    private String userName;

    private String nickName;

    private String password;

    private String status;

    private String email;

    private String phonenumber;

    private String sex;

    private String avatar;

    private String userType;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private Integer delFlag;

}
