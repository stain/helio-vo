
<table style="width: 100%;border-bottom: 1px solid black" class="input_form_table">
  
  <tr  >
    <td style="vertical-align:middle;" >
      
<img class="block_img" style=" padding: 0; float: left; margin: 10px;" src="${resource(dir:'images/helio',file:'block.png')}" />
      
    </td>
    <td>
      <table>

        <tr>
          <td>
            <div class="message"><b>Step 1</b><br/>Select a mission of interest.</div>
            <label>Mission:</label>
            <select id="input_mission">
              <option> </option>
              <option value="ACE">ACE</option>
              <option value="WIND">WIND</option>
              <option value="ULYSSES">Ulysses</option>
              <option value="STA">STEREO-A</option>
              <option value="STB">STEREO-B</option>
            </select>
            </td>

          
            
          
          
          </tr>
          <tr>
          <td>
            <div class="message"><b>Step 2</b><br/>Select an instrument.</div>
            <label>Instrument</label>
            <select style="min-width: 15px" id="input_instrument">
              <option> </option>
      
            </select>
          
            

          </td>
            </tr>
          <tr>
          <td >
            <label>Measurement Type:</label>
            <input disabled="disabled" id="input_measurement" type="text"/>
          
          </td>
          
            </tr>
          <tr>
           <td>
             <div class="message"><b>Step 3</b><br/>Select the function to evaluate and the fill in the relevant parameters.</div>
            <label>Function:</label>

            <select id="input_function">
           
            </select>
           </td>
        </tr>
         <tr>
           <td>
             (Argument: 
             <select id="input_argument">
               <option> </option>
             </select>
             <select id="input_operator">
             <option><</option>
             <option>></option>
             <option>=</option>
             </select>
             <input id="input_condition" size="5"  type="text" value=""/>
             , Average Time: <input id="input_average_time" size="5" type="text" value=""/>sec, Time Window: <input  id="input_time_window" size="5" type="text" value=""/>sec )
           </td>
        
        </tr>
      </table>
    </td>
   
  </tr><tr>
    <td></td>
    <td style="margin-top: 15px">
      <div class="message"><b>Step 4</b><br/>Revise the equation before accepting.(currently only displayed as pql)</div>
      <label>Expression(beta): </label> <b><span id="input_expression"> </span></b>

    </td>
   
  </tr>

  </table>
 
