package com.bootstrap

import org.stevegood.taglib.BootstrapPlusException
import org.stevegood.taglib.TagLibHelper

class BootstrapPlusTagLib {
    static defaultEncodeAs = [taglib:'raw']
    static namespace = 'bsp'
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    protected def getWriterAndMarkupBuilder() {
        TagLibHelper.writerAndMarkupBuilder
    }

    protected String simpleDiv(attrs, body) {
        def (writer, builder) = writerAndMarkupBuilder
        builder.div(attrs) {
            mkp.yieldUnescaped body()
        }
        writer.toString()
    }

    // Container
    def container = { attrs, body ->
        attrs.prepend 'class', 'container'
        out << simpleDiv(attrs, body)
    }
    // /Container

    // Row
    def row = { attrs, body ->
        attrs.prepend 'class', 'row'
        out << simpleDiv(attrs, body)
    }
    // /Row

    // Col
    def col = { attrs, body ->
        if (!attrs.'col-size') throw new BootstrapPlusException("Attribute ['col-size'] is required for the ['col'] tag!", 'col')
        def colSizeList = attrs.'col-size'.split(',')
        colSizeList*.replaceAll('col-','')
        attrs.prepend 'class', "${colSizeList.collect{ "col-${it.trim()}" }.join(' ')}".trim()
        attrs.remove('col-size')
        out << simpleDiv(attrs, body)
    }
    // /Col

    // Page header
    def pageHeader = { attrs, body ->
        def (writer, builder) = writerAndMarkupBuilder
        attrs.prepend 'class', 'page-header'

        builder.div(attrs) {
            mkp.yieldUnescaped body()
        }

        out << writer.toString()
    }
    // /Page header

    // Button
    def button = {attrs, body ->
        def container = attrs.container ?: 'button'
        def type = attrs.type
        if (!type) type = 'default'

        def size = attrs.size
        if (size) size = "btn-${size.replaceAll('btn-','')}"

        def icon = attrs.icon
        def iconPosition = attrs.iconPosition ?: 'left'
        def iconProps = [:]
        if (icon) {
            icon = icon.replaceAll('glyphicon-','')
            iconProps['class'] = "glyphicon glyphicon-$icon"
        }

        attrs.prepend 'class', "btn btn-$type ${size ?: ''}".trim()
        ['container','type','size','icon','iconPosition'].each { attrs.remove(it) }

        def (writer, builder) = writerAndMarkupBuilder

        builder."$container"(attrs){
            if (icon && iconPosition == 'left') i(iconProps)
            mkp.yieldUnescaped body()
            if (icon && iconPosition == 'right') i(iconProps)
        }

        out << writer.toString()
    }

    def defaultButton = { attrs, body ->
        attrs.type = 'default'
        out << button(attrs, body)
    }

    def primaryButton = { attrs, body ->
        attrs.type = 'primary'
        out << button(attrs, body)
    }

    def successButton = { attrs, body ->
        attrs.type = 'success'
        out << button(attrs, body)
    }

    def infoButton = { attrs, body ->
        attrs.type = 'info'
        out << button(attrs, body)
    }

    def warningButton = { attrs, body ->
        attrs.type = 'warning'
        out << button(attrs, body)
    }

    def dangerButton = { attrs, body ->
        attrs.type = 'danger'
        out << button(attrs, body)
    }
    // /Button

    // Panel
    def panel = { attrs, body ->
        def title = attrs.title
        def footer = attrs.footer
        def type = attrs.type ?: 'default'
        def (writer, builder) = writerAndMarkupBuilder

        builder.div(class: "panel panel-$type") {
            if (title) mkp.yieldUnescaped panelHeading(attrs, title)
            mkp.yieldUnescaped panelBody(attrs, body)
            if (footer) mkp.yieldUnescaped panelFooter(attrs, footer)
        }

        out << writer.toString()
    }

    def panelHeading = { attrs, body ->
        def h = attrs.h ?: '3'
        def (writer, builder) = writerAndMarkupBuilder
        build.div(class: 'panel-heading') {
            mkp.yieldUnescaped "<h$h class=\"panel-title\">${body()}</h$h>"
        }
        out << writer.toString()
    }

    def panelBody = { attrs, body ->
        def (writer, builder) = writerAndMarkupBuilder
        builder.div(class: 'panel-body') {
            mkp.yieldUnescaped body()
        }
        out << writer.toString()
    }

    def panelFooter = { attrs, body ->
        def (writer, builder) = writerAndMarkupBuilder
        builder.div(class: 'panel-footer') {
            mkp.yieldUnescaped body()
        }
        out << writer.toString()
    }
    // /Panel
}
