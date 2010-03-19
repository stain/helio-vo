//date picker code//

   var now = new Date;
    var MyMonth = new Array("January", "February", "March", "April", "May", 
                            "June", "July", "August", "September", "October", 
                            "November", "December");

	var MyYear = now.getFullYear();
    var Days = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    var DaysinWeek = 7;
    var Today = now.getDate();
    var ThisMonth = now.getMonth();
	var ThisYear = now.getYear();
    var MonthStart = now.setDate(1);
    var AddDays = now.getDate();
    var DayofWeek = now.getDay();
    var DaysinMonth = Days[now.getMonth()];
	var countOfMonth=0;
	var fontface = "MS Sans Serif";
	var fontsize = 1;
	var gHeaderColor = "#5B5B5B";
	var textDate="";
    var sDay=0;
    var sMonth=0;
    var sYear=0;
    var befMonth=0;
    var objDateId="";
    var calColDiv="";
    var calIframeDiv="";
    var scrOfX = 0, scrOfY = 0;
    var mouse_X = 0, mouse_Y = 0;
    

	var highlightBgColor="#AABFBF";

    var ie = document.all?true:false;
	if (!ie) document.captureEvents(Event.MOUSEMOVE)
	//document.onmousemove = getMouseXY;
    
	function show_calendar(dateObj,ind){	
		var sDateValue="";
		
		
		
		objDateId="document."+dateObj;;			
		//asiging text box value to and creating date object.
		if(!isValidObject(eval(objDateId)) || (eval(objDateId).value==null || trim(eval(objDateId).value)==""  || trim(eval(objDateId).value)=="0000/00/00")){
               now = new Date();
               sDateValue="";
          }else{
               now = new Date(eval(objDateId).value);
               sDateValue=eval(objDateId).value
            }

		//alert(countOfMonth);	   
	    Today = now.getDate();
	    ThisMonth = now.getMonth();
		ThisYear = now.getYear();
	    MonthStart = now.setDate(1);
	    AddDays = now.getDate();
	    DayofWeek = now.getDay();
	    DaysinMonth = Days[now.getMonth()];
	  	befMonth=ThisMonth-1;
		MyYear = now.getFullYear();
		countOfMonth=year_between(new Date().getYear(), ThisYear);				
		AddCalendar(ThisMonth,"#ffffff","#eeeeee","CurrMonth");
		AddCalendar(befMonth,"#ffffff","#eeeeee","PrevMonth");
		setAnotherMonth(sDateValue);
		
		TodayDate();		
		update_cal_pos();			
		document.getElementById('CalDiv').style.visibility = "visible";	
		document.getElementById('iFrameDiv').style.height = 200; //222
		document.getElementById('iFrameDiv').style.width = 312;	//400
		document.getElementById('iFrameDiv').style.visibility = "visible";			
	}
   
	 function update_cal_pos(){       
		 getMouseXY();
		document.getElementById('CalDiv').style.left = mouse_X-10 ;
		document.getElementById('CalDiv').style.top  = mouse_Y+scrOfY-2;
		document.getElementById('iFrameDiv').style.left = mouse_X-10 ;
		document.getElementById('iFrameDiv').style.top  = mouse_Y+scrOfY+2;
	 }
	 
	 function setOpenerDate(day,month,year){	 		 	 
	 	eval(objDateId).value=getFormat(Number(day),Number(month),Number(year));
		//eval(objDateId).focus();
	 	document.getElementById('CalDiv').style.visibility = "hidden";
	  	document.getElementById('iFrameDiv').style.visibility = "hidden";
	 }

	//Creating current month details.
    function CreateCurrMonth(TableBG,CellBG){   
    	var currentDate = new Date();     
        // Checks Leap Year
        if ((((MyYear % 4)==0) && ((MyYear % 100)!=0) || ((MyYear % 400)==0)) && (DaysinMonth == 28)) { 
            DaysinMonth = 29;
        }else{
            DaysinMonth = Days[now.getMonth()];
        }
		var CalenderString="";
        CalenderString=CalenderString+("<table border=1 width=143 cellspacing=0 cellpadding=2 bgcolor=" + 
                        TableBG +"><tr><td colspan=7 align=center bgcolor=" + 
                        "'#558DC8'" + "><font face=\"MS Sans Serif\" size=1><b>" + 
                        MyMonth[now.getMonth()] + " " + now.getFullYear() + 
                        "</b></font></td></tr>");
                        
		// form Day row
		CalenderString=CalenderString+("<tr>");
		
		CalenderString = CalenderString + "<TD HEIGHT='10' WIDTH='14%' ALIGN='center'><FONT SIZE='1' ALIGN='center' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'>S</FONT></TD>";
		CalenderString = CalenderString + "<TD HEIGHT='10' WIDTH='14%' ALIGN='center'><FONT SIZE='1' ALIGN='center' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'>M</FONT></TD>";
		CalenderString = CalenderString + "<TD HEIGHT='10' WIDTH='14%' ALIGN='center'><FONT SIZE='1' ALIGN='center' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'>T</FONT></TD>";
		CalenderString = CalenderString + "<TD HEIGHT='10' WIDTH='14%' ALIGN='center'><FONT SIZE='1' ALIGN='center' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'>W</FONT></TD>";
		CalenderString = CalenderString + "<TD HEIGHT='10' WIDTH='14%' ALIGN='center'><FONT SIZE='1' ALIGN='center' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'>T</FONT></TD>";
		CalenderString = CalenderString + "<TD HEIGHT='10' WIDTH='14%' ALIGN='center'><FONT SIZE='1' ALIGN='center' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'>F</FONT></TD>";
		CalenderString = CalenderString + "<TD HEIGHT='10' WIDTH='16%' ALIGN='center'><FONT SIZE='1' ALIGN='center' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'>S</FONT></TD>";
		
		CalenderString=CalenderString+("</tr>");
        
		var weekIndex=0;
		CalenderString=CalenderString+("<tr>");
		weekIndex=weekIndex+1;
        // Build rows in a month
		var bgCol="white";	
		var clickVal="href";	
		var futeDate=false;
		var linkProvider="href=javascript:setOpenerDate('"+AddDays+"','"+now.getMonth()+"','"+now.getYear()+"');";
        for (i = 0; AddDays < DaysinMonth + 1; i++){	              
            if (i < DayofWeek){
                CalenderString=CalenderString+ ("<td></td>");
            }else{
				if(	i%7==0 || (i+1)%7==0)
				{
					//bgCol="#B4EDFA";	
					//highlightBgColor="#B4EDFA";
					//linkProvider="";
					highlightBgColor="#AABFBF";
					bgCol="white";
					linkProvider="href=javascript:setOpenerDate('"+AddDays+"','"+now.getMonth()+"','"+now.getYear()+"');";
				}
				else
				{
					highlightBgColor="#AABFBF";
					bgCol="white";
					linkProvider="href=javascript:setOpenerDate('"+AddDays+"','"+now.getMonth()+"','"+now.getYear()+"');";
				}		
				//To disable link for dates more then current date.						
				if((AddDays > currentDate.getDate()) && (now.getMonth() == currentDate.getMonth()) && now.getYear()==currentDate.getYear() ){    
					linkProvider="";
					//linkProvider="href=javascript:setOpenerDate('"+AddDays+"','"+now.getMonth()+"','"+now.getYear()+"');";
					//bgCol="#D3D3D3";
					//bgCol="white";
					futeDate=true;
					highlightBgColor=bgCol;
					
				}


				if ((i % 7) == 0){
					CalenderString=CalenderString+ ("</tr>");
					CalenderString=CalenderString+("<tr>"); 	               	                         
					if(i!=0)
					{
						weekIndex=weekIndex+1;
					}
				}


                if((AddDays == Today) && (now.getMonth() == ThisMonth) && now.getYear()==ThisYear ){    
				   	CalenderString=CalenderString+ ("<td onmouseover=this.bgColor='"+highlightBgColor+"' onmouseout=this.bgColor='' align=\"center\"><b><a "+linkProvider+" style=cursor:hand><font face=\"MS Sans Serif\" color=red size=1>" + AddDays + "</font></a></b></td>");                  
                }else{
					CalenderString=CalenderString+ ("<td onmouseover=this.bgColor='"+highlightBgColor+"' onmouseout=this.bgColor='"+bgCol+"' align=\"center\" bgcolor="+bgCol+"><font face=\"MS Sans Serif\" size=1>");
					
					if(linkProvider=="")
					{
						if(futeDate)
							{
								CalenderString=CalenderString+ ("<a style=cursor:default  "	+linkProvider+"><font color='#c0c0c0'>" + AddDays + "</font></a>");
							}
						else
							{
							CalenderString=CalenderString+ ("<a style=cursor:hand "+linkProvider+">" + AddDays + "</a>");
							}
						
					}else
					{
						CalenderString=CalenderString+ ("<a style=cursor:hand "+linkProvider+">" + AddDays + "</a>");
					}


					CalenderString=CalenderString+ ("</font></td>");               


                }
				
				 AddDays = AddDays + 1

            }
        }

        CalenderString=CalenderString+ "</tr>";     
        if(weekIndex!= 6)
        {  
          if(DaysinMonth==28 && DayofWeek==0){
          		CalenderString=CalenderString+"<tr bordercolor=\"white\" ><td  align=\"center\"><font face=\"MS Sans Serif\" size=1>&nbsp;</td></tr>";
          		CalenderString=CalenderString+"<tr bordercolor=\"white\" ><td  align=\"center\"><font face=\"MS Sans Serif\" size=1>&nbsp;</td></tr>";
          }else{
         		CalenderString=CalenderString+"<tr bordercolor=\"white\" ><td  align=\"center\"><font face=\"MS Sans Serif\" size=1>&nbsp;</td></tr>";
          }           
        }
        CalenderString=CalenderString+"</table>";
		return CalenderString;
    }

	//Get Date format in mm/dd/yyyy
	function getFormat(dd,mm,yyyy)
	{
		mm=mm+1
		var vDD = (dd.toString().length < 2) ? "0" + dd : dd;
		var vMM = (mm.toString().length < 2) ? "0" + mm : mm;
		return yyyy+"/"+vMM+"/"+vDD;		
		//return yyyy+"-"+vMM+"-"+vDD;
	}


	function cuu(TableBG,CellBG){
			var x=document.getElementById('CurrMonth');		
			x.innerHTML=CreateCurrMonth(TableBG,CellBG);				
	}


	//Method is used to print Specified month details.
    function AddCalendar(addmonth,TableBG,CellBG,lay){			

		now.setDate(1);		
		var NewMonth =  addmonth;
        if (NewMonth > 11){
            NewMonth=(NewMonth-12);
            now.setYear(MyYear + 1);
        }      
        now.setMonth(NewMonth);
        DayofWeek = now.getDay();
        AddDays = now.getDate();       
        DaysinMonth = Days[now.getMonth()];
		var x=document.getElementById(lay);
		x.innerHTML= CreateCurrMonth(TableBG,CellBG);
    }

    // Prints today's date
	function setToday()
	{
		now=new Date();
		ThisMonth = now.getMonth();		
		ThisMonth=ThisMonth;
		befMonth=ThisMonth-1;
		Today = now.getDate();
	    ThisMonth = now.getMonth();
		ThisYear = now.getYear();
	    MonthStart = now.setDate(1);
	    AddDays = now.getDate();
	    DayofWeek = now.getDay();
	    DaysinMonth = Days[now.getMonth()];			
		AddCalendar(ThisMonth,"#ffffff","#eeeeee","CurrMonth");		
		AddCalendar(befMonth,"#ffffff","#eeeeee","PrevMonth");		
		var x=document.getElementById('anotherMonth');
		x.value="0";		
	}
	
	// Method is used to set today's date.
    function TodayDate(){    
		var xxx=new Date();
		var x=document.getElementById('todayId');
		var y="";
		y=y+("<font face='MS Sans Serif' size=1pt ><b><a href='javascript:setToday()'>Today</a> is " + MyMonth[xxx.getMonth()] + " ");
        y=y+(xxx.getDate() + ", ");
        y=y+(xxx.getYear()+"</b></font> <a onclick='javascript:calClose()'></a>");
		x.innerHTML=y;
		return true;
    }
    
	// Method is used to fill combo box.
	function setAnotherMonth(textDate)
	{
		var f="";		
		var selectStatus="";
		
		f=f+"<select name='anotherMonth' id='anotherMonth' class='stylecombo' onchange='javascript:changemonth()'>";
		var gt=new Date();
		var textDate=new Date(textDate);
		var sSelected=""			
		for(var i=0;i<countOfMonth;i++)
		{
			gt=new Date();
			//alert(gt.getMonth());
			gt.setDate(1); // setdate to 1 as if date is 29/30/31... Feb month not coming correctly
			gt.setMonth(gt.getMonth()-i);
			if(MyMonth[gt.getMonth()]==MyMonth[textDate.getMonth()] && gt.getYear() == textDate.getYear())
			{ 			
			  f=f+"<option  value="+(-i)+" selected=selected>"+MyMonth[gt.getMonth()]+"-"+gt.getYear()+"</option>";
			}else{
			  f=f+"<option  value="+(-i)+" >"+MyMonth[gt.getMonth()]+"-"+gt.getYear()+"</option>";
			}			
		}
		f=f+"<option  value=Add >More ...</option>";
		f=f+"</select> ";
		var anot1 = document.getElementById('anot');
		anot1.innerHTML=f;
		return true;
	}
	
	// Change month from select box.
	function changemonth(){	  	
		now=new Date();
		ThisMonth = now.getMonth();		
		ThisMonth=ThisMonth;
		befMonth=ThisMonth-1;
		Today = now.getDate();
	    ThisMonth = now.getMonth();
		ThisYear = now.getYear();
	    MonthStart = now.setDate(1);
	    AddDays = now.getDate();
	    DayofWeek = now.getDay();
	    DaysinMonth = Days[now.getMonth()];			
		var chMonth=now.getMonth();	
		var x=document.getElementById('anotherMonth');
		var selectedVal=x.value;		
		if(selectedVal!="Add"){
			var monthSelected=x.options[x.selectedIndex].value;			
			monthSelected = parseInt(monthSelected);
			monthSelected=monthSelected+chMonth;		
			AddCalendar(monthSelected,"#ffffff","#eeeeee","CurrMonth");
			now = new Date;  		
			AddCalendar(monthSelected-1,"#ffffff","#eeeeee","PrevMonth");
		}else{
			// Code for Add More
			countOfMonth=countOfMonth+12;
			setAnotherMonth(eval(objDateId).value);			
		}
	}

	//Close calendar pop up div.
	function calClose()
	{
	  	document.getElementById('CalDiv').style.visibility = "hidden";
	  	document.getElementById('iFrameDiv').style.visibility = "hidden";
	}
	
	
	function getMouseXY(e){
		if (ie){
			 // grab the x-y pos.s if browser is IE
			if(event && document && document.body)
			{
				mouse_X = event.clientX + document.body.scrollLeft;
				mouse_Y = event.clientY + document.body.scrollTop;
			}
		}
		else{
			// grab the x-y pos.s if browser is NS
			mouse_X = e.pageX;
			mouse_Y = e.pageY;
		}
		if (mouse_X < 0){mouse_X = 0;}
		if (mouse_Y < 0){mouse_Y = 0;}				
	}
	
  window.onscroll=getScrollXY;
  
	function getScrollXY(){	
	  if( typeof( window.pageYOffset ) == 'number' ){
	    //Netscape compliant
	    scrOfY = window.pageYOffset;
	    scrOfX = window.pageXOffset;
	  }else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ){
	    //DOM compliant
	    scrOfY = document.body.scrollTop;
	    scrOfX = document.body.scrollLeft;
	  }else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop )){
	    //IE6 standards compliant mode
	    scrOfY = document.documentElement.scrollTop;
	    scrOfX = document.documentElement.scrollLeft;
	 }	 
  	}
  	
  	//To find diff between years.
  function year_between(year1, year2) {		
		var diffYear=year1-year2;
		diffYear=diffYear*12;
		if(diffYear==0 || diffYear<=25){
		      //same year set defualt value 26
				diffYear=24;	
		}			
	    return (diffYear+2);	
	}

function isValidObject(objToTest){

              if ("undefined" == typeof(objToTest)) return false;
              if (null == objToTest) return false;

              return true;
            }

