package org.stevegood.taglib

import groovy.xml.MarkupBuilder

/**
 * @author <a href="http://stevegood.org">Steve Good</a>
 * @since 11/9/14.
 */
class TagLibHelper {

    static def getWriterAndMarkupBuilder() {
        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)
        [writer, builder]
    }

}
