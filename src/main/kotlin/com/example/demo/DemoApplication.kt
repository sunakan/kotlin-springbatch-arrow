package com.example.demo

import com.example.demo.property.SlackProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class DemoApplication

@Autowired
lateinit var slackProperty:  SlackProperty

fun main(args: Array<String>) {
    println("---------------------------------")
    println(slackProperty)
    println("---------------------------------")
    runApplication<DemoApplication>(*args)
}
