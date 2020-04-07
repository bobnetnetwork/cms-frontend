package network.bobnet.cms

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties::class)
class CmsApplication

fun main(args: Array<String>) {
	runApplication<CmsApplication>(*args){
		setBannerMode(Banner.Mode.OFF);
	}
}
