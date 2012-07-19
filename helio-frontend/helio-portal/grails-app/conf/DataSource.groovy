dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
    dialect = org.hibernate.dialect.H2Dialect
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:h2:~/.helio/helio-dev/portaldb;MVCC=TRUE"
            logSql = false
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE"
        }

        // disable cache for testing.
        hibernate {
            cache.use_second_level_cache=false
            cache.use_query_cache=false
            cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:~/.helio/helio/portaldb;MVCC=TRUE"
            pooled = true
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="SELECT 1"
            }
        }
    }
}