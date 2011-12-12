package eu.heliovo.hfe.filter

import java.io.IOException
import java.util.List

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.context.SecurityContextHolder

import eu.heliovo.hfe.model.security.User

class TempUserSecurityFilter implements Filter {
    
    /**
    * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least one role, so
    * we give a user with no granted roles this one which gets past that restriction but
    * doesn't grant anything.
    */
    static final List NO_ROLES = [new GrantedAuthorityImpl(SpringSecurityUtils.NO_ROLE)]
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(createAuthentication(req));
        }
        chain.doFilter(req, resp);
    }

    /**
     * Create a temporary user and save it to the database. 
     * @param request the request.
     * @return an {@link AnonymousAuthenticationToken} holding the newly create user.
     */
    private Authentication createAuthentication(req) {
        // create the user
        def user = new User()
        def uuid = java.util.UUID.randomUUID().toString()
        user.username = uuid
        user.temporary = true
        user.password = "top_secret1!"
        User.withTransaction { status ->
            if (!user.save()) {
                throw new RuntimeException("Internal Error: Unable to save temporary user. Reason: " + user.errors)
            }
        }
        
        def authenticationToken = new AnonymousAuthenticationToken('helioHFE', user, NO_ROLES)
        return authenticationToken
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // nothing todo
    }

    @Override
    public void destroy() {
        // nothing todo
    }
}
