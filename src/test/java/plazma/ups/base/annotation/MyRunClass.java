package plazma.ups.base.annotation;

public class MyRunClass {

    public void init() {
        System.out.println("init");
    }

    @Run
    public void run() {
        System.out.println("run");
    }

    public void stop() {
        System.out.println("stop");
    }

    @Run
    public void call() {
        System.out.println("call");
    }

}