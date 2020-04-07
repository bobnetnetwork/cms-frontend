package network.bobnet.cms.model

import javax.persistence.*

@Entity
data class Options(var name: String,
                var value: String,
                @Id @GeneratedValue var id: Long? = null)