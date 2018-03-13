package ru.monopolio.quiz.properties

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.Properties

class LocalProperties(private val fileName: String = "local.properties") {

    val token: String
        get() = readProperties().getProperty(PROP_TOKEN)
                ?: throw RuntimeException("Token is not set in " + fileName)

    private fun writeProperties(props: Properties) {
        FileOutputStream(fileName).use { output -> props.store(output, null) }
    }

    private fun readProperties() = Properties().apply {
        try {
            FileInputStream(fileName).use { input -> load(input) }
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException("File ${System.getProperty("user.dir")}${File.separator}$fileName not found.")
        }
    }

    companion object {
        val PROP_TOKEN = "telegram.bot.token"
    }
}