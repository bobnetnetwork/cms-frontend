package network.bobnet.cms.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Options(var name: String,
                var value: String,
                @Id @GeneratedValue var id: Long? = null)
