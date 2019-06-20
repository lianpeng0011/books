package com.lianpeng.learning.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lianp
 * @date 2019/6/19 11:34
 * @since
 **/
@Data
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -1037937052253585195L;

    private String className;

    private String methodName;

    private Class<?>[] types;

    private Object[] params;
}
