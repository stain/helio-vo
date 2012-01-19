dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbc.JDBCDriver"
    username = "sa"
    password = ""
    
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:hsqldb:mem:devDB"
            logSql = false
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:mem:testDb"
		}
        
        // disable cache for testing.
        hibernate {
            cache.use_second_level_cache=false
            cache.use_query_cache=false
            cache.provider_class='org.hibernate.cache.EhCacheProvider'
        }
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:file:prodHelioDb;shutdown=true"
		}
	}
}