package network.bobnet.cms.model.content

import network.bobnet.cms.model.data.RenderedFile
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
class File(

    var fileName: String = "",
    var url: String = "",
    var slug: String = "",
    var mimeType: String = "",
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue
    var id: Long? = null){

    fun render() = id?.let {
        RenderedFile(
                fileName,
                url,
                slug,
                mimeType,
                addedAt,
                it
        )
    }
}