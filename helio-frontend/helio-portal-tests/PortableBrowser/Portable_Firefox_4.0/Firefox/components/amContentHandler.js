/*
//@line 38 "e:\builds\moz2_slave\win32_build\build\toolkit\mozapps\extensions\amContentHandler.js"
*/

const Cc = Components.classes;
const Ci = Components.interfaces;
const Cr = Components.results;

const XPI_CONTENT_TYPE = "application/x-xpinstall";

Components.utils.import("resource://gre/modules/XPCOMUtils.jsm");

function amContentHandler() {
}

amContentHandler.prototype = {
  /**
   * Handles a new request for an application/x-xpinstall file.
   *
   * @param  aMimetype
   *         The mimetype of the file
   * @param  aContext
   *         The context passed to nsIChannel.asyncOpen
   * @param  aRequest
   *         The nsIRequest dealing with the content
   */
  handleContent: function XCH_handleContent(aMimetype, aContext, aRequest) {
    if (aMimetype != XPI_CONTENT_TYPE)
      throw Cr.NS_ERROR_WONT_HANDLE_CONTENT;

    if (!(aRequest instanceof Ci.nsIChannel))
      throw Cr.NS_ERROR_WONT_HANDLE_CONTENT;

    let uri = aRequest.URI;

    let referer = null;
    if (aRequest instanceof Ci.nsIPropertyBag2) {
      referer = aRequest.getPropertyAsInterface("docshell.internalReferrer",
                                                Ci.nsIURI);
    }

    let window = null;
    let callbacks = aRequest.notificationCallbacks ?
                    aRequest.notificationCallbacks :
                    aRequest.loadGroup.notificationCallbacks;
    if (callbacks)
      window = callbacks.getInterface(Ci.nsIDOMWindow);

    aRequest.cancel(Cr.NS_BINDING_ABORTED);

    let manager = Cc["@mozilla.org/addons/integration;1"].
                  getService(Ci.amIWebInstaller);
    manager.installAddonsFromWebpage(aMimetype, window, referer, [uri.spec],
                                     [null], [null], [null], null, 1);
  },

  classID: Components.ID("{7beb3ba8-6ec3-41b4-b67c-da89b8518922}"),
  QueryInterface: XPCOMUtils.generateQI([Ci.nsIContentHandler])
};

var NSGetFactory = XPCOMUtils.generateNSGetFactory([amContentHandler]);
