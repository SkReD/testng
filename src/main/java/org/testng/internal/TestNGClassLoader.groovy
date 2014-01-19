package org.testng.internal

class TestNGClassLoader extends GroovyClassLoader {
  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    return super.loadClass(name, true);
  }
}
