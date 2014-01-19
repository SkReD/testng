package org.testng.util

import org.codehaus.groovy.reflection.CachedClass
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

    def superclass = cls.getSuperclass()
    if (superclass)
    {
      methods = methods.plus(getAllMethods(superclass))
    }

    return methods;
  }

  static final boolean isAccessibleFrom(Class cls, Method method)
  {
    return getAllMethods(cls).contains(method)
  }

  static final CachedClass[] getAllMixins(Class cls)
  {
    CachedClass[] mixin = cls.getMetaClass().mixinClasses.collect{it.mixinClass};

    def superclass = cls.getSuperclass()
    if (superclass)
    {
      mixin = mixin.plus(getAllMixins(superclass))
    }

    return mixin
  }

  static final boolean isAssignableFrom(Class cls, Class assigned)
  {
    return assigned.isAssignableFrom(cls) || getAllMixins(cls).collect{it.name}.contains(assigned.name)
  }
}
