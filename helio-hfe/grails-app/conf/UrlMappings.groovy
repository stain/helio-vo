class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
      "/"(view:"/index")
	   "500"(controller:"errors", action:"notFound")
        "404"(controller:"errors", action:"notFound")
        "403"(controller:"errors", action:"notFound")
	}
}
