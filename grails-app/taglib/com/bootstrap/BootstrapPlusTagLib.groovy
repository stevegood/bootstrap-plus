package com.bootstrap

import org.stevegood.taglib.TagLibHelper

class BootstrapPlusTagLib {
    static defaultEncodeAs = [taglib:'raw']
    static namespace = 'bsp'
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    // Panel
    def panel = { attrs, body ->
        def title = attrs.title
        def footer = attrs.footer
        def type = attrs.type ?: 'default'
        def (writer, builder) = TagLibHelper.writerAndMarkupBuilder

        builder.div(class: "panel panel-$type") {
            if (title) mkp.yieldUnescaped panelHeading(attrs, title)
            mkp.yieldUnescaped panelBody(attrs, body)
            if (footer) mkp.yieldUnescaped panelFooter(attrs, footer)
        }

        out << writer.toString()
    }

    def panelHeading = { attrs, body ->
        def h = attrs.h ?: '3'
        def (writer, builder) = TagLibHelper.writerAndMarkupBuilder
        build.div(class: 'panel-heading') {
            mkp.yieldUnescaped "<h$h class=\"panel-title\">${body()}</h$h>"
        }
        out << writer.toString()
    }

    def panelBody = { attrs, body ->
        def (writer, builder) = TagLibHelper.writerAndMarkupBuilder
        builder.div(class: 'panel-body') {
            mkp.yieldUnescaped body()
        }
        out << writer.toString()
    }

    def panelFooter = { attrs, body ->
        def (writer, builder) = TagLibHelper.writerAndMarkupBuilder
        builder.div(class: 'panel-footer') {
            mkp.yieldUnescaped body()
        }
        out << writer.toString()
    }
    // /Panel
}
