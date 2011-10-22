package ch.i4ds.helio;

class ErrorsController {

    def notFound = {
        println params;
        render "internal error"
    }
}
