package com.lianpeng.learning.api;

/**
 * @author lianp
 * @date 2019/6/19 11:31
 * @since
 **/
public interface ICalculateService {

    /**
     * 相加
     *
     * @param a
     * @param b
     * @return
     */
    int add( int a, int b );


    /**
     * 减
     *
     * @param a
     * @param b
     * @return
     */
    int sub( int a, int b );

    /**
     * 乘
     *
     * @param a
     * @param b
     * @return
     */
    int mult( int a, int b );


    /**
     * 初
     *
     * @param a
     * @param b
     * @return
     */
    int div( int a, int b );
}
