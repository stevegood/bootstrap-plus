package org.stevegood.taglib

/**
 * @author <a href="http://stevegood.org">Steve Good</a>
 * @since 11/9/14.
 */
class BootstrapPlusException extends Exception {

    String tagName

    BootstrapPlusException(String message, String tagName) {
        super(message)
        this.tagName = tagName
    }

}
