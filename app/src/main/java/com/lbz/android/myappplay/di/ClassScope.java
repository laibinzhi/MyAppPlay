package com.lbz.android.myappplay.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by lbz on 2017/7/13.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface ClassScope {
}
