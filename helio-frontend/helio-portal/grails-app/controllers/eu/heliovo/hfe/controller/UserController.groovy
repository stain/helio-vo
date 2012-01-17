package eu.heliovo.hfe.controller

import org.codehaus.groovy.grails.commons.ApplicationHolder as AH
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import eu.heliovo.hfe.model.security.*
import grails.converters.JSON

/**
 * Controller to manage user accounts such as registration,
 * retrieval of lost passwords, etc.
 * @author MarcoSoldati
 *
 */
class UserController {

    def springSecurityService
    def rememberMeServices
    
    def saltSource
    
    def index = { }
    
    
    /**
     * Asynchronously register a new user.
     */
    def ajaxRegister = {RegisterCommand command ->
        if (springSecurityService.isLoggedIn()) {
            if (!springSecurityService.principal.temporary) {
                render ([errors: 'You are already logged in.'] as JSON)
                return
            }
        }

        if (!command.validate() || command.hasErrors()) {
            render (command.errors as JSON)
            return
        }

        def user = new User(email: command.username, username: command.username,
                password: command.password, accountLocked: false, enabled: true)
        if (!user.validate() || !user.save()) {
            render (user.errors as JSON)
            return
        }
        def roleUser = Role.findByAuthority("ROLE_USER");
        UserRole.create user, roleUser, true

        springSecurityService.reauthenticate(command.username,command.password)
        rememberMeServices.onLoginSuccess(request,response,springSecurityService.authentication)
        
        render ([success: true, username:user.username] as JSON)       
    }
    
    
    static final passwordValidator = { String password, command ->
        if (command.username && command.username.equals(password)) {
            return 'command.password.error.username'
        }

        if (password && password.length() >= 8 && password.length() <= 64 &&
                (!password.matches('^.*\\p{Alpha}.*$') ||
                !password.matches('^.*\\p{Digit}.*$') ||
                !password.matches('^.*[!@#$%^&].*$'))) {
            return 'command.password.error.strength'
        }
    }

    static final password2Validator = { value, command ->
        if (command.password != command.password2) {
            return 'command.password2.error.mismatch'
        }
    }
}

class RegisterCommand {
    
    String username
    String password
    String password2
    
    static constraints = {
        username blank: false, email : true, validator: { value, command ->
            if (value) {
                def User = AH.application.getDomainClass(
                SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
                if (User.findByUsername(value)) {
                    return 'registerCommand.username.unique'
                }
            }
        }
        
        password blank: false, minSize: 8, maxSize: 64, validator: UserController.passwordValidator
        password2 validator: UserController.password2Validator
    }
}
