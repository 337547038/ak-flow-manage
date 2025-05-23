package com.flow.ak.entity;

import java.io.Serializable;
import lombok.Data;
import java.io.Serial;


/**
 * 用户表(User)实体类
 *
 * @author ak.design 337547038
 * @since 2025-05-22 17:30:06
 */
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -54640531665119219L;

    private Integer id;

    private String userName;
/**
     * 上级id
     */
    private Integer leaderId;


}

