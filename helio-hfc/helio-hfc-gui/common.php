<?php
require_once("functions.php");
// get min and max date from the database
$sql_query = "SELECT MIN(DATE_OBS) AS MINDATE, MAX(DATE_OBS) AS MAXDATE FROM OBSERVATIONS";
$res = execute_query($sql_query);
$mindate = $res['MINDATE'][0];
$maxdate = $res['MAXDATE'][0];
$tmp = getdate(strtotime($mindate));
$minyear = $tmp['year'];
$tmp = getdate(strtotime($maxdate));
$maxyear = $tmp['year'];
?>
	<script type="text/javascript">
		$(function(){
			// Widget Tabs for the query form
			$('#tabs_simple').tabs();

			// Accordion output options
			$("#accordion_output_options").accordion({
				collapsible: true,
				autoHeight: false,
				header: "h3"
			});
			// make the accordion completely close
			$("#accordion_output_options").accordion("activate", false);

			// Date selection form
			//var dates = $( "#from, #to" ).dateplustimepicker({
			var dates = $( "#from, #to" ).datetimepicker({
			dateFormat: 'yy-mm-dd',
			timeFormat: 'hh:mm',
			maxDate: "<?php echo $maxdate; ?>",
			minDate: "<?php echo $mindate; ?>",
			separator: 'T',
			yearRange: "<?php echo $minyear.':'.$maxyear; ?>",
			changeYear: true,
			changeMonth: true,
			numberOfMonths: 1,
			onSelect: function( selectedDate ) {
				var option = this.id == "from" ? "minDate" : "maxDate",
					instance = $(this).data( "datepicker" );
				date = $.datepicker.parseDate(
					instance.settings.dateFormat ||
					$.datepicker._defaults.dateFormat,
					selectedDate, instance.settings );
				dates.not(this).datepicker( "option", option, date );
				// modification of the other date
				if (this.id == "from") {
					var begindt = new Date($('#from').datepicker("getDate"));
					var enddt = new Date(begindt.getTime() + (1000*60*60*24*15));
					$('#to').datepicker("setDate", $.datepicker.formatDate('yy-mm-dd', enddt));
				}
/*				else {
					var enddt = new Date($('#to').datepicker("getDate"));
					var begindt = new Date(enddt.getTime() - (1000*60*60*24*15));
					$('#from').datepicker("setDate", $.datepicker.formatDate('yy-mm-dd', begindt));
				} */
				// modify duration value
				// get date timestamp values
				var begindt = $.datepicker.formatDate('@', $('#from').datepicker("getDate"));
				var enddt = $.datepicker.formatDate('@', $('#to').datepicker("getDate"));
				var duration = (enddt-begindt)/(1000*60*60*24);
				if (duration > 60) duration = 60;
				//if (duration < 0) duration = 0;
				var begindt2 = new Date($('#from').datepicker("getDate"));
				enddt = new Date(begindt2.getTime() + ((1000*60*60*24)*duration));
				$('#to').datepicker("setDate", $.datepicker.formatDate('yy-mm-dd', enddt));
				document.getElementById('duration').value = duration;
			}
			});

			// end_date change if duration change
			$("#duration").change(function() {
				var duration = document.getElementById('duration').value;
				if (duration > 60) duration = 60;
				if (duration < 0) duration = 0;
				document.getElementById('duration').value = duration;
				var begindt = new Date($('#from').datepicker("getDate"));
				var enddt = new Date(begindt.getTime() + ((1000*60*60*24)*duration));
				$('#to').datepicker("setDate", $.datepicker.formatDate('yy-mm-dd', enddt));
				$('#from').datepicker( "option", "maxDate", $.datepicker.formatDate('yy-mm-dd', enddt) );
			});

			function toggle_win_ext_criteria_feat(feat, win_id) {
				var butstat = document.getElementById(feat).checked;
				if (butstat == true) {
					$(win_id).show();
				} else {
					$(win_id).hide();
				}
			}
			// initial state for each extended criteria win
			toggle_win_ext_criteria_feat('fil', '#win_ext_criteria_fil');
			toggle_win_ext_criteria_feat('pro', '#win_ext_criteria_pro');
			toggle_win_ext_criteria_feat('ar', '#win_ext_criteria_ar');
			toggle_win_ext_criteria_feat('ch', '#win_ext_criteria_ch');
			toggle_win_ext_criteria_feat('sp', '#win_ext_criteria_sp');
			toggle_win_ext_criteria_feat('t3', '#win_ext_criteria_t3');
			$('#fil').click(function(){
				toggle_win_ext_criteria_feat('fil', '#win_ext_criteria_fil');
			});
			$('#pro').click(function(){
				toggle_win_ext_criteria_feat('pro', '#win_ext_criteria_pro');
			});
			$('#ar').click(function(){
				toggle_win_ext_criteria_feat('ar', '#win_ext_criteria_ar');
			});
			$('#ch').click(function(){
				toggle_win_ext_criteria_feat('ch', '#win_ext_criteria_ch');
			});
			$('#sp').click(function(){
				toggle_win_ext_criteria_feat('sp', '#win_ext_criteria_sp');
			});
			$('#t3').click(function(){
				toggle_win_ext_criteria_feat('t3', '#win_ext_criteria_t3');
			});

			function switch_win_feat_criteria(feat) {
				switch(feat) {
					case 'fil':
						var feat_criteria = document.getElementById('fil_criter_select').value;
						if (feat_criteria == "Length") $('#win_fil_length').show();
						else $('#win_fil_length').hide();
						if (feat_criteria == "Orientation") $('#win_fil_orientation').show();
						else $('#win_fil_orientation').hide();
						if (feat_criteria == "Area") $('#win_fil_area').show();
						else $('#win_fil_area').hide();
						if (feat_criteria == "Area") $('#win_fil_area').show();
						else $('#win_fil_area').hide();
						break;
					case 'pro':
						var feat_criteria = document.getElementById('pro_criter_select').value;
						if (feat_criteria == "Height") $('#win_pro_height').show();
						else $('#win_pro_height').hide();
						if (feat_criteria == "Delta latitude") $('#win_pro_delta_lat').show();
						else $('#win_pro_delta_lat').hide();
						if (feat_criteria == "Max. intensity") $('#win_pro_maxint').show();
						else $('#win_pro_maxint').hide();
						break;
					case 'ar':
						var feat_criteria = document.getElementById('ar_criter_select').value;
						if (feat_criteria == "Area") $('#win_ar_area').show();
						else $('#win_ar_area').hide();
						if (feat_criteria == "Max. intensity") $('#win_ar_maxint').show();
						else $('#win_ar_maxint').hide();
						break;
					case 'ch':
						var feat_criteria = document.getElementById('ch_criter_select').value;
						if (feat_criteria == "Area") $('#win_ch_area').show();
						else $('#win_ch_area').hide();
						if (feat_criteria == "Width") $('#win_ch_width').show();
						else $('#win_ch_width').hide();
						break;
					case 'sp':
						var feat_criteria = document.getElementById('sp_criter_select').value;
						if (feat_criteria == "Area") $('#win_sp_area').show();
						else $('#win_sp_area').hide();
						break;
					case 't3':
						var feat_criteria = document.getElementById('t3_criter_select').value;
						if (feat_criteria == "Max. intensity") $('#win_t3_maxint').show();
						else $('#win_t3_maxint').hide();
						break;
				}
			}
			$('#fil_criter_select').change(function(){
				switch_win_feat_criteria('fil');
			});
			switch_win_feat_criteria('fil');
			$('#pro_criter_select').change(function(){
				switch_win_feat_criteria('pro');
			});
			switch_win_feat_criteria('pro');
			$('#ar_criter_select').change(function(){
				switch_win_feat_criteria('ar');
			});
			switch_win_feat_criteria('ar');
			$('#ch_criter_select').change(function(){
				switch_win_feat_criteria('ch');
			});
			switch_win_feat_criteria('ch');
			$('#sp_criter_select').change(function(){
				switch_win_feat_criteria('sp');
			});
			switch_win_feat_criteria('sp');
			$('#t3_criter_select').change(function(){
				switch_win_feat_criteria('t3');
			});
			switch_win_feat_criteria('t3');

			$("input:submit").button();
			$("input:submit").click(function(){
				//alert user if no feature selected
				var stat=0;
				if (document.getElementById('fil').checked) stat = stat+1;
				if (document.getElementById('pro').checked) stat = stat+1;
				if (document.getElementById('ar').checked) stat = stat+1;
				if (document.getElementById('ch').checked) stat = stat+1;
				if (document.getElementById('sp').checked) stat = stat+1;
				if (document.getElementById('t3').checked) stat = stat+1;
				if (document.getElementById('rs').checked) stat = stat+1;
				//if (document.getElementById('t2').checked) stat = stat+1;
				if (stat == 0) {
					alert("At least one feature type must be selected");
					return false;
				}
			});

			// Button Help (?) elements
			$('#but_help').button();
			$( "#dialog_help" ).dialog({
				autoOpen: false,
				width: 500
			});
			$('#but_help').click(function(){
				$('#dialog_help').dialog('open');
				return false;
			});

			// Buttons from the Output options tab
			$( "#output_format" ).buttonset();
			$( "#map_type" ).buttonset();
		});	
	</script>
	<script type="text/javascript" charset="utf-8" src="js/uploadvot.js"></script>
