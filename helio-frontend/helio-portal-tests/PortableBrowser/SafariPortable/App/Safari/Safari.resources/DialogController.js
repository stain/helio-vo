String.prototype.escapeCharacters = function(chars)
{
    var foundChar = false;
    for (var i = 0; i < chars.length; ++i) {
        if (this.indexOf(chars.charAt(i)) !== -1) {
            foundChar = true;
            break;
        }
    }

    if (!foundChar)
        return this;

    var result = "";
    for (var i = 0; i < this.length; ++i) {
        if (chars.indexOf(this.charAt(i)) !== -1)
            result += "\\";
        result += this.charAt(i);
    }

    return result;
}

String.prototype.format = function()
{
    var stringParts = this.split("%@");
    for (var i = 0; i < arguments.length; ++i)
        stringParts.splice(i * 2 + 1, 0, arguments[i].toString());
    return stringParts.join("");
}

String.prototype.escapeForRegExp = function()
{
    return this.escapeCharacters("^[]{}()\\.$*+?|");
}

Element.prototype.removeStyleClass = function(className) 
{
    // Test for the simple case before using a RegExp.
    if (this.className === className) {
        this.className = "";
        return;
    }

    var regex = new RegExp("(^|\\s+)" + className.escapeForRegExp() + "($|\\s+)");
    if (regex.test(this.className))
        this.className = this.className.replace(regex, " ");
}

Element.prototype.addStyleClass = function(className) 
{
    if (className && !this.hasStyleClass(className))
        this.className += (this.className.length ? " " + className : className);
}

Element.prototype.hasStyleClass = function(className) 
{
    if (!className)
        return false;
    // Test for the simple case before using a RegExp.
    if (this.className === className)
        return true;
    var regex = new RegExp("(^|\\s)" + className.escapeForRegExp() + "($|\\s)");
    return regex.test(this.className);
}

Element.prototype.removeChildren = function()
{
    while (this.firstChild) 
        this.removeChild(this.firstChild);        
}

Element.prototype.__defineGetter__("totalOffsetLeft", function()
{
    var total = 0;
    for (var element = this; element; element = element.offsetParent)
        total += element.offsetLeft;
    return total;
});

Element.prototype.__defineGetter__("totalOffsetTop", function()
{
    var total = 0;
    for (var element = this; element; element = element.offsetParent)
        total += element.offsetTop;
    return total;
});

Element.prototype.__defineGetter__("totalScrollTop", function()
{
    var total = 0;
    for (var element = this; element; element = element.offsetParent)
        total += element.scrollTop;
    return total;
});

var pageArguments = new Object;

