
/*****
 *
 *    Person constructor
 *
 *****/
function Person(first, last) {
    if ( arguments.length > 0 )
        this.init(first, last);
}

function DialogForm(){
           
}

DialogForm.prototype.init = function(){
    $("#dialog-message").remove();
    var div =$('<div></div>');
    div.attr('id','dialog-message');
    div.attr('title','Date Selection');
    var html = window.workspace.getDivisions()["input_time"];
    div.append(html);
    $("#testdiv").append(div);
    var date_range_list = $("#input_time_range_list");
    date_range_list.html("");

    formatButton($(".custom_button"))
    //$("#input_time_range_button").button({ disabled: true });
    $('#dialog-message').dialog({
        modal: true,
        height:530,
        width:700,
        buttons: {
            Ok: function() {
                var table =$("<table>");
                var itr = 1;

                date_range_list.find("tr").each(function(){
                    var tr = $('<tr></tr>');
                    while(!$("#minDate"+itr).length){
                        itr++;
                        if(itr==100)continue;
                    }
                    tr.append("<td><b>Range "+itr+":</b></td>"+
                        "<td>"+$("#minDate"+itr).val()+"</td>"+
                        "<td>"+$("#minTime"+itr).val()+"</td>"+
                        "<td>--</td><td>"+$("#maxDate"+itr).val()+"</td>"+
                        "<td>"+$("#maxTime"+itr).val()+"</td>");
                    tr.append("<input type='hidden' name='maxDate' value='"+$("#maxDate"+itr).val()+"'>")
                    tr.append("<input type='hidden' name='minDate' value='"+$("#minDate"+itr).val()+"'>")
                    tr.append("<input type='hidden' name='maxTime' value='"+$("#maxTime"+itr).val()+"'>")
                    tr.append("<input type='hidden' name='minTime' value='"+$("#minTime"+itr).val()+"'>")
                    table.append(tr);

                    itr++;
                });
                $("#time_area").html(table);


                $("#time_drop").attr('src','../images/helio/circle_time.png');
                $("#time_drop").addClass('drop_able');

                formatButton($(".custom_button"))

                $("#dialog-message").dialog( "close" );
                $("#dialog-message").remove();
                


            }
        }
    });
        

    
}
/*****
 *
 *    Person init
 *
 *****/
Person.prototype.init = function(first, last) {
    this.first = first;
    this.last  = last;
};

/*****
 *
 *    Person toString
 *
 *****/
Person.prototype.toString = function() {
    return this.first + "," + this.last;
};


/*****
 *
 *    Setup Employee inheritance
 *
 *****/
Employee.prototype = new Person();
Employee.prototype.constructor = Employee;
Employee.superclass = Person.prototype;

/*****
 *
 *    Employee constructor
 *
 *****/
function Employee(first, last, id) {
    if ( arguments.length > 0 )
        this.init(first, last, id);
}

/*****
 *
 *    Employee init
 *
 *****/
Employee.prototype.init = function(first, last, id) {
    // Call superclass method
    Employee.superclass.init.call(this, first, last);

    // init properties
    this.id = id;
}

/*****
 *
 *    Employee toString
 *
 *****/
Employee.prototype.toString = function() {
    var name = Employee.superclass.toString.call(this);

    return this.id + ":" + name;
};


/*****
 *
 *    Setup Manager inheritance
 *
 *****/
Manager.prototype = new Employee;
Manager.prototype.constructor = Manager;
Manager.superclass = Employee.prototype;

/*****
 *
 *    Manager constructor
 *
 *****/
function Manager(first, last, id, department) {
    if ( arguments.length > 0 )
        this.init(first, last, id, department);
}

/*****
 *
 *    Manager init
 *
 *****/
Manager.prototype.init = function(first, last, id, department){
    // Call superclass method
    Manager.superclass.init.call(this, first, last, id);

    // init properties
    this.department = department;
}

/*****
 *
 *    Manager toString
 *
 *****/
Manager.prototype.toString = function() {
    var employee = Manager.superclass.toString.call(this);

    return employee + " manages " + this.department;
}