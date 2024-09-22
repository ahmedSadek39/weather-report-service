package com.app.weather.utility.htttp;

@FunctionalInterface
public interface RequestHandlerInterface<T> {
    T process() throws Exception;
}