package org.testng.util

import org.codehaus.groovy.reflection.MixinInMetaClass

import java.lang.reflect.Method

/**
 * Created with IntelliJ IDEA.
 * User: Michael
 * Date: 24.08.13
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */
class GroovyClassHelper {
  static final getAllMethods(Class cls)
  {
    Method[] methods = cls.getMethods();
    MixinInMetaClass[] mixin = cls.getMetaClass().mixinClasses;
    mixin.each{
      methods = methods.plus(it.mixinClass.methods.collect{ it.cachedMethod });
    };

    return methods;
  }

  static final boolean isAccessibleFrom(Class cls, Method method)
  {
    return getAllMethods(cls).contains(method)
  }

  static final boolean isAssignableFrom(Class cls, Class assigned)
  {
    def mixin = cls.getMetaClass().mixinClasses.collect { it.mixinClass.name };

    return assigned.isAssignableFrom(cls) || mixin.contains(assigned.name)
  }
}
