package com.dogeby.wheretogo.core.ui.util

import junit.framework.TestCase.assertEquals
import org.junit.Test

class DataFormatterTest {

    @Test
    fun test_buildLocationText_nonBlankAreaAndSigungu() {
        val areaName = "Seoul"
        val sigunguName = "Gangnam"
        val result = buildLocationText(areaName, sigunguName)
        assertEquals("Seoul Gangnam", result)
    }

    @Test
    fun test_buildLocationText_nonBlankAreaAndBlankSigungu() {
        val areaName = "Seoul"
        val sigunguName = ""
        val result = buildLocationText(areaName, sigunguName)
        assertEquals("Seoul", result)
    }

    @Test
    fun test_buildLocationText_blankAreaAndNonBlankSigungu() {
        val areaName = ""
        val sigunguName = "Gangnam"
        val result = buildLocationText(areaName, sigunguName)
        assertEquals("Gangnam", result)
    }

    @Test
    fun test_buildLocationText_blankAreaAndSigungu() {
        val areaName = ""
        val sigunguName = ""
        val result = buildLocationText(areaName, sigunguName)
        assertEquals("", result)
    }

    @Test
    fun test_buildLocationContentTypeText_nonBlankAreaSigunguAndContentType() {
        val areaName = "Seoul"
        val sigunguName = "Gangnam"
        val contentTypeName = "Festival"
        val result = buildLocationContentTypeText(areaName, sigunguName, contentTypeName)
        assertEquals("Seoul Gangnam Festival", result)
    }

    @Test
    fun test_buildLocationContentTypeText_nonBlankAreaAndSigunguAndBlankContentType() {
        val areaName = "Seoul"
        val sigunguName = "Gangnam"
        val contentTypeName = ""
        val result = buildLocationContentTypeText(areaName, sigunguName, contentTypeName)
        assertEquals("Seoul Gangnam", result)
    }

    @Test
    fun test_buildLocationContentTypeText_nonBlankAreaAndBlankSigunguAndNonBlankContentType() {
        val areaName = "Seoul"
        val sigunguName = ""
        val contentTypeName = "Festival"
        val result = buildLocationContentTypeText(areaName, sigunguName, contentTypeName)
        assertEquals("Seoul Festival", result)
    }

    @Test
    fun test_buildLocationContentTypeText_blankAreaAndNonBlankSigunguAndContentType() {
        val areaName = ""
        val sigunguName = "Gangnam"
        val contentTypeName = "Festival"
        val result = buildLocationContentTypeText(areaName, sigunguName, contentTypeName)
        assertEquals("Gangnam Festival", result)
    }

    @Test
    fun test_buildLocationContentTypeText_blankAreaAndSigunguAndNonBlankContentType() {
        val areaName = ""
        val sigunguName = ""
        val contentTypeName = "Festival"
        val result = buildLocationContentTypeText(areaName, sigunguName, contentTypeName)
        assertEquals("Festival", result)
    }

    @Test
    fun test_buildLocationContentTypeText_blankAreaSigunguAndContentType() {
        val areaName = ""
        val sigunguName = ""
        val contentTypeName = ""
        val result = buildLocationContentTypeText(areaName, sigunguName, contentTypeName)
        assertEquals("", result)
    }
}