var DialogController = {
    
    setItemText: function(itemID, value)
    {
        var ele = document.getElementById(itemID);
        if (ele.tagName == "INPUT" || ele.tagName == "SELECT" || ele.tagName == "TEXTAREA")
            ele.value = value;
        else
            ele.innerText = value;
    },
    
    itemText: function(itemID)
    {
        var ele = document.getElementById(itemID);
        if (ele.tagName == "INPUT" || ele.tagName == "SELECT" || ele.tagName == "TEXTAREA")
            return ele.value;
        else
            return ele.innerText;
    },

    setItemChecked: function(itemID, value)
    {
        document.getElementById(itemID).checked = value;
    },
    
    itemChecked: function(itemID)
    {
        return document.getElementById(itemID).checked;
    },
    
    setItemEnabled: function(itemID, value)
    {
        var item = document.getElementById(itemID);
        item.disabled = !value;
        if (item.parentElement.tagName == "LABEL") {
            if (value)
                item.parentElement.removeStyleClass("disabled");
            else
                item.parentElement.addStyleClass("disabled");
        }
    },
    
    insertOptionInSelect: function(selectID, position, label, value)
    {
        var select = document.getElementById(selectID);
        var option = document.createElement("option");
        option.innerText = label;
        option.value = value;
        select.add(option, select.options[position]);
    },

    appendToSelect: function(itemID, label, itemValue, itemStyle)
    {
        var selectEle = document.getElementById(itemID);
        var o;
        if (label == "")
            o = document.createElement("hr");
        else {
            o = document.createElement("option");
            o.innerText = label;
            o.value = itemValue ? itemValue : selectEle.length;
            o.style.cssText = itemStyle;
        }
        selectEle.add(o, null);        
    },
    
    setSelectIndex: function(itemID, index)
    {
        var selectEle = document.getElementById(itemID);
        selectEle.selectedIndex = index;
    },

    setOptionEnabled: function(selectID, optionIndex, enabled)
    {
        document.getElementById(selectID)[optionIndex].disabled = !enabled;
    },

    indexOfItemWithValue: function(selectID, value)
    {
        var select = document.getElementById(selectID);
        for (var i = 0; i < select.options.length; ++i) {
            if (select.options[i].value == value)
                return i;
        }
        return -1;
    },
    
    clearSelect: function(itemID)
    {
        var selectEle = document.getElementById(itemID);
        selectEle.length = 0;
    },
    
    setWidth: function(width)
    {
        window.resizeTo(width);
    },
    
    htmlOffsetHeight: function()
    {
        return document.getElementsByTagName("html")[0].offsetHeight;
    },
    
    addClass: function(itemID, itemClass)
    {
        document.getElementById(itemID).addStyleClass(itemClass);
    },
    
    removeClass: function(itemID, itemClass)
    {
        document.getElementById(itemID).removeStyleClass(itemClass);
    },
    
    setAttribute: function(itemID, attrName, attrValue)
    {
        document.getElementById(itemID).setAttribute(attrName, attrValue);
    },
    
    setTitle: function(newTitle)
    {
        document.title = newTitle;
    },
    
    focusItem: function(itemID)
    {
        var ele = document.getElementById(itemID);
        ele.focus();
        ele.selectionStart = 0;
        ele.selectionEnd = ele.value.length;
    },
    
    trySubmit: function(button)
    {
        var computedStyle = window.getComputedStyle(button);
        if (button.type != "submit" || computedStyle.visibility === "hidden" || computedStyle.display === "none")
            return false;
        button.click();
        return true;
    },

    keyDown: function(event)
    {
        if (event.target.tagName === "TEXTAREA" || event.keyIdentifier !== "Enter")
            return;

        var buttons = document.getElementsByTagName("button");
        for (var i = 0; i < buttons.length; ++i) {
            if (DialogController.trySubmit(buttons[i])) {
                event.preventDefault();
                return;
            }
        }

        var inputButtons = document.getElementsByTagName("input");
        for (var i = 0; i < inputButtons.length; ++i) {
            if (DialogController.trySubmit(inputButtons[i])) {
                event.preventDefault();
                return;
            }
        }
    },

    contextMenu: function(event)
    {
        if (event.target.tagName === "TEXTAREA")
            return;

        if (event.target.tagName === "INPUT" && (event.target.type === "password" || event.target.type === "text"))
            return;

        event.preventDefault();
    },

    pageLoaded: function()
    {
        var query = document.location.search;
        if (query) {
            query = query.substr(1);
            args = query.split("&");
            for (var i = 0; i < args.length; i++) {
                var nameValue = args[i].split("=");
                pageArguments[nameValue[0]] = nameValue[1];
            }
        }
        
        document.body.addEventListener("keydown", DialogController.keyDown);
        document.addEventListener("contextmenu", DialogController.contextMenu);
        
        var hideList;
        if (navigator.platform == "Win32")
            hideList = document.getElementsByClassName("mac");
        else
            hideList = document.getElementsByClassName("windows");
        
        for (var i = 0; i < hideList.length; i++)
            hideList[i].style.display = "none";
            
        DialogController.localize();
    },
    
    UIString: function(string)
    {
        if (!window.localizedStrings)
            return string;
        if (string in window.localizedStrings)
            string = window.localizedStrings[string];
        else {
            console.error("Localized string \"" + string + "\" not found.");
            string = "LOCALIZED STRING NOT FOUND";
        }
        return string;
    },

    loadLocalizedStrings: function(controller)
    {
        var localizedStringsURL;
        if (controller.localizedStringsURL)
            localizedStringsURL = controller.localizedStringsURL();
        if (!localizedStringsURL && pageArguments["lang"])
            localizedStringsURL = pageArguments["lang"] + ".lproj/localizedStrings.js";
        if (!localizedStringsURL)
            localizedStringsURL = "en.lproj/localizedStrings.js";
        document.write("<script type='text/javascript' charset='utf-8' src='" + localizedStringsURL + "'></" + "script>");
    },
    
    localize: function()
    {
        var elements = document.getElementsByClassName("l12n");
        for (var i = 0; i < elements.length; ++i)
            elements[i].innerText = DialogController.UIString(elements[i].innerText);
        var toolTipElements = document.getElementsByClassName("l12n-tooltip");
        for (var i = 0; i < toolTipElements.length; ++i)
            toolTipElements[i].title = DialogController.UIString(toolTipElements[i].title);
    }
}

// JS class inheritance
var JSClass = {
    inherit: function(subclass, baseClass) {
        function inheritance() { }
        inheritance.prototype = baseClass.prototype;

        subclass.prototype = new inheritance();
        subclass.prototype.constructor = subclass;
        subclass.baseConstructor = baseClass;
        subclass.superClass = baseClass.prototype;
    }
}

// Array utilities
var ArrayUtilities = {
    indexOf: function(array, value) {
        var i;
        for (i = 0; i < array.length; i++) {
            if (array[i] == value)
                return i;
        }
        return -1;    
    }
}

var DateUtilities = {
    approximateTimeStringForDuration: function(duration)
    {
        var seconds = duration;
        var minutes = Math.round(seconds / 60);

        if (minutes <= 1)
            return DialogController.UIString("1 minute");
        if (minutes < 60)
            return DialogController.UIString("%@ minutes").format(minutes);

        var hours = Math.round(minutes / 60);
        if (hours == 1)
            return DialogController.UIString("1 hour");
        if (hours < 24)
            return DialogController.UIString("%@ hours").format(hours);

        var days = Math.round(hours / 24);
        if (days == 1)
            return DialogController.UIString("1 day");
        return DialogController.UIString("%@ days").format(days);
    }
};
