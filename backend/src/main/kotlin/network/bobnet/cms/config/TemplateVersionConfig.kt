package network.bobnet.cms.config

import org.apache.maven.model.Model
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import org.apache.maven.project.MavenProject
import org.springframework.stereotype.Controller
import java.io.FileReader
import java.util.*

@Controller
class TemplateVersionConfig {
    lateinit var bootstrap: String
    lateinit var fontAwesome: String
    lateinit var jquery: String
    lateinit var tinymce: String
    lateinit var chartJs: String
    lateinit var jqueryEasing: String
    lateinit var datatables: String
    lateinit var animateCSS: String
    lateinit var aos: String
    lateinit var waypoints: String
    lateinit var owlCarousel: String
    lateinit var isotopeLayout: String

    init {
        readVersions()
    }

    private fun readVersions() {
        val model: Model
        val reader: FileReader
        val mavenReader = MavenXpp3Reader()
        val project: MavenProject
        val pomFile = "pom.xml"

        reader = FileReader(pomFile)
        model = mavenReader.read(reader)
        project = MavenProject(model)

        val properties: Properties = project.properties

        bootstrap = properties["bootstrap.version"] as String
        fontAwesome = properties["font-awesome.version"] as String
        jquery = properties["jquery.version"] as String
        tinymce = properties["tinymce.version"] as String
        chartJs = properties["chart.js.version"] as String
        jqueryEasing = properties["jquery-easing.version"] as String
        datatables = properties["datatables.version"] as String
        animateCSS = properties["animate.css.version"] as String
        aos = properties["aos.version"] as String
        waypoints = properties["waypoints.version"] as String
        owlCarousel = properties["owl.carousel.version"] as String
        isotopeLayout = properties["isotope-layout.version"] as String
    }

}
