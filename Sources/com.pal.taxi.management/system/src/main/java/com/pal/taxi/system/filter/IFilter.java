package com.pal.taxi.system.filter;

import java.util.function.Predicate;

/**Filter needs to be applied while filtering*/
public interface IFilter<T> extends Predicate<T>{

}
