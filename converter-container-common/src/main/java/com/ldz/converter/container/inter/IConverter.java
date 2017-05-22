package com.ldz.converter.container.inter;

/**
 * Created by ldalzotto on 14/04/2017.
 */
public interface IConverter<A,B> {
    public B apply(A a);
}
