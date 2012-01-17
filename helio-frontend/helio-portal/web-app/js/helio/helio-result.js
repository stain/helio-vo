/**
 * Classes related to result handling.
 */
(function() {

/**
 * Base class for results
 * @param {helio.AbstractTask} task the task this object is bound to. 
 * @param {String} taskName the actual name of the task variant. 
 */
helio.AbstractResult = function(task, taskName) {
    this.task = task;
    this.taskName = taskName;
};

/**
 * Abstract base function to clear a result
 * Overload this method.
 */
helio.AbstractResult.prototype.clear = undefined;

/**
 * VOTable result
 * @param {helio.TimeRangeTask} task the task this summary is associated with.  
 * @param {String} taskName the actual name of the task variant. 
 * 
 */
helio.VOTableResult = function(task, taskName) {
    helio.AbstractResult.apply(this, [task, taskName]);
    this.dataKey = taskName + ".result";
    this._init();
};

//create TimeRangeDialog as subclass of AbstractDialog
helio.VOTableResult.prototype = new helio.AbstractResult;
helio.VOTableResult.prototype.constructor = helio.VOTableResult;

helio.VOTableResult.prototype._init = function() {
    // format the reponse elements
    // 1. buttons
    formatButton($(".custom_button"));

    // 2. result table
    $(".resultTable").each(function() {fnFormatTable(this.id);});
    
    // 3. enable ok-dialogs
    $(".ok_dialog").dialog({ autoOpen: false, modal: true, width: 600,
        buttons: { "Ok": function() { $(this).dialog("close"); }} 
    });
    
    // 4. make result area collapsible
    $.collapsible("#task_result_area .queryHeader", "group2");

    // 5. connect table info buttons
    $(".table_info_button").click(function() {
        var dialogId = "#" + this.id.substring(0, this.id.length - '_button'.length);
        $(dialogId).dialog('open');
    });
        
    var rowpos = $('#task_result_area').position();
    if(rowpos){
        $('html,body').scrollTop(rowpos.top);
    }

};
})();


