package fr.an.test.sparkserver.rest;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public abstract class AbstractRestController {

    protected final String baseHttpPath;

    protected AbstractRestController(String baseHttpPath) {
        this.baseHttpPath = baseHttpPath;
    }

    protected <T> T withLog(String httpVerb, String httpPath, String arg,
                            Supplier<T> callback) {
        String displayMsg = "handle http " + httpVerb + " " + baseHttpPath + httpPath + ((arg != null) ? " " + arg : "");
        log.info(displayMsg + " ...");
        long startTime = System.currentTimeMillis();
        T res;
        try {
            res = callback.get();
            long millis = System.currentTimeMillis() - startTime;
            log.info(".. done " + displayMsg + ", took " + millis + "ms");
            return res;
        } catch(RuntimeException ex) {
            long millis = System.currentTimeMillis() - startTime;
            log.error(".. Failed " + displayMsg + ", after " + millis + "ms", ex);
            throw ex;
        }
    }

}
