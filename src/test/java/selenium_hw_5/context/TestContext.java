package selenium_hw_5.context;

import java.util.HashMap;

public class TestContext {

    private static TestContext instance;

    private HashMap<String, Object> context = new HashMap<>();

    private TestContext() {}

    public TestContext putTestObject(String key, Object value) {
        context.put(key, value);
        return this;
    }

    public <T> T getTestObject(String key) {
        return (T) context.get(key);
    }

    public void clean() {
        instance = null;
        context.clear();
    }

    public static TestContext getInstance() {
        if (instance == null) {
            return instance = new TestContext();
        }
        return instance;
    }
}
