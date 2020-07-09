package network.bobnet.cms.service

import network.bobnet.cms.model.Options
import network.bobnet.cms.model.user.Role
import network.bobnet.cms.model.user.User
import network.bobnet.cms.repository.OptionsRepository
import network.bobnet.cms.repository.user.RoleRepository
import network.bobnet.cms.repository.user.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class SetupService (
                        val optionsRepository: OptionsRepository,
                        val userRepository: UserRepository,
                        val roleRepository: RoleRepository){

    lateinit var siteName: String
    lateinit var siteDescription: String
    lateinit var home: String
    lateinit var language: String
    lateinit var copyright: String
    lateinit var userName: String
    lateinit var password: String
    lateinit var userMail: String

    fun save() {
        initRoles()
        addFirstUser()
        setSiteDetails()
    }

    private fun initRoles(){
        val role = Role("admin")
        roleRepository.save(role)
    }

    private fun addFirstUser(){
        val user = User()
        user.userName = userName
        user.passWord = BCryptPasswordEncoder().encode(password)
        user.email = userMail
        user.roles.add(roleRepository.findByRolename("admin"))
        userRepository.save(user)
    }

    private fun setSiteDetails(){

        var options = Options("sitename", siteName)
        optionsRepository.save(options)

        options = Options("sitedescription", siteDescription)
        optionsRepository.save(options)

        options = Options("home", home)
        optionsRepository.save(options)

        options = Options("sitelanguage", language)
        optionsRepository.save(options)

        options = Options("tinymcelang", language)
        optionsRepository.save(options)

        options = Options("copyright", copyright)
        optionsRepository.save(options)
    }

}
