package arpinum.boundedcontext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class BoundedContext {


    public abstract String name();


    private static final Logger LOGGER = LoggerFactory.getLogger(BoundedContext.class);

}
