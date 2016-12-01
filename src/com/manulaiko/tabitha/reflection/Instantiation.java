package com.manulaiko.tabitha.reflection;

import com.manulaiko.tabitha.Console;

/**
 * Helper methods for dynamically instancing objects.
 *
 * One of the things I love from PHP is the easy way to instance objects
 * dynamically, is as easy as this:
 *
 *     $name      = "TestObject";
 *     $arguments = ["arg1", false, 1234];
 *
 *     $object = new $name(... $arguments)
 *
 * In java it can be quite complex, this class will solve the problem.
 *
 * The method `instance` accepts as parameter a string being the class name to instance.
 *
 * For example:
 *
 *     String name = "TestObject";
 *
 *     TestObject obj = (TestObject)Instantiation.instance(name);
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Instantiation
{
    /**
     * Instances and returns an object of type `name`.
     *
     * @param name Name of the class to instance.
     *
     * @return Instance of `name`.
     */
    public static Object instance(String name)
    {
        Object o = null;
        try {
            Class c = Class.forName(name);
            o       = c.newInstance();
        } catch(ClassNotFoundException e) {
            Console.println("Class " + name + " doesn't exist!");

            return null;
        } catch(Exception e) {
            Console.println("Couldn't instance " + name + "!");
        }

        return o;
    }
}
