package plazma.ups.base.reflection;

import java.io.Serializable;

public class MyReflectionClass implements Serializable, Cloneable {

    public String id;
    private String code = "-";
    protected String name;
    private int count = 10;
    private float percent = 100.0f;

    public void init() {
        System.out.println("init");
    }

    public void start() {
        System.out.println("start");
    }

    public void stop() {
        System.out.println("stop");
    }

    protected void prepare() {
        System.out.println("prepare");
    }

    private final void reset() {
        System.out.println("reset");
    }

    public final float calculate(float x) {
        return x * x;
    }

}
