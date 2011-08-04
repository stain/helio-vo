//@line 43 "e:\builds\moz2_slave\win32_build\build\browser\components\sidebar\src\nsSidebar.js"

Components.utils.import("resource://gre/modules/XPCOMUtils.jsm");

const DEBUG = false; /* set to false to suppress debug messages */

const SIDEBAR_CID               = Components.ID("{22117140-9c6e-11d3-aaf1-00805f8a4905}");
const nsISupports               = Components.interfaces.nsISupports;
const nsISidebar                = Components.interfaces.nsISidebar;
const nsISidebarExternal        = Components.interfaces.nsISidebarExternal;
const nsIClassInfo              = Components.interfaces.nsIClassInfo;

// File extension for Sherlock search plugin description files
const SHERLOCK_FILE_EXT_REGEXP = /\.src$/i;

function nsSidebar()
{
    const PROMPTSERVICE_CONTRACTID = "@mozilla.org/embedcomp/prompt-service;1";
    const nsIPromptService = Components.interfaces.nsIPromptService;
    this.promptService =
        Components.classes[PROMPTSERVICE_CONTRACTID].getService(nsIPromptService);

    const SEARCHSERVICE_CONTRACTID = "@mozilla.org/browser/search-service;1";
    const nsIBrowserSearchService = Components.interfaces.nsIBrowserSearchService;
    this.searchService =
      Components.classes[SEARCHSERVICE_CONTRACTID].getService(nsIBrowserSearchService);
}

nsSidebar.prototype.classID = SIDEBAR_CID;

nsSidebar.prototype.nc = "http://home.netscape.com/NC-rdf#";

function sidebarURLSecurityCheck(url)
{
    if (!/^(https?:|ftp:)/i.test(url)) {
        Components.utils.reportError("Invalid argument passed to window.sidebar.addPanel: Unsupported panel URL." );
        return false;
    }
    return true;
}

/* decorate prototype to provide ``class'' methods and property accessors */
nsSidebar.prototype.addPanel =
function (aTitle, aContentURL, aCustomizeURL)
{
    debug("addPanel(" + aTitle + ", " + aContentURL + ", " +
          aCustomizeURL + ")");

    return this.addPanelInternal(aTitle, aContentURL, aCustomizeURL, false);
}

nsSidebar.prototype.addPersistentPanel =
function(aTitle, aContentURL, aCustomizeURL)
{
    debug("addPersistentPanel(" + aTitle + ", " + aContentURL + ", " +
           aCustomizeURL + ")\n");

    return this.addPanelInternal(aTitle, aContentURL, aCustomizeURL, true);
}

nsSidebar.prototype.addPanelInternal =
function (aTitle, aContentURL, aCustomizeURL, aPersist)
{
    var WINMEDSVC = Components.classes['@mozilla.org/appshell/window-mediator;1']
                              .getService(Components.interfaces.nsIWindowMediator);
    var win = WINMEDSVC.getMostRecentWindow( "navigator:browser" );
                                                                                
    if (!sidebarURLSecurityCheck(aContentURL))
      return;

    var uri = null;
    var ioService = Components.classes["@mozilla.org/network/io-service;1"]
                              .getService(Components.interfaces.nsIIOService);
    try {
      uri = ioService.newURI(aContentURL, null, null);
    }
    catch(ex) { return; }

    win.PlacesUIUtils.showMinimalAddBookmarkUI(uri, aTitle, null, null, true, true);
}

nsSidebar.prototype.validateSearchEngine =
function (engineURL, iconURL)
{
  try
  {
    // Make sure the URLs are HTTP, HTTPS, or FTP.
    var isWeb = /^(https?|ftp):\/\//i;

    if (!isWeb.test(engineURL))
      throw "Unsupported search engine URL";
  
    if (iconURL && !isWeb.test(iconURL))
      throw "Unsupported search icon URL.";
  }
  catch(ex)
  {
    debug(ex);
    Components.utils.reportError("Invalid argument passed to window.sidebar.addSearchEngine: " + ex);
    
    var searchBundle = srGetStrBundle("chrome://global/locale/search/search.properties");
    var brandBundle = srGetStrBundle("chrome://branding/locale/brand.properties");
    var brandName = brandBundle.GetStringFromName("brandShortName");
    var title = searchBundle.GetStringFromName("error_invalid_engine_title");
    var msg = searchBundle.formatStringFromName("error_invalid_engine_msg",
                                                [brandName], 1);
    var ww = Components.classes["@mozilla.org/embedcomp/window-watcher;1"].
             getService(Components.interfaces.nsIWindowWatcher);
    ww.getNewPrompter(null).alert(title, msg);
    return false;
  }
  
  return true;
}

