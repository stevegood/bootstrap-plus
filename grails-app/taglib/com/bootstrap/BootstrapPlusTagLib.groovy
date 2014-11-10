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

    protected void validateAttrs(String property, String tagName, attrs) {
        if (attrs[property] == null) throw new BootstrapPlusException("Attribute ['$property'] is required for the ['$tagName'] tag!", tagName)
    }

    protected void validateAttrs(ArrayList<String> properties, String tagName, attrs) {
        properties.each {
            validateAttrs it, tagName, attrs
        }
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
        validateAttrs 'col-size', 'col', attrs
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

    // Navbar
    def navbar = { attrs, body ->
        def inverse = (attrs.inverse ?: false) in [true, 'true', 'inverse']
        def position = attrs.position
        if (position) {
            position = "navbar-${position.replaceAll('navbar-','')}"
        }
        attrs.prepend 'class', "navbar navbar-${inverse ? 'inverse' : 'default'} ${position ?: ''}".trim()
        ['inverse','position'].each { attrs.remove(it) }

        out << simpleDiv(attrs, body)
    }

    def navbarHeader = { attrs, body ->
        attrs.prepend 'class', 'navbar-header'
        out << simpleDiv(attrs, body)
    }

    def navbarBrand = { attrs, body ->
        boolean useLinkTag = (attrs.action && !attrs.href)
        attrs.prepend 'class', 'navbar-brand'
        if (useLinkTag) {
            out << g.link(attrs, body)
        } else {
            def (writer, builder) = writerAndMarkupBuilder
            builder.a(attrs) {
                mkp.yieldUnescaped body()
            }
            out << writer.toString()
        }
    }

    def navbarToggle = { attrs, body ->
        def (writer, builder) = writerAndMarkupBuilder
        validateAttrs 'target', 'navbarToggle', attrs
        def target = attrs.target

        attrs.prepend 'class', 'navbar-toggle'
        attrs.type = 'button'
        attrs.'data-toggle' = 'collapse'
        attrs.'data-target' = target
        attrs.remove('target')

        builder.button(attrs) {
            3.times {
                span(class: 'icon-bar','')
            }
        }

        out << writer.toString()
    }

    def navbarCollapse = { attrs, body ->
        attrs.prepend 'class', 'navbar-collapse collapse'
        out << simpleDiv(attrs, body)
    }

    def navbarNav = { attrs, body ->
        attrs.prepend 'class', "nav navbar-nav ${attrs.position ? 'navbar-' + attrs.position : ''}".trim()
        def (writer, builder) = writerAndMarkupBuilder
        builder.ul(attrs) {
            mkp.yieldUnescaped body()
        }
        out << writer.toString()
    }

    def navbarDropdown = { attrs, body ->
        validateAttrs(['label'], 'navbarDropdown', attrs)
        def label = attrs.label
        def id = attrs.id ?: "el${new Date().time}"
        attrs.remove('label')
        def (writer, builder) = writerAndMarkupBuilder

        builder.li(class: 'dropdown') {
            a(class: 'dropdown-toggle', 'data-toggle': 'dropdown', href: '#', id: id) {
                mkp.yieldUnescaped label
                span(class:'caret','')
            }
            ul(class:'dropdown-menu', 'aria-labeledby': id) {
                mkp.yieldUnescaped body()
            }
        }

        out << writer.toString()
    }

    def navbarNavLink = { attrs, body ->
        boolean useLinkTag = (attrs.action && !attrs.href)
        def (writer, builder) = writerAndMarkupBuilder
        def liAttrs = [:]
        def keysToRemove = []

        attrs.each {String k, v ->
            if (k.startsWith('li-')) {
                liAttrs[k.replaceAll('li-','')] = v
                keysToRemove << k
            }
        }

        keysToRemove.each {
            attrs.remove it
        }

        builder.li(liAttrs) {
            if (useLinkTag) {
                g.link(attrs) {
                    mkp.yieldUnescaped body()
                }
            } else {
                a(attrs) {
                    mkp.yieldUnescaped body()
                }
            }
        }

        out << writer.toString()
    }

    def navbarDropdownDivider = { attrs, body ->
        attrs.prepend 'class', 'divider'
        def (writer, builder) = writerAndMarkupBuilder
        builder.li(attrs,'')
        out << writer.toString()
    }

    def navbarDropdownHeader = { attrs, body ->
        attrs.prepend 'class', 'dropdown-header'
        def (writer, builder) = writerAndMarkupBuilder
        builder.li(attrs) {
            mkp.yieldUnescaped body()
        }
        out << writer.toString()
    }

    def navbarForm = { attrs, body ->
        attrs.prepend 'class', "navbar-form ${attrs.position ? 'navbar-' + attrs.position : ''}".trim()
        def (writer, builder) = writerAndMarkupBuilder
        builder.form(attrs) {
            mkp.yieldUnescaped body()
        }
        out << writer.toString()
    }
    // /Navbar

}
