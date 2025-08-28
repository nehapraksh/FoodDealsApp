package com.example.fooddeals

import java.io.InputStreamReader

/**
 * Mock response file reader - class for read the json response file
 * for success result in the test classes
 *
 * @constructor
 *
 * @param path
 */
class MockResponseFileReader (path: String) {

    val content: String

    init {
        // create reader to read data from given file name
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        // read the file content
        content = reader.readText()
        // close the reader once done
        reader.close()
    }

}