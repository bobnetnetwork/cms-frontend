package network.bobnet.cms.config

import org.apache.maven.model.Model
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import org.apache.maven.project.MavenProject
import org.springframework.stereotype.Controller
import java.io.FileReader
import java.util.*

@Controller
class TemplateVersionConfig {
    lateinit var bootstrap : String
    lateinit var font_awesome : String
    lateinit var jquery : String
    lateinit var tinymce : String
    lateinit var chart_js : String
    lateinit var jquery_easing : String
    lateinit var datatables : String

    constructor(){
        readVersions()
    }

    private fun readVersions(){
        var model : Model
        var reader : FileReader
        var mavenreader : MavenXpp3Reader = MavenXpp3Reader()
        var project : MavenProject
        var pomfile : String = "pom.xml"

        try{
            reader = FileReader(pomfile)
            model = mavenreader.read(reader)
            project = MavenProject(model)

            var properties : Properties = project.properties

            bootstrap = properties["bootstrap.version"] as String
            font_awesome = properties["font-awesome.version"] as String
            jquery = properties["jquery.version"] as String
            tinymce = properties["tinymce.version"] as String
            chart_js = properties["chart.js.version"] as String
            jquery_easing = properties["jquery-easing.version"] as String
            datatables = properties["datatables.version"] as String

        }catch(ex : Exception){
            ex.printStackTrace()
        }


    }

}