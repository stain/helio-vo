package eu.heliovo.hfe.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse;

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.context.request.RequestContextHolder

import eu.heliovo.hfe.model.security.User

class TempUserSecurityFilter implements Filter {
    
    /**
    * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least one role, so
    * we give a user with no granted roles this one which gets past that restriction but
    * doesn't grant anything.f
    */
    static final List NO_ROLES = [new GrantedAuthorityImpl(SpringSecurityUtils.NO_ROLE)]
    
    private static final SECURITY_COOKIE_NAME = "helio_temp_user"
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {            
            SecurityContextHolder.getContext().setAuthentication(createAuthentication(req, resp));
        }
        chain.doFilter(req, resp);
    }

    /**
     * Create a temporary user and save it to the database. 
     * @param req the request.
     * @param resp the response
     * @return an {@link AnonymousAuthenticationToken} holding the newly create user.
     */
    private Authentication createAuthentication(req, resp) {
        // try to get user from session 
        def session = RequestContextHolder.currentRequestAttributes().getSession(true)
        def user = session.getAttribute('tempuser')
        
        if (!user) {
            def uuid = getUuidFromCookie(req)
            if (uuid) {
                user = User.findByUsername(uuid)
            }
        }
        
        if (!user) {
            // else create new user
            user = new User()
            def uuid = java.util.UUID.randomUUID().toString()
            user.username = uuid
            user.temporary = true
            user.password = "top_secret1!"
            User.withTransaction { status ->
                if (!user.save()) {
                    throw new RuntimeException("Internal Error: Unable to save temporary user. Reason: " + user.errors)
                }
            }
            session.setAttribute("tempuser", user)
            setUuidInCookie(resp, uuid)
        }
        
        def authenticationToken = new AnonymousAuthenticationToken('helioHFE', user, NO_ROLES)
        return authenticationToken
    }
    
    /**
     * Get the UUID from a cookie if possible
     * @param req the current request
     * @return the uuid of the currently active temporary user.
     */
    def getUuidFromCookie(req) {
        def uuid
        Cookie[] cookies
        if (req instanceof HttpServletRequest) {
            cookies  = req.getCookies()
            for (Cookie cookie : cookies) {
                if (SECURITY_COOKIE_NAME == cookie.getName()) {
                    uuid = cookie.getValue()
                    break;
                }
            }
        }
        uuid
    }
    
    /**
     * Add the uuid as cookie to the response
     * @param resp the response
     * @param uuid the current temp uuid.
     */
    def setUuidInCookie(resp, uuid) {
        if (resp instanceof HttpServletResponse) {
            Cookie cookie = new Cookie(SECURITY_COOKIE_NAME, uuid)
            resp.addCookie(cookie)
        }
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
