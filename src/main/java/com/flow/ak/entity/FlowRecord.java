package com.flow.ak.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import java.io.Serial;


/**
 * (FlowRecord)实体类
 *
 * @author ak.design 337547038
 * @since 2025-05-22 18:47:25
 */
@Data
public class FlowRecord implements Serializable {
    @Serial
    private static final long serialVersionUID = 272228870873621425L;

    private Integer id;
/**
     * 所属流程
     */
    private Integer flowId;
/**
     * 节点处理人
     */
    private String name;
/**
     * 处理时间
     */
    private Date dateTime;
/**
     * 处理意见
     */
    private String remark;
/**
     * 1同意 2拒绝 3返回发起人 4委托
     */
    private Integer status;


}

