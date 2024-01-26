package fr.an.exprlib.sql.eval;

import java.util.LinkedHashMap;
import java.util.Map;

public class EvalCtx {

    protected Map<String,Object> props = new LinkedHashMap<>();

    public <T> void putProp(String key, T value) {
        this.props.put(key, value);
    }

    public <T> T getProp(String key) {
        return (T) this.props.get(key);
    }

}
