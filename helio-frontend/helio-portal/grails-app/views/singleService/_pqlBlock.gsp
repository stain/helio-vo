<div class="block" id="sqlBlock" style="display:true">
 <h4>Current Query:</h4>
 
 <div id="dateRangeHistory">

 </div>
 <div id="dateRangesHistory" style="display:none">
    <h4>Date Ranges selected from results:</h4>
    <ul>
      
    </ul>
 </div>
 
 <div id="serviceHistory">

 </div>
  <div id="tableHistory">

 </div>
   <div id="columnHistory">

 </div>
 <g:if test="${result}">
 ${result?.queryInfo?.contains('QUERY_ERROR')? "<h4 style='color:red' title='"+result?.queryInfo.replace('\'','')+"'>Query Error</h4>":"<h4 style='color:green' title='"+result?.queryInfo.replace('\'','')+"'>Query Success</h4>"}
 </g:if>
 
 <br>
  <div style="display:none">
  
  SELECT=&#36;ALL FROM= <input type="text" style="width:300px" value="" id="fromPQL" />
  WHERE=<input type="text" style="width:300px" value="" id="wherePQL" />
</div>
<g:actionSubmit onclick="mysubmit()" action="querySubmit" value="Search" style="margin:0 10px 0 10px;width:60px;height:30px" />
<input type="button" onclick="resetAll()" name="resetService" value="Reset" style="width:60px;height:30px" class="reset">
</div><!--endblock pqlBlock-->