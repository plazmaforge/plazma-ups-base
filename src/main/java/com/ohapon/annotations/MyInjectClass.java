package com.ohapon.annotations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyInjectClass {

    String id;

    @Inject(clazz = String.class)
    String name;

    @Inject(clazz = ArrayList.class)
    List list1;

    @Inject(clazz = LinkedList.class)
    List list2;

}
