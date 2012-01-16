package eu.heliovo.hfe.service

import eu.heliovo.hfe.model.param.AbstractParam;
import eu.heliovo.hfe.model.param.TimeRangeParam;
import eu.heliovo.hfe.model.security.User;

class ParamService {

    static transactional = true
    
    /**
     * Auto-wire the spring security service.
     */
    def springSecurityService;

    def findLatestTimeRangeParam() {
//        def user = User.get(springSecurityService.principal.id)
//        TimeRangeParam.find("from TimeRangeParam AS trp WHERE trp.owner=:owner ORDER BY lastUpdated desc", [owner:user], [cache:true])
        findLatestParam(TimeRangeParam)
    }
    
    def findLatestParam(Class<AbstractParam> paramType) {
        def user = User.get(springSecurityService.principal.id)
        paramType.find("from " + paramType.getSimpleName() + " AS p WHERE p.owner=:owner ORDER BY lastUpdated desc", [owner:user])
    }
}
