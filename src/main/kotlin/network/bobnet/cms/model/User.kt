package network.bobnet.cms.model

import javax.persistence.*


@Entity
class User(
        var login: String,
        var firstname: String,
        var lastname: String,
        var description: String? = null,
        @Id @GeneratedValue var id: Long? = null,
        var password: String)