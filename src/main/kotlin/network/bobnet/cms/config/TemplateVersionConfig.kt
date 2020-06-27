package network.bobnet.cms.config

import org.apache.maven.model.Model
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import org.apache.maven.project.MavenProject
import org.springframework.stereotype.Controller
import java.io.FileReader
import java.util.*

@Controller
class TemplateVersionConfig() {
    lateinit var bootstrap : String
    lateinit var font_awesome : String
    lateinit var jquery : String
    lateinit var tinymce : String
    lateinit var chart_js : String
    lateinit var jquery_easing : String
    lateinit var datatables : String

    init {
        readVersions()
    }

    private fun readVersions(){
        val model : Model
        val reader : FileReader
        val mavenReader : MavenXpp3Reader = MavenXpp3Reader()
        val project : MavenProject
        val pomFile : String = "pom.xml"

        try{
            reader = FileReader(pomFile)
            model = mavenReader.read(reader)
            project = MavenProject(model)

            val properties : Properties = project.properties

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