package org.course.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class LoggingExtension implements BeforeAllCallback, BeforeEachCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        log.info( "before all" +extensionContext.getDisplayName() );
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {

        log.info("before each test method name is{}", extensionContext.getDisplayName());
    }

}
