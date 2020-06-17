package network.bobnet.cms

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CmsApplication

fun main(args: Array<String>) {
	runApplication<CmsApplication>(*args){
		setBannerMode(Banner.Mode.OFF)
	}
}
