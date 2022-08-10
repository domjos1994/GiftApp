package de.domjos.gift_app.services

import android.content.Context
import de.domjos.gift_app.R
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.net.URL
import java.util.concurrent.Callable
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class DailyVerse(val context: Context) : Callable<DailyVerse.JsonVerse> {

    override fun call(): JsonVerse? {
        try {
            val url: String = this.context.getString(R.string.main_daily_url)
            val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
            val documentBuilder: DocumentBuilder = factory.newDocumentBuilder()
            val document: Document = documentBuilder.parse(InputSource(URL(url).openStream()))

            val nodeList: NodeList = document.getElementsByTagName("item")
            if(nodeList.length != 0) {
                val element: Element = nodeList.item(0) as Element

                val dailyVerse = JsonVerse()
                dailyVerse.notice = getChild("dc:rights", element)
                dailyVerse.text = getChild("content:encoded", element)
                dailyVerse.reference = getChild("title", element)

                return dailyVerse
            }

            return null
        } catch (ex: Exception) {
            return null
        }
    }

    private fun getChild(item: String, document: Element): String {
        try {
            val nodeList: NodeList = document.getElementsByTagName(item)
            for (i in 0 until nodeList.length) {
                return nodeList.item(i).textContent
            }
        } catch (ex: Exception) {}
        return ""
    }

    class JsonVerse {
        var notice: String = ""
        var text: String = ""
        var reference: String = ""
    }
}