// The suggestedTitle and suggestedCategory parameters are ignored, but remain
// for backward compatibility.
nsSidebar.prototype.addSearchEngine =
function (engineURL, iconURL, suggestedTitle, suggestedCategory)
{
  debug("addSearchEngine(" + engineURL + ", " + iconURL + ", " +
        suggestedCategory + ", " + suggestedTitle + ")");

  if (!this.validateSearchEngine(engineURL, iconURL))
    return;

  // OpenSearch files will likely be far more common than Sherlock files, and
  // have less consistent suffixes, so we assume that ".src" is a Sherlock
  // (text) file, and anything else is OpenSearch (XML).
  var dataType;
  if (SHERLOCK_FILE_EXT_REGEXP.test(engineURL))
    dataType = Components.interfaces.nsISearchEngine.DATA_TEXT;
  else
    dataType = Components.interfaces.nsISearchEngine.DATA_XML;

  this.searchService.addEngine(engineURL, dataType, iconURL, true);
}

// This function exists largely to implement window.external.AddSearchProvider(),
// to match other browsers' APIs.  The capitalization, although nonstandard here,
// is therefore important.
nsSidebar.prototype.AddSearchProvider =
function (aDescriptionURL)
{
  // Get the favicon URL for the current page, or our best guess at the current
  // page since we don't have easy access to the active document.  Most search
  // engines will override this with an icon specified in the OpenSearch
  // description anyway.
  var WINMEDSVC = Components.classes['@mozilla.org/appshell/window-mediator;1']
                            .getService(Components.interfaces.nsIWindowMediator);
  var win = WINMEDSVC.getMostRecentWindow("navigator:browser");
  var browser = win.document.getElementById("content");
  var iconURL = "";
  // Use documentURIObject in the check for shouldLoadFavIcon so that we
  // do the right thing with about:-style error pages.  Bug 453442
  if (browser.shouldLoadFavIcon(browser.selectedBrowser
                                       .contentDocument
                                       .documentURIObject))
    iconURL = win.gProxyFavIcon.getAttribute("src");
  
  if (!this.validateSearchEngine(aDescriptionURL, iconURL))
    return;

  const typeXML = Components.interfaces.nsISearchEngine.DATA_XML;
  this.searchService.addEngine(aDescriptionURL, typeXML, iconURL, true);
}

// This function exists to implement window.external.IsSearchProviderInstalled(),
// for compatibility with other browsers.  It will return an integer value
// indicating whether the given engine is installed for the current user.
// However, it is currently stubbed out due to security/privacy concerns
// stemming from difficulties in determining what domain issued the request.
// See bug 340604 and
// http://msdn.microsoft.com/en-us/library/aa342526%28VS.85%29.aspx .
// XXX Implement this!
nsSidebar.prototype.IsSearchProviderInstalled =
function (aSearchURL)
{
  return 0;
}

nsSidebar.prototype.addMicrosummaryGenerator =
function (generatorURL)
{
    debug("addMicrosummaryGenerator(" + generatorURL + ")");

    if (!/^https?:/i.test(generatorURL))
      return;

    var stringBundle = srGetStrBundle("chrome://browser/locale/sidebar/sidebar.properties");
    var titleMessage = stringBundle.GetStringFromName("addMicsumGenConfirmTitle");
    var dialogMessage = stringBundle.formatStringFromName("addMicsumGenConfirmText", [generatorURL], 1);
      
    if (!this.promptService.confirm(null, titleMessage, dialogMessage))
        return;

    var ioService = Components.classes["@mozilla.org/network/io-service;1"].
                    getService(Components.interfaces.nsIIOService);
    var generatorURI = ioService.newURI(generatorURL, null, null);

    var microsummaryService = Components.classes["@mozilla.org/microsummary/service;1"].
                              getService(Components.interfaces.nsIMicrosummaryService);
    if (microsummaryService)
      microsummaryService.addGenerator(generatorURI);
}

// property of nsIClassInfo
nsSidebar.prototype.flags = nsIClassInfo.DOM_OBJECT;

// property of nsIClassInfo
nsSidebar.prototype.classDescription = "Sidebar";

// method of nsIClassInfo
nsSidebar.prototype.getInterfaces = function(count) {
    var interfaceList = [nsISidebar, nsISidebarExternal, nsIClassInfo];
    count.value = interfaceList.length;
    return interfaceList;
}

// method of nsIClassInfo
nsSidebar.prototype.getHelperForLanguage = function(count) {return null;}

nsSidebar.prototype.QueryInterface =
function (iid) {
    if (iid.equals(nsISidebar) ||
        iid.equals(nsISidebarExternal) ||
        iid.equals(nsIClassInfo) ||
        iid.equals(nsISupports))
        return this;

    throw Components.results.NS_ERROR_NO_INTERFACE;
};

var NSGetFactory = XPCOMUtils.generateNSGetFactory([nsSidebar]);

/* static functions */
if (DEBUG)
    debug = function (s) { dump("-*- sidebar component: " + s + "\n"); }
else
    debug = function (s) {}

// String bundle service
var gStrBundleService = null;

function srGetStrBundle(path)
{
  if (!gStrBundleService)
    gStrBundleService =
      Components.classes["@mozilla.org/intl/stringbundle;1"]
                .getService(Components.interfaces.nsIStringBundleService);

  return gStrBundleService.createBundle(path);
}
